package com.xabaohui.modules.storage.service;

import java.util.List;

import com.xabaohui.modules.storage.dto.CreateOrderDTO;
import com.xabaohui.modules.storage.entity.StorageIoBatch;
import com.xabaohui.modules.storage.entity.StorageIoDetail;
import com.xabaohui.modules.storage.entity.StorageOrder;
import com.xabaohui.modules.storage.entity.StoragePosition;

/**
 * 仓储模块对外统一接口
 * @author yz
 *
 */
public interface StorageService {

	// ----- 出库相关方法  ------
	/**
	 * 直接出库<p/>
	 * <ol>
	 * <li>无需新建批次，该方法会自动关联记录到当日批次(type=daily)</li>
	 * <li>更新StorageProduct，减少总量，减少可用量</li>
	 * <li>更新StoragePosStock，减少总量</li>
	 * <li>新增StorageIoDetail，记录posId, skuId, amount, operator, status=sent</li>
	 * </ol>
	 * @param repoId 仓库Id
	 * @param skuId 商品SkuId
	 * @param amount 出库数量
	 * @param posLabel 商品库位标签
	 * @param operator 操作员Id
	 */
	void directSend(Integer repoId, Integer skuId, Integer amount, String posLabel, Integer operator);
	
	/**
	 * 创建订单<p/>
	 * <ol>
	 * <li>创建StorageOrder, status=created</li>
	 * <li>修改StorageProduct，增加占用库存，减少可用库存（防止超卖）</li>
	 * <li>新增库存占用明细StorageProductOccupy，关联StorageOrder, StorageProduct</li>
	 * </ol>
	 */
	StorageOrder createOrder(CreateOrderDTO request);
	
	/**
	 * 分配订单到库位<p/>
	 * <ol>
	 * <li>创建配货批次StorageIoBatch</li>
	 * <li>更新订单状态StorageOrder.tradeStatus=PROCESSING</li>
	 * <li>根据（下单阶段）库存占用明细StorageProductOccupy，查找可用的库位库存StoragePosStock</li>
	 * <li>创建出库明细StorageIoDetail，status=waiting</li>
	 * <li>更新库位库存StoragePosStock，增加占用量（防止同一库位的商品被分配给多个订单）</li>
	 * </ol>
	 */
	int arrangeOrder(Integer repoId, List<Integer> orderIds, Integer operator);
	
	/**
	 * 取件-完成操作<p/>
	 * 对ioDetailId执行取件操作，完成后返回下一条等待取件的记录；<br/>
	 * @param ioDetailId 出库明细
	 * @param batchId 批次号
	 * @param operator 操作员
	 * @return 如果完成所有取件，返回null
	 */
	StorageIoDetail pickupDoneAndLockNext(Integer ioDetailId, Integer operator);
	
	/**
	 * 取件-锁定记录<p/>
	 * 返回第一条等待取件的记录并锁定，防止其他操作员查看和操作<br/>
	 * 如果意外退出、关机（锁定但未取货），也可以通过调用此方法获取最近一次未完成操作的记录
	 * @param batchId
	 * @param operator
	 * @return 如果完成所有取件，返回null
	 */
	StorageIoDetail pickupLock(Integer batchId, Integer operator);
	
	/**
	 * (全部)缺货<p/>
	 * <ol>
	 * <li>对ioDetailId执行缺货处理；</li>
	 * <li>重新生成等待取件的记录，并关联到当前批次中；</li>
	 * <li>返回下一条等待取件的记录；</li>
	 * </ol>
	 * @param ioDetailId 出库明细
	 * @param operator 操作员
	 * @return 如果完成所有取件，返回null
	 */
	StorageIoDetail lackAll(Integer ioDetailId, Integer operator);

	/**
	 * 部分缺货<p/>
	 * <ol>
	 * <li>ioDetail拆分为两条记录：有货和缺货；</li>
	 * <li>有货的执行取件操作（{@link StorageService#pickupDoneAndLockNext(Integer, Integer)}）；</li>
	 * <li>缺货的执行缺货操作（{@link StorageService#lackAll(Integer, Integer)}）</li>
	 * <li>返回下一条等待取件的记录；</li>
	 * </ol>
	 * @param ioDetailId 出库明细
	 * @param operator 操作员
	 * @param actualAmt 实际取件数量
	 * @return 如果完成所有取件，返回null
	 */
	StorageIoDetail lackPart(Integer ioDetailId, Integer operator, Integer actualAmt);
	
	/**
	 * 完成配货<p/>
	 * 检查是否还存在未完成的配货明细，如果存在，抛出异常<br/>
	 * 更新批次为已完成；<br/>
	 * 更新订单为已出库
	 * @param batchId
	 */
	void finishBatchSend(Integer batchId);
	
	/**
	 * 取消订单
	 * @param orderId
	 */
	void cancelOrder(Integer orderId);
	
	// ----- 入库相关方法  ------
	
	/**
	 * 创建入库单<p/>
	 * 生成入库批次 {@link StorageIoBatch}
	 * @param repoId 仓库Id
	 * @return
	 */
	StorageIoBatch createInStorage(Integer repoId, String memo, Integer operator);
	
	/**
	 * 入库<p/>
	 * 生成入库明细 {@link StorageIoDetail},st=待确认
	 * @param batchId 批次号
	 * @param skuId
	 * @param amount 数量
	 * @param posLabel 库位标签
	 * @param operator 操作员
	 */
	StorageIoDetail addInStorageDetail(Integer batchId, Integer skuId, Integer amount, String posLabel, Integer operator);
	
	/**
	 * 确认入库
	 * <p/>
	 * <ol>
	 * <li>更新入库明细，st=已完成</li>
	 * <li>更新入库批次，st=已完成</li>
	 * <li>增加库位库存</li>
	 * <li>增加总库存</li>
	 * </ol>
	 * 
	 * @param batchId
	 * @param operator
	 */
	void confirmInStorage(Integer batchId, Integer operator);
	
	/**
	 * 取消入库<p/>
	 * <ol>
	 * <li>更新入库明细，st=已取消</li>
	 * <li>更新入库批次，st=已取消</li>
	 * </ol> 
	 * @param batchId
	 * @param operator
	 */
	void cancelInStorage(Integer batchId, Integer operator);
	
	/**
	 * 直接入库<p/>
	 * <ol>
	 * <li>增加入库明细，st=已完成</li>
	 * <li>自动关联当日批次</li>
	 * <li>增加库位库存</li>
	 * <li>增加总库存</li>
	 * </ol>
	 * @param repoId 仓库Id
	 * @param skuId
	 * @param amount 数量
	 * @param posLabel 库位标签
	 * @param operator 操作员
	 */
	void directInStorage(Integer repoId, Integer skuId, Integer amount, String posLabel, Integer operator);
	
	// ----- 库位相关方法 -----
	StoragePosition createStoragePosition(Integer repoId, String posLabel);
}

package com.xabaohui.modules.storage.service;

import com.xabaohui.modules.storage.dto.CreateOrderDTO;
import com.xabaohui.modules.storage.entity.StorageOrder;

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
	 * <li>更新StorageProduct，减少总量</li>
	 * <li>更新StoragePosStock，减少总量</li>
	 * <li>新增StorageIoDetail，记录posId, skuId, amount, operator, status=sent</li>
	 * </ol>
	 * @param repoId 仓库Id
	 * @param skuId 商品SkuId
	 * @param amount 出库数量
	 * @param posId 商品库位Id
	 * @param operator 操作员Id
	 */
	void sentDirect(Integer repoId, Integer skuId, Integer amount, Integer posId, Integer operator);
	
	/**
	 * 创建订单<p/>
	 * <ol>
	 * <li>创建StorageOrder, status=created</li>
	 * <li>创建StorageOrderDetail, status=created</li>
	 * <li>修改StorageProduct，增加占用库存</li>
	 * <li>新增StorageProductOccupy，关联StorageOrder, StorageOrderDetail, StorageProduct</li>
	 * </ol>
	 */
	StorageOrder createOrder(CreateOrderDTO request);
	
	/**
	 * 准备批量发货，批量生成配货单<p/>
	 * <ol>
	 * <li>创建StorageIoBatch</li>
	 * <li>创建StorageIoDetail，status=preparing</li>
	 * <li>更新StoragePosStock，增加占用量</li>
	 * </ol>
	 */
	void prepareBatchSend();
	
	// ----- 入库相关方法  ------
}

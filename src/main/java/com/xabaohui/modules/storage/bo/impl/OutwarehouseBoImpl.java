package com.xabaohui.modules.storage.bo.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.xabaohui.modules.storage.bo.CheckStockBo;
import com.xabaohui.modules.storage.bo.InwarehouseBo;
import com.xabaohui.modules.storage.bo.OutwarehouseBo;
import com.xabaohui.modules.storage.dto.DistrBatchOutOfStockRequestDTO;
import com.xabaohui.modules.storage.dto.DistrBatchOutOfStockRequestDetail;
import com.xabaohui.modules.storage.dto.DistrBatchOutOfStockResponseDTO;
import com.xabaohui.modules.storage.dto.DistrBatchOutOfStockResponseDetail;
import com.xabaohui.modules.storage.dto.StorageCheckDiffReaultDetail;
import com.xabaohui.modules.storage.entity.StorageIoDetail;
import com.xabaohui.modules.storage.entity.StorageIoDetail.IoType;
import com.xabaohui.modules.storage.entity.StorageIoBatch;
import com.xabaohui.modules.storage.entity.StoragePosStock;
import com.xabaohui.modules.storage.entity.StoragePosition;
import com.xabaohui.modules.storage.entity.StorageProduct;
import com.xabaohui.modules.storage.exception.StockNotEnoughException;

/**
 * 出库操作Bo实现
 * 
 * @author cxin
 * 
 */

public class OutwarehouseBoImpl extends WareHouseControlBoImpl implements OutwarehouseBo {
	@Resource
	private InwarehouseBo inwarehouseBo;
	@Resource
	private CheckStockBo checkStockBo;

	@Override
	protected Logger getLogger() {
		return LoggerFactory.getLogger(OutwarehouseBoImpl.class);
	}

	/**
	 * 添加一次出库操作<列表>
	 * 
	 * @param storageIoTask
	 * @param listResponseDetails
	 *            (出库单明细细)
	 */
	public void addOutwarehouseOperate(StorageIoBatch task, List<StorageIoDetail> listStorageIoDetails) {
		if (listStorageIoDetails == null || listStorageIoDetails.isEmpty()) {
			throw new RuntimeException("出库明细单不能为空！！");
		}
		Integer disAmount = 0;
		for (StorageIoDetail ioDetail : listStorageIoDetails) {
			disAmount += ioDetail.getAmount();
			outwarehouseForSku(task.getTaskId(), ioDetail.getSkuId(), ioDetail.getAmount(),
					ioDetail.getPositionId());
		}
		// 更新变动单总量并修改状态为已完成
		inwarehouseBo.updateStorageIoTaskToComplete(task, disAmount);
	}

	/**
	 * addOutwarehouseOperate参数检查
	 * 
	 * @param storageIoTask
	 * @param skuId
	 * @param amount
	 * @param positionId
	 */
	private void parameterCheckOutwarehouse(Integer taskId, Integer skuId, Integer amount, Integer positionId) {
		if (taskId == null || taskId <= 0) {
			throw new RuntimeException("taskId不能为空,0或者负数");
		}
		if (skuId == null || skuId <= 0) {
			throw new RuntimeException("skuId不能为空,0或者负数");
		}
		if (amount == null || amount <= 0) {
			throw new RuntimeException("数量不能为空,0或者负数");
		}
		if (positionId == null || positionId <= 0) {
			throw new RuntimeException("positionId不能为空,0或者负数");
		}
	}

	/**
	 * 执行出库操作<根据skuId+amount>
	 * 
	 * @param skuId
	 * @param amount
	 * @return List<StorageIoDetail>出库明细单
	 */
	@Transactional
	public List<StorageIoDetail> outwarehouseForSku(Integer taskId, Integer skuId, Integer amount) {
		List<StoragePosStock> listPosStocks = this.findBySkuIdOrderByAmount(skuId);// 查询指定SKUID库存明细
		if (listPosStocks == null || listPosStocks.isEmpty()) {
			throw new StockNotEnoughException("当前商品无库存明细！！！");
		}
		int sum = amount;
		int i = 0;
		List<StorageIoDetail> list = new ArrayList<StorageIoDetail>();
		do {
			int curPosAmount = listPosStocks.get(i).getAmount();
			int curAmount = (sum <= curPosAmount) ? sum : curPosAmount;
			StorageIoDetail ioDetail = this.outwarehouseForSku(taskId, skuId, curAmount, listPosStocks
					.get(i).getPositionId());// 出库
			list.add(ioDetail);
			sum -= curAmount;
			i++;
		} while (sum > 0 && i < listPosStocks.size());
		return list;
	}

	/**
	 * 执行出库操作<根据skuId+amount+positionId>
	 * 
	 * @param storageIoTask
	 * @param skuId
	 * @param amount
	 * @param positionId
	 */
	@Transactional
	public StorageIoDetail outwarehouseForSku(Integer taskId, Integer skuId, Integer amount, Integer positionId) {
		parameterCheckOutwarehouse(taskId, skuId, amount, positionId);
		// 修改库存<总,可用数量->不用修改，预扣库存已减去
		// this.updateStorageProductOutwarehouse(skuId, amount);
		// 修改库位库存<数量->
		this.updateStoragePosStockOutwarehouse(skuId, positionId, amount);
		// 添加变动单明细<>
		return this.addStorageIoTaskDetail(taskId, positionId, skuId, amount, IoType.OUT);
	}

	/**
	 * 查询出库明细并封装成配货明细
	 * 
	 * @param taskId
	 * @param lisIoDetails
	 *            (null||List<StorageIoDetail>)
	 * @return
	 */
	public List<DistrBatchOutOfStockResponseDetail> findResponseListDetail(Integer taskId) {
		List<StorageIoDetail> lisIoDetails = storageIoDetailDao.findByTaskId(taskId);
		if (lisIoDetails == null) {// 未查询到配货明细
			return null;
		}
		List<DistrBatchOutOfStockResponseDetail> listResponseDetails = new ArrayList<DistrBatchOutOfStockResponseDetail>();
		for (StorageIoDetail ioDetail : lisIoDetails) {
			DistrBatchOutOfStockResponseDetail response = new DistrBatchOutOfStockResponseDetail();
			response.setSkuCount(ioDetail.getAmount());
			StoragePosition position = storagePositionDao.findOne(ioDetail.getPositionId());
			if (position == null) {
				throw new RuntimeException("库位不存在！！");
			}
			String positionLabel = position.getLabel();
			response.setPosLabel(positionLabel);
			response.setSkuId(ioDetail.getSkuId());
			listResponseDetails.add(response);
		}
		return listResponseDetails;
	}

	/**
	 * 获取请求结果TODO 单独判断listResponseDetails是否为空complete
	 */
	public DistrBatchOutOfStockResponseDTO buildResponseDto(String failReason, Integer taskId) {
		DistrBatchOutOfStockResponseDTO responseDTO = new DistrBatchOutOfStockResponseDTO();
		List<DistrBatchOutOfStockResponseDetail> listResponseDetails = findResponseListDetail(taskId);// 获取配货明细单
		if (listResponseDetails != null && !listResponseDetails.isEmpty() && failReason == null) {
			responseDTO.setSuccess(true);
		} else {
			responseDTO.setSuccess(false);
		}
		responseDTO.setFailReason(failReason);
		responseDTO.setList(listResponseDetails);
		responseDTO.setTaskId(taskId);
		return responseDTO;
	}

	/**
	 * 出错重配：只对变动明细做标记,库存明细做标记
	 * 
	 * @param dbsr
	 */
	public void processAddErrorInfo(DistrBatchOutOfStockRequestDTO dbsr) {
		List<DistrBatchOutOfStockRequestDetail> listRequestDetails = dbsr.getList();
		List<StorageCheckDiffReaultDetail> list = new ArrayList<StorageCheckDiffReaultDetail>();
		for (DistrBatchOutOfStockRequestDetail requestDetail : listRequestDetails) {// 添加出错标记,缺配标记
			StoragePosition position = this.storagePositionDao.findByLabel(requestDetail.getPosLabel());
			if (position == null) {
				throw new RuntimeException("库位不存在！！");
			}
			Integer positionId = position.getPositionId();
			// 更新变动单明细标记
			this.updateStorageIoDetailErrorReturn(dbsr.getTaskId(), positionId, requestDetail.getSkuId(),
					requestDetail.getSkuCount());
			// TODO记录出错数据<添加到盘点差异数据表中>盘点的时候做调整
			list.clear();
			list.add(new StorageCheckDiffReaultDetail(requestDetail.getSkuId(), 0, requestDetail.getSkuCount()));
			checkStockBo.addCheckDiff(list, null, positionId);
			// 添加出错标记
			this.updateStoragePosStockMark(requestDetail.getSkuId(), positionId, "缺货", requestDetail.getSkuCount());
		}

	}

	/**
	 * 出错重配:参数检查
	 * 
	 * @param dbsr
	 * @return taskId
	 */
	public void processCheckParamter(DistrBatchOutOfStockRequestDTO dbsr) {
		if (dbsr == null) {
			throw new RuntimeException("出错重配请求为空！！！");
		}
		if (dbsr.getOperator() == null || dbsr.getOperator() <= 0) {
			throw new RuntimeException("操作员不能为空,0或者负数！");
		}
		Integer taskId = dbsr.getTaskId();
		if (taskId == null || taskId <= 0) {
			throw new RuntimeException("重配请求流水号不能为0或者负数！！！");
		}
		StorageIoBatch storageIoTask = storageIoTaskDao.findOne(taskId);
		if (storageIoTask == null) {
			throw new RuntimeException("配货流水不存在！！！");
		}
	}

	/**
	 * 更新库存:出库 TODO检查数量是否足够 complete
	 * 
	 * @param skuId
	 * @param amount
	 */
	public void updateStorageProductOutwarehouse(Integer skuId, Integer amount) {// 出库更新库存
		checkForPamarater(skuId, amount);
		StorageProduct storageProduct = findStorageProduct(skuId);
		if (amount > storageProduct.getStockOccupy() || amount > storageProduct.getStockAmt()) {
			throw new RuntimeException("出库数量不能大于占用库存和库存");
		}
		storageProduct.setStockAmt(storageProduct.getStockAmt() - amount);// 总库存减少
		storageProduct.setStockOccupy(storageProduct.getStockOccupy() - amount);// 占用库存减少
		updateStorageProduct(storageProduct);
	}

	/**
	 * 更新库存:占用
	 * 
	 * @param skuId
	 * @param amount
	 */
	@Transactional
	public String occupyStock(String outTradeNo, Integer skuId, Integer amount) {// 占用库存更新库存
		checkForPamarater(skuId, amount);
		StorageProduct storageProduct = findStorageProduct(skuId);
		if (storageProduct.getStockAvailabe() < amount) {
			return "占用数量不能大于可用量";
		}
		// TODO 录入占用明细
		storageProduct.setStockAvailabe(storageProduct.getStockAvailabe() - amount);// 可用库存减少
		storageProduct.setStockOccupy(storageProduct.getStockOccupy() + amount);// 占用库存增加
		updateStorageProduct(storageProduct);
		return null;
	}

	/**
	 * 更新库存:取消占用
	 * 
	 * @param skuId
	 * @param amount
	 */
	@Transactional
	public String unoccupyStock(String outTradeNo, Integer skuId, Integer amount) {// 取消占用更新库存
		checkForPamarater(skuId, amount);
		StorageProduct storageProduct = findStorageProduct(skuId);
		if (storageProduct.getStockAvailabe() < amount) {
			return "取消占用数量不能大于占用量";
		}
		// TODO 验证该订单是否存在占用
		// if(!isOccupyExist(outTradeNo)) {
		// return "该订单不存在占用：" + outTradeNo;
		// }
		
		// TODO 更新占用明细表
		storageProduct.setStockAvailabe(storageProduct.getStockAvailabe() + amount);// 可用库存增加
		storageProduct.setStockOccupy(storageProduct.getStockOccupy() - amount);// 占用库存减少
		updateStorageProduct(storageProduct);
		return null;
	}
}

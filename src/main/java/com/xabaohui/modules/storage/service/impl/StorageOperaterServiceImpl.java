package com.xabaohui.modules.storage.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import com.xabaohui.modules.storage.bo.CheckStockBo;
import com.xabaohui.modules.storage.bo.InwarehouseBo;
import com.xabaohui.modules.storage.bo.OutwarehouseBo;
import com.xabaohui.modules.storage.constant.ConstantStorageCheckCheckTime;
import com.xabaohui.modules.storage.constant.ConstantStorageIoTaskBizType;
import com.xabaohui.modules.storage.dto.DistrBatchOutOfStockRequestDTO;
import com.xabaohui.modules.storage.dto.DistrBatchOutOfStockRequestDetail;
import com.xabaohui.modules.storage.dto.DistrBatchOutOfStockResponseDTO;
import com.xabaohui.modules.storage.dto.DistrBatchRequestDTO;
import com.xabaohui.modules.storage.dto.DistrBatchRequestDetail;
import com.xabaohui.modules.storage.dto.StorageCheckDiffReaultDetail;
import com.xabaohui.modules.storage.dto.StorageCheckSnapDataDetail;
import com.xabaohui.modules.storage.dto.StorageInwarehouseDataDetail;
import com.xabaohui.modules.storage.entity.StorageCheck;
import com.xabaohui.modules.storage.entity.StorageCheckPlan;
import com.xabaohui.modules.storage.entity.StorageIoDetail;
import com.xabaohui.modules.storage.entity.StorageIoTask;
import com.xabaohui.modules.storage.service.StorageOperaterService;

/**
 * 
 * @author cxin
 * 
 */
public class StorageOperaterServiceImpl implements StorageOperaterService {
	@Resource
	private OutwarehouseBo outwarehouseBo;
	@Resource
	private InwarehouseBo inwarehouseBo;
	@Resource
	private CheckStockBo checkStockBo;

	/**
	 * 请求配货:配货的时候出库,保证数据的准确性
	 */
	public DistrBatchOutOfStockResponseDTO firstDistriApply(DistrBatchRequestDTO requestDto) {
		if (requestDto == null) {
			throw new RuntimeException("配货请求为空!");
		}
		StorageIoTask task = createStorageIoTask(requestDto.getOutTradeNo(), requestDto.getOperateor(), requestDto.getMemo(),
				ConstantStorageIoTaskBizType.OUT_WAREHOUSE);
		List<DistrBatchRequestDetail> listRequestDetails = requestDto.getList();// 获取请求列表
		int totalAmount = 0;// 请求总量
		int disAmount = 0;// 配货总量
		String failReason = null;
		try {
			for (DistrBatchRequestDetail requestDetail : listRequestDetails) {
				totalAmount += requestDetail.getSkuCount();
				disAmount += outwarehouseBo.outwarehouseForSku(task.getTaskId(), requestDetail.getSkuId(),
						requestDetail.getSkuCount());
			}
		} catch (RuntimeException e) {
			failReason = e.getMessage();
		}
		if (failReason != null && disAmount < totalAmount) {
			failReason += ",库存量不足,配货不完全！";
		}
		// 修改变动单总量,修改变动单为已完成
		inwarehouseBo.updateStorageIoTaskToComplete(task, disAmount);
		return outwarehouseBo.buildResponseDto(failReason, task.getTaskId());
	}

	/**
	 * 重新打印配货单
	 */
	public DistrBatchOutOfStockResponseDTO againDistriApply(Integer taskId) {
		return outwarehouseBo.buildResponseDto(null, taskId);
	}

	/**
	 * 缺货补配
	 */
	public DistrBatchOutOfStockResponseDTO errorOutDetailFill(DistrBatchOutOfStockRequestDTO requestDto) {
		outwarehouseBo.processCheckParamter(requestDto);
		// 获取请求
		List<DistrBatchOutOfStockRequestDetail> listreRequestDetails = requestDto.getList();
		List<DistrBatchRequestDetail> list = new ArrayList<DistrBatchRequestDetail>();

		for (DistrBatchOutOfStockRequestDetail requestDetail : listreRequestDetails) {
			list.add(new DistrBatchRequestDetail(requestDetail.getSkuId(), requestDetail.getSkuCount()));
		}
		DistrBatchRequestDTO request = new DistrBatchRequestDTO(requestDto.getOperator(), "缺货补配", list);
		// 缺货补配先占用库存
		occupyStorageProduct(list);
		// 添加缺货补配信息
		outwarehouseBo.processAddErrorInfo(requestDto);
		return firstDistriApply(request);
	}
	
	@Override
	public String occupyStock(String outTradeNo, Integer skuId, Integer amount) {
		return outwarehouseBo.occupyStock(outTradeNo, skuId, amount);
	}

	/**
	 * 占用库存
	 */
	public void occupyStorageProduct(List<DistrBatchRequestDetail> list) {
		for (DistrBatchRequestDetail instance : list) {
			outwarehouseBo.occupyStock(instance.getSkuId(), instance.getSkuCount());
		}
	}

	/**
	 * 取消占用
	 */
	public void removeOccupyStorageProduct(List<DistrBatchRequestDetail> list) {
		for (DistrBatchRequestDetail instance : list) {
			outwarehouseBo.unoccupyStock(instance.getSkuId(), instance.getSkuCount());
		}
	}

	/**
	 * 创建变动单
	 */
	public StorageIoTask createStorageIoTask(String outTradeNo, Integer operator, String memo, String operateType) {
		if (operator <= 0 || operator == null) {
			throw new RuntimeException("skuId不能为0或者负数");
		}
		if (StringUtils.isBlank(memo)) {
			throw new IllegalArgumentException("memo不能为空");
		}
		if (operateType != ConstantStorageIoTaskBizType.IN_WAREHOUSE
				&& operateType != ConstantStorageIoTaskBizType.OUT_WAREHOUSE) {
			throw new RuntimeException("变动单类型不正确");
		}
		return inwarehouseBo.addStorageIoTask(outTradeNo, operator, 0, memo, operateType);
	}

	/**
	 * 批量入库
	 */
	public List<StorageIoDetail> storageInWareHouse(Integer operator, String memo, String operateType,
			List<StorageInwarehouseDataDetail> listDetails) {
		StorageIoTask storageIoTask = createStorageIoTask(operator, memo, operateType);

		List<StorageIoDetail> list = new ArrayList<StorageIoDetail>();
		for (StorageInwarehouseDataDetail data : listDetails) {
			StorageIoDetail detail = new StorageIoDetail();
			BeanUtils.copyProperties(data, detail);
			list.add(detail);
		}
		return inwarehouseBo.addListInwarehouseOperate(storageIoTask, list);
	}

	/**
	 * 单条入库
	 */
	public void storageInWareHouse(Integer taskId, Integer skuId, Integer amount, Integer positionId) {
		inwarehouseBo.addOneInwarehouseOperate(taskId, skuId, amount, positionId);
	}

	/**
	 * 单条入库 修改数量 修改变动单已完成
	 * 
	 * @param storageIoTask
	 * @param amount
	 */
	public void updateStorageIoTaskToComplete(StorageIoTask storageIoTask, Integer amount) {
		inwarehouseBo.updateStorageIoTaskToComplete(storageIoTask, amount);
	}

	/**
	 * 创建盘点计划
	 */
	public StorageCheckPlan createStorageCheckPlan(Integer operator, String memo) {
		return checkStockBo.addStorageCheckPlan(operator, memo);
	}

	/**
	 * 创建盘点
	 */
	public StorageCheck createStorageCheck(Integer planId, Integer operator, Integer positionId, String memo) {
		return checkStockBo.addStorageCheck(positionId, planId, operator, memo);
	}

	/**
	 * 第一次盘点
	 */
	public List<StorageCheckDiffReaultDetail> firstStorageCheck(List<StorageCheckSnapDataDetail> list, Integer checkId) {

		checkStockBo.addCheckSnapData(list, checkId, ConstantStorageCheckCheckTime.FIRST_CHECK);
		return checkStockBo.compareDiff(checkId, ConstantStorageCheckCheckTime.FIRST_CHECK);
	}

	/**
	 * 第二次盘点
	 */
	public List<StorageCheckDiffReaultDetail> secondStorageCheck(List<StorageCheckSnapDataDetail> list, Integer checkId) {

		checkStockBo.addCheckSnapData(list, checkId, ConstantStorageCheckCheckTime.SECOND_CHECK);
		List<StorageCheckDiffReaultDetail> listDetails = checkStockBo.compareDiff(checkId,
				ConstantStorageCheckCheckTime.SECOND_CHECK);
		if (listDetails == null) {
			throw new RuntimeException("两次盘点数据不一致,请重新盘点！！");
		}
		return listDetails;
	}

	/**
	 * 库存调整<单个>
	 */
	public void adjustMentCheckResult(Integer operator, String memo, Integer checkId, Integer skuId, Integer realAmount) {
		checkStockBo.changeStock(operator, memo, checkId, skuId, realAmount);
	}

	/**
	 * 解锁库位<全部调整完,手动>
	 * 
	 * @param operator
	 * @param positionId
	 */
	public void unLockPosition(Integer operator, Integer positionId) {
		checkStockBo.unLockPosition(operator, positionId);
	}
}
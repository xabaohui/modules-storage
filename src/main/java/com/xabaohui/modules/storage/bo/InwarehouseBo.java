package com.xabaohui.modules.storage.bo;

import java.util.List;

import com.xabaohui.modules.storage.entiry.StorageIoDetail;
import com.xabaohui.modules.storage.entiry.StorageIoTask;
import com.xabaohui.modules.storage.entiry.StoragePosition;

public interface InwarehouseBo {

	/**
	 * 批量入库：存在不能入库,则全部不能入库
	 * @param operator
	 * @param memo
	 * @param listDetails
	 * @return
	 */
	 List<StorageIoDetail> addListInwarehouseOperate(StorageIoTask storageIoTask,List<StorageIoDetail> listDetails);
	
	/**
	 * 单个入库
	 * @param storageIoTask
	 * @param ioDetail
	 * @return 
	 */
	 int addOneInwarehouseOperate(Integer taskId, Integer skuId, Integer amount, Integer positionId);

	 /**
	  * 执行入库操作<>
	  * @param taskId
	  * @param skuId
	  * @param amount
	  * @param positionId
	  * @param ioDetailType
	  * @return
	  */
	 int addInwarehouseOperate(Integer taskId, Integer skuId, Integer amount, Integer positionId, String ioDetailType);
	
	 /**
	 * 添加库位库存明细
	 * @param positionId
	 * @param skuId
	 * @param amount
	 */
	 void addStoragePosStock(Integer positionId, Integer skuId, Integer amount);
	
	/**
	 * 添加库存变动单
	 * @param operator
	 * @param amount
	 * @param memo
	 * @param operateType
	 * @return
	 */
	  StorageIoTask addStorageIoTask(Integer operator, Integer amount,String memo,String operateType);
	  
	/**
	 * 添加库位信息
	 * 
	 * @param positionLabel
	 * @param capacity
	 * @param memo
	 */
	StoragePosition addStoragePosition(String positionLabel, Integer capacity,String memo);
			
	/**
	 * 修改库存变动单状态为已完成
	 * @param taskId
	 * @param totalAmount
	 */
	 void updateStorageIoTaskToComplete(StorageIoTask storageIoTask,Integer amount);
}

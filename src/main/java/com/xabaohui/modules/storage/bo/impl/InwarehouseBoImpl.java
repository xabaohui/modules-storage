package com.xabaohui.modules.storage.bo.impl;

import java.util.ArrayList;
import java.util.List;


import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;


import com.xabaohui.modules.storage.bo.InwarehouseBo;
import com.xabaohui.modules.storage.constant.ConstantStorageIoDetailIoDetailType;
import com.xabaohui.modules.storage.constant.ConstantStorageIoTaskBizType;
import com.xabaohui.modules.storage.constant.ConstantStorageIoTaskStatus;
import com.xabaohui.modules.storage.entiry.StorageIoDetail;
import com.xabaohui.modules.storage.entiry.StorageIoTask;
import com.xabaohui.modules.storage.entiry.StoragePosStock;
import com.xabaohui.modules.storage.entiry.StoragePosition;
import com.xabaohui.modules.storage.entiry.StorageProduct;

/**
 * 入库操作Bo实现
 * @author cxin
 * 
 */
public class InwarehouseBoImpl extends WareHouseControlBoImpl implements InwarehouseBo{
	
	@Override
	protected Logger getLogger() {
		return LoggerFactory.getLogger(InwarehouseBoImpl.class);
	}
	
	@Transactional
	/**
	 * 批量入库：存在不能入库,则全部不能入库
	 * @param operator
	 * @param memo
	 * @param listDetails
	 * @return
	 */
	public List<StorageIoDetail> addListInwarehouseOperate(StorageIoTask storageIoTask,List<StorageIoDetail> listDetails) {
		if(storageIoTask==null){
			throw new RuntimeException("库存变动单不存在,不能入库");
		}
		if(listDetails == null || listDetails.isEmpty()) {
			throw new IllegalArgumentException("入库集合listDetails不能为空");
		}
		// 遍历所有记录，检查是否符合录入条件
		List<StorageIoDetail> failList= findFailList(listDetails);
		if(!failList.isEmpty()) {
			return failList;
		}
		// 逐条处理记录，返回记录总数
		Integer totalAmount=0;
		Integer ioAmount=0;
		for (StorageIoDetail storageIoDetail : listDetails) {
			totalAmount+=storageIoDetail.getAmount();
			ioAmount+=addOneInwarehouseOperate(storageIoTask.getTaskId(), storageIoDetail.getSkuId(),storageIoDetail.getAmount(),storageIoDetail.getPositionId());
		}
		// 记录总量修改 complete
		updateStorageIoTaskToComplete(storageIoTask, ioAmount);
		return new ArrayList<StorageIoDetail>();
	}
	
	/**
	 * 单个入库
	 * @param storageIoTask
	 * @param ioDetail
	 * @return 
	 * @return 
	 */
	@Transactional
	public int addOneInwarehouseOperate(Integer taskId, Integer skuId, Integer amount, Integer positionId){
		int disAmount=0;
		String failReason=findFailIoDetail(skuId,amount,positionId);
		if(StringUtils.isBlank(failReason)){//能入库
			disAmount =  addInwarehouseOperate(taskId, skuId, amount, positionId, ConstantStorageIoDetailIoDetailType.IN_WAREHOUSE);
		}else{//不能入库抛出失败原因
			throw new RuntimeException(failReason);
		}
		return disAmount;
	}
	
	private List<StorageIoDetail> findFailList(List<StorageIoDetail> listDetails) {
		List<StorageIoDetail> failList = new ArrayList<StorageIoDetail>();
		String failReason=null;
		for (StorageIoDetail io : listDetails) {
			failReason=findFailIoDetail(io.getSkuId(),io.getAmount(),io.getPositionId());
			if(StringUtils.isNotBlank(failReason)){//不能入库
				failList.add(io);
			}
		}
		return failList;
	}
	
	//return 不能入库失败原因，能入库返回空字符串complete
	private String findFailIoDetail(Integer skuId, Integer amount, Integer positionId){
		if(skuId == null || amount == null || amount <= 0) {
			return "入库数据不正确！！";
		}
		StorageProduct product = this.findStorageProductBySkuId(skuId);
		if (product==null) {
			return StringUtils.EMPTY;//商品不存在能入库
		}
		if (product.getLockFlag() == true) {// 锁定不能入库
			return product.getLockReason();
		}
		StoragePosition position = storagePositionDao.findById(positionId);
		if (position == null) {// 无库位不能入库
			return "库位不存在！！";
		}
		if (position.getPositionIslock() == true) {//已锁定不能入库
			return position.getLockReason();
		}
		return StringUtils.EMPTY;
	}
	
	public int addInwarehouseOperate(Integer taskId, Integer skuId, Integer amount, Integer positionId, String ioDetailType) {
		// 添加变动单明细<>
		this.addStorageIoTaskDetail(taskId, positionId, skuId, amount,ioDetailType);
		// 添加或更新总库存
		StorageProduct product = this.findStorageProductBySkuId(skuId);
		if (product == null) {
			// 库存无记录则添加
			this.addStorageProduct(skuId, amount);
		} else {
			// 库存有记录则更新库存
			this.updateStorageProductInwarehouse(skuId, amount);
		}
		// 添加或者更新库位库存明细
		StoragePosStock posStock = this.findStoragePosStockBySkuIdAndPositionId(skuId, positionId);
		if (posStock == null) {// 不存在对应直接添加
			this.addStoragePosStock(positionId, skuId, amount);
		}else{
			// 存在对应则更新库位库存明细
			this.updateStoragePosStockInwarehouse(skuId, positionId, amount);
		}
		return amount;
	}
	
	/**
	 * 更新库存:入库
	 * @param skuId
	 * @param amount
	 */
	@Transactional
	private void updateStorageProductInwarehouse(Integer skuId, Integer amount){
		StorageProduct storageProduct = findStorageProduct(skuId);
		storageProduct.setStockAmt(storageProduct.getStockAmt() + amount);// 总库存增加
		storageProduct.setStockAvailabe(storageProduct.getStockAvailabe() + amount);// 可用库存增加
		updateStorageProduct(storageProduct);
	}
	
	/**
	 * 添加库存记录
	 * @param skuId
	 * @param amount
	 */
	protected void addStorageProduct(Integer skuId, Integer amount) {
		if (skuId==null||skuId < 0 || skuId == 0) {
			throw new RuntimeException("skuId不能为NULL,0或者负数");
		}
		if (amount==null||amount < 0 || amount == 0) {
			throw new RuntimeException("商品数量不能为NULL,0或者负数");
		}
		List<StorageProduct> list  =  this.findStorageProductListBySkuId(skuId);
		if((list!=null)&&(!list.isEmpty())){
			throw new RuntimeException("存在skuId的库存，不能新增记录！！");
		}
		StorageProduct instance = new StorageProduct();
		instance.setSkuId(skuId);
		instance.setStockAmt(amount);
		instance.setStockAvailabe(amount);
		instance.setStockOccupy(0);
		saveStorageProduct(instance);
	}

	/**
	 * 添加库位库存明细
	 * @param positionId
	 * @param skuId
	 * @param amount
	 */
	public void addStoragePosStock(Integer positionId, Integer skuId, Integer amount) {
		if (skuId==null||skuId < 0 || skuId == 0) {
			throw new RuntimeException("skuId不能为空,0或者负数");
		}
		if (amount==null||amount < 0 || amount == 0) {
			throw new RuntimeException("商品数量不能为空,0或者负数");
		}
		if(positionId==null||positionId<0||positionId==0){
			throw new RuntimeException("库位Id不能为0或者负数");
		}
		StoragePosStock posStock = this.findStoragePosStockBySkuIdAndPositionId(skuId, positionId);
		if(posStock!=null){
			throw new RuntimeException("已存在库存明细");
		}
		StoragePosStock storagePosStock=new StoragePosStock();
		storagePosStock.setPositionId(positionId);
		storagePosStock.setSkuId(skuId);
		storagePosStock.setAmount(amount);
		saveStoragePosStock(storagePosStock);
	}
	
	/**
	 * 添加库存变动单
	 * @param operator
	 * @param amount
	 * @param memo
	 * @param operateType<入库，出库，调整>
	 * @return
	 */
	public  StorageIoTask addStorageIoTask(Integer operator, Integer amount,String memo,String operateType){
		StorageIoTask storageIoTask = processGetIoTask(operator,amount,memo);
		String statusString="";
		if(operateType.equals(ConstantStorageIoTaskBizType.IN_WAREHOUSE)){
			statusString=ConstantStorageIoTaskStatus.IN_WAREHOUSE_ING;//正在入库
		}
		if(operateType.equals(ConstantStorageIoTaskBizType.OUT_WAREHOUSE)){
			statusString=ConstantStorageIoTaskStatus.OUT_WAREHOUSE_ING;//正在出库
		}
		storageIoTask.setBizType(operateType);
		storageIoTask.setStatus(statusString);
		storageIoTaskDao.update(storageIoTask);
		return storageIoTask;
	}
	
	/**
	 * 添加库位信息
	 * @param positionLabel
	 * @param capacity
	 * @param memo
	 */
	public StoragePosition addStoragePosition(String positionLabel, Integer capacity,String memo) {
		if(StringUtils.isBlank(positionLabel)){
			throw new RuntimeException("库位标签不能够为空");
		}
		if (capacity <=0 || capacity == null) {
			throw new RuntimeException("商品数量不能为空,0或者负数");
		}
		StoragePosition position=  this.findStoragePositionByPositionLabel(positionLabel);
		if(position != null ) {
			throw new RuntimeException("当前库位已存在！！");
		}
		StoragePosition instance = new StoragePosition();
		instance.setPositionLabel(positionLabel);
		instance.setPositionCapacity(capacity);
		instance.setMemo(memo);
		return saveStoragePosition(instance);
	}
	
	/**
	 * 修改数量
	 * 修改库存变动单状态为已完成
	 * @param taskId
	 * @param totalAmount
	 */
	public void updateStorageIoTaskToComplete(StorageIoTask storageIoTask,Integer disAmount) {
		storageIoTask.setStatus(ConstantStorageIoTaskStatus.COMPLETE);
		storageIoTask.setAmount(storageIoTask.getAmount()+disAmount);
		updateStorageIoTask(storageIoTask);
	}
}
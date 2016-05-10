package com.xabaohui.modules.storage.bo;

import java.util.List;


import com.xabaohui.modules.storage.dto.StorageCheckDiffReaultDetail;
import com.xabaohui.modules.storage.dto.StorageCheckSnapDataDetail;
import com.xabaohui.modules.storage.entiry.StorageCheck;
import com.xabaohui.modules.storage.entiry.StorageCheckPlan;
import com.xabaohui.modules.storage.entiry.StorageCheckSnap;

public interface CheckStockBo {

	/**
	 * 新增一个盘点计划
	 * 
	 * @param operator
	 * @param posCount
	 * @param memo
	 * @return checkPlan盘点计划对象
	 */
	StorageCheckPlan addStorageCheckPlan(int operator, String memo);

	/**
	 * 添加一条盘点记录
	 * 
	 * @param positionId
	 * @param checkPlanId
	 * @param operator操作员ID
	 */
	StorageCheck addStorageCheck(int positionId, Integer checkPlanId,int operator, String memo);

	/**
	 * 添加盘点快照数据
	 * 
	 * @param list
	 * @param checkId盘点Id
	 * @param checkTime盘点次数
	 *            (一次/二次)
	 */
	void addCheckSnapData(List<StorageCheckSnapDataDetail> list, int checkId,String checkTime);

	/**
	 * 比较差异
	 * 
	 * @param list
	 * @param checkId
	 * @param checkTime
	 * @return null表示两次盘点数据相同,listresult表示盘点差异数据
	 */
	List<StorageCheckDiffReaultDetail> compareDiff(int checkId, String checkTime);

	/**
	 * 添加差异数据<两次盘点之后>
	 * @param list
	 * @param checkId
	 */
	void addCheckDiff(List<StorageCheckDiffReaultDetail> list,Integer checkId,int positionId);
	
	/**
	 *  调整库存
	 * @param operator
	 * @param memo
	 * @param checkId
	 * @param skuId
	 * @param realAmount
	 * @param storageIoTask
	 */
	void changeStock(int operator, String memo, int checkId, int skuId, int realAmount);

	/**
	 *  查询快照数据
	 * @param checkId
	 * @param checkTime
	 * @return
	 */
	List<StorageCheckSnap> findCheckSnapData(Integer checkId, String checkTime);
	
	/**
	 * 解锁库位
	 * @param checkId
	 */
	void unLockPosition(int operator,int positionId);
}

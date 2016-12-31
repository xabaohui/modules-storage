package com.xabaohui.modules.storage.bo.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

import com.xabaohui.modules.storage.bo.CheckStockBo;
import com.xabaohui.modules.storage.bo.InwarehouseBo;
import com.xabaohui.modules.storage.bo.OutwarehouseBo;
import com.xabaohui.modules.storage.constant.ConstantStorageCheckCheckResult;
import com.xabaohui.modules.storage.constant.ConstantStorageCheckCheckTime;
import com.xabaohui.modules.storage.constant.ConstantStorageCheckDiffCheckDiffStatus;
import com.xabaohui.modules.storage.constant.ConstantStorageCheckPlanStatus;
import com.xabaohui.modules.storage.constant.ConstantStorageIoDetailIoDetailType;
import com.xabaohui.modules.storage.dao.StorageCheckDao;
import com.xabaohui.modules.storage.dao.StorageCheckPlanDao;
import com.xabaohui.modules.storage.dto.StorageCheckDiffReaultDetail;
import com.xabaohui.modules.storage.dto.StorageCheckSnapDataDetail;
import com.xabaohui.modules.storage.entity.StorageCheck;
import com.xabaohui.modules.storage.entity.StorageCheckDiff;
import com.xabaohui.modules.storage.entity.StorageCheckDiffAdjust;
import com.xabaohui.modules.storage.entity.StorageCheckPlan;
import com.xabaohui.modules.storage.entity.StorageCheckSnap;
import com.xabaohui.modules.storage.entity.StoragePosStock;

/**
 * 盘点
 * 
 * @author cxin
 * 
 */

public class CheckStockBoImpl extends WareHouseControlBoImpl implements CheckStockBo {
	@Resource
	private StorageCheckPlanDao storageCheckPlanDao;
	@Resource
	private InwarehouseBo inwarehouseBo;
	@Resource
	private OutwarehouseBo outwarehouseBo;
	@Resource
	private StorageCheckDao storageCheckDao;

	protected Logger getLogger() {
		return LoggerFactory.getLogger(CheckStockBoImpl.class);
	}

	/**
	 * 新增一个盘点计划<无明确计划> <无明确计划盘点，根据具体盘点而定>
	 * 
	 * @param operator
	 * @param posCount
	 * @param memo
	 * @return checkPlan盘点计划对象
	 */
	public StorageCheckPlan addStorageCheckPlan(int operator, String memo) {
		if (operator < 0 || operator == 0) {
			throw new IllegalArgumentException("操作员Id不能为0或者负数！！");
		}
		StorageCheckPlan checkPlan = new StorageCheckPlan();
		checkPlan.setUserId(operator);
		checkPlan.setPosCount(0);
		checkPlan.setStatus(ConstantStorageCheckPlanStatus.PLAN_STAING);
		checkPlan.setMemo(memo);
		saveCheckPlane(checkPlan);
		return checkPlan;
	}

	/**
	 * 新增一个盘点计划<明确计划> <有明确计划盘点哪些库位,根据要盘点的库位创建多个盘点>
	 * 
	 * @param operator
	 * @param listPositionId
	 * @param memo
	 * @return
	 */
	public StorageCheckPlan addStorageCheckPlan(int operator, List<Integer> listPositionId, String memo) {
		if (operator < 0 || operator == 0) {
			throw new IllegalArgumentException("操作员Id不能为0或者负数！！");
		}
		StorageCheckPlan checkPlan = addStorageCheckPlan(operator, memo);
		for (Integer positionId : listPositionId) {// 循环创建多个盘点
			addStorageCheck(positionId, checkPlan.getPlanId(), operator, memo);
		}
		return checkPlan;
	}

	/**
	 * 添加一条盘点记录 盘点计划的数量根据盘点的数量来改变 不使用盘点计划，直接开始盘点
	 * 
	 * @param positionId
	 * @param checkPlanId
	 *            (checkPlanId/null)
	 * @param operator操作员ID
	 */
	@Transactional
	public StorageCheck addStorageCheck(int positionId, Integer checkPlanId, int operator, String memo) {
		// 检查盘点库位否已锁定
		if (positionId <= 0) {
			throw new IllegalArgumentException("库位Id不能为0或者负数！！");
		}
		StorageCheckPlan checkPlan = null;
		if (checkPlanId != null) {
			checkPlan = storageCheckPlanDao.findById(checkPlanId);
			if (checkPlan == null) {
				throw new RuntimeException("所属盘点计划不存在！！");
			}
			// 修改盘点计划数量
			checkPlan.setPosCount(checkPlan.getPosCount() + 1);
			updateStorageCheckPlan(checkPlan);
		}
		StorageCheck storageCheck = new StorageCheck();
		storageCheck.setPlanId(checkPlanId);
		storageCheck.setPositionId(positionId);
		storageCheck.setCheckResult(ConstantStorageCheckCheckResult.STAING_CHECK);
		storageCheck.setUserId(operator);
		storageCheck.setMemo(memo);
		saveStorageCheck(storageCheck);
		// 锁定库位
		this.lockStoragePosition(operator, positionId, "operator:" + operator + "checking");
		return storageCheck;
	}

	/**
	 * 添加盘点快照数据
	 * 
	 * @param list
	 * @param checkId盘点Id
	 * @param checkTime盘点次数
	 *            (一次/二次)
	 * 
	 */
	public void addCheckSnapData(List<StorageCheckSnapDataDetail> list, int checkId, String checkTime) {
		if (list == null || list.isEmpty()) {
			throw new RuntimeException("盘点数据为空！！！");
		}
		if (checkId < 0 || checkId == 0) {
			throw new RuntimeException("盘点Id不能为0或者负数");
		}
		StorageCheck storageCheck = storageCheckDao.findById(checkId);
		if (storageCheck == null) {
			throw new RuntimeException("盘点id不存在！！！");
		}
		if (!checkTime.equals(ConstantStorageCheckCheckTime.FIRST_CHECK)
				&& !checkTime.equals(ConstantStorageCheckCheckTime.SECOND_CHECK)) {
			throw new RuntimeException("盘点次数输入有误，只能为第一次或者第二次");
		}
		for (StorageCheckSnapDataDetail storageCheckSnapDataDetail : list) {
			int checkSnapNum = storageCheckSnapDataDetail.getSnapNum();
			int skuId = storageCheckSnapDataDetail.getSkuId();
			if (checkSnapNum < 0) {
				throw new RuntimeException("商品数量不能为负");
			}
			if (skuId < 0 || skuId == 0) {
				throw new RuntimeException("商品skuId不能为负或者0");
			}
			StorageCheckSnap storageCheckSnap = new StorageCheckSnap();
			storageCheckSnap.setSkuId(skuId);
			storageCheckSnap.setSnapNum(checkSnapNum);
			storageCheckSnap.setCheckId(checkId);
			storageCheckSnap.setCheckTime(checkTime);
			saveStorageCheckSnap(storageCheckSnap);
		}
	}

	/**
	 * 比较差异:存在差异，进行二次盘点,比较两次盘点的数据，两次数据不同则清空数据再次盘点
	 * 
	 * @param list
	 * @param checkId
	 * @param checkTime
	 * @return null表示两次盘点数据相同,listresult表示盘点差异数据
	 */
	@Transactional
	public List<StorageCheckDiffReaultDetail> compareDiff(int checkId, String checkTime) {
		if (checkId < 0 || checkId == 0) {
			throw new RuntimeException("盘点Id不能为0或者负数");
		}
		if (StringUtils.isBlank(checkTime)) {
			throw new RuntimeException("盘点位次不能为空");
		}
		StorageCheck storageCheck = storageCheckDao.findById(checkId);
		if (storageCheck == null) {
			throw new RuntimeException("盘点id不存在！！！");
		}
		Integer positionId = storageCheck.getPositionId();
		if (positionId == null) {
			throw new RuntimeException("盘点未指明盘点库位！！！");
		}
		if (!checkTime.equals(ConstantStorageCheckCheckTime.FIRST_CHECK)
				&& !checkTime.equals(ConstantStorageCheckCheckTime.SECOND_CHECK)) {
			throw new RuntimeException("盘点位次只能为第一次或者第二次");
		}
		List<StorageCheckDiffReaultDetail> listResult = null;
		if (checkTime.equals(ConstantStorageCheckCheckTime.SECOND_CHECK)) {// 第二次，只比较快照数据
			List<StorageCheckSnap> listFirst = findCheckSnapData(checkId, ConstantStorageCheckCheckTime.FIRST_CHECK);
			List<StorageCheckSnap> listSecond = findCheckSnapData(checkId, ConstantStorageCheckCheckTime.SECOND_CHECK);
			if (compare(listFirst, listSecond)) {// 相等
				listResult = compareFirstDetail(checkId);// 获取差异数据
				this.addCheckDiff(listResult, checkId, storageCheck.getPositionId());// 添加盘点差异数据
				return listResult;
			} else {// 不等则清空盘点表
				clearnCheckSnapData(checkId);
				// TODO通知重新盘点 complete
				return null;
			}
		} else {// 第一次
			listResult = compareFirstDetail(checkId);
			// 如果无差异则要解锁库位 complete
			if (listResult.isEmpty() || listResult == null) {// 无差异
				StorageCheck check = storageCheckDao.findById(checkId);
				unLockPosition(check.getUserId(), check.getPositionId());// 解锁库位
			}
			return listResult;
		}
	}

	/**
	 * 一次盘点与库存比较
	 * 
	 * @param list盘点数据集
	 * @param checkId
	 * @return listReault差异结果集
	 */
	private List<StorageCheckDiffReaultDetail> compareFirstDetail2(int checkId) {
		StorageCheck storageCheck = storageCheckDao.findById(checkId);
		if (storageCheck == null) {
			throw new RuntimeException("盘点id不存在！！！");
		}
		Integer positionId = storageCheck.getPositionId();
		// 比较结果
		List<StorageCheckDiffReaultDetail> listResault = new ArrayList<StorageCheckDiffReaultDetail>();
		// 把库存封装成StorageCheckSnapDataDetail对象列表
		List<StorageCheckSnapDataDetail> listPosDetails = processGetListPosDetail(positionId);
		// 把快照数据封装成StorageCheckSnapDataDetail对象列表
		List<StorageCheckSnapDataDetail> listSnapDetails = processFindListSnapDetail(checkId);
		// 排序
		Collections.sort(listPosDetails);
		Collections.sort(listSnapDetails);
		int j = 0;
		int i = 0;
		for (;;) {
			if (i >= listSnapDetails.size() && j >= listPosDetails.size()) {// 不存在数据结束循环
				break;
			}
			Integer posSkuId = listPosDetails.get(j).getSkuId();
			Integer posNum = listPosDetails.get(j).getSnapNum();
			if (i >= listSnapDetails.size() && j < listPosDetails.size()) {// 只剩库存数据
				StorageCheckDiffReaultDetail diffData = new StorageCheckDiffReaultDetail(posSkuId, 0, posNum);
				listResault.add(diffData);
				j++;
				continue;
			}
			Integer snapSkuId = listSnapDetails.get(i).getSkuId();
			Integer snapNum = listSnapDetails.get(i).getSnapNum();
			if (j >= listPosDetails.size() && i < listSnapDetails.size()) {// 只剩真实盘点数据
				StorageCheckDiffReaultDetail diffData = new StorageCheckDiffReaultDetail(snapSkuId, snapNum, 0);
				listResault.add(diffData);
				i++;
				continue;
			}

			if (snapSkuId.equals(posSkuId)) {// skuId相同,
				if (snapNum.equals(posNum)) {// 数量相同
				} else {// 数量不同
					StorageCheckDiffReaultDetail diffData = new StorageCheckDiffReaultDetail(snapSkuId, snapNum, posNum);
					listResault.add(diffData);
				}
				i++;
				j++;
				continue;
			} else {// skuId不同
				if (snapSkuId < posSkuId) {// 真实数据存在，库存不存在
					StorageCheckDiffReaultDetail diffData = new StorageCheckDiffReaultDetail(snapSkuId, snapNum, 0);
					listResault.add(diffData);
					i++;
					continue;
				} else {// 真实数据不存在，库存存在
					StorageCheckDiffReaultDetail diffData = new StorageCheckDiffReaultDetail(posSkuId, 0, posNum);
					listResault.add(diffData);
					j++;
					continue;
				}
			}
		}
		return listResault;
	}

	/**
	 * 一次盘点与库存比较
	 * 
	 * @param list盘点数据集
	 * @param checkId
	 * @return listReault差异结果集
	 */
	private List<StorageCheckDiffReaultDetail> compareFirstDetail(int checkId) {
		StorageCheck storageCheck = storageCheckDao.findById(checkId);
		if (storageCheck == null) {
			throw new RuntimeException("盘点id不存在！！！");
		}
		Integer positionId = storageCheck.getPositionId();
		// 比较结果
		List<StorageCheckDiffReaultDetail> listResault = new ArrayList<StorageCheckDiffReaultDetail>();
		// 把库存封装成StorageCheckSnapDataDetail对象列表
		List<StorageCheckSnapDataDetail> listPosDetails = processGetListPosDetail(positionId);
		// 把快照数据封装成StorageCheckSnapDataDetail对象列表
		List<StorageCheckSnapDataDetail> listSnapDetails = processFindListSnapDetail(checkId);
		// 排序
		Collections.sort(listPosDetails);
		Collections.sort(listSnapDetails);
		int j = 0;
		int i = 0;
		while (i < listSnapDetails.size() && j < listPosDetails.size()) {
			Integer posSkuId = listPosDetails.get(j).getSkuId();
			Integer posNum = listPosDetails.get(j).getSnapNum();
			Integer snapSkuId = listSnapDetails.get(i).getSkuId();
			Integer snapNum = listSnapDetails.get(i).getSnapNum();

			// skuId和数量都相同
			if (snapSkuId.equals(posSkuId) && snapNum.equals(posNum)) {
				i++;
				j++;
				continue;
			}
			// skuId相同但数量不同
			if (snapSkuId.equals(posSkuId) && !snapNum.equals(posNum)) {
				listResault.add(new StorageCheckDiffReaultDetail(snapSkuId, snapNum, posNum));
				i++;
				j++;
				continue;
			}
			// skuId不同：真实数据存在，库存不存在
			if (snapSkuId < posSkuId) {// 真实数据存在，库存不存在
				listResault.add(new StorageCheckDiffReaultDetail(snapSkuId, snapNum, 0));
				i++;
				continue;
			}
			// skuId不同：真实数据不存在，库存存在
			listResault.add(new StorageCheckDiffReaultDetail(posSkuId, 0, posNum));
			j++;
			continue;
		}
		;
		List<StorageCheckSnapDataDetail> listSnap;
		// 如果快照有剩余记录，全部添加到listResults中
		if (i < listSnapDetails.size()) {
			listSnap = listSnapDetails.subList(i, listSnapDetails.size());
			for (StorageCheckSnapDataDetail dataDetail : listSnap) {
				listResault.add(new StorageCheckDiffReaultDetail(dataDetail.getSkuId(), dataDetail.getSnapNum(), 0));
			}
		}
		// 如果实际库存有剩余记录，全部添加到listResults中
		if (j < listPosDetails.size()) {
			// listResault.addAll(restCheckList(listPosDetails, j));
			listSnap = listPosDetails.subList(i, listPosDetails.size());
			for (StorageCheckSnapDataDetail dataDetail : listSnap) {
				listResault.add(new StorageCheckDiffReaultDetail(dataDetail.getSkuId(), 0, dataDetail.getSnapNum()));
			}
		}
		return listResault;
	}

	private List<StorageCheckSnapDataDetail> processFindListSnapDetail(Integer checkId) {
		List<StorageCheckSnapDataDetail> listSnapDetails = new ArrayList<StorageCheckSnapDataDetail>();
		List<StorageCheckSnap> listSnap = this.findByCheckIdAndCheckTime(checkId,
				ConstantStorageCheckCheckTime.FIRST_CHECK);
		if (listSnap == null || listSnap.isEmpty()) {
			return listSnapDetails;
		}
		for (StorageCheckSnap snap : listSnap) {
			listSnapDetails.add(new StorageCheckSnapDataDetail(snap.getSkuId(), snap.getSnapNum()));
		}
		return listSnapDetails;
	}

	private List<StorageCheckSnapDataDetail> processGetListPosDetail(Integer positionId) {
		List<StorageCheckSnapDataDetail> listPosDetails = new ArrayList<StorageCheckSnapDataDetail>();
		List<StoragePosStock> listPos = this.findStoragePosStockByPositionId(positionId);// 数据库记录的明细
		if (listPos == null || listPos.isEmpty()) {
			return listPosDetails;
		}
		for (StoragePosStock storagePosStock : listPos) {
			listPosDetails.add(new StorageCheckSnapDataDetail(storagePosStock.getSkuId(), storagePosStock.getAmount()));
		}
		return listPosDetails;
	}

	/**
	 * 实现比较
	 * 
	 * @param a
	 * @param b
	 * @return true/false
	 */
	private boolean compare(List<StorageCheckSnap> a, List<StorageCheckSnap> b) {
		// private <T extends Comparable<T>> boolean
		// compare(List<StorageCheckSnap> a, List<StorageCheckSnap> b){
		if (a.size() != b.size())
			return false;
		Collections.sort(a);
		Collections.sort(b);
		for (int i = 0; i < a.size(); i++) {
			if (!a.get(i).equals(b.get(i)))
				return false;
		}
		return true;
	}

	/**
	 * 添加差异数据<两次盘点之后,或者配货出错>
	 * 
	 * @param list
	 * @param checkId
	 */
	public void addCheckDiff(List<StorageCheckDiffReaultDetail> list, Integer checkId, int positionId) {
		if (list == null || list.isEmpty()) {
			throw new RuntimeException("盘点差异数据为空");
		}
		for (StorageCheckDiffReaultDetail result : list) {
			StorageCheckDiff checkDiff = new StorageCheckDiff();
			BeanUtils.copyProperties(result, checkDiff);
			checkDiff.setCheckId(checkId);
			checkDiff.setPositionId(positionId);
			checkDiff.setCheckDiffStatus(ConstantStorageCheckDiffCheckDiffStatus.HAVING_DiFF);// 存在差异
			saveStorageCheckDiff(checkDiff);
		}
		if (checkId == null) {
			return;
		}
		// 确定了差异,修改盘点结果
		StorageCheck instance = storageCheckDao.findById(checkId);
		if (instance == null) {
			throw new RuntimeException("盘点不存在");
		}
		instance.setCheckResult(ConstantStorageCheckCheckResult.COMPLETE_CHECK);// 完成盘点
		updateStorageCheck(instance);
	}

	/**
	 * 库存差异调整<单个指定> 根据skuId和checkId对每种商品做不同的调整(自己制定的调整，根据差异数据调整)
	 * 
	 * @param operator
	 * @param memo
	 * @param checkId
	 * @param skuId
	 * @param realAmount
	 * @param storageIoTask
	 */
	public void changeStock(int operator, String memo, int checkId, int skuId, int realAmount) {
		if (operator <= 0) {
			throw new RuntimeException("操作员ID不能0或者负数");
		}
		if (checkId <= 0) {
			throw new RuntimeException("盘点ID不能0或者负数");
		}
		if (realAmount < 0) {
			throw new RuntimeException("数量不能为负数");
		}
		if (StringUtils.isBlank(memo)) {
			throw new RuntimeException("调整说明不能为空");
		}
		StorageCheckDiff checkDiff = this.findStorageCheckDiffByCheckIdAndSkuId(checkId, skuId);
		if (checkDiff == null) {
			throw new RuntimeException("未查到与checkId，skuId对应的盘点差异数据");
		}
		int positionId = storageCheckDao.findById(checkId).getPositionId();
		int amount = realAmount - checkDiff.getStockamount();
		if (amount < 0) {
			outwarehouseBo.addOutwarehouseOperate(checkId, skuId, amount, positionId,
					ConstantStorageIoDetailIoDetailType.CHECK_ADJUST_REDUCE_WAREHOUSE);// 调整
		} else {
			inwarehouseBo.addInwarehouseOperate(checkId, skuId, amount, positionId,
					ConstantStorageIoDetailIoDetailType.CHECK_ADJUST_INCREASE_WAREHOUSE);// 调整
		}
		addCheckDiffAdjust(operator, memo, checkId, skuId, Math.abs(amount));// 添加一条调整记录
		// 差异数据状态修改
		checkDiff.setCheckDiffStatus(ConstantStorageCheckDiffCheckDiffStatus.COMPLETE_DIFF_ADJUST);
		super.updateStorageCheckDiff(checkDiff);
	}

	/**
	 * 添加一条调整记录
	 * 
	 * @param operator
	 * @param memo
	 * @param checkId
	 * @param skuId
	 * @param amount
	 */
	private void addCheckDiffAdjust(Integer operator, String memo, Integer checkId, Integer skuId, Integer amount) {
		if (skuId == null || skuId <= 0) {
			throw new RuntimeException("商品ID不能0或者负数");
		}
		if (amount == null || amount <= 0) {
			throw new RuntimeException("调整数量不能0或者负数");
		}
		StorageCheckDiffAdjust instance = new StorageCheckDiffAdjust();
		instance.setCheckId(checkId);
		instance.setSkuId(skuId);
		instance.setOperator(operator);
		instance.setMemo(memo);
		instance.setAdjustNum(amount);
		saveStorageCheckDiffAdjust(instance);
	}

	/**
	 * 查询快照数据
	 */
	public List<StorageCheckSnap> findCheckSnapData(Integer checkId, String checkTime) {
		if (StringUtils.isBlank(checkTime)) {
			throw new RuntimeException("盘点次数不正确");
		}
		if (!ConstantStorageCheckCheckTime.FIRST_CHECK.equals(checkTime)
				&& !ConstantStorageCheckCheckTime.SECOND_CHECK.equals(checkTime)) {
			throw new RuntimeException("盘点次数只能为第一次，第二次");
		}
		List<StorageCheckSnap> list = this.findByCheckIdAndCheckTime(checkId, checkTime);
		if (list == null || list.isEmpty()) {
			throw new RuntimeException("盘点快照数据为空");
		}
		return list;
	}

	/**
	 * 清除快照数据
	 * 
	 * @param checkId
	 */
	private void clearnCheckSnapData(Integer checkId) {
		List<StorageCheckSnap> list = this.findStorageCheckSnapByCheckId(checkId);
		if (list == null || list.isEmpty()) {
			throw new RuntimeException("快照记录为空");
		}
		for (StorageCheckSnap instance : list) {
			instance.setIsdelete(true);
			updateStorageCheckSnap(instance);
		}
	}

	/**
	 * 解锁库位
	 * 
	 * @param checkId
	 */
	public void unLockPosition(int operator, int positionId) {
		if (operator <= 0) {
			throw new RuntimeException("操作员ID布恩那个为负数或者0");
		}
		if (positionId <= 0) {
			throw new RuntimeException("操作员ID布恩那个为负数或者0");
		}
		this.unLockStoragePosition(operator, positionId);
	}
}

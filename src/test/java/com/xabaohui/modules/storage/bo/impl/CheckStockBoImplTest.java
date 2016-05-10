package com.xabaohui.modules.storage.bo.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;


import com.xabaohui.modules.storage.bo.CheckStockBo;
import com.xabaohui.modules.storage.bo.InwarehouseBo;
import com.xabaohui.modules.storage.bo.OutwarehouseBo;
import com.xabaohui.modules.storage.constant.ConstantStorageCheckCheckTime;
import com.xabaohui.modules.storage.dto.StorageCheckDiffReaultDetail;
import com.xabaohui.modules.storage.dto.StorageCheckSnapDataDetail;
import com.xabaohui.modules.storage.entiry.StorageCheck;
import com.xabaohui.modules.storage.entiry.StoragePosition;
import com.xabaohui.modules.storage.service.StorageOperaterService;



@TransactionConfiguration(transactionManager="transactionManager",defaultRollback=true)
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class CheckStockBoImplTest {
	@Resource
	private OutwarehouseBo outwarehouseBo;
	@Resource
	private InwarehouseBo inwarehouseBo;
	@Resource
	private CheckStockBo checkStockBo;
	@Resource
	private StorageOperaterService storageOperaterService;
	
	private static final Logger log=LoggerFactory.getLogger(CheckStockBoImplTest.class);
	@Test
	public void testCompareDiff() {
		// 添加库位记录
		StoragePosition position1 = inwarehouseBo.addStoragePosition("a1-11-22-07", 200, "测试库位");
		int operator = 444444;
		String memo = "测试入库";
		// 创建盘点
		StorageCheck check = checkStockBo.addStorageCheck(position1.getPositionId(), null, operator, memo);
		// 添加库存明细数据positionId skuId amount
		inwarehouseBo.addStoragePosStock(position1.getPositionId(), 100011, 2);
		inwarehouseBo.addStoragePosStock(position1.getPositionId(), 100012, 2);
		inwarehouseBo.addStoragePosStock(position1.getPositionId(), 100013, 2);
		inwarehouseBo.addStoragePosStock(position1.getPositionId(), 100014, 2);
		// 添加快照数据 list checkId checkTime
		List<StorageCheckSnapDataDetail> list = new ArrayList<StorageCheckSnapDataDetail>();
		list.add(new StorageCheckSnapDataDetail(100011, 1));
		list.add(new StorageCheckSnapDataDetail(100012, 2));
		list.add(new StorageCheckSnapDataDetail(100013, 3));
		list.add(new StorageCheckSnapDataDetail(100014, 4));
		checkStockBo.addCheckSnapData(list, check.getCheckId(),
				ConstantStorageCheckCheckTime.FIRST_CHECK);

		//比较
		List<StorageCheckDiffReaultDetail> listReault =
				checkStockBo.compareDiff(check.getCheckId(), ConstantStorageCheckCheckTime.FIRST_CHECK);
		assertEquals(3, listReault.size());
		for (StorageCheckDiffReaultDetail result : listReault) {
			log.info(result.getSkuId()+"--"+result.getRealamount()+"--"+result.getStockamount());
		}
	}
	@Test
	public void testCompareDiff2() {
		// 添加库位记录
		StoragePosition position1 = inwarehouseBo.addStoragePosition("a2-22-22-07", 200, "测试库位");
		int operator = 444444;
		String memo = "测试入库";
		// 创建盘点
		StorageCheck check = checkStockBo.addStorageCheck(position1.getPositionId(), null, operator, memo);
		// 添加库存明细数据positionId skuId amount
		inwarehouseBo.addStoragePosStock(position1.getPositionId(), 200011, 2);
		inwarehouseBo.addStoragePosStock(position1.getPositionId(), 200012, 2);
		inwarehouseBo.addStoragePosStock(position1.getPositionId(), 200013, 2);
		inwarehouseBo.addStoragePosStock(position1.getPositionId(), 200014, 2);
		// 添加快照数据 list checkId checkTime
		List<StorageCheckSnapDataDetail> list = new ArrayList<StorageCheckSnapDataDetail>();
		list.add(new StorageCheckSnapDataDetail(200011, 1));
		list.add(new StorageCheckSnapDataDetail(200012, 2));
		list.add(new StorageCheckSnapDataDetail(200013, 3));
		checkStockBo.addCheckSnapData(list, check.getCheckId(),
				ConstantStorageCheckCheckTime.FIRST_CHECK);

		//比较
		List<StorageCheckDiffReaultDetail> listReault =
				checkStockBo.compareDiff(check.getCheckId(), ConstantStorageCheckCheckTime.FIRST_CHECK);
		assertEquals(3, listReault.size());
		for (StorageCheckDiffReaultDetail result : listReault) {
			log.info(result.getSkuId()+"--"+result.getRealamount()+"--"+result.getStockamount());
		}
	}
	@Test
	public void testCompareDiff3() {
		// 添加库位记录
		StoragePosition position1 = inwarehouseBo.addStoragePosition("a3-33-22-07", 200, "测试库位");
		int operator = 444444;
		String memo = "测试入库";
		// 创建盘点
		StorageCheck check = checkStockBo.addStorageCheck(position1.getPositionId(), null, operator, memo);
		// 添加库存明细数据positionId skuId amount
		inwarehouseBo.addStoragePosStock(position1.getPositionId(), 300011, 2);
		inwarehouseBo.addStoragePosStock(position1.getPositionId(), 300012, 2);
		inwarehouseBo.addStoragePosStock(position1.getPositionId(), 300013, 2);
		inwarehouseBo.addStoragePosStock(position1.getPositionId(), 300014, 2);
		// 添加快照数据 list checkId checkTime
		List<StorageCheckSnapDataDetail> list = new ArrayList<StorageCheckSnapDataDetail>();
		list.add(new StorageCheckSnapDataDetail(300011, 1));
		list.add(new StorageCheckSnapDataDetail(300012, 2));
		list.add(new StorageCheckSnapDataDetail(300013, 3));
		list.add(new StorageCheckSnapDataDetail(300014, 3));
		list.add(new StorageCheckSnapDataDetail(300015, 3));
		list.add(new StorageCheckSnapDataDetail(300016, 3));
		list.add(new StorageCheckSnapDataDetail(300017, 3));
		checkStockBo.addCheckSnapData(list, check.getCheckId(),
				ConstantStorageCheckCheckTime.FIRST_CHECK);

		//比较
		List<StorageCheckDiffReaultDetail> listReault =
				checkStockBo.compareDiff(check.getCheckId(), ConstantStorageCheckCheckTime.FIRST_CHECK);
		assertEquals(6, listReault.size());
		for (StorageCheckDiffReaultDetail result : listReault) {
			log.info(result.getSkuId()+"--"+result.getRealamount()+"--"+result.getStockamount());
		}
	}
	@Test
	public void testCompareDiff4() {
		// 添加库位记录
		StoragePosition position1 = inwarehouseBo.addStoragePosition("a4-44-22-07", 200, "测试库位");
		int operator = 444444;
		String memo = "测试入库";
		// 创建盘点
		StorageCheck check = checkStockBo.addStorageCheck(position1.getPositionId(), null, operator, memo);
		// 添加库存明细数据positionId skuId amount
		inwarehouseBo.addStoragePosStock(position1.getPositionId(), 400011, 2);
		inwarehouseBo.addStoragePosStock(position1.getPositionId(), 400014, 2);
		inwarehouseBo.addStoragePosStock(position1.getPositionId(), 400016, 2);
		inwarehouseBo.addStoragePosStock(position1.getPositionId(), 400018, 2);
		// 添加快照数据 list checkId checkTime
		List<StorageCheckSnapDataDetail> list = new ArrayList<StorageCheckSnapDataDetail>();
		list.add(new StorageCheckSnapDataDetail(400011, 1));
		list.add(new StorageCheckSnapDataDetail(400012, 2));
		list.add(new StorageCheckSnapDataDetail(400013, 3));
		list.add(new StorageCheckSnapDataDetail(400014, 2));
		list.add(new StorageCheckSnapDataDetail(400015, 3));
		list.add(new StorageCheckSnapDataDetail(400016, 3));
		list.add(new StorageCheckSnapDataDetail(400017, 3));
		checkStockBo.addCheckSnapData(list, check.getCheckId(),
				ConstantStorageCheckCheckTime.FIRST_CHECK);

		//比较
		List<StorageCheckDiffReaultDetail> listReault =
				checkStockBo.compareDiff(check.getCheckId(), ConstantStorageCheckCheckTime.FIRST_CHECK);
		assertEquals(7, listReault.size());
		for (StorageCheckDiffReaultDetail result : listReault) {
			log.info(result.getSkuId()+"--"+result.getRealamount()+"--"+result.getStockamount());
		}
	}
}
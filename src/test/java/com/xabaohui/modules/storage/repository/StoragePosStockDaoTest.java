package com.xabaohui.modules.storage.repository;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xabaohui.modules.storage.BaseTestUnit;
import com.xabaohui.modules.storage.entity.StoragePosStock;

public class StoragePosStockDaoTest extends BaseTestUnit {

	@Autowired
	StoragePosStockDao dao;

	@Test
	public void test() {
		StoragePosStock s = getStoragePosStock();
		dao.save(s);

		List<StoragePosStock> list = dao.findBySkuIdOrderByPositionIdAndAmount(s.getSkuId());
		
		Assert.assertFalse(list.isEmpty());
		for (StoragePosStock s1 : list) {
			Assert.assertEquals(s.getPosStockId(), s1.getPosStockId());
		}
	}

	@Test
	public void test1() {
		StoragePosStock s = getStoragePosStock();
		s.setSkuId(1223444);
		s.setAmount(0);
		dao.save(s);
		
		List<StoragePosStock> list = dao.findBySkuIdOrderByPositionIdAndAmount(s.getSkuId());
		
		Assert.assertFalse(!list.isEmpty());
	}

	@Test
	public void test2() {
		StoragePosStock s = getStoragePosStock();
		s.setPositionId(765932);
		dao.save(s);
		
		List<StoragePosStock> list1 = dao.findStoragePosStockByPositionId(s.getPositionId());
		
		Assert.assertFalse(list1.isEmpty());
		for (StoragePosStock s1 : list1) {
			Assert.assertEquals(s.getPosStockId(), s1.getPosStockId());
		}
	}

	@Test
	public void test3() {
		StoragePosStock s = getStoragePosStock();
		s.setAmount(0);
		s.setPositionId(10069773);
		dao.save(s);
		
		List<StoragePosStock> list1 = dao.findStoragePosStockByPositionId(s.getPositionId());
		
		Assert.assertFalse(!list1.isEmpty());
	}

	@Test
	public void test4() {
		StoragePosStock s = getStoragePosStock();
		s.setPositionId(445566767);
		s.setSkuId(223677);
		dao.save(s);
		
		List<StoragePosStock> list2 = dao.findStoragePosStockBySkuIdAndPositionId(s.getSkuId(), s.getPositionId());
		
		Assert.assertFalse(list2.isEmpty());
		for (StoragePosStock s1 : list2) {
			Assert.assertEquals(s.getPosStockId(), s1.getPosStockId());
		}
	}

	@Test
	public void test5() {
		StoragePosStock s = getStoragePosStock();
		s.setAmount(0);
		s.setPositionId(23668900);
		s.setSkuId(983756);
		dao.save(s);
		
		List<StoragePosStock> list2 = dao.findStoragePosStockBySkuIdAndPositionId(s.getSkuId(), s.getPositionId());
		
		Assert.assertFalse(!list2.isEmpty());
	}

	private StoragePosStock getStoragePosStock() {
		StoragePosStock s = new StoragePosStock();
		s.setAmount(13123);
		s.setGmtCreate(new Date());
		s.setGmtModify(new Date());
		s.setMark("123123");
		s.setPositionId(232323);
		s.setSkuId(1232323);
		return s;
	}
}
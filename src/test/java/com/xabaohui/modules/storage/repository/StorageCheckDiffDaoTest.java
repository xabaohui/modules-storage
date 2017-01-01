package com.xabaohui.modules.storage.repository;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xabaohui.modules.storage.BaseTestUnit;
import com.xabaohui.modules.storage.entity.StorageCheckDiff;

public class StorageCheckDiffDaoTest extends BaseTestUnit {

	@Autowired
	StorageCheckDiffDao dao;

	@Test
	public void test() {
		StorageCheckDiff s = getStorageCheckDiff();
		dao.save(s);

		List<StorageCheckDiff> list = dao.findByCheckId(s.getCheckId());

		List<StorageCheckDiff> list1 = dao.findByCheckIdAndSkuId(s.getCheckId(), s.getSkuId());

		Assert.assertFalse(list.isEmpty());
		Assert.assertFalse(list1.isEmpty());
		for (StorageCheckDiff s1 : list) {
			Assert.assertEquals(s.getCheckId(), s1.getCheckId());
		}
		for (StorageCheckDiff s2 : list1) {
			Assert.assertEquals(s.getCheckId(), s2.getCheckId());
			Assert.assertEquals(s.getSkuId(), s2.getSkuId());
		}

	}

	private StorageCheckDiff getStorageCheckDiff() {
		StorageCheckDiff s = new StorageCheckDiff();
		s.setCheckDiffStatus("xisidndyf");
		s.setCheckId(213123);
		s.setGmtCreate(new Date());
		s.setGmtModify(new Date());
		s.setPositionId(2323);
		s.setRealAmount(33333);
		s.setSkuId(12312312);
		s.setStockAmount(33443);
		return s;
	}
}
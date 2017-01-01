package com.xabaohui.modules.storage.repository;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xabaohui.modules.storage.BaseTestUnit;
import com.xabaohui.modules.storage.entity.StorageCheckSnap;

public class StorageCheckSnapDaoTest extends BaseTestUnit {

	@Autowired
	StorageCheckSnapDao dao;

	@Test
	public void test() {
		StorageCheckSnap s = getStorageCheckSnap();
		dao.save(s);

		List<StorageCheckSnap> list = dao.findByCheckId(s.getCheckId());

		Assert.assertFalse(list.isEmpty());
		for (StorageCheckSnap s1 : list) {
			Assert.assertEquals(s.getCheckId(), s1.getCheckId());
		}

	}

	@Test
	public void test1() {
		StorageCheckSnap s = getStorageCheckSnap();
		s.setIsdelete(true);
		s.setCheckId(434433);
		s.setCheckTime("232323");
		dao.save(s);

		List<StorageCheckSnap> list1 = dao.findByCheckIdAndCheckTimeNotIsDelete(s.getCheckId(), s.getCheckTime());

		Assert.assertFalse(!list1.isEmpty());

	}

	@Test
	public void test2() {
		StorageCheckSnap s = getStorageCheckSnap();
		s.setIsdelete(false);
		s.setCheckId(2333);
		s.setCheckTime("44df22ff");
		dao.save(s);

		List<StorageCheckSnap> list1 = dao.findByCheckIdAndCheckTimeNotIsDelete(s.getCheckId(), s.getCheckTime());

		Assert.assertFalse(list1.isEmpty());

		for (StorageCheckSnap s1 : list1) {
			Assert.assertEquals(s.getCheckId(), s1.getCheckId());
			Assert.assertEquals(s.getCheckTime(), s1.getCheckTime());
		}
	}

	private StorageCheckSnap getStorageCheckSnap() {
		StorageCheckSnap s = new StorageCheckSnap();
		s.setCheckId(123123);
		s.setCheckTime("1231223123");
		s.setGmtCreate(new Date());
		s.setGmtModify(new Date());
		s.setIsdelete(false);
		s.setSkuId(2123123);
		s.setSnapNum(1231231);
		return s;
	}
}
package com.xabaohui.modules.storage.repository;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xabaohui.modules.storage.BaseTestUnit;
import com.xabaohui.modules.storage.entity.StorageCheck;

public class StorageCheckDaoTest extends BaseTestUnit {

	@Autowired
	StorageCheckDao storageCheckDao;

	@Test
	public void save() {
		StorageCheck s = getStorageCheck();
		storageCheckDao.save(s);

		List<StorageCheck> list = storageCheckDao.findByPlanId(s.getPlanId());
		
		Assert.assertFalse(list.isEmpty());
		for (StorageCheck s1 : list) {
			Assert.assertEquals(s.getPlanId(), s1.getPlanId());
		}
	}

	private StorageCheck getStorageCheck() {
		StorageCheck s = new StorageCheck();
		s.setPlanId(10);
		s.setPositionId(20);
		s.setUserId(22);
		s.setCheckResult("ssssssssssssss");
		s.setMemo("xxxxxxxxxxxxx");
		s.setGmtCreate(new Date());
		s.setGmtModify(new Date());
		return s;
	}
}
package com.xabaohui.modules.storage.repository;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xabaohui.modules.storage.BaseTestUnit;
import com.xabaohui.modules.storage.entity.StoragePosLocation;

public class StoragePosLocationDaoTest extends BaseTestUnit {

	@Autowired
	StoragePosLocationDao dao;

	@Test
	public void test() {
		StoragePosLocation s = getStoragePosLocation();
		dao.save(s);
		StoragePosLocation s1 = dao.findOne(s.getPosLocId());

		Assert.assertNotNull(s1);
		Assert.assertEquals(s.getMemo(), s1.getMemo());
	}

	private StoragePosLocation getStoragePosLocation() {
		StoragePosLocation s = new StoragePosLocation();
		s.setCurrentLable("sdasda");
		s.setGmtCreate(new Date());
		s.setGmtModify(new Date());
		s.setMemo("sadadasd");
		s.setParentId(123123);
		return s;
	}
}
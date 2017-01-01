package com.xabaohui.modules.storage.repository;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xabaohui.modules.storage.BaseTestUnit;
import com.xabaohui.modules.storage.entity.StorageCheckPlan;

public class StorageCheckPlanDaoTest extends BaseTestUnit {

	@Autowired
	StorageCheckPlanDao dao;

	@Test
	public void test() {
		StorageCheckPlan s = getStorageCheckPlan();

		dao.save(s);

		StorageCheckPlan s1 = dao.findOne(s.getPlanId());
		
		Assert.assertNotNull(s1);
		Assert.assertEquals(s.getPlanId(), s1.getPlanId());
	}

	private StorageCheckPlan getStorageCheckPlan() {
		StorageCheckPlan s = new StorageCheckPlan();
		s.setGmtCreate(new Date());
		s.setGmtModify(new Date());
		s.setMemo("sadasdasd");
		s.setPosCount(123123);
		s.setStatus("sddss");
		s.setUserId(123123123);
		return s;
	}
}
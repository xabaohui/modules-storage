package com.xabaohui.modules.storage.repository;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xabaohui.modules.storage.BaseTestUnit;
import com.xabaohui.modules.storage.entity.StorageCheckDiffAdjust;

public class StorageCheckDiffAdjustDaoTest extends BaseTestUnit {

	@Autowired
	StorageCheckDiffAdjustDao dao;

	@Test
	public void save() {
		StorageCheckDiffAdjust s = getStorageCheckDiffAdjust();

		dao.save(s);

		StorageCheckDiffAdjust ss = dao.findOne(s.getAdjustId());

		Assert.assertNotNull(ss);

		Assert.assertEquals(s.getAdjustId(), ss.getAdjustId());
	}

	private StorageCheckDiffAdjust getStorageCheckDiffAdjust() {
		StorageCheckDiffAdjust s = new StorageCheckDiffAdjust();
		s.setAdjustNum(123);
		s.setCheckId(3333);
		s.setGmtCreate(new Date());
		s.setGmtModify(new Date());
		s.setMemo("2342fewtsdgwevs");
		s.setOperator(334444);
		s.setSkuId(1233333);
		return s;
	}
}
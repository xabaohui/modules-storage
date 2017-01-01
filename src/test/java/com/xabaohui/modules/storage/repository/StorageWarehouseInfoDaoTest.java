package com.xabaohui.modules.storage.repository;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xabaohui.modules.storage.BaseTestUnit;
import com.xabaohui.modules.storage.entity.StorageWarehouseInfo;

public class StorageWarehouseInfoDaoTest extends BaseTestUnit {

	@Autowired
	StorageWarehouseInfoDao dao;

	@Test
	public void test() {
		StorageWarehouseInfo s = getStorageWarehouseInfo();
		dao.save(s);

		StorageWarehouseInfo s1 = dao.findOne(s.getWarehouseId());

		Assert.assertNotNull(s1);
		Assert.assertEquals(s.getWarehouseName(), s.getWarehouseName());
	}

	private StorageWarehouseInfo getStorageWarehouseInfo() {
		StorageWarehouseInfo s = new StorageWarehouseInfo();
		s.setGmtCreate(new Date());
		s.setGmtModify(new Date());
		s.setMemo("1231221");
		s.setUserId(13111);
		s.setWarehouseName("asd3awwdawdw");
		return s;
	}
}
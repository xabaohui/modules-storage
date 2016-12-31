package com.xabaohui.modules.storage.repository;

import java.util.Date;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xabaohui.modules.storage.BaseTestUnit;
import com.xabaohui.modules.storage.entity.StoragePosition;

public class StoragePositionDaoTest extends BaseTestUnit {

	@Autowired
	StoragePositionDao dao;
	
	@Test
	public void testSave() {
		StoragePosition pos = buildPosition();
		dao.save(pos);
	}
	
	@Test
	public void test() {
		StoragePosition pos = buildPosition();
		dao.save(pos);
		
		String label = pos.getLabel();
		StoragePosition result = dao.findByLabel(label);
		Assert.assertNotNull(result);
	}

	private StoragePosition buildPosition() {
		StoragePosition pos = new StoragePosition();
		pos.setCapacity(100);;
		pos.setLabel("L" + new Random().nextInt(100000));
		pos.setIsfull(false);
		pos.setIslock(false);
		pos.setGmtCreate(new Date());
		pos.setGmtModify(new Date());
		return pos;
	}
}

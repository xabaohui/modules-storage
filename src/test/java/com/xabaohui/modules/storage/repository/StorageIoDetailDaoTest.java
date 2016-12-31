package com.xabaohui.modules.storage.repository;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xabaohui.modules.storage.BaseTestUnit;
import com.xabaohui.modules.storage.entity.StorageIoDetail;
import com.xabaohui.modules.storage.entity.StorageIoDetail.IoType;

public class StorageIoDetailDaoTest extends BaseTestUnit{

	@Autowired
	StorageIoDetailDao dao;
	
	@Test
	public void testSave() {
		StorageIoDetail detail = buildDetail();
		detail = dao.save(detail);
		Assert.assertNotNull(detail.getDetailId());
	}
	
	@Test
	public void testFindByTaskId() {
		StorageIoDetail detail = buildDetail();
		dao.save(detail);
		
		List<StorageIoDetail> list = dao.findByTaskId(detail.getTaskId());
		Assert.assertNotNull(list);
		Assert.assertTrue(!list.isEmpty());
	}

	private StorageIoDetail buildDetail() {
		StorageIoDetail d = new StorageIoDetail();
		d.setAmount(1);
		d.setGmtCreate(new Date());
		d.setGmtModify(new Date());
		d.setIoDetailType(IoType.OUT.getValue());
		d.setPositionId(133);
		d.setSkuId(111);
		d.setTaskId(1);
		return d;
	}
}

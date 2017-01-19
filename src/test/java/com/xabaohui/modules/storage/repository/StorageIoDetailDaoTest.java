package com.xabaohui.modules.storage.repository;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xabaohui.modules.storage.BaseTestUnit;
import com.xabaohui.modules.storage.entity.StorageIoDetail;
import com.xabaohui.modules.storage.entity.StorageIoDetail.DetailStatus;
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
		
		List<StorageIoDetail> list = dao.findByBatchId(detail.getBatchId());
		Assert.assertNotNull(list);
		Assert.assertTrue(!list.isEmpty());
	}

	private StorageIoDetail buildDetail() {
		StorageIoDetail d = new StorageIoDetail();
		d.setAmount(1);
		d.setOperator(1);
		d.setGmtCreate(new Date());
		d.setGmtModify(new Date());
		d.setIoDetailType(IoType.OUT.getValue());
		d.setDetailStatus(DetailStatus.PREPARING.getValue());
		d.setPosId(133);
		d.setPosLabel("label");
		d.setSkuId(111);
		d.setRepoId(1);
		d.setBatchId(1);
		return d;
	}
}

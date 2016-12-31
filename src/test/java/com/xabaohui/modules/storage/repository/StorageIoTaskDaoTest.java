package com.xabaohui.modules.storage.repository;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xabaohui.modules.storage.BaseTestUnit;
import com.xabaohui.modules.storage.entity.StorageIoTask;
import com.xabaohui.modules.storage.entity.StorageIoTask.BizType;
import com.xabaohui.modules.storage.entity.StorageIoTask.InvoiceStatus;

public class StorageIoTaskDaoTest extends BaseTestUnit{
	
	@Autowired
	StorageIoTaskDao dao;

	@Test
	public void test() {
		StorageIoTask task = buildTask();
		dao.save(task);
	}

	private StorageIoTask buildTask() {
		StorageIoTask t = new StorageIoTask();
		t.setAmount(1);
		t.setBizType(BizType.ORDER.getValue());
		t.setOutTradeNo("T150123123");
		t.setStatus(InvoiceStatus.CREATED.getValue());
		t.setUserId(1231);
		t.setGmtCreate(new Date());
		t.setGmtModify(new Date());
		return t;
	}
}

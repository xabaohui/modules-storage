package com.xabaohui.modules.storage.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.xabaohui.modules.storage.BaseTestUnit;
import com.xabaohui.modules.storage.dto.CreateOrderDTO;
import com.xabaohui.modules.storage.dto.CreateOrderDTO.CreateOrderDetail;
import com.xabaohui.modules.storage.entity.StorageIoBatch;
import com.xabaohui.modules.storage.entity.StorageIoDetail;
import com.xabaohui.modules.storage.entity.StorageOrder.OrderType;
import com.xabaohui.modules.storage.service.StorageService;

public class StorageServiceTest extends BaseTestUnit {
	
	final Integer operator = 9527;
	final Integer repoId = 6;

	@Autowired
	StorageService service;
	
	@Test
	public void testCreateStoragePosition() {
		service.createStoragePosition(repoId, "A-02-01-4498");
	}
	
	@Test
	public void testCreateInStorage() {
		StorageIoBatch batch = service.createInStorage(6, "测试创建入库单", 9527);
	}
	
	@Test
	public void testAddInStorageDetail() {
		final String posLabel = "A-01-01-" + new Random().nextInt(10000);
		StorageIoBatch batch = service.createInStorage(repoId, "测试创建入库单", operator);
		service.createStoragePosition(repoId, posLabel);
		service.addInStorageDetail(batch.getBatchId(), 1102, 3, posLabel, operator);
	}
	
	@Test
	public void testConfirmInStorage() {
		final String posLabel = "D-01-01-4498";
		StorageIoBatch batch = service.createInStorage(repoId, "测试创建入库单", operator);
		service.addInStorageDetail(batch.getBatchId(), 1102, 2, posLabel, operator);
		service.confirmInStorage(batch.getBatchId(), operator);
	}
	
	@Test
	public void testCancelInStorage() {
		final String posLabel = "A-01-01-4498";
		StorageIoBatch batch = service.createInStorage(repoId, "测试创建入库单", operator);
		service.addInStorageDetail(batch.getBatchId(), 1102, 3, posLabel, operator);
		service.cancelInStorage(batch.getBatchId(), operator);
	}
	
	@Test
	public void testCreateOrder() {
		List<CreateOrderDetail> detailList = new ArrayList<CreateOrderDTO.CreateOrderDetail>();
		CreateOrderDetail d = new CreateOrderDetail();
		d.setAmount(6);
		d.setSkuId(1102);
		detailList.add(d);
		CreateOrderDTO request = new CreateOrderDTO();
		request.setOutTradeNo("test-20170301001");
		request.setOrderType(OrderType.SELF);
		request.setRepoId(repoId);
		request.setShopId(1113);
		request.setDetailList(detailList);
		service.createOrder(request);
	}
	
	@Test
	public void testPrepareBatchSend() {
		List<Integer> orderIds = new ArrayList<Integer>();
		orderIds.add(23);
		service.prepareBatchSend(repoId, orderIds, operator);
	}
	
	@Test
	public void testPickup() {
		StorageIoDetail detail = service.pickupLock(43, operator);
		System.out.println(JSONObject.toJSONString(detail));
		do {
			detail = service.pickupDoneAndLockNext(detail.getDetailId(), operator);
			System.out.println(JSONObject.toJSONString(detail));
		} while (detail != null);
		Assert.assertNull(detail);
	}
	
	@Test
	public void testCancelOrder() {
		service.cancelOrder(20);
	}
	
	@Test
	public void testFinishBatchSend() {
		service.finishBatchSend(43);
	}
}

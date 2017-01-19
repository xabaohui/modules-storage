package com.xabaohui.modules.storage.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xabaohui.modules.storage.BaseTestUnit;
import com.xabaohui.modules.storage.entity.StorageOrder;
import com.xabaohui.modules.storage.entity.StorageOrder.OrderType;
import com.xabaohui.modules.storage.entity.StorageOrder.TradeStatus;

public class StorageOrderDaoTest extends BaseTestUnit {

	@Autowired
	StorageOrderDao dao;
	
	@Test
	public void testFindByOrderIds() {
		StorageOrder order = buildOrder();
		order = dao.save(order);
		StorageOrder order2 = buildOrder();
		order2 = dao.save(order2);
		
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(order.getOrderId());
		ids.add(order2.getOrderId());
		List<StorageOrder> list = dao.findByOrderIds(ids);
		Assert.assertEquals(2, list.size());
	}

	private StorageOrder buildOrder() {
		StorageOrder o = new StorageOrder();
		o.setOutTradeNo("123123123");
		o.setRepoId(1);
		o.setShopId(1);
		o.setOrderDetail("testDetail");
		o.setOrderType(OrderType.SELF.getValue());
		o.setTradeStatus(TradeStatus.CREATED.getValue());
		o.setVersion(0);
		o.setGmtCreate(new Date());
		o.setGmtModify(new Date());
		return o;
	}
}
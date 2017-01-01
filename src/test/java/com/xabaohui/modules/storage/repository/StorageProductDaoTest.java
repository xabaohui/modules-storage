package com.xabaohui.modules.storage.repository;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xabaohui.modules.storage.BaseTestUnit;
import com.xabaohui.modules.storage.entity.StorageProduct;

public class StorageProductDaoTest extends BaseTestUnit {

	@Autowired
	StorageProductDao dao;

	@Test
	public void test() {
		StorageProduct s = getStorageProduct();
		dao.save(s);

		List<StorageProduct> list = dao.findBySkuId(s.getSkuId());

		Assert.assertFalse(list.isEmpty());
		for (StorageProduct s1 : list) {
			Assert.assertEquals(s.getStockId(), s1.getStockId());
		}
	}

	private StorageProduct getStorageProduct() {
		StorageProduct s = new StorageProduct();
		s.setGmtCreate(new Date());
		s.setGmtModify(new Date());
		s.setLockFlag(false);
		s.setLockReason("sdsdsdsd");
		s.setSkuId(12312331);
		s.setStockAmt(23333);
		s.setStockAvailabe(131312);
		s.setStockId(123123);
		s.setStockOccupy(13131);
		s.setSubjectId(2111113);
		return s;
	}
}
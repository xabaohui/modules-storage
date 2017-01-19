package com.xabaohui.modules.storage.repository;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xabaohui.modules.storage.BaseTestUnit;
import com.xabaohui.modules.storage.entity.StorageProduct;

public class StorageProductDaoTest extends BaseTestUnit {

	@Autowired
	StorageProductDao dao;

	@Test
	public void testFindBySkuIdAndRepoId() {
		StorageProduct s = getStorageProduct();
		dao.save(s);

		List<StorageProduct> list = dao.findBySkuIdAndRepoId(s.getSkuId(), s.getRepoId());

		Assert.assertFalse(list.isEmpty());
		for (StorageProduct s1 : list) {
			Assert.assertEquals(s.getSkuId(), s1.getSkuId());
		}
	}

	private StorageProduct getStorageProduct() {
		StorageProduct s = new StorageProduct();
		s.setGmtCreate(new Date());
		s.setGmtModify(new Date());
		s.setLockFlag(false);
		s.setLockReason("sdsdsdsd");
		s.setSkuId(new Random().nextInt());
		s.setSkuName("testSku");
		s.setRepoId(1);
		s.setStockAmt(23333);
		s.setStockAvailable(131312);
		s.setStockOccupy(13131);
		s.setSubjectId(2111113);
		return s;
	}
}
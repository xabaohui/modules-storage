package com.xabaohui.modules.storage.bo;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xabaohui.modules.storage.dao.StoragePosStockDao;
import com.xabaohui.modules.storage.dao.StorageProductDao;
import com.xabaohui.modules.storage.entity.StorageIoDetail;
import com.xabaohui.modules.storage.entity.StoragePosStock;
import com.xabaohui.modules.storage.entity.StorageProduct;
import com.xabaohui.modules.storage.entity.StorageIoDetail.IoType;
import com.xabaohui.modules.storage.exception.StockNotEnoughException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class OutwarehouseBoTest {

	@Autowired
	OutwarehouseBo outwarehouseBo;
	@Autowired
	StoragePosStockDao storagePosStockDao;
	@Autowired
	StorageProductDao storageProductDao;

	@Test
	/**
	 * 指定库位出库
	 */
	public void testOutwarehouseForSkuWithPosition() {
		final Integer positionId = new Random().nextInt(1000000);
		final Integer skuId = new Random().nextInt(1000000);
		final Integer initAmt = 5;
		final Integer amount = 3;
		storagePosStockDao.save(buildPosStock(positionId, skuId, initAmt));
		StorageIoDetail ioDetail = outwarehouseBo.outwarehouseForSku(1, skuId, amount, positionId);
		// 检查出库明细
		Assert.assertNotNull(ioDetail);
		Assert.assertEquals(positionId, ioDetail.getPositionId());
		Assert.assertEquals(skuId, ioDetail.getSkuId());
		Assert.assertEquals(amount, ioDetail.getAmount());
		Assert.assertEquals(IoType.OUT.getValue(), ioDetail.getIoDetailType());
		// TODO test 确保库存库位记录已扣减
	}

	@Test
	/**
	 * 指定库位出库--库存不足异常
	 */
	public void testOutwarehouseForSkuWithPositionEx() {
		final Integer positionId = new Random().nextInt(1000000);
		final Integer skuId = new Random().nextInt(1000000);
		final Integer initAmt = 5;
		final Integer amount = 8;
		storagePosStockDao.save(buildPosStock(positionId, skuId, initAmt));
		try {
			outwarehouseBo.outwarehouseForSku(1, skuId, amount, positionId);
			Assert.fail("本方法应该出现异常");
		} catch (StockNotEnoughException e) {
			// success
		}
	}

	@Test
	/**
	 * 出库
	 */
	public void testOutwarehouseForSku() {
		final Integer positionId = new Random().nextInt(1000000);
		final Integer skuId = new Random().nextInt(1000000);
		final Integer initAmt = 5;
		final Integer amount = 8;
		storagePosStockDao.save(buildPosStock(positionId, skuId, initAmt));
		storagePosStockDao.save(buildPosStock(positionId + 1, skuId, initAmt));

		List<StorageIoDetail> list = outwarehouseBo.outwarehouseForSku(1, skuId, amount);
		Assert.assertEquals(2, list.size());
	}

	@Test
	/**
	 * 占用库存
	 */
	public void testOccupyStock() {
		final int stockAmt = 10; // 库存总量
		final int initOccupy = 1; // 初始化占用量
		final int toBeOccupy = 3; // 新增占用量
		StorageProduct product = buildStorageProduct();
		product.setStockAmt(stockAmt);
		product.setStockOccupy(initOccupy);
		product.setStockAvailabe(stockAmt - initOccupy);
		storageProductDao.save(product);

		String result = outwarehouseBo.occupyStock("T00112312", product.getSkuId(), toBeOccupy);
		Assert.assertNull(result);
		StorageProduct resultData = storageProductDao.findOne(product.getStockId());
		// 验证库存总量没变化
		Assert.assertEquals(Integer.valueOf(stockAmt), resultData.getStockAmt());
		// 验证最终占用量 = 初始化占用量 + 新增占用量
		Assert.assertEquals(Integer.valueOf(initOccupy + toBeOccupy), resultData.getStockOccupy());
		// 验证最终可用数量 = 库存总量 - 初始化占用量 - 新增占用量
		Assert.assertEquals(Integer.valueOf(stockAmt - initOccupy - toBeOccupy), resultData.getStockAvailabe());
	}

	@Test
	/**
	 * 占用库存--可用库存不足
	 */
	public void testOccupyStockForEx() {
		final int stockAmt = 10; // 库存总量
		final int initOccupy = 1; // 初始化占用量
		final int toBeOccupy = 10; // 新增占用量
		StorageProduct product = buildStorageProduct();
		product.setStockAmt(stockAmt);
		product.setStockOccupy(initOccupy);
		product.setStockAvailabe(stockAmt - initOccupy);
		storageProductDao.save(product);

		String result = outwarehouseBo.occupyStock("T00112312", product.getSkuId(), toBeOccupy);
		Assert.assertNotNull(result);
		StorageProduct resultData = storageProductDao.findOne(product.getStockId());
		// 验证库存总量没变化
		Assert.assertEquals(Integer.valueOf(stockAmt), resultData.getStockAmt());
		// 验证占用量没变化
		Assert.assertEquals(Integer.valueOf(initOccupy), resultData.getStockOccupy());
		// 验证可用数量没变化
		Assert.assertEquals(Integer.valueOf(stockAmt - initOccupy), resultData.getStockAvailabe());
	}

	@Test
	public void testUnoccupy() {
		final int stockAmt = 10; // 库存总量
		final int initOccupy = 1; // 初始化占用量
		final int toBeOccupy = 3; // 新增占用量
		StorageProduct product = buildStorageProduct();
		product.setStockAmt(stockAmt);
		product.setStockOccupy(initOccupy);
		product.setStockAvailabe(stockAmt - initOccupy);
		storageProductDao.save(product);
		outwarehouseBo.occupyStock("T00112312", product.getSkuId(), toBeOccupy);
		StorageProduct resultData = storageProductDao.findOne(product.getStockId());
		// 验证占用生效：最终占用量 = 初始化占用量 + 新增占用量
		Assert.assertEquals(Integer.valueOf(initOccupy + toBeOccupy), resultData.getStockOccupy());

		String result = outwarehouseBo.unoccupyStock("T00112312", product.getSkuId(), toBeOccupy);
		Assert.assertNull(result);
		resultData = storageProductDao.findOne(product.getStockId());
		// 验证占用量已减少
		Assert.assertEquals(Integer.valueOf(initOccupy), resultData.getStockOccupy());
	}

	private StorageProduct buildStorageProduct() {
		StorageProduct product = new StorageProduct();
		product.setSkuId(new Random().nextInt(1000000));
		product.setSubjectId(133);
		product.setStockAmt(10);
		product.setStockOccupy(0);
		product.setStockAvailabe(10);
		product.setGmtCreate(new Date());
		product.setGmtModify(new Date());
		product.setVersion(0);
		return product;
	}

	private StoragePosStock buildPosStock(Integer positionId, Integer skuId, Integer amount) {
		StoragePosStock stock = new StoragePosStock();
		stock.setPositionId(positionId);
		stock.setSkuId(skuId);
		stock.setAmount(amount);
		stock.setGmtCreate(new Date());
		stock.setGmtModify(new Date());
		stock.setVersion(0);
		return stock;
	}
}

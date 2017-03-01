package com.xabaohui.modules.storage.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.xabaohui.modules.storage.dto.CreateOrderDTO;
import com.xabaohui.modules.storage.dto.CreateOrderDTO.CreateOrderDetail;
import com.xabaohui.modules.storage.dto.StockDTO;
import com.xabaohui.modules.storage.entity.StorageIoBatch;
import com.xabaohui.modules.storage.entity.StorageIoBatch.BizType;
import com.xabaohui.modules.storage.entity.StorageIoDetail;
import com.xabaohui.modules.storage.entity.StorageIoDetail.DetailStatus;
import com.xabaohui.modules.storage.entity.StorageIoDetail.IoType;
import com.xabaohui.modules.storage.entity.StorageOrder;
import com.xabaohui.modules.storage.entity.StorageOrder.OrderType;
import com.xabaohui.modules.storage.entity.StorageOrder.TradeStatus;
import com.xabaohui.modules.storage.entity.StoragePosStock;
import com.xabaohui.modules.storage.entity.StoragePosition;
import com.xabaohui.modules.storage.entity.StoragePosition.PosStatus;
import com.xabaohui.modules.storage.entity.StorageProduct;
import com.xabaohui.modules.storage.entity.StorageProductOccupy;
import com.xabaohui.modules.storage.repository.StorageIoBatchDao;
import com.xabaohui.modules.storage.repository.StorageIoDetailDao;
import com.xabaohui.modules.storage.repository.StorageOrderDao;
import com.xabaohui.modules.storage.repository.StoragePosStockDao;
import com.xabaohui.modules.storage.repository.StoragePositionDao;
import com.xabaohui.modules.storage.repository.StorageProductDao;
import com.xabaohui.modules.storage.repository.StorageProductOccupyDao;
import com.xabaohui.modules.storage.repository.StorageRepoInfoDao;
import com.xabaohui.modules.storage.service.StorageService;

@Service
public class StorageServiceImpl implements StorageService {

	@Autowired
	private StorageIoBatchDao storageIoBatchDao;
	@Autowired
	private StorageIoDetailDao storageIoDetailDao;
	@Autowired
	private StorageOrderDao storageOrderDao;
	@Autowired
	private StoragePositionDao storagePositionDao;
	@Autowired
	private StoragePosStockDao storagePosStockDao;
	@Autowired
	private StorageProductDao storageProductDao;
	@Autowired
	private StorageProductOccupyDao storageProductOccupyDao;
	@Autowired
	private StorageRepoInfoDao storageRepoInfoDao;

	@Override
	public void directSend(Integer repoId, Integer skuId, Integer amount, String posLabel, Integer operator) {
		// TODO Auto-generated method stub

	}

	@Override
	@Transactional
	public StorageOrder createOrder(CreateOrderDTO request) {
		// param check
		checkForCreateOrder(request);
		
		// 创建StorageOrder, status=created
		// 修改StorageProduct，增加占用库存
		// 新增StorageProductOccupy，关联StorageOrder, StorageProduct
		List<CreateOrderDetail> detailList = request.getDetailList();
		Integer repoId = request.getRepoId();
		StorageOrder order = new StorageOrder();
		Date now = new Date();
		order.setOrderType(OrderType.SELF.getValue());
		order.setOutTradeNo(request.getOutTradeNo());
		order.setRepoId(request.getRepoId());
		order.setShopId(request.getShopId());
		order.setTradeStatus(TradeStatus.CREATED.getValue());
		String orderDetail = JSONObject.toJSONString(detailList);
		order.setOrderDetail(orderDetail);
		order.setGmtCreate(now);
		order.setGmtModify(now);
		this.storageOrderDao.save(order);

		for (CreateOrderDetail detail : detailList) {
			StorageProduct prod = this.storageProductDao.findBySkuIdAndRepoId(detail.getSkuId(), repoId);
			if(prod == null) {
				throw new RuntimeException(String.format("库存商品不存在,skuId=%s,repoId=%s.", detail.getSkuId(), repoId));
			}
			if (prod.getStockAvailable() < detail.getAmount()) {
				throw new RuntimeException(String.format("库存不足,productId=%s,skuId=%s,订单量%s,可用量%s", prod.getProductId(),
						detail.getSkuId(), detail.getAmount(), prod.getStockAvailable()));
			}
			doOccupy(prod, order.getOrderId(), detail.getAmount());
		}
		return order;
	}

	/**
	 * 占用库存
	 * @param prod 商品
	 * @param orderId 订单
	 * @param amount 占用数量
	 */
	private void doOccupy(StorageProduct prod, Integer orderId, Integer amount) {
		Date now = new Date();
		prod.setStockOccupy(prod.getStockOccupy() + amount);
		prod.setStockAvailable(prod.getStockAvailable() - amount);
		prod.setGmtModify(now);
		this.storageProductDao.save(prod);
		StorageProductOccupy occupy = new StorageProductOccupy();
		occupy.setOrigAmt(amount);
		occupy.setCurAmt(amount);
		occupy.setOrderId(orderId);
		occupy.setProductId(prod.getProductId());
		occupy.setStatus(StorageProductOccupy.Status.OCCUPY.getValue());
		occupy.setCreateTime(now);
		occupy.setUpdateTime(now);
		this.storageProductOccupyDao.save(occupy);
	}
	
	/**
	 * 取消库存占用
	 * @param orderId
	 */
	private void undoOccupy(Integer orderId) {
		Date now = new Date();
		List<StorageProductOccupy> occupys = this.storageProductOccupyDao.findByOrderId(orderId);
		for (StorageProductOccupy occupy : occupys) {
			occupy.setStatus(StorageProductOccupy.Status.CANCEL.getValue());
			occupy.setUpdateTime(now);
			this.storageProductOccupyDao.save(occupy);
			StorageProduct prod = this.storageProductDao.findOne(occupy.getProductId());
			prod.setStockOccupy(prod.getStockOccupy() - occupy.getCurAmt());
			prod.setStockAvailable(prod.getStockAvailable() + occupy.getCurAmt());
			prod.setGmtModify(now);
			this.storageProductDao.save(prod);
		}
	}

	private void checkForCreateOrder(CreateOrderDTO request) {
		if (request == null) {
			throw new IllegalArgumentException("request不能为空");
		}
		if(StringUtils.isBlank(request.getOutTradeNo())) {
			throw new IllegalArgumentException("OutTradeNo不能为空");
		}
		if(request.getRepoId() == null) {
			throw new IllegalArgumentException("RepoId不能为空");
		}
		if(request.getShopId() == null) {
			throw new IllegalArgumentException("ShopId不能为空");
		}
		if(request.getOrderType() == null) {
			throw new IllegalArgumentException("OrderType不能为空");
		}
	}

	@Override
	@Transactional
	public int prepareBatchSend(Integer repoId, List<Integer> orderIds, Integer operator) {
		if (orderIds == null || orderIds.isEmpty()) {
			throw new IllegalArgumentException("orderIds不能为空");
		}
		// 创建StorageIoBatch 
		StorageIoBatch batch = saveStorageIoBatch(repoId, operator, BizType.OUT_BATCH, "订单出库");
		// 遍历所有订单，执行分配库位的操作
		for (Integer orderId : orderIds) {
			StorageOrder order = this.storageOrderDao.findOne(orderId);
			if (order == null) {
				throw new RuntimeException("order不存在, orderId=" + orderId);
			}
			if(!repoId.equals(order.getRepoId())) {
				throw new RuntimeException("所选订单不属于同一个仓库");
			}
			if(!TradeStatus.CREATED.getValue().equals(order.getTradeStatus())) {
				throw new RuntimeException("所选订单不能生成配货单，tradeStatus=" + order.getTradeStatus());
			}
			// 更新订单状态为处理中
			order.setTradeStatus(TradeStatus.PROCESSING.getValue());
			order.setGmtModify(new Date());
			this.storageOrderDao.save(order);
			// 根据订单占用量，分配库位
			List<StorageProductOccupy> occupys = this.storageProductOccupyDao.findByOrderId(orderId);
			if (occupys == null || occupys.isEmpty()) {
				throw new RuntimeException("occupys不存在, orderId=" + orderId);
			}
			for (StorageProductOccupy occupy : occupys) {
				// 查询可用库存，生成StorageIoDetail，更新StoragePosStock的占用量
				List<StockDTO> stocks = this.storagePosStockDao.findAvailableStock(occupy.getProductId());
				int amountNotDivide = occupy.getCurAmt();
				for (StockDTO stock : stocks) {
					int amountAvailable = stock.getTotalAmt() - stock.getOccupyAmt();// 当前库位可用数量
					int amountDivide = Math.min(amountAvailable, amountNotDivide);// 本次分配数量
					StoragePosition pos = new StoragePosition();
					pos.setPosId(stock.getPosId());
					pos.setLabel(stock.getPosLabel());
					// 生成StorageIoDetail
					saveStorageIoDetail(batch, pos, occupy.getProductId(), stock.getSkuId(), amountDivide, operator, orderId);
					// 更新StoragePosStock的占用量
					StoragePosStock posStock = this.storagePosStockDao.findOne(stock.getStockId());
					posStock.setOccupyAmt(posStock.getOccupyAmt() + amountDivide);
					this.storagePosStockDao.save(posStock);
					amountNotDivide -= amountDivide;// 剩余未分配数量
					if(amountNotDivide == 0) {
						break;
					}
					if(amountNotDivide < 0) {
						throw new RuntimeException("分配库位错误，未分配数量小于0");
					}
				}
				if(amountNotDivide > 0) {
					throw new RuntimeException("分配库位错误，库存量不足");
				}
			}
		}
		return batch.getBatchId();
	}

	@Override
	@Transactional
	public StorageIoDetail pickupDoneAndLockNext(Integer ioDetailId, Integer operator) {
		if (ioDetailId == null) {
			throw new IllegalArgumentException("ioDetailId不能为空");
		}
		if (operator == null) {
			throw new IllegalArgumentException("operator不能为空");
		}
		// 已取件，status:processing->success
		StorageIoDetail current = this.storageIoDetailDao.findOne(ioDetailId);
		if(!DetailStatus.PROCESSING.getValue().equals(current.getDetailStatus())) {
			throw new RuntimeException("不能执行取件，detailStatus=" + current.getDetailStatus());
		}
		if(!current.getOperator().equals(operator)) {
			throw new RuntimeException("不能操作别人锁定的记录");
		}
		current.setDetailStatus(DetailStatus.SUCCESS.getValue());
		this.storageIoDetailDao.save(current);
		// 减少库位库存和占用
		StoragePosStock stock = this.storagePosStockDao.findByPosIdAndProductId(current.getPosId(), current.getProductId());
		if (stock == null) {
			throw new RuntimeException(String.format("stock不存在, posId=%s, productId=%s",current.getPosId(), current.getProductId()));
		}
		stock.setOccupyAmt(stock.getOccupyAmt() - current.getAmount());
		stock.setTotalAmt(stock.getTotalAmt() - current.getAmount());
		this.storagePosStockDao.save(stock);
		// 减少商品库存和占用
		StorageProduct prod = this.storageProductDao.findOne(current.getProductId());
		if (prod == null) {
			throw new RuntimeException("prod不存在, productId=" + current.getProductId());
		}
		prod.setStockAmt(prod.getStockAmt() - current.getAmount());
		prod.setStockOccupy(prod.getStockOccupy() - current.getAmount());
		this.storageProductDao.save(prod);
		StorageProductOccupy occupy = this.storageProductOccupyDao.findByOrderIdAndProductId(current.getOrderId(), current.getProductId());
		if (occupy == null) {
			throw new RuntimeException(String.format("occupy不存在, orderId=%s, productId=%s",current.getOrderId(), current.getProductId()));
		}
		occupy.setCurAmt(occupy.getCurAmt() - current.getAmount());
		if(occupy.getCurAmt() == 0) {
			occupy.setStatus(StorageProductOccupy.Status.SENT.getValue());
		}
		this.storageProductOccupyDao.save(occupy);
		// 返回下一条记录，status:waiting->processing（防止多个操作员同时操作过程中看到同一个记录）
		return doPickupLock(current.getBatchId(), operator);
	}
	
	@Override
	@Transactional
	public StorageIoDetail pickupLock(Integer batchId, Integer operator) {
		if (batchId == null) {
			throw new IllegalArgumentException("batchId不能为空");
		}
		if (operator == null) {
			throw new IllegalArgumentException("operator不能为空");
		}
		return doPickupLock(batchId, operator);
	}

	// 返回下一条记录或找到丢失的记录
	private StorageIoDetail doPickupLock(Integer batchId, Integer operator) {
		// 查找意外退出、关机丢失的数据（锁定但未取货）
		Pageable page = new PageRequest(0, 1);
		Page<StorageIoDetail> lost = this.storageIoDetailDao.findProcessingRecordByBatchIdAndOperator(batchId, operator, page);
		if(lost != null && lost.hasContent()) {
			return lost.getContent().get(0);
		}
		
		Page<StorageIoDetail> next = this.storageIoDetailDao.findNextRecordForPickup(batchId, page);
		if(next == null || !next.hasContent()) {
			return null;
		}
		// 返回下一条记录，status:waiting->processing（防止多个操作员同时操作过程中看到同一个记录）
		StorageIoDetail detail = next.getContent().get(0);
		detail.setDetailStatus(DetailStatus.PROCESSING.getValue());
		detail.setOperator(operator);
		this.storageIoDetailDao.save(detail);
		return detail;
	}

	@Override
	public StorageIoDetail lackAll(Integer ioDetailId, Integer operator) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StorageIoDetail lackPart(Integer ioDetailId, Integer operator, Integer actualAmt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public void finishBatchSend(Integer batchId) {
		if (batchId == null) {
			throw new IllegalArgumentException("batchId不能为空");
		}
		StorageIoBatch batch = this.storageIoBatchDao.findOne(batchId);
		if (batch == null) {
			throw new RuntimeException("batch不存在, batchId=" + batchId);
		}
		if(!BizType.OUT_BATCH.getValue().equals(batch.getBizType())) {
			throw new RuntimeException("批次类型必须是out_batch");
		}
		if(!StorageIoBatch.Status.CREATED.getValue().equals(batch.getStatus())) {
			throw new RuntimeException("批次状态必须是created");
		}
		List<StorageIoDetail> list = this.storageIoDetailDao.findByBatchIdAndDetailStatus(batchId, DetailStatus.WAITING.getValue());
		if(!CollectionUtils.isEmpty(list)) {
			throw new RuntimeException("还有未完成的配货明细");
		}
		Date now = new Date();
		batch.setStatus(StorageIoBatch.Status.FINISH.getValue());
		batch.setGmtModify(now);
		this.storageIoBatchDao.save(batch);
		this.storageOrderDao.updateToSentByBatchId(batchId);
	}

	@Override
	@Transactional
	public void cancelOrder(Integer orderId) {
		if (orderId == null) {
			throw new IllegalArgumentException("orderId不能为空");
		}
		StorageOrder order = this.storageOrderDao.findOne(orderId);
		if (order == null) {
			throw new RuntimeException("order不存在, orderId=" + orderId);
		}
		if(!TradeStatus.CREATED.getValue().equals(order.getTradeStatus())) {
			throw new RuntimeException("配货中的订单，不允许取消");
		}
		order.setTradeStatus(TradeStatus.CANCEL.getValue());
		order.setGmtModify(new Date());
		this.storageOrderDao.save(order);
		undoOccupy(orderId);
		undoOccupyPosStock(orderId);
	}
	
	/**
	 * 取消库位库存占用
	 * @param orderId
	 */
	private void undoOccupyPosStock(Integer orderId) {
		Date now = new Date();
		List<StorageIoDetail> list = this.storageIoDetailDao.findByOrderId(orderId);
		for (StorageIoDetail detail : list) {
			detail.setDetailStatus(DetailStatus.CANCEL.getValue());
			detail.setGmtModify(now);
			this.storageIoDetailDao.save(detail);
			StoragePosStock stock = this.storagePosStockDao.findByPosIdAndProductId(detail.getPosId(), detail.getProductId());
			if (stock == null) {
				throw new RuntimeException(String.format("StoragePosStock不存在, posId=%s,productId=%s", detail.getPosId(), detail.getProductId()));
			}
			stock.setOccupyAmt(stock.getOccupyAmt() - detail.getAmount());
			stock.setGmtModify(now);
			this.storagePosStockDao.save(stock);
		}
	}

	@Override
	@Transactional
	public StorageIoBatch createInStorage(Integer repoId, String memo, Integer operator) {
		if(repoId == null) {
			throw new IllegalArgumentException("repoId不能为空");
		}
		if(operator == null) {
			throw new IllegalArgumentException("operator不能为空");
		}
		StorageIoBatch batch = saveStorageIoBatch(repoId, operator, BizType.IN_BATCH, memo);
		return batch;
	}

	private StorageIoBatch saveStorageIoBatch(Integer repoId, Integer operator, BizType bizType, String memo) {
		StorageIoBatch batch = new StorageIoBatch();
		batch.setRepoId(repoId);
		batch.setMemo(memo);
		batch.setOperator(operator);
		batch.setAmount(0);
		batch.setBizType(bizType.getValue());
		batch.setStatus(StorageIoBatch.Status.CREATED.getValue());
		Date now = new Date();
		batch.setGmtCreate(now);
		batch.setGmtModify(now);
		this.storageIoBatchDao.save(batch);
		return batch;
	}

	@Override
	@Transactional
	public void addInStorageDetail(Integer batchId, Integer skuId, Integer amount, String posLabel, Integer operator) {
		if(batchId == null) {
			throw new IllegalArgumentException("batchId不能为空");
		}
		if(skuId == null) {
			throw new IllegalArgumentException("skuId不能为空");
		}
		if(amount == null || amount <= 0) {
			throw new IllegalArgumentException("入库数量错误，amount=" + amount);
		}
		if(StringUtils.isBlank(posLabel)) {
			throw new IllegalArgumentException("库位标签posLabel不能为空");
		}
		if(operator == null) {
			throw new IllegalArgumentException("operator不能为空");
		}
		// 批次存在，且必须是入库类型，CREATED状态
		StorageIoBatch batch = this.storageIoBatchDao.findOne(batchId);
		checkStorageIoBatchForInStorage(batchId, batch);
		batch.setAmount(batch.getAmount() + amount);
		this.storageIoBatchDao.save(batch);
		// 查找商品表（如果没有，则新增）
		StorageProduct prod = this.storageProductDao.findBySkuIdAndRepoId(skuId, batch.getRepoId());
		if (prod == null) {
			prod = buildNewStorageProduct(skuId, batch.getRepoId());
			this.storageProductDao.save(prod);
		}
		// 库位必须存在且可用
		StoragePosition pos = this.storagePositionDao.findByLabelAndRepoId(posLabel, batch.getRepoId());
		if(pos == null) {
			throw new IllegalArgumentException(String.format("库位不存在，repoId=%s, posLabel=%s", batch.getRepoId(), posLabel));
		}
		if(!StoragePosition.PosStatus.AVAILABLE.getValue().equals(pos.getPosStatus())) {
			throw new RuntimeException(String.format("库位状态不允许入库操作, posId=%s, status=%s", pos.getPosId(), pos.getPosStatus()));
		}
		saveStorageIoDetail(batch, pos, prod.getProductId(), skuId, amount, operator, null);
	}

	private void saveStorageIoDetail(StorageIoBatch batch, StoragePosition pos, Integer productId, Integer skuId, Integer amount, Integer operator, Integer orderId) {
		StorageIoDetail detail = new StorageIoDetail();
		detail.setSkuId(skuId);
		detail.setAmount(amount);
		detail.setProductId(productId);
		detail.setBatchId(batch.getBatchId());
		detail.setDetailStatus(DetailStatus.WAITING.getValue());
		if(BizType.isInStorage(batch.getBizType())) {
			detail.setIoDetailType(IoType.IN.getValue());
		} else {
			detail.setIoDetailType(IoType.OUT.getValue());
		}
		detail.setOperator(operator);
		detail.setPosLabel(pos.getLabel());
		detail.setPosId(pos.getPosId());
		detail.setRepoId(batch.getRepoId());
		detail.setOrderId(orderId);
		Date now = new Date();
		detail.setGmtCreate(now);
		detail.setGmtModify(now);
		this.storageIoDetailDao.save(detail);
	}

	private void checkStorageIoBatchForInStorage(Integer batchId, StorageIoBatch batch) {
		if(batch == null) {
			throw new IllegalArgumentException("入库批次不存在，batchId=" + batchId);
		}
		if(!StorageIoBatch.BizType.IN_BATCH.getValue().equals(batch.getBizType())) {
			throw new RuntimeException("入库批次类型错误, bizType=" + batch.getBizType());
		}
		if(!StorageIoBatch.Status.CREATED.getValue().equals(batch.getStatus())) {
			throw new RuntimeException("入库批次状态错误, status=" + batch.getStatus());
		}
	}

	@Override
	@Transactional
	public void confirmInStorage(Integer batchId, Integer operator) {
		if (batchId == null) {
			throw new IllegalArgumentException("batchId不能为空");
		}
		if (operator == null) {
			throw new IllegalArgumentException("operator不能为空");
		}
		// 批次存在，且必须是入库类型，CREATED状态
		StorageIoBatch batch = this.storageIoBatchDao.findOne(batchId);
		checkStorageIoBatchForInStorage(batchId, batch);
		this.storageIoBatchDao.save(batch);
		List<StorageIoDetail> list = this.storageIoDetailDao.findByBatchId(batchId);
		if(list == null || list.isEmpty()) {
			throw new RuntimeException("该批次没有记录，无法进行确认入库操作，batchId=" + batchId);
		}
		// 针对每一个入库明细：更新状态为已完成，增加库位库存，增加总库存
		for (StorageIoDetail detail : list) {
			inStorageForOne(batch, detail);
		}
		// 更新入库批次，st=已完成 
		batch.setStatus(StorageIoBatch.Status.FINISH.getValue());
	}

	private void inStorageForOne(StorageIoBatch batch, StorageIoDetail detail) {
		// 增加总库存（如果没有，则报错）
		StorageProduct prod = this.storageProductDao.findOne(detail.getProductId());
		if (prod == null) {
			throw new RuntimeException("商品不存在, productId=" + detail.getProductId());
		}
		prod.setStockAmt(prod.getStockAmt() + detail.getAmount());
		prod.setStockAvailable(prod.getStockAvailable() + detail.getAmount());
		this.storageProductDao.save(prod);
		// 更新入库明细，st=已完成 
		detail.setDetailStatus(DetailStatus.SUCCESS.getValue());
		detail.setProductId(prod.getProductId());
		this.storageIoDetailDao.save(detail);
		// 增加库位库存（如果没有，则新增）
		StoragePosStock posStock = this.storagePosStockDao.findByPosIdAndProductId(detail.getPosId(), prod.getProductId());
		if (posStock == null) {
			posStock = buildNewStoragePosStock(detail.getPosId(), prod.getProductId());
		}
		posStock.setTotalAmt(posStock.getTotalAmt() + detail.getAmount());
		this.storagePosStockDao.save(posStock);
	}

	private StoragePosStock buildNewStoragePosStock(Integer posId, Integer productId) {
		StoragePosStock stock = new StoragePosStock();
		stock.setProductId(productId);
		stock.setPosId(posId);
		stock.setTotalAmt(0);
		stock.setOccupyAmt(0);
		Date now = new Date();
		stock.setGmtCreate(now);
		stock.setGmtModify(now);
		return stock;
	}

	private StorageProduct buildNewStorageProduct(Integer skuId, Integer repoId) {
		// TODO lvbin 商品信息，需要查询bookService
		StorageProduct prod = new StorageProduct();
		prod.setLockFlag(false);
		prod.setRepoId(repoId);
		prod.setSkuId(skuId);
		prod.setSkuName("skuName");
		prod.setSubjectId(skuId);
		prod.setSubjectName("subjectName");
		prod.setStockAmt(0);
		prod.setStockAvailable(0);
		prod.setStockOccupy(0);
		Date now = new Date();
		prod.setGmtCreate(now);
		prod.setGmtModify(now);
		return prod;
	}

	@Override
	@Transactional
	public void cancelInStorage(Integer batchId, Integer operator) {
		if (batchId == null) {
			throw new IllegalArgumentException("batchId不能为空");
		}
		if (operator == null) {
			throw new IllegalArgumentException("operator不能为空");
		}
		// 批次存在，且必须是入库类型，CREATED状态
		StorageIoBatch batch = this.storageIoBatchDao.findOne(batchId);
		checkStorageIoBatchForInStorage(batchId, batch);
		batch.setStatus(StorageIoBatch.Status.CANCEL.getValue());
		// 更新入库明细，st=已取消 
		// 更新入库批次，st=已取消 
		this.storageIoBatchDao.save(batch);
		this.storageIoDetailDao.batchCancel(batchId);
	}

	@Override
	public void directInStorage(Integer repoId, Integer skuId, Integer amount, String posLabel, Integer operator) {
		// TODO Auto-generated method stub

	}

	@Override
	@Transactional
	public StoragePosition createStoragePosition(Integer repoId, String posLabel) {
		if (repoId == null) {
			throw new IllegalArgumentException("repoId不能为空");
		}
		if (StringUtils.isBlank(posLabel)) {
			throw new IllegalArgumentException("posLabel不能为空");
		}
		StoragePosition pos = new StoragePosition();
		pos.setLabel(posLabel);
		pos.setRepoId(repoId);
		pos.setPosStatus(PosStatus.AVAILABLE.getValue());
		this.storagePositionDao.save(pos);
		return pos;
	}

}

package com.xabaohui.modules.storage.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.xabaohui.modules.storage.entity.StorageIoDetail;

public interface StorageIoDetailDao extends CrudRepository<StorageIoDetail, Integer>,
		JpaSpecificationExecutor<StorageIoDetail> {

	List<StorageIoDetail> findByBatchId(Integer batchId);

	List<StorageIoDetail> findByBatchIdAndDetailStatus(Integer batchId, String detailStatus);
	
	List<StorageIoDetail> findByOrderId(Integer orderId);
	
	List<StorageIoDetail> findByBatchIdAndSkuIdAndPosId(Integer batchId, Integer skuId, Integer posId);
	
	@Modifying
	@Query("update StorageIoDetail set detailStatus='cancel' where batchId=:batchId")
	int batchCancel(@Param("batchId") Integer batchId);
	
	/**
	 * 查找(下一条)等待取件的记录
	 * @return
	 */
	@Query(countQuery="select count(*) from StorageIoDetail where ioDetailType='outwarehouse' and detailStatus='waiting' and batchId=:batchId",
			value="from StorageIoDetail where ioDetailType='outwarehouse' and detailStatus='waiting' and batchId=:batchId order by posLabel asc")
	Page<StorageIoDetail> findNextRecordForPickup(@Param("batchId")Integer batchId, Pageable page);
	
	/**
	 * 查找指定批次指定操作员锁定的正在处理的记录
	 * @param batchId
	 * @param operator
	 * @return
	 */
	@Query(countQuery="select count(*) from StorageIoDetail where ioDetailType='outwarehouse' and detailStatus='processing' and batchId=:batchId and operator=:operator",
			value="from StorageIoDetail where ioDetailType='outwarehouse' and detailStatus='processing' and batchId=:batchId and operator=:operator order by gmtModify asc")
	Page<StorageIoDetail> findProcessingRecordByBatchIdAndOperator(@Param("batchId")Integer batchId, @Param("operator")Integer operator, Pageable page);
}

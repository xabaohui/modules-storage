package com.xabaohui.modules.storage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.xabaohui.modules.storage.entity.StorageIoDetail;

public interface StorageIoDetailDao extends CrudRepository<StorageIoDetail, Integer>,
		JpaSpecificationExecutor<StorageIoDetail> {

	List<StorageIoDetail> findByBatchId(Integer batchId);
	
	List<StorageIoDetail> findByBatchIdAndSkuIdAndPosId(Integer batchId, Integer skuId, Integer posId);
}

package com.xabaohui.modules.storage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xabaohui.modules.storage.entity.StorageCheckDiff;

public interface StorageCheckDiffDao extends JpaRepository<StorageCheckDiff, Integer> {
	
	List<StorageCheckDiff> findByCheckId(Integer checkId);

	List<StorageCheckDiff> findByCheckIdAndSkuId(Integer checkId, Integer skuId);
}
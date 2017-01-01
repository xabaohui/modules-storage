package com.xabaohui.modules.storage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xabaohui.modules.storage.entity.StorageCheck;

public interface StorageCheckDao extends JpaRepository<StorageCheck, Integer>{
	
	List<StorageCheck> findByPlanId(Integer checkPlanId);
}
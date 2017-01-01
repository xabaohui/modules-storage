package com.xabaohui.modules.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xabaohui.modules.storage.entity.StorageCheckPlan;

public interface StorageCheckPlanDao extends JpaRepository<StorageCheckPlan, Integer> {
}
package com.xabaohui.modules.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xabaohui.modules.storage.entity.StoragePosLocation;

public interface StoragePosLocationDao extends JpaRepository<StoragePosLocation, Integer>{
}
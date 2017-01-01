package com.xabaohui.modules.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xabaohui.modules.storage.entity.StorageWarehouseInfo;

public interface StorageWarehouseInfoDao extends JpaRepository<StorageWarehouseInfo, Integer>{
}
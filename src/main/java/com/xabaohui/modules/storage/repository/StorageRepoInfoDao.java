package com.xabaohui.modules.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xabaohui.modules.storage.entity.StorageRepoInfo;

public interface StorageRepoInfoDao extends JpaRepository<StorageRepoInfo, Integer>{
}
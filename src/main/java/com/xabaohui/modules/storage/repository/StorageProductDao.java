package com.xabaohui.modules.storage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xabaohui.modules.storage.entity.StorageProduct;

public interface StorageProductDao extends JpaRepository<StorageProduct, Integer>{
	
	List<StorageProduct> findBySkuId(Integer skuId);
}
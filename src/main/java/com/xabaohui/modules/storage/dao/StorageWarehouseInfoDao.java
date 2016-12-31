package com.xabaohui.modules.storage.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.xabaohui.modules.storage.entity.StorageWarehouseInfo;

public interface StorageWarehouseInfoDao {

	void save(StorageWarehouseInfo transientInstance);

	void delete(StorageWarehouseInfo persistentInstance);

	StorageWarehouseInfo findById(java.lang.Integer id);

	List<StorageWarehouseInfo>  findByExample(StorageWarehouseInfo instance);
	
	void update(StorageWarehouseInfo instanceInfo);
	
	List<StorageWarehouseInfo> findByCriteria(DetachedCriteria criteria);
}
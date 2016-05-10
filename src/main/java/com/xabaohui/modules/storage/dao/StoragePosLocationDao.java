package com.xabaohui.modules.storage.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.xabaohui.modules.storage.entiry.StoragePosLocation;

public interface StoragePosLocationDao {

	void save(StoragePosLocation transientInstance);

	void delete(StoragePosLocation persistentInstance);

	StoragePosLocation findById(java.lang.Integer id);

	List<StoragePosLocation> findByExample(StoragePosLocation instance);
	
	void update(StoragePosLocation instance);

	List<StoragePosLocation> findByCriteria(DetachedCriteria criteria);
}
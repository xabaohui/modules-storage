package com.xabaohui.modules.storage.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.xabaohui.modules.storage.entiry.StoragePosition;

public interface StoragePositionDao {

	void save(StoragePosition transientInstance);

	void delete(StoragePosition persistentInstance);

	StoragePosition findById(java.lang.Integer id);
	
	List<StoragePosition> findByExample(StoragePosition instance);

	void update(StoragePosition instance);
	
	List<StoragePosition> findByCriteria(DetachedCriteria criteria);
}
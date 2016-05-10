package com.xabaohui.modules.storage.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.xabaohui.modules.storage.entiry.StorageCheckSnap;

public interface StorageCheckSnapDao {
	void save(StorageCheckSnap transientInstance);

	void delete(StorageCheckSnap persistentInstance);
	
	StorageCheckSnap findById(java.lang.Integer id);

	List<StorageCheckSnap> findByExample(StorageCheckSnap instance);
	
	void update(StorageCheckSnap instance);
	
	List<StorageCheckSnap> findByCriteria(DetachedCriteria criteria);
}
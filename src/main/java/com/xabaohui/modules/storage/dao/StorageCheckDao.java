package com.xabaohui.modules.storage.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.xabaohui.modules.storage.entiry.StorageCheck;

public interface StorageCheckDao {


	void save(StorageCheck transientInstance);

	void delete(StorageCheck persistentInstance);

	StorageCheck findById(java.lang.Integer id);

	List<StorageCheck> findByExample(StorageCheck instance);

	void update(StorageCheck instance);
	
	List<StorageCheck> findByCriteria(DetachedCriteria criteria);

}
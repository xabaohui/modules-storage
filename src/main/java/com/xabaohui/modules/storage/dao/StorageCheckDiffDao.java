package com.xabaohui.modules.storage.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.xabaohui.modules.storage.entiry.StorageCheckDiff;

public interface StorageCheckDiffDao {

	void save(StorageCheckDiff transientInstance);

	void delete(StorageCheckDiff persistentInstance);

	StorageCheckDiff findById(java.lang.Integer id);

	List<StorageCheckDiff> findByExample(StorageCheckDiff instance);
	
	void update(StorageCheckDiff instance);

	List<StorageCheckDiff> findByCriteria(DetachedCriteria criteria);

}
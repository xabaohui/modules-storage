package com.xabaohui.modules.storage.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import com.xabaohui.modules.storage.entiry.StorageIoTask;

public interface StorageIoTaskDao {

	void save(StorageIoTask transientInstance);

	void delete(StorageIoTask persistentInstance);

	StorageIoTask findById(java.lang.Integer id);

	List<StorageIoTask> findByExample(StorageIoTask instance);
	
	void update(StorageIoTask instance);

	List<StorageIoTask> findByCriteria(DetachedCriteria criteria);
}
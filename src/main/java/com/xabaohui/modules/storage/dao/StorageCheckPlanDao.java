package com.xabaohui.modules.storage.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.xabaohui.modules.storage.entity.StorageCheckPlan;

public interface StorageCheckPlanDao {

	void save(StorageCheckPlan transientInstance);
	
	void delete(StorageCheckPlan persistentInstance);
	
	StorageCheckPlan findById(java.lang.Integer id);
	
	List<StorageCheckPlan> findByExample(StorageCheckPlan instance);
	
	void update(StorageCheckPlan instance);
	
	List<StorageCheckPlan> findByCriteria(DetachedCriteria criteria);
}
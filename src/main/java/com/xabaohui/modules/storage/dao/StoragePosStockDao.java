package com.xabaohui.modules.storage.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.xabaohui.modules.storage.entity.StoragePosStock;

public interface StoragePosStockDao {

	void save(StoragePosStock transientInstance);

	void delete(StoragePosStock persistentInstance);

	StoragePosStock findById(java.lang.Integer id);

	List<StoragePosStock> findByExample(StoragePosStock instance);
	
	void update(StoragePosStock instance);
	
	List<StoragePosStock> findByCriteria(DetachedCriteria criteria);
}
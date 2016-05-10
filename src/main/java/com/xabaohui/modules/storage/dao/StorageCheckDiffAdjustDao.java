package com.xabaohui.modules.storage.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import com.xabaohui.modules.storage.entiry.StorageCheckDiffAdjust;

public interface StorageCheckDiffAdjustDao {

	void save(StorageCheckDiffAdjust transientInstance);

	void delete(StorageCheckDiffAdjust persistentInstance);

	StorageCheckDiffAdjust findById(java.lang.Integer id);

	List<StorageCheckDiffAdjust> findByExample(StorageCheckDiffAdjust instance);

	void update(StorageCheckDiffAdjust instance);
	
	List<StorageCheckDiffAdjust> findByCriteria(DetachedCriteria criteria);
}
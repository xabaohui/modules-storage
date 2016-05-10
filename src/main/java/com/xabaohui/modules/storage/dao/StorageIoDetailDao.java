package com.xabaohui.modules.storage.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.xabaohui.modules.storage.entiry.StorageIoDetail;
/**
 * 变动明细
 * @author 10313
 *
 */
public interface StorageIoDetailDao {

	void save(StorageIoDetail transientInstance);

	void delete(StorageIoDetail persistentInstance);

	StorageIoDetail findById(java.lang.Integer id);

	List<StorageIoDetail> findByExample(StorageIoDetail instance);

	void update(StorageIoDetail instance);
	
	List<StorageIoDetail> findByCriteria(DetachedCriteria criteria);

}
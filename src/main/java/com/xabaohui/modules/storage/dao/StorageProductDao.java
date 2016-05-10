package com.xabaohui.modules.storage.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import com.xabaohui.modules.storage.entiry.StorageProduct;

public interface StorageProductDao {

	void save(StorageProduct transientInstance);

	void delete(StorageProduct persistentInstance);

	StorageProduct findById(java.lang.Integer id);

	List<StorageProduct> findByExample(StorageProduct instance);

	void update(StorageProduct instance);

	List<StorageProduct> findByCriteria(DetachedCriteria criteria);
}
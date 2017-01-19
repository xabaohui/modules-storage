package com.xabaohui.modules.storage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.xabaohui.modules.storage.entity.StorageOrder;

public interface StorageOrderDao extends CrudRepository<StorageOrder, Integer> {

	@Query("from StorageOrder where orderId in (:ids)")
	List<StorageOrder> findByOrderIds(@Param("ids")List<Integer> ids);
}

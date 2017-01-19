package com.xabaohui.modules.storage.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.xabaohui.modules.storage.entity.StorageProductOccupy;

public interface StorageProductOccupyDao extends CrudRepository<StorageProductOccupy, Integer>{
	
	/**
	 * 按照订单Id查询库存占用记录
	 * @param orderId
	 * @return
	 */
	List<StorageProductOccupy> findByOrderId(Integer orderId);
}

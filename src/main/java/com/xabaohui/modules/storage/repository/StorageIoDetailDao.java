package com.xabaohui.modules.storage.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.xabaohui.modules.storage.entity.StorageIoDetail;

public interface StorageIoDetailDao extends CrudRepository<StorageIoDetail, Integer> {

	List<StorageIoDetail> findByTaskId(Integer taskId);
}

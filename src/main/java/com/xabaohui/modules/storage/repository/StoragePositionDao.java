package com.xabaohui.modules.storage.repository;

import org.springframework.data.repository.CrudRepository;

import com.xabaohui.modules.storage.entity.StoragePosition;

public interface StoragePositionDao extends CrudRepository<StoragePosition, Integer> {

	StoragePosition findByLabel(String label);
}

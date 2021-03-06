package com.xabaohui.modules.storage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.xabaohui.modules.storage.entity.StoragePosition;

public interface StoragePositionDao extends CrudRepository<StoragePosition, Integer> {
	
	/**
	 * 查找指定仓库下的所有库位
	 * @param repoId
	 * @return
	 */
	@Query("from StoragePosition where repoId=:repoId and posStatus!='delete'")
	List<StoragePosition> findByRepoId(@Param("repoId")Integer repoId);

	/**
	 * 按照库位标签查找库位
	 * @param label
	 * @param repoId
	 * @return
	 */
	@Query("from StoragePosition where label=:label and repoId=:repoId and posStatus!='delete'")
	StoragePosition findByLabelAndRepoId(@Param("label")String label, @Param("repoId")Integer repoId);
	
	
}

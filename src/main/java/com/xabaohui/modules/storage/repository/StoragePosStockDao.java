package com.xabaohui.modules.storage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.xabaohui.modules.storage.entity.StoragePosStock;

public interface StoragePosStockDao extends JpaRepository<StoragePosStock, Integer> {

	@Query(value = "from StoragePosStock where positionId = :positionId and amount > 0")
	List<StoragePosStock> findStoragePosStockByPositionId(@Param(value = "positionId") Integer positionId);

	@Query(value = "from StoragePosStock where skuId = :skuId and positionId = :positionId and amount > 0")
	List<StoragePosStock> findStoragePosStockBySkuIdAndPositionId(@Param(value = "skuId") Integer skuId,
			@Param(value = "positionId") Integer positionId);

	@Query(value = "from StoragePosStock where skuId = :skuId and amount > 0 ORDER BY amount DESC")
	List<StoragePosStock> findBySkuIdOrderByPositionIdAndAmount(@Param(value = "skuId") Integer skuId);

}
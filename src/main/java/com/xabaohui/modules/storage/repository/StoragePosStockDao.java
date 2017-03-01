package com.xabaohui.modules.storage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.xabaohui.modules.storage.dto.StockDTO;
import com.xabaohui.modules.storage.entity.StoragePosStock;

public interface StoragePosStockDao extends JpaRepository<StoragePosStock, Integer> {

	/**
	 * 查询指定库位的商品
	 * 
	 * @param posId
	 * @return
	 */
	@Query(value = "from StoragePosStock where posId = :posId and totalAmt > occupyAmt")
	List<StoragePosStock> findByPosId(@Param(value = "posId") Integer posId);
	
	/**
	 * 查询指定库位指定商品
	 * 
	 * @param posId
	 * @return
	 */
	StoragePosStock findByPosIdAndProductId(Integer posId, Integer productId);
	
	/**
	 * 查询可用库存分布
	 * @param productId
	 * @return
	 */
	@Query("select new com.xabaohui.modules.storage.dto.StockDTO(s.stockId, pos.posId, pos.label, prod.repoId, prod.productId, prod.skuId, s.totalAmt, s.occupyAmt) " +
			"from StoragePosStock s, StoragePosition pos, StorageProduct prod " +
			"where s.posId=pos.posId and s.productId=prod.productId " +
			"and s.totalAmt > s.occupyAmt and s.productId = :productId " +
			"order by (s.totalAmt - s.occupyAmt) desc")
	List<StockDTO> findAvailableStock(@Param("productId")Integer productId);
}
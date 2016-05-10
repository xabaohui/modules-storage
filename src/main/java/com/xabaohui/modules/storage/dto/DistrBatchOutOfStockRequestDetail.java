package com.xabaohui.modules.storage.dto;

/**
 * 缺货重配请求明细
 * @author cxin
 *
 */
public class DistrBatchOutOfStockRequestDetail {
	private String posLabel;//缺货库位标签
	private Integer skuCount;//缺货数量
	private Integer skuId;//缺货商品skuId
	
	
	
	
	public DistrBatchOutOfStockRequestDetail() {
	}
	
	public DistrBatchOutOfStockRequestDetail(String posLabel, Integer skuCount,
			Integer skuId) {
		this.posLabel = posLabel;
		this.skuCount = skuCount;
		this.skuId = skuId;
	}

	public String getPosLabel() {
		return posLabel;
	}
	public void setPosLabel(String posLabel) {
		this.posLabel = posLabel;
	}
	public Integer getSkuCount() {
		return skuCount;
	}
	public void setSkuCount(Integer skuCount) {
		this.skuCount = skuCount;
	}
	public Integer getSkuId() {
		return skuId;
	}
	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}
	
}

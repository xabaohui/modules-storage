package com.xabaohui.modules.storage.dto;

public class DistrBatchRequestDetail {

	private Integer skuId;
	private Integer skuCount;

	public DistrBatchRequestDetail() {
	}

	public DistrBatchRequestDetail(Integer skuId, Integer skuCount) {
		this.skuId = skuId;
		this.skuCount = skuCount;
	}

	public Integer getSkuId() {
		return skuId;
	}

	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}

	public Integer getSkuCount() {
		return skuCount;
	}

	public void setSkuCount(Integer skuCount) {
		this.skuCount = skuCount;
	}
}

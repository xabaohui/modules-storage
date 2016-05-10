package com.xabaohui.modules.storage.dto;
/**
 * 缺货重配结果明细
 * @author cxin
 *
 */
public class DistrBatchOutOfStockResponseDetail {
	private String posLabel;//库位标签
	private Integer skuId;
	private Integer skuCount;
	
	
	public DistrBatchOutOfStockResponseDetail() {
	}
	public DistrBatchOutOfStockResponseDetail(String posLabel, Integer skuId,
			Integer skuCount) {
		super();
		this.posLabel = posLabel;
		this.skuId = skuId;
		this.skuCount = skuCount;
	}
	public String getPosLabel() {
		return posLabel;
	}
	public void setPosLabel(String posLabel) {
		this.posLabel = posLabel;
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

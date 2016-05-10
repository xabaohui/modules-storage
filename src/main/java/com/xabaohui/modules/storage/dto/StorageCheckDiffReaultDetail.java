package com.xabaohui.modules.storage.dto;

public class StorageCheckDiffReaultDetail {
	private Integer skuId;
	private Integer stockamount;//库存量，+ —
	private Integer realamount;//真实量
	
	
	public StorageCheckDiffReaultDetail(Integer skuId, Integer realamount,Integer stockamount){
		this.skuId = skuId;
		this.stockamount = stockamount;
		this.realamount = realamount;
	}


	public Integer getSkuId() {
		return skuId;
	}


	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}


	public Integer getStockamount() {
		return stockamount;
	}


	public void setStockamount(Integer stockamount) {
		this.stockamount = stockamount;
	}


	public Integer getRealamount() {
		return realamount;
	}


	public void setRealamount(Integer realamount) {
		this.realamount = realamount;
	}
	
}

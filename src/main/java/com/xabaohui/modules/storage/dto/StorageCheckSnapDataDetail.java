package com.xabaohui.modules.storage.dto;

public class StorageCheckSnapDataDetail implements Comparable<StorageCheckSnapDataDetail>{
	private Integer skuId;
	private Integer snapNum;
	
	
	
	public StorageCheckSnapDataDetail(Integer skuId, Integer snapNum) {
		this.skuId = skuId;
		this.snapNum = snapNum;
	}
	public Integer getSkuId() {
		return skuId;
	}
	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}
	public Integer getSnapNum() {
		return snapNum;
	}
	public void setSnapNum(Integer snapNum) {
		this.snapNum = snapNum;
	}
	public boolean equals(StorageCheckSnapDataDetail obj) {
		if(this.getSkuId().equals(obj.getSkuId())&&
				(this.getSnapNum().equals(obj.getSnapNum()))){
			return true;
		}else{
			return false;
		}
	}
	public int compareTo(StorageCheckSnapDataDetail obj) {
		if(this.getSkuId()>obj.getSkuId()){
			return 1;
		}
		if(this.getSkuId()<obj.getSkuId()){
			return -1;
		}
		return 0;
	}
	
}

package com.xabaohui.modules.storage.entiry;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * StorageCheckSnap entity. @author MyEclipse Persistence Tools
 */

public class StorageCheckSnap  implements java.io.Serializable,Comparable<StorageCheckSnap>{

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 8321156346003770766L;
	private Integer checkSnapId;
	private Integer checkId;
	private Integer skuId;
	private Integer snapNum;
	private String checkTime;
	private Boolean isdelete;
	private Timestamp gmtCreate;
	private Timestamp gmtModify;
	private Integer version;

	// Constructors

	/** default constructor */
	public StorageCheckSnap() {
	}

	/** full constructor */
	public StorageCheckSnap(Integer checkId, Integer skuId, Integer snapNum,
			String checkTime, Boolean isdelete, Timestamp gmtCreate,
			Timestamp gmtModify, Integer version) {
		this.checkId = checkId;
		this.skuId = skuId;
		this.snapNum = snapNum;
		this.checkTime = checkTime;
		this.isdelete = isdelete;
		this.gmtCreate = gmtCreate;
		this.gmtModify = gmtModify;
		this.version = version;
	}

	// Property accessors

	public Integer getCheckSnapId() {
		return this.checkSnapId;
	}

	public void setCheckSnapId(Integer checkSnapId) {
		this.checkSnapId = checkSnapId;
	}

	public Integer getCheckId() {
		return this.checkId;
	}

	public void setCheckId(Integer checkId) {
		this.checkId = checkId;
	}

	public Integer getSkuId() {
		return this.skuId;
	}

	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}

	public Integer getSnapNum() {
		return this.snapNum;
	}

	public void setSnapNum(Integer snapNum) {
		this.snapNum = snapNum;
	}

	public String getCheckTime() {
		return this.checkTime;
	}

	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}

	public Boolean getIsdelete() {
		return this.isdelete;
	}

	public void setIsdelete(Boolean isdelete) {
		this.isdelete = isdelete;
	}

	public Timestamp getGmtCreate() {
		return this.gmtCreate;
	}

	public void setGmtCreate(Timestamp gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Timestamp getGmtModify() {
		return this.gmtModify;
	}

	public void setGmtModify(Timestamp gmtModify) {
		this.gmtModify = gmtModify;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public boolean equals(StorageCheckSnap obj) {
		if(this.getSkuId().equals(obj.getSkuId())&&
				this.getSnapNum().equals(obj.getSnapNum())){
			return true;
		}
		return false;
	}

	public int compareTo(StorageCheckSnap obj) {
		if(this.getSkuId()>obj.getSkuId()){
			return 1;
		}
		if(this.getSkuId()<obj.getSkuId()){
			return -1;
		}
		return 0;
	}
	public static void main(String[] args) {
		StorageCheckSnap snap=new StorageCheckSnap();
		snap.setSkuId(10002);
		snap.setSnapNum(10);
		StorageCheckSnap snap2=new StorageCheckSnap();
		snap2.setSkuId(10002);
		snap2.setSnapNum(10);
		System.out.println(snap.equals(snap2));
		List<StorageCheckSnap> list=new ArrayList<StorageCheckSnap>();
		list.add(snap2);
		list.add(snap);
		 for (StorageCheckSnap storageCheckSnap : list) {
				System.out.println(storageCheckSnap.getSkuId());
			}
		 Collections.sort(list);
		 for (StorageCheckSnap storageCheckSnap : list) {
			System.out.println(storageCheckSnap.getSkuId());
		}
	}
}
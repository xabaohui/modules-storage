package com.xabaohui.modules.storage.entiry;

import java.sql.Timestamp;

/**
 * StoragePosLocation entity. @author MyEclipse Persistence Tools
 */

public class StoragePosLocation implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -8421844942668059302L;
	private Integer posLocId;
	private String currentLable;
	private Integer parentId;
	private Timestamp gmtCreate;
	private Timestamp gmtModify;
	private Integer version;
	private String memo;

	// Constructors

	/** default constructor */
	public StoragePosLocation() {
	}

	/** full constructor */
	public StoragePosLocation(String currentLable, Integer parentId,
			Timestamp gmtCreate, Timestamp gmtModify, Integer version,
			String memo) {
		this.currentLable = currentLable;
		this.parentId = parentId;
		this.gmtCreate = gmtCreate;
		this.gmtModify = gmtModify;
		this.version = version;
		this.memo = memo;
	}

	// Property accessors

	public Integer getPosLocId() {
		return this.posLocId;
	}

	public void setPosLocId(Integer posLocId) {
		this.posLocId = posLocId;
	}

	public String getCurrentLable() {
		return this.currentLable;
	}

	public void setCurrentLable(String currentLable) {
		this.currentLable = currentLable;
	}

	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
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

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
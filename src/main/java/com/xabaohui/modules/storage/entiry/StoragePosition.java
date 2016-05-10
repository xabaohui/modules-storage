package com.xabaohui.modules.storage.entiry;

import java.sql.Timestamp;

/**
 * StoragePosition entity. @author MyEclipse Persistence Tools
 */

public class StoragePosition implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -2842713714039775273L;
	private Integer positionId;
	private String positionLabel;
	private Integer positionCapacity;
	private Boolean positionIsfull;
	private Boolean positionIslock;
	private String lockReason;
	private Timestamp gmtCreate;
	private Timestamp gmtModify;
	private Integer version;
	private String memo;

	// Constructors

	/** default constructor */
	public StoragePosition() {
	}

	/** full constructor */
	public StoragePosition(String positionLabel, Integer positionCapacity,
			Boolean positionIsfull, Boolean positionIslock, String lockReason,
			Timestamp gmtCreate, Timestamp gmtModify, Integer version,
			String memo) {
		this.positionLabel = positionLabel;
		this.positionCapacity = positionCapacity;
		this.positionIsfull = positionIsfull;
		this.positionIslock = positionIslock;
		this.lockReason = lockReason;
		this.gmtCreate = gmtCreate;
		this.gmtModify = gmtModify;
		this.version = version;
		this.memo = memo;
	}

	// Property accessors

	public Integer getPositionId() {
		return this.positionId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	public String getPositionLabel() {
		return this.positionLabel;
	}

	public void setPositionLabel(String positionLabel) {
		this.positionLabel = positionLabel;
	}

	public Integer getPositionCapacity() {
		return this.positionCapacity;
	}

	public void setPositionCapacity(Integer positionCapacity) {
		this.positionCapacity = positionCapacity;
	}

	public Boolean getPositionIsfull() {
		return this.positionIsfull;
	}

	public void setPositionIsfull(Boolean positionIsfull) {
		this.positionIsfull = positionIsfull;
	}

	public Boolean getPositionIslock() {
		return this.positionIslock;
	}

	public void setPositionIslock(Boolean positionIslock) {
		this.positionIslock = positionIslock;
	}

	public String getLockReason() {
		return this.lockReason;
	}

	public void setLockReason(String lockReason) {
		this.lockReason = lockReason;
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
package com.xabaohui.modules.storage.entity;

import java.sql.Timestamp;

/**
 * StorageCheckPlan entity. @author MyEclipse Persistence Tools
 */

public class StorageCheckPlan implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 3984228363061465195L;
	private Integer planId;
	private Integer userId;
	private String status;
	private Integer posCount;
	private Timestamp gmtCreate;
	private Timestamp gmtModify;
	private Integer version;
	private String memo;

	// Constructors

	/** default constructor */
	public StorageCheckPlan() {
	}

	/** full constructor */
	public StorageCheckPlan(Integer userId, String status, Integer posCount,
			Timestamp gmtCreate, Timestamp gmtModify, Integer version,
			String memo) {
		this.userId = userId;
		this.status = status;
		this.posCount = posCount;
		this.gmtCreate = gmtCreate;
		this.gmtModify = gmtModify;
		this.version = version;
		this.memo = memo;
	}

	// Property accessors

	public Integer getPlanId() {
		return this.planId;
	}

	public void setPlanId(Integer planId) {
		this.planId = planId;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getPosCount() {
		return this.posCount;
	}

	public void setPosCount(Integer posCount) {
		this.posCount = posCount;
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
package com.xabaohui.modules.storage.entiry;

import java.sql.Timestamp;

/**
 * StorageIoTask entity. @author MyEclipse Persistence Tools
 */

public class StorageIoTask implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5687364144151442202L;
	private Integer taskId;
	private Integer userId;
	private String bizType;
	private Integer amount;
	private String status;
	private Timestamp gmtCreate;
	private Timestamp gmtModify;
	private String memo;
	private Integer version;

	// Constructors

	/** default constructor */
	public StorageIoTask() {
	}

	/** full constructor */
	public StorageIoTask(Integer userId, String bizType, Integer amount,
			String status, Timestamp gmtCreate, Timestamp gmtModify,
			String memo, Integer version) {
		this.userId = userId;
		this.bizType = bizType;
		this.amount = amount;
		this.status = status;
		this.gmtCreate = gmtCreate;
		this.gmtModify = gmtModify;
		this.memo = memo;
		this.version = version;
	}

	// Property accessors

	public Integer getTaskId() {
		return this.taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getBizType() {
		return this.bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public Integer getAmount() {
		return this.amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}
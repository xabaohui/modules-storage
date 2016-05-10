package com.xabaohui.modules.storage.entiry;

import java.sql.Timestamp;

/**
 * StorageIoDetail entity. @author MyEclipse Persistence Tools
 */

public class StorageIoDetail implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -8747200343987870893L;
	private Integer detailId;
	private Integer taskId;
	private Integer positionId;
	private Integer skuId;
	private Integer amount;
	private String ioDetailType;
	private String memo;
	private Timestamp gmtCreate;
	private Timestamp gmtModify;
	private Integer version;

	// Constructors

	/** default constructor */
	public StorageIoDetail() {
	}

	/** full constructor */
	public StorageIoDetail(Integer taskId, Integer positionId, Integer skuId,
			Integer amount, String ioDetailType, String memo,
			Timestamp gmtCreate, Timestamp gmtModify, Integer version) {
		this.taskId = taskId;
		this.positionId = positionId;
		this.skuId = skuId;
		this.amount = amount;
		this.ioDetailType = ioDetailType;
		this.memo = memo;
		this.gmtCreate = gmtCreate;
		this.gmtModify = gmtModify;
		this.version = version;
	}
	// Property accessors
	
	
	public Integer getDetailId() {
		return this.detailId;
	}

	public String getIoDetailType() {
		return ioDetailType;
	}

	public void setIoDetailType(String ioDetailType) {
		this.ioDetailType = ioDetailType;
	}

	public void setDetailId(Integer detailId) {
		this.detailId = detailId;
	}

	public Integer getTaskId() {
		return this.taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public Integer getPositionId() {
		return this.positionId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	public Integer getSkuId() {
		return this.skuId;
	}

	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}

	public Integer getAmount() {
		return this.amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
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

}
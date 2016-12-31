package com.xabaohui.modules.storage.entity;

import java.sql.Timestamp;

/**
 * StorageCheckDiff entity. @author MyEclipse Persistence Tools
 */

public class StorageCheckDiff implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 4247560478492228211L;
	private Integer checkDiffId;
	private Integer checkId;
	private Integer positionId;
	private Integer skuId;
	private Integer realamount;
	private Integer stockamount;
	private String checkDiffStatus;
	private Timestamp gmtCreate;
	private Timestamp gmtModify;
	private Integer version;

	// Constructors

	/** default constructor */
	public StorageCheckDiff() {
	}

	/** full constructor */
	public StorageCheckDiff(Integer checkId, Integer positionId, Integer skuId,
			Integer realamount, Integer stockamount, String checkDiffStatus,
			Timestamp gmtCreate, Timestamp gmtModify, Integer version) {
		this.checkId = checkId;
		this.positionId = positionId;
		this.skuId = skuId;
		this.realamount = realamount;
		this.stockamount = stockamount;
		this.checkDiffStatus = checkDiffStatus;
		this.gmtCreate = gmtCreate;
		this.gmtModify = gmtModify;
		this.version = version;
	}
	// Property accessors

	
	
	public Integer getCheckDiffId() {
		return this.checkDiffId;
	}

	public Integer getPositionId() {
		return positionId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	public String getCheckDiffStatus() {
		return checkDiffStatus;
	}

	public void setCheckDiffStatus(String checkDiffStatus) {
		this.checkDiffStatus = checkDiffStatus;
	}

	public void setCheckDiffId(Integer checkDiffId) {
		this.checkDiffId = checkDiffId;
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

	public Integer getRealamount() {
		return this.realamount;
	}

	public void setRealamount(Integer realamount) {
		this.realamount = realamount;
	}

	public Integer getStockamount() {
		return stockamount;
	}

	public void setStockamount(Integer stockamount) {
		this.stockamount = stockamount;
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
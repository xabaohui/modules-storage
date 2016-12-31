package com.xabaohui.modules.storage.entity;

import java.sql.Timestamp;
import java.util.Date;

/**
 * StorageProduct entity. @author MyEclipse Persistence Tools
 */

public class StorageProduct implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -2373349269736459947L;
	private Integer stockId;
	private Integer skuId;
	private Integer subjectId;
	private Integer stockAmt;
	private Integer stockOccupy;
	private Integer stockAvailabe;
	private Boolean lockFlag;
	private String lockReason;
	private Date gmtCreate;
	private Date gmtModify;
	private Integer version;

	// Constructors

	/** default constructor */
	public StorageProduct() {
	}

	/** full constructor */
	public StorageProduct(Integer skuId, Integer subjectId, Integer stockAmt,
			Integer stockOccupy, Integer stockAvailabe, Boolean lockFlag,
			String lockReason, Timestamp gmtCreate, Timestamp gmtModify,
			Integer version) {
		this.skuId = skuId;
		this.subjectId = subjectId;
		this.stockAmt = stockAmt;
		this.stockOccupy = stockOccupy;
		this.stockAvailabe = stockAvailabe;
		this.lockFlag = lockFlag;
		this.lockReason = lockReason;
		this.gmtCreate = gmtCreate;
		this.gmtModify = gmtModify;
		this.version = version;
	}

	// Property accessors

	public Integer getStockId() {
		return this.stockId;
	}

	public void setStockId(Integer stockId) {
		this.stockId = stockId;
	}

	public Integer getSkuId() {
		return this.skuId;
	}

	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}

	public Integer getSubjectId() {
		return this.subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public Integer getStockAmt() {
		return this.stockAmt;
	}

	public void setStockAmt(Integer stockAmt) {
		this.stockAmt = stockAmt;
	}

	public Integer getStockOccupy() {
		return this.stockOccupy;
	}

	public void setStockOccupy(Integer stockOccupy) {
		this.stockOccupy = stockOccupy;
	}

	public Integer getStockAvailabe() {
		return this.stockAvailabe;
	}

	public void setStockAvailabe(Integer stockAvailabe) {
		this.stockAvailabe = stockAvailabe;
	}

	public Boolean getLockFlag() {
		return this.lockFlag;
	}

	public void setLockFlag(Boolean lockFlag) {
		this.lockFlag = lockFlag;
	}

	public String getLockReason() {
		return this.lockReason;
	}

	public void setLockReason(String lockReason) {
		this.lockReason = lockReason;
	}

	public Date getGmtCreate() {
		return this.gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModify() {
		return this.gmtModify;
	}

	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}
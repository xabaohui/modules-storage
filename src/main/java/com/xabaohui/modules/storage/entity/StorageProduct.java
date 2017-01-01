package com.xabaohui.modules.storage.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * StorageProduct entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "storage_product")
public class StorageProduct {

	@Id
	@GeneratedValue
	@Column(name = "stock_id")
	private Integer stockId;

	@Column(name = "sku_id")
	private Integer skuId;

	@Column(name = "subject_id")
	private Integer subjectId;

	@Column(name = "stock_amt")
	private Integer stockAmt;

	@Column(name = "stock_occupy")
	private Integer stockOccupy;

	@Column(name = "stock_availabe")
	private Integer stockAvailabe;

	@Column(name = "lock_flag")
	private Boolean lockFlag;

	@Column(name = "lock_reason")
	private String lockReason;

	@Column(name = "gmt_create", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date gmtCreate;

	@Column(name = "gmt_modify")
	@Temporal(TemporalType.TIMESTAMP)
	private Date gmtModify;
	
	@Version
	@Column(name = "version")
	private Integer version;

	// Constructors

	/** default constructor */
	public StorageProduct() {
	}

	/** full constructor */
	public StorageProduct(Integer skuId, Integer subjectId, Integer stockAmt, Integer stockOccupy,
			Integer stockAvailabe, Boolean lockFlag, String lockReason, Date gmtCreate, Date gmtModify,
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
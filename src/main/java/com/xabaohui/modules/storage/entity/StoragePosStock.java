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
 * StoragePosStock entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "storage_pos_stock")
public class StoragePosStock {

	@Id
	@GeneratedValue
	@Column(name = "pos_stock_id")
	private Integer posStockId;

	@Column(name = "position_id")
	private Integer positionId;

	@Column(name = "sku_id")
	private Integer skuId;

	@Column(name = "amount")
	private Integer amount;

	@Column(name = "mark")
	private String mark;

	@Column(name = "gmt_create", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date gmtCreate;

	@Column(name = "gmt_modify")
	@Temporal(TemporalType.TIMESTAMP)
	private Date gmtModify;

	@Version
	@Column(name = "version")
	private Integer version;

	@Column(name = "memo")
	private String memo;

	// Constructors

	/** default constructor */
	public StoragePosStock() {
	}

	/** full constructor */
	public StoragePosStock(Integer positionId, Integer skuId, Integer amount, String mark, Date gmtCreate,
			Date gmtModify, Integer version, String memo) {
		this.positionId = positionId;
		this.skuId = skuId;
		this.amount = amount;
		this.mark = mark;
		this.gmtCreate = gmtCreate;
		this.gmtModify = gmtModify;
		this.version = version;
		this.memo = memo;
	}

	// Property accessors

	public Integer getPosStockId() {
		return this.posStockId;
	}

	public void setPosStockId(Integer posStockId) {
		this.posStockId = posStockId;
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

	public String getMark() {
		return this.mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
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

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
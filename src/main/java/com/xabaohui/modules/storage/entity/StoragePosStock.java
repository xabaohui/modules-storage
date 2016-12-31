package com.xabaohui.modules.storage.entity;

import java.sql.Timestamp;
import java.util.Date;

/**
 * StoragePosStock entity. @author MyEclipse Persistence Tools
 */

public class StoragePosStock implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -8486704688398731274L;
	private Integer posStockId;
	private Integer positionId;
	private Integer skuId;
	private Integer amount;
	private String mark;
	private Date gmtCreate;
	private Date gmtModify;
	private Integer version;
	private String memo;

	// Constructors

	/** default constructor */
	public StoragePosStock() {
	}

	/** full constructor */
	public StoragePosStock(Integer positionId, Integer skuId, Integer amount,
			String mark, Timestamp gmtCreate, Timestamp gmtModify,
			Integer version, String memo) {
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
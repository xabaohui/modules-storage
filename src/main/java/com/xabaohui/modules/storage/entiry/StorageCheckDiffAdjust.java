package com.xabaohui.modules.storage.entiry;

import java.sql.Timestamp;

/**
 * StorageCheckDiffAdjust entity. @author MyEclipse Persistence Tools
 */

public class StorageCheckDiffAdjust implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 4598706158675685701L;
	private Integer adjustId;
	private Integer checkId;
	private Integer skuId;
	private Integer adjustNum;
	private Integer operator;
	private Timestamp gmtCreate;
	private Timestamp gmtModify;
	private Integer version;
	private String memo;

	// Constructors

	/** default constructor */
	public StorageCheckDiffAdjust() {
	}

	/** minimal constructor */
	public StorageCheckDiffAdjust(Integer adjustId) {
		this.adjustId = adjustId;
	}

	/** full constructor */
	public StorageCheckDiffAdjust(Integer adjustId, Integer checkId,
			Integer skuId, Integer adjustNum, Integer operator,
			Timestamp gmtCreate, Timestamp gmtModify, Integer version,
			String memo) {
		this.adjustId = adjustId;
		this.checkId = checkId;
		this.skuId = skuId;
		this.adjustNum = adjustNum;
		this.operator = operator;
		this.gmtCreate = gmtCreate;
		this.gmtModify = gmtModify;
		this.version = version;
		this.memo = memo;
	}

	// Property accessors

	public Integer getAdjustId() {
		return this.adjustId;
	}

	public void setAdjustId(Integer adjustId) {
		this.adjustId = adjustId;
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

	public Integer getAdjustNum() {
		return this.adjustNum;
	}

	public void setAdjustNum(Integer adjustNum) {
		this.adjustNum = adjustNum;
	}


	public Integer getOperator() {
		return operator;
	}

	public void setOperator(Integer operator) {
		this.operator = operator;
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
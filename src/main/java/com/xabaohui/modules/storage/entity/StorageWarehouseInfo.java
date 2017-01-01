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
 * StorageWarehouseInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "storage_warehouse_info")
public class StorageWarehouseInfo {
	
	@Id
	@GeneratedValue
	@Column(name = "warehouse_id")
	private Integer warehouseId;
	
	@Column(name = "warehouse_name")
	private String warehouseName;
	
	@Column(name = "user_id")
	private Integer userId;
	
	@Column(name = "gmt_create",updatable = false)
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
	public StorageWarehouseInfo() {
	}

	/** full constructor */
	public StorageWarehouseInfo(String warehouseName, Integer userId,
			Date gmtCreate, Date gmtModify, Integer version,
			String memo) {
		this.warehouseName = warehouseName;
		this.userId = userId;
		this.gmtCreate = gmtCreate;
		this.gmtModify = gmtModify;
		this.version = version;
		this.memo = memo;
	}

	// Property accessors

	public Integer getWarehouseId() {
		return this.warehouseId;
	}

	public void setWarehouseId(Integer warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getWarehouseName() {
		return this.warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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
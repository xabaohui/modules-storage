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
 * StoragePosLocation entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "storage_pos_location")
public class StoragePosLocation {
	
	@Id
	@GeneratedValue
	@Column(name = "pos_loc_id")
	private Integer posLocId;
	
	@Column(name = "current_lable", length = 128)
	private String currentLable;
	
	@Column(name = "parent_id")
	private Integer parentId;
	
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
	public StoragePosLocation() {
	}

	/** full constructor */
	public StoragePosLocation(String currentLable, Integer parentId,
			Date gmtCreate, Date gmtModify, Integer version,
			String memo) {
		this.currentLable = currentLable;
		this.parentId = parentId;
		this.gmtCreate = gmtCreate;
		this.gmtModify = gmtModify;
		this.version = version;
		this.memo = memo;
	}

	// Property accessors

	public Integer getPosLocId() {
		return this.posLocId;
	}

	public void setPosLocId(Integer posLocId) {
		this.posLocId = posLocId;
	}

	public String getCurrentLable() {
		return this.currentLable;
	}

	public void setCurrentLable(String currentLable) {
		this.currentLable = currentLable;
	}

	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
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
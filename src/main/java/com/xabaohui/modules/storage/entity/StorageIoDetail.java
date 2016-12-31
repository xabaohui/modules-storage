package com.xabaohui.modules.storage.entity;

import java.sql.Timestamp;
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
 * StorageIoDetail entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "storage_io_detail")
public class StorageIoDetail {

	// Fields
	@Id
	@GeneratedValue
	@Column(name = "detail_id")
	private Integer detailId;

	@Column(name = "task_id", nullable = false, updatable = false)
	private Integer taskId;

	@Column(name = "position_id", nullable = false, updatable = false)
	private Integer positionId;

	@Column(name = "sku_id", nullable = false, updatable = false)
	private Integer skuId;
	
	@Column(name = "amount", nullable = false)
	private Integer amount;
	
	@Column(name = "io_detail_type", nullable = false)
	private String ioDetailType;
	
	@Column(name = "memo")
	private String memo;
	
	@Column(name = "gmt_create", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date gmtCreate;

	@Column(name = "gmt_modify")
	@Temporal(TemporalType.TIMESTAMP)
	private Date gmtModify;
	
	@Version
	@Column(name = "version")
	private Integer version;

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

	/**
	 * 出入库类型
	 * 
	 * @author yz
	 * 
	 */
	public enum IoType {
		IN("inwarehouse", "入库"), OUT("outwarehouse", "出库");

		private String value;
		private String display;

		private IoType(String value, String display) {
			this.value = value;
			this.display = display;
		}

		public String getValue() {
			return value;
		}

		public String getDisplay() {
			return display;
		}
	}
}
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
 * StorageIoTask entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "storage_invoice")
public class StorageIoTask {

	@Id
	@GeneratedValue
	@Column(name = "invoice_id")
	private Integer taskId;

	@Column(name = "out_trade_no", length = 64, nullable = false, updatable = false)
	private String outTradeNo;

	@Column(name = "user_id")
	private Integer userId;

	@Column(name = "biz_type", nullable = false)
	private String bizType;

	@Column(name = "amount", nullable = false)
	private Integer amount;

	@Column(name = "status", nullable = false)
	private String status;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "gmt_create", nullable = false, updatable = false)
	private Date gmtCreate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "gmt_modify", nullable = false)
	private Date gmtModify;

	@Column(name = "memo")
	private String memo;

	@Version
	@Column(name = "version")
	private Integer version;

	// Property accessors

	public Integer getTaskId() {
		return this.taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getBizType() {
		return this.bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public Integer getAmount() {
		return this.amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	/**
	 * 单据业务类型
	 * @author yz
	 *
	 */
	public enum BizType {
		ORDER("order", "订单出库");
		
		private String value;
		private String display;

		private BizType(String value, String display) {
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

	/**
	 * 单据状态
	 * @author yz
	 *
	 */
	public enum InvoiceStatus {
		CREATED("created", "已创建"), MATCHED("matched", "已匹配库位"), PRINTED("printed", "已打印"), FINISH("finish", "已完成"), CANCEL(
				"cancel", "已取消");

		private String value;
		private String display;

		private InvoiceStatus(String value, String display) {
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
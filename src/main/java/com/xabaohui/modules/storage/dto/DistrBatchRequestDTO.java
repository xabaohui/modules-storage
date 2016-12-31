package com.xabaohui.modules.storage.dto;

import java.util.List;

public class DistrBatchRequestDTO {

	private String outTradeNo; // 外部交易号
	private Integer operateor;// 操作员ID
	private String memo;// 操作备注
	private List<DistrBatchRequestDetail> list;

	public DistrBatchRequestDTO() {
	}

	public DistrBatchRequestDTO(Integer operateor, String memo, List<DistrBatchRequestDetail> list) {
		this.operateor = operateor;
		this.memo = memo;
		this.list = list;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getOperateor() {
		return operateor;
	}

	public void setOperateor(Integer operateor) {
		this.operateor = operateor;
	}

	public List<DistrBatchRequestDetail> getList() {
		return list;
	}

	public void setList(List<DistrBatchRequestDetail> list) {
		this.list = list;
	}

}

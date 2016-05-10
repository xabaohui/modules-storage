package com.xabaohui.modules.storage.dto;

import java.util.List;

/**
 *缺货重配结果对象
 * @author cxin
 *
 */
public class DistrBatchOutOfStockResponseDTO {
	private Integer taskId;//配货流水号
	private boolean isSuccess;
	private String failReason;
	private List<DistrBatchOutOfStockResponseDetail> list;
	
	public DistrBatchOutOfStockResponseDTO(){}
	public DistrBatchOutOfStockResponseDTO(Integer taskId, boolean isSuccess,
			String failReason, List<DistrBatchOutOfStockResponseDetail> list) {
		this.taskId = taskId;
		this.isSuccess = isSuccess;
		this.failReason = failReason;
		this.list = list;
	}
	public Integer getTaskId() {
		return taskId;
	}
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getFailReason() {
		return failReason;
	}
	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}
	public List<DistrBatchOutOfStockResponseDetail> getList() {
		return list;
	}
	public void setList(List<DistrBatchOutOfStockResponseDetail> list) {
		this.list = list;
	}
	
}

package com.xabaohui.modules.storage.dto;

import java.util.List;

/**
 * 缺货重配请求对象
 * @author cxin
 *
 */
public class DistrBatchOutOfStockRequestDTO {
	private Integer operator;//操作员Id
	private Integer taskId;//配货流水号
	private List<DistrBatchOutOfStockRequestDetail> list;

	
	
	public DistrBatchOutOfStockRequestDTO() {
	}
	public DistrBatchOutOfStockRequestDTO(Integer operator, Integer taskId,
			List<DistrBatchOutOfStockRequestDetail> list) {
		super();
		this.operator = operator;
		this.taskId = taskId;
		this.list = list;
	}
	public Integer getOperator() {
		return operator;
	}
	public void setOperator(Integer operator) {
		this.operator = operator;
	}
	public Integer getTaskId() {
		return taskId;
	}
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	public List<DistrBatchOutOfStockRequestDetail> getList() {
		return list;
	}
	public void setList(List<DistrBatchOutOfStockRequestDetail> list) {
		this.list = list;
	}
	
}

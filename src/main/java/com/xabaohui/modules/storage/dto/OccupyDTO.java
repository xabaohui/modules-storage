package com.xabaohui.modules.storage.dto;

/**
 * 库存占用明细
 * 
 * @author yz
 * 
 */
public class OccupyDTO {

	private Integer skuId;
	private Integer subjectId;
	private Integer orderId;
	private Integer amount;

	public OccupyDTO() {
	}

	public OccupyDTO(Integer skuId, Integer subjectId, Integer orderId,
			Integer amount) {
		this.skuId = skuId;
		this.subjectId = subjectId;
		this.orderId = orderId;
		this.amount = amount;
	}

	public Integer getSkuId() {
		return skuId;
	}

	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

}

package com.quickretail.bo.entity;

import java.util.Calendar;

public class PurchaseOrder extends AbstractEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5256113227385072150L;
	private int orderId;
	private int customerId;
	private Calendar purchaseDate;
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public Calendar getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(Calendar purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	@Override
	public String toString() {
		return "PurchaseOrder [orderId=" + orderId + ", customerId="
				+ customerId + ", purchaseDate=" + purchaseDate + "]";
	}
	
	

}

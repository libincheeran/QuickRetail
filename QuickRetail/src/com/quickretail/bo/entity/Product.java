package com.quickretail.bo.entity;

public class Product extends AbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8709447427936925885L;
	private int productId;
	private String productName;
	private String productType;
	
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	@Override
	public String toString() {
		return "Product [productId=" + productId + ", productName="
				+ productName + ", productType=" + productType + "]";
	}
	
	
}

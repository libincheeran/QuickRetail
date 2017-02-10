package com.quickretail.bo.entity;

public class StockDetail extends AbstractEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3782663729069584873L;
	private int stockId;
	private Product product;
	private Retailer retailer;
	private int productQuantity = 1;
	private float unitPrice;
	private float zonePrice;
	private int pricingId;
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Retailer getRetailer() {
		return retailer;
	}
	public void setRetailer(Retailer retailer) {
		this.retailer = retailer;
	}
	public int getProductQuantity() {
		return productQuantity;
	}
	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}
	public float getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(float unitPrice) {
		this.unitPrice = unitPrice;
	}
	public int getStockId() {
		return stockId;
	}
	public void setStockId(int stockId) {
		this.stockId = stockId;
	}
	
	public float getZonePrice() {
		return zonePrice;
	}
	public void setZonePrice(float zonePrice) {
		this.zonePrice = zonePrice;
	}
	
	public int getPricingId() {
		return pricingId;
	}
	public void setPricingId(int pricingId) {
		this.pricingId = pricingId;
	}
	@Override
	public String toString() {
		return "StockDetail [stockId=" + stockId + ", product=" + product
				+ ", retailer=" + retailer + ", productQuantity="
				+ productQuantity + ", unitPrice=" + unitPrice + ", zonePrice="
				+ zonePrice + "]";
	}

	
	

}

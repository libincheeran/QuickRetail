package com.quickretail.bo.entity;

public class PricingDetails extends AbstractEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8066316803823597955L;
	private int pricingId;
	private int pricingZone;
	private float price;
	public int getPricingId() {
		return pricingId;
	}
	public void setPricingId(int pricingId) {
		this.pricingId = pricingId;
	}
	public int getPricingZone() {
		return pricingZone;
	}
	public void setPricingZone(int pricingZone) {
		this.pricingZone = pricingZone;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "PricingDetails [pricingId=" + pricingId + ", pricingZone="
				+ pricingZone + ", price=" + price + "]";
	}
	

}

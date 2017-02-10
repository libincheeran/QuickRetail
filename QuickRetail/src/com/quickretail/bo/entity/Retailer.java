package com.quickretail.bo.entity;

public class Retailer extends AbstractEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -822301183513729186L;
	private int retailerID;
	private String retailerName;
	private int retailerZone;
	
	public int getRetailerID() {
		return retailerID;
	}
	public void setRetailerID(int retailerID) {
		this.retailerID = retailerID;
	}
	public String getRetailerName() {
		return retailerName;
	}
	public void setRetailerName(String retailerName) {
		this.retailerName = retailerName;
	}
	public int getRetailerZone() {
		return retailerZone;
	}
	public void setRetailerZone(int retailerZone) {
		this.retailerZone = retailerZone;
	}
	@Override
	public String toString() {
		return "Retailer [retailerID=" + retailerID + ", retailerName="
				+ retailerName + ", retailerZone=" + retailerZone + "]";
	}
	

}

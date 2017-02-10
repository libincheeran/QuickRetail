package com.quickretail.bo.entity;

import java.util.Calendar;

public class Shipping extends AbstractEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7572005391427794609L;
	private int id;
	private Calendar shippingDate;
	private String address;
	private String city;
	private String state;
	private String zipCode;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Calendar getShippingDate() {
		return shippingDate;
	}
	public void setShippingDate(Calendar shippingDate) {
		this.shippingDate = shippingDate;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	@Override
	public String toString() {
		return "Shipping [id=" + id + ", shippingDate=" + shippingDate
				+ ", address=" + address + ", city=" + city + ", state="
				+ state + ", zipCode=" + zipCode + "]";
	}
	
	

}

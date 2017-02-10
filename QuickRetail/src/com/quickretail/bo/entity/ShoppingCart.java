package com.quickretail.bo.entity;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart extends AbstractEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 935764676947215209L;
	private List<StockDetail> cartItems=null;
	private Customer customer = null;
	private float totalPrice;

	public List<StockDetail> getCartItems() {
		return cartItems;
	}

	public void addCartItems(StockDetail stockItem) {
		if ( cartItems == null ){
			cartItems = new ArrayList<StockDetail>();
		}
		cartItems.add(stockItem);
	}

	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Override
	public String toString() {
		return "ShoppingCart [cartItems=" + cartItems + "]";
	}
	

}

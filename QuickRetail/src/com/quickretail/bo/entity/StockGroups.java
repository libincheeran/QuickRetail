package com.quickretail.bo.entity;

import java.util.ArrayList;
import java.util.List;

public class StockGroups extends AbstractEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3647152424138162485L;
	private List<StockDetail> stockDetailList;
	private float zonePrice;

	public float getZonePrice() {
		return zonePrice;
	}

	public void setZonePrice(float zonePrice) {
		this.zonePrice = zonePrice;
	}

	public List<StockDetail> getStockDetailList() {
		return stockDetailList;
	}

	public void add(StockDetail sd) {
		if ( stockDetailList ==null ){
			stockDetailList = new ArrayList<StockDetail>();
		}
		stockDetailList.add(sd);
	}
	
	

}

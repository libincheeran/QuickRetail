package com.quickretail.ui;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.quickretail.bo.entity.Product;
import com.quickretail.bo.entity.StockDetail;
import com.quickretail.dao.DBManager;

public class SearchAction implements ActionDispatcher {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) {
		
		try {
			
			String activity = req.getParameter("activity");
			
			if ( "product".equals(activity)) {
				
				String prodId = req.getParameter("product");
				System.out.println(" Calling getStockDetails ");
				List<StockDetail> list = DBManager.getStockDetails(Integer.parseInt(prodId));
				System.out.println(" GOT Stock List "+list.size());
				req.getSession().setAttribute("STOCK_LIST", list);	
				req.getServletContext().getRequestDispatcher("/jsp/Stock.jsp").forward(req, res);
			}
			
		} catch ( Exception ex ){
			ex.printStackTrace();
		}
		return null;
	}

}

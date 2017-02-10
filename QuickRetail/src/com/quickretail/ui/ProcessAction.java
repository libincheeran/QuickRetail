package com.quickretail.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.quickretail.bo.entity.Customer;
import com.quickretail.bo.entity.PurchaseOrder;
import com.quickretail.bo.entity.Shipping;
import com.quickretail.bo.entity.ShoppingCart;
import com.quickretail.bo.entity.StockDetail;
import com.quickretail.bo.entity.StockGroups;
import com.quickretail.dao.DBManager;

public class ProcessAction implements ActionDispatcher {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) {
		try {
			
			String activity = req.getParameter("activity");
			
			if ( "shopping_cart_add".equals(activity)) {
				HttpSession session = req.getSession();
				if( req.getParameter("stockid") == null ) {
					req.getServletContext().getRequestDispatcher("/jsp/Shop.jsp").forward(req, res);
					return null;
				}
				int sid = Integer.parseInt((String)req.getParameter("stockid"));
				if ( session.getAttribute("SHOPPING_CART") == null ) {
					ShoppingCart sc = new ShoppingCart();
					StockDetail sd = new StockDetail();					
					sd.setStockId(sid);
					sd.setProductQuantity(1);
					sc.addCartItems(sd);
					session.setAttribute("SHOPPING_CART", sc);
					System.out.println(" ADDED sid "+sid);
				} else {
					ShoppingCart sc = (ShoppingCart)session.getAttribute("SHOPPING_CART");
					StockDetail sd = new StockDetail();
					sd.setStockId(sid);
					sd.setProductQuantity(1);
					sc.addCartItems(sd);
					System.out.println(" ADDED sid "+sid);
				}
				req.getServletContext().getRequestDispatcher("/jsp/Shop.jsp").forward(req, res);
			}
			
			if ( "checkout".equals(activity)){
				
				Map<String,StockGroups> map = new HashMap<String,StockGroups>();
				ShoppingCart sc = (ShoppingCart)req.getSession().getAttribute("SHOPPING_CART");
				Customer cust = (Customer)req.getSession().getAttribute("USER");
				sc.setCustomer(cust);
				System.out.println(" Calling Cart Details ");
				sc = DBManager.getShoppingCartDetails(sc);
				sc.setCustomer(cust);
				
				List<StockDetail> scItemList = sc.getCartItems();
				for ( StockDetail sd : scItemList ){
					
					String retailerId= Integer.toString(sd.getRetailer().getRetailerID());
					System.out.println(" Adding Retailer ID "+retailerId);
					if (map.containsKey(retailerId) ) {
						StockGroups group = map.get(retailerId);
						group.add(sd);						
					}else {
						StockGroups group = new StockGroups();
						group.add(sd);
						group.setZonePrice(sd.getZonePrice());
						String retailerID = Integer.toString(sd.getRetailer().getRetailerID());
						map.put(retailerID, group);
					}
					
				}
				
				float totalCharges = 0;
				Set<String> set = map.keySet();
				for (String s : set ){
					StockGroups group = map.get(s);
					for ( StockDetail sd : group.getStockDetailList() ){
						float val = sd.getUnitPrice();
						totalCharges += val;
					}
					totalCharges+=group.getZonePrice();
					
				}
				
				System.out.println(" Total Charges "+totalCharges );
				
				sc.setTotalPrice(totalCharges);				
				req.setAttribute("STOCK_GROUP_MAP", map);
				req.setAttribute("TOTAL_CHARGES", Float.toString(totalCharges));
				req.getSession().setAttribute("TOTAL_CHARGES", Float.toString(totalCharges));
				req.getSession().setAttribute("SHOPPING_CART", sc);
				req.getServletContext().getRequestDispatcher("/jsp/ConfirmPage.jsp").forward(req, res);
				
			} if ( "confirm".equals(activity) ) {
				
				ShoppingCart cart = (ShoppingCart)req.getSession().getAttribute("SHOPPING_CART");
				System.out.println(" Creating Shipping Record ");
				Shipping shipping = DBManager.createShippingRecord(cart);
				System.out.println(" Creating PO Record ");
				PurchaseOrder po = DBManager.createPurchaseOrderRecord(cart);
				System.out.println(" Creating invoice ");
				int invoiceID = DBManager.createInvoiceRecord(cart,po,shipping);
				System.out.println(" Done Creating the record PO ID "+po.getOrderId());
				req.setAttribute("PO_ID", Integer.valueOf(po.getOrderId()));
				req.setAttribute("SHIPPING_ID", Integer.valueOf(shipping.getId()));
				req.getServletContext().getRequestDispatcher("/jsp/FinalPage.jsp").forward(req, res);
				req.setAttribute("SHOPPING_CART", "");
			}
			
			
			System.out.println(" ----------------------------------------------------------------- ");
			System.out.println(" ----------------------------------------------------------------- ");
			System.out.println(" Content in shopping Cart "+(ShoppingCart)req.getSession().getAttribute("SHOPPING_CART"));
			
		} catch ( Exception ex ){
			ex.printStackTrace();
		}
		return null;
	}

}

package com.quickretail.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.quickretail.bo.entity.Customer;
import com.quickretail.bo.entity.Product;
import com.quickretail.bo.entity.PurchaseOrder;
import com.quickretail.bo.entity.Retailer;
import com.quickretail.bo.entity.Shipping;
import com.quickretail.bo.entity.ShoppingCart;
import com.quickretail.bo.entity.StockDetail;

public class DBManager {
	
	public static Connection getConnection() {
		
		
		try {
			Class.forName ("oracle.jdbc.OracleDriver");
			Connection conn = DriverManager.getConnection
				     ("jdbc:oracle:thin:@//localhost:1523/QUICK_RETAIL_STORE", "dbms", "dbms1234");
			System.out.println(" Got Connection  ");
			//Statement stmt = conn.createStatement();
			//ResultSet rset = stmt.executeQuery("select BANNER from SYS.V_$VERSION");
			//while ( rset.next()){
			//	System.out.println (rset.getString(1));
			//}
				
			return conn;
		} catch ( Exception ex ){
			ex.printStackTrace();
		}
		return null;
	}
	
	public static void close( Connection con , PreparedStatement pstmt ,ResultSet rs ) {
		
		try {
			if ( rs != null ){
				rs.close();
			}
			if ( pstmt != null ) {
				pstmt.close();
			}
			if ( con != null ) {
				con.close();
			}
		} catch ( Exception ex ) {
			ex.printStackTrace();
		}
		
		
	}
	
	
	public static List<Product> getProduct () {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			
			con = DBManager.getConnection();
			pstmt = con.prepareStatement("select * from product order by product_name");
			rs = pstmt.executeQuery();
			List<Product> list = new ArrayList<Product>();
			System.out.println(" Done");
			while ( rs.next() ){
				Product product = new Product();
				product.setProductId(rs.getInt("PRODUCT_ID"));
				product.setProductName(rs.getString("PRODUCT_NAME"));
				product.setProductType(rs.getString("PRODUCT_TYPE"));
				list.add(product);
			}
			System.out.println(" Product List Size "+list.size());
			return list;
		}catch ( Exception ex ){
			ex.printStackTrace();
		} finally {
			DBManager.close(con, pstmt, rs);
		}
		return null;
	}
	
	
	public static List<StockDetail> getStockDetails ( int prodId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			
			con = DBManager.getConnection();
			String query = "select stock_id,r.retailer_name,r.retailer_id,sd.product_quantity,sd.unit_price,p.product_name "
					+ "from stock_detail sd, retailer r, product p "
					+ "where p.product_id=? "
					+ "and p.product_id=sd.product_id "
					+ "and sd.retailer_id=r.retailer_id "
					+ "order by sd.unit_price";
			
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, prodId);
			rs = pstmt.executeQuery();
			List<StockDetail> list = new ArrayList<StockDetail>();
			while ( rs.next() ){
				StockDetail sd = new StockDetail();
				sd.setStockId(rs.getInt(1));
				Retailer retailer = new Retailer();
				retailer.setRetailerName(rs.getString((2)));
				retailer.setRetailerID(rs.getInt(3));
				sd.setRetailer(retailer);
				sd.setProductQuantity(rs.getInt(4));
				sd.setUnitPrice(rs.getFloat((5)));
				Product p = new Product();
				p.setProductId(prodId);
				p.setProductName(rs.getString(6));
				sd.setProduct(p);
				list.add(sd);
			}
			System.out.println(" Stock List Size "+list);
			return list;
		}catch ( Exception ex ){
			ex.printStackTrace();
		} finally {
			DBManager.close(con, pstmt, rs);
		}
		return null;
		
	}
	
	public static Customer getCustomer(String userName ) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Customer cust = null;
		try {
			
			con = DBManager.getConnection();
			String query = "select * from customer where username=? ";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, userName);
			rs = pstmt.executeQuery();
			while ( rs.next() ){
				cust = new Customer();
				cust.setCustomerId(rs.getInt("CUSTOMER_ID"));
				cust.setFirstName(rs.getString("CUSTOMER_NAME"));
				//cust.setLastName(rs.getString("LAST_NAME"));
				java.sql.Date d = rs.getDate("DOB");
				Calendar cal = Calendar.getInstance();
				cal.setTime(d);
				cust.setDob(cal);
				cust.setZone(rs.getInt("ZONE"));
				cust.setAddress(rs.getString("ADDRESS"));
				cust.setCity(rs.getString("CITY"));
				cust.setState(rs.getString("STATE"));
				cust.setZipCode(rs.getString("ZIPCODE"));
				cust.setPwd(rs.getString("PASSWORD"));
				cust.setUname(rs.getString("USERNAME"));
				//cust.setPhone(rs.getString("PHONE"));
			}
		} catch ( Exception ex ){
			ex.printStackTrace();
		}finally {
			DBManager.close(con, pstmt, rs);
		}
		return cust;
	}
	
	public static ShoppingCart getShoppingCartDetails( ShoppingCart cart ) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ShoppingCart sCart = null;
		try {
			
			List<StockDetail> sdList = cart.getCartItems();
			String queryInclude = "";
			boolean addComma = false;
			for ( StockDetail sd :sdList ) {
				
				if ( addComma ) {
					queryInclude += ",";
				}
				queryInclude += sd.getStockId();
				addComma = true;
			}			
			con = DBManager.getConnection();
			String query = "select sd.stock_id,r.retailer_id,r.retailer_name,sd.unit_price,p.PRODUCT_NAME,p.product_id,pd.PRICE,pd.pricing_id "
					+ "from stock_detail sd, retailer r, product p ,pricing_details pd "
					+ "where sd.stock_id in ("+queryInclude+") "
					+ "and p.product_id=sd.product_id "
					+ "and sd.retailer_id=r.retailer_id "
					+ "and pd.retail_zone=r.RETAILER_ZONE "
					+ "and pd.customer_zone="+cart.getCustomer().getZone();
			System.out.println(" Query "+query);
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			sCart = new ShoppingCart();
			while ( rs.next() ){
				StockDetail sd = new StockDetail();
				sd.setStockId(rs.getInt("stock_id"));
				Retailer retailer = new Retailer();
				retailer.setRetailerID(rs.getInt("retailer_id"));
				retailer.setRetailerName(rs.getString("retailer_name"));
				sd.setRetailer(retailer);
				Product product = new Product();
				product.setProductId(rs.getInt("product_id"));
				product.setProductName(rs.getString("PRODUCT_NAME"));
				sd.setProduct(product);
				sd.setUnitPrice(rs.getFloat("unit_price"));
				sd.setZonePrice(rs.getFloat("PRICE"));
				sd.setPricingId(rs.getInt("PRICING_ID"));
				sCart.addCartItems(sd);
			}
		} catch ( Exception ex ){
			ex.printStackTrace();
		}finally {
			DBManager.close(con, pstmt, rs);
		}
		return sCart;
		
	}
	
	public static Shipping createShippingRecord ( ShoppingCart cart ) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Customer cust = null;
		Shipping ship = null;
		try {
			
			con = DBManager.getConnection();
			String insert = "insert into shipping (SHIPPING_ID,SHIPPING_DATE,ADDRESS,CITY,STATE,ZIP_CODE) "
					+ "values (SHIPPING_SEQ.nextval,sysdate,?,?,?,?)";
			System.out.println("insert "+insert);
			pstmt = con.prepareStatement(insert,new String[]{"SHIPPING_ID"});
			pstmt.setString(1, cart.getCustomer().getAddress());
			pstmt.setString(2, cart.getCustomer().getCity());
			pstmt.setString(3, cart.getCustomer().getState());
			pstmt.setString(4, cart.getCustomer().getZipCode());
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if ( rs.next() ){
				//System.out.println(" RS "+rs.getInt(1));
				ship = new Shipping();
				ship.setId(rs.getInt(1));
				ship.setShippingDate(Calendar.getInstance());
				ship.setAddress(cart.getCustomer().getAddress());
				ship.setCity(cart.getCustomer().getCity());
				ship.setState(cart.getCustomer().getState());
				ship.setZipCode(cart.getCustomer().getZipCode());				
			}
		} catch ( Exception ex ){
			ex.printStackTrace();
		}finally {
			DBManager.close(con, pstmt, rs);
		}
		
		return ship;
		
	}
	
	public static PurchaseOrder createPurchaseOrderRecord ( ShoppingCart cart ) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PurchaseOrder po = null;
		try {
			
			con = DBManager.getConnection();
			String insert = "insert into PURCHASE_ORDER (ORDER_ID,CUSTOMER_ID,ORDER_DATE,TOTAL_PRICE) "
					+ "values (PURCHASE_ORDER_SEQ.nextval,?,sysdate,?)";
			System.out.println("insert "+insert);
			pstmt = con.prepareStatement(insert,new String[]{"ORDER_ID"});
			pstmt.setInt(1, cart.getCustomer().getCustomerId());
			pstmt.setFloat(2, cart.getTotalPrice());
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if ( rs.next() ){
				po = new PurchaseOrder();
				po.setOrderId(rs.getInt(1));
				po.setCustomerId(cart.getCustomer().getCustomerId());
				po.setPurchaseDate(Calendar.getInstance());
			}
		} catch ( Exception ex ){
			ex.printStackTrace();
		}finally {
			DBManager.close(con, pstmt, rs);
		}		
		return po;		
	}
	
	public static int createInvoiceRecord ( ShoppingCart cart, PurchaseOrder po, Shipping shipping ) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int invoiceId=0;
		try {
			
			con = DBManager.getConnection();
			String insert = "insert into INVOICE (INVOICE_ID,ORDER_ID,PRODUCT_ID,STOCK_ID,PRICING_ID,SHIPPING_ID,RETAIL_ID,QUANTITY) "
					+ "values (INVOICE_SEQ.nextval,?,?,?,?,?,?,?)";
			System.out.println("insert "+insert);
			List<StockDetail> cartList = cart.getCartItems();
			for ( StockDetail sd: cartList ) {
				pstmt = con.prepareStatement(insert,new String[]{"INVOICE_ID"});
				pstmt.setInt(1, po.getOrderId());
				pstmt.setInt(2, sd.getProduct().getProductId());
				pstmt.setInt(3, sd.getStockId());
				pstmt.setInt(4, sd.getPricingId());
				pstmt.setInt(5, shipping.getId());
				pstmt.setInt(6, sd.getRetailer().getRetailerID());
				pstmt.setInt(7, sd.getProductQuantity());
				pstmt.executeUpdate();
				rs = pstmt.getGeneratedKeys();
				if ( rs.next() ){
					invoiceId= rs.getInt(1);
				}
			}
		} catch ( Exception ex ){
			ex.printStackTrace();
		}finally {
			DBManager.close(con, pstmt, rs);
		}
		return invoiceId;
	}
	
	
	public static void main( String[] args ){

		ShoppingCart cart = new ShoppingCart();
		StockDetail sd = new StockDetail();
		sd.setStockId(4);
		sd.setPricingId(1);
		Product prod = new Product();
		prod.setProductId(1234);
		sd.setProduct(prod);
		sd.setProductQuantity(1);
		Retailer ret = new Retailer();
		ret.setRetailerID(1);
		sd.setRetailer(ret);
		sd.setStockId(1);
		sd.setUnitPrice(2);
		sd.setZonePrice(3);		
		cart.addCartItems(sd);
		Customer cus = new Customer();
		cus.setCustomerId(1);
		cus.setAddress("200 Englewood");
		cus.setCity("Buffalo");
		cus.setState("NY");
		cus.setZipCode("14214");
		cart.setCustomer(cus);
		PurchaseOrder po = new PurchaseOrder();
		po.setOrderId(1);
		Shipping ship = new Shipping();
		ship.setId(1);
		System.out.println(DBManager.createInvoiceRecord(cart,po,ship));
	}

}

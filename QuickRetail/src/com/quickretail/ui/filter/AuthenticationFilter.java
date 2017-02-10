package com.quickretail.ui.filter;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.quickretail.bo.entity.Customer;
import com.quickretail.dao.DBManager;

/**
 * Servlet Filter implementation class AuthenticationFilter
 */
public class AuthenticationFilter implements Filter {

    /**
     * Default constructor. 
     */
    public AuthenticationFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		System.out.println(" Filter Intercepted from the Actual Filter");
		HttpServletRequest httpReq = (HttpServletRequest)request;
		HttpSession session = httpReq.getSession();
		if (session.getAttribute("USER") == null ) {
			
			String userName = request.getParameter("username");
			String password = request.getParameter("password");
			
			if ( userName ==  null || password ==null ) {
				System.out.println(" Login Failure as username :" +userName + " .. pass : "+password);
				request.getServletContext().getRequestDispatcher("/jsp/LoginFailure.jsp").forward(request, response);
				return;
			} else {
				
				Customer cust = DBManager.getCustomer(userName);
				
				if ( cust ==  null ) {
					System.out.println(" Could not find the customer " +userName);
					request.getServletContext().getRequestDispatcher("/jsp/LoginFailure.jsp").forward(request, response);
					return;
				}
				
				System.out.println(" entered pwd "+password + " DB pwd "+cust.getPwd());
				
				if ( cust!= null  && password.equals(cust.getPwd())){
					System.out.println(" Login Success ");
					session.setAttribute("USER", cust);
				} else  {
					System.out.println(" Login failure due to pwd mismatch ");
					request.getServletContext().getRequestDispatcher("/jsp/LoginFailure.jsp").forward(request, response);
					return;
				}
			}
		}
		
		System.out.println(" User session Authenticated for " + session.getAttribute("USER"));
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}
	
	private String hashPWD ( String pwd ){
		try {
			String original = "libin3137";
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(original.getBytes());
			byte[] digest = md.digest();
			StringBuffer sb = new StringBuffer();
			for (byte b : digest) {
				sb.append(String.format("%02x", b & 0xff));
			}

			System.out.println("original:" + original);
			System.out.println("digested(hex):" + sb.toString());
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

}

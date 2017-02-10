package com.quickretail.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginAction implements ActionDispatcher{

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) {
		System.out.println(" Hello from LoginAction" + req.getParameter("username"));
		try {
			req.getServletContext().getRequestDispatcher("/jsp/Home.jsp").forward(req, res);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	/*private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String execute() throws Exception {
		
		System.out.println(" Hello from LoginAction" + this.getUsername());
		return "success";
	}*/

}

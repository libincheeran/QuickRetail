package com.quickretail.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ActionDispatcher {
	
	public String execute(HttpServletRequest req, HttpServletResponse res);

}

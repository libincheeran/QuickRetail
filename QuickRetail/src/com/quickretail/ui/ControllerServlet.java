package com.quickretail.ui;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ControllerServlet
 */
@WebServlet("/app/controller")
public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	static Map<String,String> actionClassMap = new HashMap<String,String>();
	static {
		actionClassMap.put("login", "com.quickretail.ui.LoginAction");
		actionClassMap.put("search", "com.quickretail.ui.SearchAction");
		actionClassMap.put("process", "com.quickretail.ui.ProcessAction");
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request,response);
	}
	
	public void process( HttpServletRequest req, HttpServletResponse res) {
		
		try {
			String action = req.getParameter("action");
			String activity = req.getParameter("activity");			
			Enumeration<String> en = req.getParameterNames();
			System.out.println(" Enum Values ");
			Map<String, String[]> mp = req.getParameterMap();
			while ( en.hasMoreElements()){
				String key = en.nextElement();
				System.out.print("NAME "+key+" --> ");
				String[] stringArr = mp.get(key);
				for ( String val : stringArr ){
					System.out.print(val+" , ");
				}
				System.out.println("");
			}
			System.out.println(" Action is "+action);
			System.out.println(" activity is "+activity);
			String className = actionClassMap.get(action);
			Class c = Class.forName(className);
			ActionDispatcher dispatcher = (ActionDispatcher)c.newInstance();
			dispatcher.execute(req, res);
			System.out.println(" Dispatcher Executed ");
			
		} catch ( Exception ex ) {
			ex.printStackTrace();
		}
		
	}

}

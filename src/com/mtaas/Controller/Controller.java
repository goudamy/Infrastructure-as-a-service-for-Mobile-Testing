package com.mtaas.Controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.mtaas.Model.*;
import com.mtaas.Utilities.*;

@WebServlet("/data")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Controller() {
		super();
	}
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
//		out.println(getCode(request));
		String value = request.getParameter("value");
//		JSONParser parser = new JSONParser();
//		JSONObject obj;
//		try {
//			obj = (JSONObject) parser.parse(value);
//			//System.out.println(obj.get("type").toString());
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		if(obj.get("type").equals("list_instance")){
//			out.println(request.getParameter("type"));
//		} 
//		else if(){
//				
//			}
//		if (result.equals("success")) {
//			rd = request.getRequestDispatcher("/success.jsp");
//			User user = new User(username, password);
//			request.setAttribute("user", user);
//		} else {
//			rd = request.getRequestDispatcher("/error.jsp");
//		}
//		rd.forward(request, response);
//		Dataproperties test = new Dataproperties();
//		Dataproperties.main(null);
//		System.out.println(test.dblink());

	}
	protected String getCode(HttpServletRequest request) {
	    return(request.getParameter("code"));
	  }
}
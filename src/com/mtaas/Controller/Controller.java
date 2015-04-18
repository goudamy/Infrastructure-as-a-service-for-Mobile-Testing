package com.mtaas.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mtaas.Model.RestClientTst;
import com.mtaas.Model.project;
import com.mtaas.Utilities.Dataproperties;

@WebServlet("/data")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Controller() {
		super();
	}
	
	@SuppressWarnings("static-access")
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
		String type = request.getParameter("type");
		String action = request.getParameter("action");
		//String instanceName = request.getParameter("instanceName");
		//System.out.println("This is: "+instanceName);
		Dataproperties data = new Dataproperties();
		if(type.equals("instance")){
			RestClientTst rst = new RestClientTst();
			Hashtable tokTable = rst.getTokenUsingCredentials(data.ret_data("stack.hostIp"), data.ret_data("stack.tenantName"), data.ret_data("stack.username"), data.ret_data("stack.password"));
			String tokenId = (String)tokTable.get("tokenId");
			String tenantId = (String)tokTable.get("tenantId");
			String tenantName = data.ret_data("stack.tenantName");
			String username = data.ret_data("stack.username");
			String password = data.ret_data("stack.password");
			String hostIp = data.ret_data("stack.hostIp");
			//String flavorId = data.ret_data("stack.flavorId");
			String imageName =  (request.getParameter("imageName") != null) ? request.getParameter("imageName"):data.ret_data("stack.imageName");
			
			String instanceName = (request.getParameter("instanceName") != null) ? request.getParameter("instanceName"):data.ret_data("stack.serverName");
			String countStr = (request.getParameter("count") != null) ? request.getParameter("count"):"1";
			String flavorId = (request.getParameter("flavor") != null) ? request.getParameter("flavor"):data.ret_data("stack.flavorId");
			
			if(action.equals("launch")){
				int count = Integer.parseInt(countStr);
				for(int i = 0; i < count; i++)
				{
					String instNameStr = instanceName + "-" + String.valueOf(i);
					rst.createInstance(hostIp, tokenId, tenantId, flavorId, imageName, instNameStr);
				}
				out.println("success");
			}
			
			if(action.equals("delete")){
				rst.deleteInstance(hostIp, tokenId, tenantId, instanceName);
				out.println("success");
			}
			
			
		}
		
		if(type.equals("dropdown")){
			
			
			String result = "";
			if(action.equals("inst")){
			try {
				result = project.list_inst_dropdown();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			out.println(result);
			}
			
		}
		
		if(type.equals("createproj")){
			
			
			String result = "";
			if(action.equals("insert")){
			try {
				result = project.create_proj(request.getParameter("value"));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if(result.equals("")) {out.println("success");} else {out.println("failed");}
			}
			
			if(action.equals("list")){
				try {
					result = project.list_proj();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				out.println(result);
				}
		}

	}
	
}
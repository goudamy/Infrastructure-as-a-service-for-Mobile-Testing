package com.mtaas.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mtaas.Model.BillingList;
import com.mtaas.Model.FlavorDetail;
import com.mtaas.Model.ImageDetail;
import com.mtaas.Model.InstanceHandler;
import com.mtaas.Model.IntanceDetails;
import com.mtaas.Model.MobileHubHandler;
import com.mtaas.Model.RestClientTst;
import com.mtaas.Model.billing;
import com.mtaas.Model.project;
import com.mtaas.Utilities.Dataproperties;

@WebServlet("/data")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String LAUNCH = "launch";
	private static final String DELETE = "delete";
	
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
		String tenantId = null;

		String tenantName, username, password, hostIp =  null;
				
		tenantName = data.ret_data("stack.tenantName");
		username = data.ret_data("stack.username");
		password = data.ret_data("stack.password");
		
		if(type.equals("instance")){
			String imageName=null, instanceName=null, countStr=null,flavorId=null;
			String regionName =  (request.getParameter("regionName") != null) ? request.getParameter("regionName") : "US";
			RestClientTst rst = new RestClientTst();
			Hashtable tokTable = new Hashtable();
			String tokenId = null;

			if(!(action.startsWith("em_") ||
					action.startsWith("phone_"))){
				hostIp = InstanceHandler.getHostIp(regionName);
				System.out.println("Host IP : " + hostIp);
				if((hostIp == null) || hostIp.equals(""))
					hostIp = data.ret_data("stack.hostIp");

				tokTable = rst.getTokenUsingCredentials(hostIp, tenantName, username, password);
				tenantId = (String)tokTable.get("tenantId");
				tokenId = (String)tokTable.get("tokenId");
				imageName =  (request.getParameter("imageName") != null) ? request.getParameter("imageName"):data.ret_data("stack.imageName");

				instanceName = (request.getParameter("instanceName") != null) ? request.getParameter("instanceName"):data.ret_data("stack.serverName");
				countStr = (request.getParameter("count") != null) ? request.getParameter("count"):"1";
				flavorId = (request.getParameter("flavor") != null) ? request.getParameter("flavor"):data.ret_data("stack.flavorId");
			}
			
			if(action.equals(LAUNCH)){
				int count = Integer.parseInt(countStr);
				String algo = request.getParameter("algo");
				
				
				for(int i = 0; i < count; i++)
				{
					if(!algo.equals("NONE"))
					{
						hostIp = InstanceHandler.getHostIpUsingAlgo(algo);
						if(hostIp == null)
							InstanceHandler.getHostIp(regionName);
						tokTable = rst.getTokenUsingCredentials(hostIp, tenantName, username, password);
						tokenId = (String)tokTable.get("tokenId");
						tenantId = (String)tokTable.get("tenantId");
					}
					else
						hostIp = InstanceHandler.getHostIp(regionName);
					
					String instNameStr = instanceName + "-" + String.valueOf(i);
					System.out.println("Instance name : " + instNameStr);
					rst.createInstance(hostIp, tokenId, tenantId, flavorId, imageName, instNameStr);
				}
				out.println("success");
				action = "list";
			} 
			
			if(action.equals(DELETE)){
				hostIp=(request.getParameter("host") != null) ? request.getParameter("host"):hostIp;
				rst.deleteInstance(hostIp, tokenId, tenantId, instanceName);
				out.println("success");
				action = "list";
			}
			
			/*if(action.equals(DELETE)){
				rst.adminActionInstance(hostIp, tokenId, tenantId, instanceName, action);
				out.println("success");
			}*/
			
			if(action.equals("list")){
				IntanceDetails inst = new IntanceDetails();
				ArrayList<String> hostIps = InstanceHandler.getAllHostIps();
				Iterator<String> itr = hostIps.iterator();
				while(itr.hasNext())
				{
					String hostIpStr = (String)itr.next();
					inst.Instance(hostIpStr);
				}
			}

			if(action.equals("Image_list")){

				ImageDetail image = new ImageDetail();
				image.Image(hostIp);

			}
			if(action.equals("Flavor_list")){

				FlavorDetail flavor = new FlavorDetail();
				flavor.Flavor(hostIp);
				
			}
			
			if(action.equals("count")){
				String result = "";
				try {					
					result = InstanceHandler.countInstances(hostIp);
				} catch (Exception e) {
					e.printStackTrace();
				}
				out.println(result);				
			}
			
			if(action.equals("countAll")){
				String result = "";
				try {					
					result = InstanceHandler.getAllHostIpsOfInstances();
				} catch (Exception e) {
					e.printStackTrace();
				}
				out.println(result);				
			}

			if(action.equals("em_create") ||
					action.equals("em_delete")	||	
					action.equals("em_start") ||
					action.equals("em_stop")	||
					action.equals("em_list") ||
					action.equals("em_checkStatus")){
				String result = "";

				String emulatorName = request.getParameter("name");
				String mobileHub_name = request.getParameter("mobileHub_name");

				try {					
					String mobileHubIp = MobileHubHandler.getMobileHubIp(mobileHub_name);
					result = InstanceHandler.handleEmulator(action, emulatorName, mobileHubIp);
				} catch (Exception e) {
					e.printStackTrace();
				}
				out.println(result);				
			}
			
			if(action.equals("phone_list") ||
					action.equals("phone_reboot") ||
					action.equals("phone_getStatus")){
				String result = "";

				String deviceId = request.getParameter("device_id");
				String mobileHub_name = request.getParameter("mobileHub_name");

				try {					
					String mobileHubIp = MobileHubHandler.getMobileHubIp(mobileHub_name);
					result = InstanceHandler.handlePhone(action, mobileHubIp, deviceId);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(action.equals("phone_list")) response.setContentType("application/json");
				out.println(result);				
			}
			
			if(action.equals("credential"))
			{
				String result = "";
				hostIp = request.getParameter("hostIp");
				tokTable = rst.getTokenUsingCredentials(hostIp, tenantName, username, password);
				tokenId = (String)tokTable.get("tokenId");
				result = tokenId;
				out.println(result);
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
		
		if(type.equals("mobilehub")){
			String result = "";
			if(action.equals("add")){
				try {
					RestClientTst rst = new RestClientTst();
					Hashtable tokTable = rst.getTokenUsingCredentials(hostIp, tenantName, username, password);
					tenantId = (String)tokTable.get("tenantId");
					String mobileHub_name = request.getParameter("mobileHub_name");
					String mobileHub_ip = request.getParameter("mobileHub_ip");
					
					result = MobileHubHandler.addMobileHub(mobileHub_name, mobileHub_ip, tenantId);
				} catch (Exception e) {
					e.printStackTrace();
				}
				out.println(result);
			}
			
			if(action.equals("list")){
				try {					
					result = MobileHubHandler.listMobileHub();
				} catch (Exception e) {
					e.printStackTrace();
				}
				response.setContentType("application/json");
				out.println(result);
			}

		}

		
		if(type.equals("createproj")){
			
			
			String result = "";
			if(action.equals("insert")){
			try {
				project.create_proj(request.getParameter("value"));
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
			
			if(action.equals("delete")){
				try {
					result = project.del_proj(request.getParameter("value"));
				} catch (SQLException e) {
					e.printStackTrace();
				}
				out.println(result);
				}
			

			if(action.equals("activate")){
				try {
					result = project.active_proj(request.getParameter("value"));
				} catch (SQLException e) {
					e.printStackTrace();
				}
				out.println(result);
				}
			if(action.equals("deactivate")){
				try {
					result = project.deactive_proj(request.getParameter("value"));
				} catch (SQLException e) {
					e.printStackTrace();
				}
				out.println(result);
				}
		}
		
		String result = "";
		if(type.equals("billing")){	
			if(action.equals("generate")){
				billing bill = new billing();
				bill.Bill();
				
				}
			if(action.equals("list")){
			
			BillingList blist = new BillingList();
			billing bill = new billing();
			bill.Bill();
			result = blist.doGet();
			response.setContentType("application/json");
			out.println(result);
			}
			
			
		}
		
		

	}
	
}

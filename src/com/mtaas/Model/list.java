package com.mtaas.Model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mtaas.Utilities.Dataproperties;

/**
 * Servlet implementation class list
 */
@WebServlet("/list")
public class list extends HttpServlet {
	StringBuffer returnData = null;
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public list() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Dataproperties data = new Dataproperties();
		String hostIp = data.ret_data("stack.hostIp");
		IntanceDetails inst = new IntanceDetails();				
		inst.Instance(hostIp);
		String id;
		String host;
		String created;
		String image;
		String status;
		String tenantId;
		String instanceName;
		String zone;
		String userId;
		String name;
		String flavor;
	
		String url = data.ret_data("mysql1.connect");
		String driver = data.ret_data("mysql1.driver");	
		String userName = data.ret_data("mysql1.userName");
		String password = data.ret_data("mysql1.password");
		try {
			Class.forName(driver).newInstance();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Connection conn = null;
		try {
			conn = (Connection) DriverManager.getConnection(url,
					userName, password);
			PreparedStatement pst = conn
					.prepareStatement("SELECT * FROM instance_list");
			ResultSet rs = pst.executeQuery();
			
			returnData = new StringBuffer("{\"topic\":{");
			returnData.append("\"details\":[");
			int flag = 0;
			while (rs.next()) {
				host = rs.getString("host");
				name = rs.getString("name");
				image = rs.getString("image");				
				id = rs.getString("ip");
				flavor = rs.getString("flavor");
				status = rs.getString("status");
				zone = rs.getString("zone");
				created = rs.getString("created");
			/*
				tenantId = rs.getString("tenantId");
				instanceName = rs.getString("instanceName");
				
				userId = rs.getString("userId");
				name = rs.getString("name");
				*/
				if(flag == 0){
				returnData.append("{\"id\":\""+id+"\",\"created\":\""+created+"\",\"image\":\""+image+"\",\"status\":\""+status+"\",\"zone\":\""+zone+"\",\"name\":\""+name+"\",\"flavor\":\""+flavor+"\"}");
				flag = 1;
				}else{
					returnData.append(",{\"id\":\""+id+"\",\"created\":\""+created+"\",\"image\":\""+image+"\",\"status\":\""+status+"\",\"zone\":\""+zone+"\",\"name\":\""+name+"\",\"flavor\":\""+flavor+"\"}");
				}
			}
			returnData.append("]}}");
			pst.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		response.setContentType("text/html");
		response.getWriter().write(generateJSONData());
		

	}

	public String generateJSONData() {

		//System.out.println(returnData);
		return returnData.toString();

	}

}

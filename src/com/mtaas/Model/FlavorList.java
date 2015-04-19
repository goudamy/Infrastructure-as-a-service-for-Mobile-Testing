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
 * Servlet implementation class FlavorList
 */
@WebServlet("/FlavorList")
public class FlavorList extends HttpServlet {
	private static final long serialVersionUID = 1L;
	StringBuffer returnData = null;     
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FlavorList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id;
		String created;
		String image;
		String status;		
		String userId;
		String name;
		String size;
		String ram;
		String vcpus;
	    String swap;
		String euphemeral;
		String disk;
		String publ;
		Dataproperties data = new Dataproperties();
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
		
		try {
			Connection conn = (Connection) DriverManager.getConnection(url,
					userName, password);
			PreparedStatement pst = conn
					.prepareStatement("SELECT * FROM instance_flavor");
			ResultSet rs = pst.executeQuery();
			
			returnData = new StringBuffer("{\"topic\":{");
			returnData.append("\"details\":[");
			int flag = 0;
			while (rs.next()) {
				name = rs.getString("name");							
				id = rs.getString("id");
				vcpus = rs.getString("vcpus");							
				swap = rs.getString("swap");
				disk = rs.getString("disk");
				euphemeral = rs.getString("euphemeral");							
				swap = rs.getString("swap");				
				ram = rs.getString("ram");
				publ = rs.getString("publ");
				
				
				
				if(flag == 0){
				returnData.append("{\"id\":\""+id+"\",\"name\":\""+name+"\",\"vcpus\":\""+vcpus+"\",\"swap\":\""+swap+"\",\"disk\":\""+disk+"\",\"euphemeral\":\""+euphemeral+"\",\"swap\":\""+swap+"\",\"ram\":\""+ram+"\",\"publ\":\""+publ+"\"}");
				flag = 1;
				}else{
					returnData.append(",{\"id\":\""+id+"\",\"name\":\""+name+"\",\"vcpus\":\""+vcpus+"\",\"swap\":\""+swap+"\",\"disk\":\""+disk+"\",\"euphemeral\":\""+euphemeral+"\",\"swap\":\""+swap+"\",\"ram\":\""+ram+"\",\"publ\":\""+publ+"\"}");
				}
			}
			returnData.append("]}}");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		response.setContentType("text/html");
		response.getWriter().write(generateJSONData());
		

	}


	public String generateJSONData() {

	
		System.out.println(returnData);
		return returnData.toString();

	}


	}

	



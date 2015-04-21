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
import com.mysql.jdbc.AbandonedConnectionCleanupThread;

/**
 * Servlet implementation class ImageList
 */
@WebServlet("/ImageList")
public class ImageList extends HttpServlet {
	private static final long serialVersionUID = 1L;
	StringBuffer returnData = null;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImageList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
			Dataproperties data = new Dataproperties();
			String id;
			String created;
			String image;
			String status;		
			String userId;
			String name;
			String size;
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
						.prepareStatement("SELECT * FROM instance_image");
				ResultSet rs = pst.executeQuery();
				
				returnData = new StringBuffer("{\"topic\":{");
				returnData.append("\"details\":[");
				int flag = 0;
				while (rs.next()) {
					name = rs.getString("name");							
					id = rs.getString("id");
					size = rs.getString("size");
					status = rs.getString("status");
					
					if(flag == 0){
					returnData.append("{\"id\":\""+id+"\",\"name\":\""+name+"\",\"size\":\""+size+"\",\"status\":\""+status+"\"}");
					flag = 1;
					}else{
						returnData.append(",{\"id\":\""+id+"\",\"name\":\""+name+"\",\"size\":\""+size+"\",\"status\":\""+status+"\"}");
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
				try {
				    AbandonedConnectionCleanupThread.shutdown();
				} catch (InterruptedException e) {
				    e.printStackTrace();
				}
			}

			response.setContentType("text/html");
			response.getWriter().write(generateJSONData());
			

		}
	

		public String generateJSONData() {

		
//			System.out.println(returnData);
			return returnData.toString();

		}

	

}

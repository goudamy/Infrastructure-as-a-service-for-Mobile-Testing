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
 * Servlet implementation class BillingList
 */
@WebServlet("/BillingList")
public class BillingList extends HttpServlet {
	private static final long serialVersionUID = 1L;	
	StringBuffer returnData = null; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BillingList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public String doGet() throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpServletResponse response = null;
		Dataproperties data = new Dataproperties();
		int id;
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
					.prepareStatement("SELECT * FROM BillingDetails");
			ResultSet rs = pst.executeQuery();
			
			returnData = new StringBuffer("{\"topic\":{");
			returnData.append("\"details\":[");
			int flag = 0;
			while (rs.next()) {
				
				int billId = rs.getInt("BillID");
				id = rs.getInt("RegionId");							
				name = rs.getString("Region");
				double tariff = rs.getDouble("tariff");
				float time = rs.getFloat("totalTime");
				String instName = rs.getString("instanceName");
				double total = rs.getDouble("total");
				if(flag == 0){
				returnData.append("{\"BillId\":\""+billId+"\",\"regionid\":\""+id+"\",\"regionname\":\""+name+"\",\"tariff\":\""+tariff+"\",\"time\":\""+time+"\",\"InstanceName\":\""+instName+"\",\"total\":\""+total+"\"}");
				flag = 1;
				}else{
					returnData.append(",{\"BillId\":\""+billId+"\",\"regionid\":\""+id+"\",\"regionname\":\""+name+"\",\"tariff\":\""+tariff+"\",\"time\":\""+time+"\",\"InstanceName\":\""+instName+"\",\"total\":\""+total+"\"}");
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

		return generateJSONData();

	}
	
public String generateJSONData() {

	System.out.println(returnData);
	return returnData.toString();

}
	
}

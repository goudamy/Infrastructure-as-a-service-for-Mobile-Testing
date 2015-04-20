package com.mtaas.Model;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.mtaas.Utilities.Dataproperties;

public class MobileHubHandler {

	//Connect to DB
	//Create Table M_ID, M_NAME, M_IP 
	//Populate the DB

	public static String addMobileHub(String mobileHub_name, String mobileHub_ip, String tenant_id) throws IOException {

		Dataproperties data = new Dataproperties();
		String url = data.ret_data("mysql1.connect");
		String driver = data.ret_data("mysql1.driver");	
		String userName = data.ret_data("mysql1.userName");
		String password = data.ret_data("mysql1.password");

		Connection conn = null;

		try {

			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager
					.getConnection(url,userName,password);

			System.out.println("Connection created");

			//System.out.println(flavor+name);
			PreparedStatement ps = ((java.sql.Connection) conn)
					.prepareStatement("insert into mobile_hub(mobileHub_name,mobileHub_ip, tenant_id) values (?,?,?)");
			ps.setString(1, mobileHub_name);
			ps.setString(2, mobileHub_ip);	
			ps.setString(3, tenant_id);	
			ps.execute();
			ps.close();
			//System.out.println("Inserted");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}				

		return null;
	}

}

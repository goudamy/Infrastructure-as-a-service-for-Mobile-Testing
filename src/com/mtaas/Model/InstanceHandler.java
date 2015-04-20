package com.mtaas.Model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mtaas.Utilities.Dataproperties;

public class InstanceHandler {


	public static String countInstances(String hostIp) throws IOException {

		Dataproperties data = new Dataproperties();
		String url = data.ret_data("mysql1.connect");
		String driver = data.ret_data("mysql1.driver");	
		String userName = data.ret_data("mysql1.userName");
		String password = data.ret_data("mysql1.password");

		String returnData = null;

		Connection conn = null;

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager
					.getConnection(url,userName,password);

			System.out.println("Connection created");

			conn = (Connection) DriverManager.getConnection(url,
					userName, password);
			PreparedStatement pst = conn
					.prepareStatement("select count(*) from instance_list where host=?");
			pst.setString(1, hostIp);
			ResultSet rs = pst.executeQuery();



			while (rs.next()) {
				returnData = rs.getString(1);
			}
			pst.close();

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

		return returnData;
	}

	public static String getHostIp(String regionId) throws IOException {

		Dataproperties data = new Dataproperties();
		String url = data.ret_data("mysql1.connect");
		String driver = data.ret_data("mysql1.driver");	
		String userName = data.ret_data("mysql1.userName");
		String password = data.ret_data("mysql1.password");

		String returnData = null;

		Connection conn = null;

		try {
			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager
					.getConnection(url,userName,password);

			System.out.println("Connection created");

			conn = (Connection) DriverManager.getConnection(url,
					userName, password);
			PreparedStatement pst = conn
					.prepareStatement("select ip from region where regionID=?");
			pst.setString(1, regionId);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				returnData = rs.getString(1);
			}
			pst.close();

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

		return returnData;
	}
}

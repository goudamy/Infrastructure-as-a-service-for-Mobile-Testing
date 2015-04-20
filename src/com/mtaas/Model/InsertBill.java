package com.mtaas.Model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mtaas.Utilities.Dataproperties;

public class InsertBill {

	public void getDetails(String ip, String tenant_Id, float tariff,
			String instanceName, float totalTime) throws IOException {

		int billId;
		String region;
		int regionId = 0;
		String regionName = "null";
		float total;
		System.out.println(totalTime);
		Dataproperties data = new Dataproperties();
		String url = data.ret_data("mysql1.connect");
		String driver = data.ret_data("mysql1.driver");
		String userName = data.ret_data("mysql1.userName");
		String password = data.ret_data("mysql1.password");
		Connection conn = null;

		try {

			Class.forName(driver).newInstance();

			conn = (Connection) DriverManager.getConnection(url, userName,
					password);

			// System.out.println("Connection created");

			PreparedStatement pst = conn
					.prepareStatement("SELECT * FROM region where ip =?");

			pst.setString(1, ip);
			ResultSet rs1 = pst.executeQuery();

			if (rs1.next()) {
				regionId = rs1.getInt("regionID");
				regionName = rs1.getString("regionName");

			}
			pst.close();

			total = totalTime * tariff;
			System.out.println(regionId + regionName + tenant_Id + tariff
					+ totalTime + instanceName + total);
			// System.out.println(flavor+name);
			PreparedStatement ps = conn
					.prepareStatement("insert into BillingDetails(RegionId,Region,TenantID,Tariff,TotalTimeUsed,InstanceName,Total) values (?,?,?,?,?,?,?)");
			ps.setInt(1, regionId);
			ps.setString(2, regionName);
			ps.setString(3, tenant_Id);
			ps.setFloat(4, tariff);
			ps.setFloat(5, totalTime);
			ps.setString(6, instanceName);
			ps.setFloat(7, total);
			ps.execute();
			ps.close();
			// System.out.println("Inserted");

		} catch (Exception e) {
			// System.out.println(e);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void deleting(String tenant_Id) throws IOException {
		System.out.println("hey");
		Dataproperties data = new Dataproperties();
		String url = data.ret_data("mysql1.connect");
		String driver = data.ret_data("mysql1.driver");
		String userName = data.ret_data("mysql1.userName");
		String password = data.ret_data("mysql1.password");
		Connection conn = null;

		try {
			Class.forName(driver).newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			conn = (Connection) DriverManager.getConnection(url, userName,
					password);

			System.out.println("Connection created");
			PreparedStatement pst1 = conn
					.prepareStatement("SET SQL_SAFE_UPDATES=0;");
			ResultSet rs2 = pst1.executeQuery();
			PreparedStatement pst = conn
					.prepareStatement("Delete FROM BillingDetails where TenantID =?");

			pst.setString(1, tenant_Id);
			pst.executeUpdate();

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

	}

}

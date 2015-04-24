package com.mtaas.Model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mtaas.Utilities.Dataproperties;
import com.mysql.jdbc.AbandonedConnectionCleanupThread;

public class InsertBill {

	public void getDetails(String ip, String tenant_Id, String instanceName,
			float totalTime, String flavorName, String regionName, int regionId)
			throws IOException {
		// initializing float array
		double arr[] = new double[] { 0.25, 0.30, 0.35, 0.40, 0.40, 0.50, 0.05,
				0.10, 0.15, 0.20, 0.25, 0.30, 0.20, 0.25, 0.30, 0.35 };
		int tariffNumber = 0;
		double tariff;
		int billId;
		String region;

		float total;
		System.out.println(totalTime);
		Dataproperties data = new Dataproperties();
		String url = data.ret_data("mysql1.connect");
		String driver = data.ret_data("mysql1.driver");
		String userName = data.ret_data("mysql1.userName");
		String password = data.ret_data("mysql1.password");
		Connection conn = null;

		if (regionName.equals("US")) {
			if (flavorName.equals("m1.nano")) {
				tariffNumber = 0;
			} else if (flavorName.equals("m1.micro")) {
				tariffNumber = 1;
			} else if (flavorName.equals("m1.tiny")) {
				tariffNumber = 2;
			} else if (flavorName.equals("m1.small")) {
				tariffNumber = 3;
			} else if (flavorName.equals("physical")) {
				tariffNumber = 4;
				regionName = "US";
				System.out.println("here");
			}

		} else if (regionName.equals("China")) {
			if (flavorName.equals("m1.nano")) {
				tariffNumber = 6;
			} else if (flavorName.equals("m1.micro")) {
				tariffNumber = 7;
			} else if (flavorName.equals("m1.tiny")) {
				tariffNumber = 8;
			} else if (flavorName.equals("m1.small")) {
				tariffNumber = 9;
			}

		} else if (regionName.equals("India")) {

			if (flavorName.equals("m1.nano")) {
				tariffNumber = 10;
			} else if (flavorName.equals("m1.micro")) {
				tariffNumber = 11;
			} else if (flavorName.equals("m1.tiny")) {
				tariffNumber = 12;
			} else if (flavorName.equals("m1.small")) {
				tariffNumber = 13;
			}

		} else if (regionName.equals("Australia")) {
			if (flavorName.equals("m1.nano")) {
				tariffNumber = 14;
			} else if (flavorName.equals("m1.micro")) {
				tariffNumber = 15;
			} else if (flavorName.equals("m1.tiny")) {
				tariffNumber = 16;
			} else if (flavorName.equals("m1.small")) {
				tariffNumber = 17;
			}
		}
		try {
			Class.forName(driver).newInstance();
			conn = (Connection) DriverManager.getConnection(url, userName,
					password);
		
			tariff = arr[tariffNumber];
			total = (float) (totalTime * tariff);
			System.out.println("Values are = " + regionId + regionName
					+ tenant_Id + tariff + totalTime + instanceName + total);

			PreparedStatement ps1 = conn
					.prepareStatement("select InstanceName from BillingDetails where InstanceName=?");
			ps1.setString(1, instanceName);
			ResultSet rs2 = ps1.executeQuery();

			if (rs2.next()) {

				PreparedStatement ps2 = conn
						.prepareStatement("update BillingDetails set RegionId=?,Region=?,tariff =?,totalTime=?,total=? where instanceName=?");

				ps2.setInt(1, regionId);
				ps2.setString(2, regionName);
				ps2.setDouble(3, tariff);
				ps2.setFloat(4, totalTime);
				ps2.setFloat(5, total);
				ps2.setString(6, instanceName);
				ps2.executeUpdate();
				ps2.close();
				System.out.println("Updating");

			} else {

				
				PreparedStatement ps = conn
						.prepareStatement("insert into BillingDetails(RegionId,Region,tenant_Id,tariff,totalTime,instanceName,total) values (?,?,?,?,?,?,?)");
				ps.setInt(1, regionId);
				ps.setString(2, regionName);
				ps.setString(3, tenant_Id);
				ps.setDouble(4, tariff);
				ps.setFloat(5, totalTime);
				ps.setString(6, instanceName);
				ps.setDouble(7, total);
				ps.execute();
				ps.close();
				System.out.println("Inserted");
			}
		} catch (Exception e) {
			// System.out.println(e);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}try {
			    AbandonedConnectionCleanupThread.shutdown();
			} catch (InterruptedException e) {
			    e.printStackTrace();
			}
		}

	}

	public void deleting(String tenant_Id) throws IOException {
		// System.out.println("hey");
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

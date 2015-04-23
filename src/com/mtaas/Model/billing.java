package com.mtaas.Model;

/*
 * Billing Module
 */

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;
import java.io.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.mtaas.Utilities.Dataproperties;

public class billing {

	public void billingData(String hostIp) {
		// TODO Auto-generated constructor stub
		try {

			insert_deleteData(hostIp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

	public static int data_diff(String dateStart, String dateStop) {
		// HH converts hour in 24 hours format (0-23), day calculation

		SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

		Date d1 = null;
		Date d2 = null;
		String diff1 = "";
		long diffHours = 0;
		try {
			d1 = format.parse(dateStart);
			d2 = format.parse(dateStop);

			// in millisecon
			long diff = d2.getTime() - d1.getTime();          
			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			diffHours = diff / (60 * 60 * 1000)%60;
			long diffDays = diff / (24 * 60 * 60 * 1000);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (int)diffHours;
	}

	public static String volume_based(String datediff, float vm_charge) {

		String vm_amount = "";

		return vm_amount;

	}

	// Goudamy Modification
	public static void insert_deleteData(String ip) throws IOException {
		//String ip = "52.11.10.120";
		String tenant_Id = "tenant-124";
		Calendar calendar = Calendar.getInstance();
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(calendar
				.getTime().getTime());

		System.out.println(currentTimestamp);
		Connection conn = null;

		try {

			Dataproperties data = new Dataproperties();

			String url;
			try {
				url = data.ret_data("mysql1.connect");

				String driver = data.ret_data("mysql1.driver");
				String userName = data.ret_data("mysql1.userName");
				String password = data.ret_data("mysql1.password");

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

				conn = (Connection) DriverManager.getConnection(url, userName,
						password);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// System.out.println("Connection created");

			PreparedStatement pst = conn
					.prepareStatement("SELECT CreatedDate,PInst from projects");

			ResultSet rs1 = pst.executeQuery();

			if (rs1.next()) {
				String dateStart = rs1.getString(1);
				String Pinst = rs1.getString(2);
				String dateStop1 = currentTimestamp.toString();
				String[] temp;
				temp = dateStop1.split("\\.");
				System.out.println(temp[0]);
				String dateStop = temp[0];
				System.out.println("dtartdate"+dateStart);
				int time = data_diff(dateStart, dateStop);
				String flavorName = "physical";
				InsertBill ib = new InsertBill();
				ib.getDetails(ip, tenant_Id, Pinst, time, flavorName);

			}
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

		Dataproperties data = new Dataproperties();
		String url = data.ret_data("mysql1.connect");
		String driver = data.ret_data("mysql1.driver");
		String userName = data.ret_data("mysql1.userName");
		String password = data.ret_data("mysql1.password");

		Connection conn1 = null;

		try {
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
				conn1 = (Connection) DriverManager.getConnection(url, userName,
						password);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// System.out.println("Connection created");

			PreparedStatement pst;
			try {
				pst = conn1.prepareStatement("SELECT * FROM instance_list");

				ResultSet rs1 = pst.executeQuery();
				InsertBill ib = new InsertBill();
				while (rs1.next()) {
					String instanceName = rs1.getString("name");
					String flavorName = rs1.getString("flavor");

					System.out.println(instanceName);
					float time = timeConsumed(instanceName);
					ib.getDetails(ip, tenant_Id,

					instanceName, time, flavorName);
					// Deleting values in the BillingDetails table
					// ib.deleting(tenant_Id);

				}
				pst.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} finally {
			try {
				conn1.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public static float timeConsumed(String instanceName) {
		float time = 0;
		String entity = "{" + "\"auth\": {" + "\"tenantName\": \"admin\","
				+ "\"passwordCredentials\": {" + "\"username\": \"admin\","
				+ "\"password\": \"test1234\"" + "}}}";
		String hostip = "52.11.10.120";
		String url = "http://" + hostip;
		String url1 = url + ":5000";
		IntanceDetails inst = new IntanceDetails();
		HttpResponse resp = IntanceDetails.post(url1, "v2.0/tokens", entity,
				null);
		Hashtable tokTable = IntanceDetails.getToken(resp);
		String tokenId = (String) tokTable.get("tokenId");
		String tenantId = (String) tokTable.get("tenantId");

		String hostUrl = url + ":8774";
		String endPointUrl = "v2/" + tenantId + "/os-simple-tenant-usage/"
				+ tenantId;
		resp = IntanceDetails.get(hostUrl, endPointUrl, tokenId);
		try {
			time = getTotalTime(resp, hostip, instanceName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return time;

	}

	private static float getTotalTime(HttpResponse resp, String hostip,
			String instanceName) throws IOException, SQLException {

		HttpEntity entity = resp.getEntity();
		byte[] buffer = new byte[1024];
		long uptime;
		float hours = 0;
		String instance_name, tenant_id;
		if (entity != null) {
			InputStream inputStream = entity.getContent();
			BufferedInputStream bis = new BufferedInputStream(inputStream);

			String str = "";
			int bytesRead = 0;
			while ((bytesRead = bis.read(buffer)) != -1) {
				str += new String(buffer, 0, bytesRead);
			}

			JSONParser parser = new JSONParser();
			JSONObject obj;
			try {
				obj = (JSONObject) parser.parse(str);

				JSONObject tenantUsage = (JSONObject) obj.get("tenant_usage");

				JSONArray serverArray = (JSONArray) tenantUsage
						.get("server_usages");

				for (int i = 0; i < serverArray.size(); i++) {
					JSONObject flavorObj = (JSONObject) serverArray.get(i);
					uptime = (long) flavorObj.get("uptime");
					instance_name = (String) flavorObj.get("name");
					tenant_id = (String) flavorObj.get("tenant_id");
					System.out.println(uptime + instance_name);
					if (instanceName.equals(instance_name)) {
						System.out.println(uptime);
						hours = uptime / (3600);
						System.out.println("days=" + hours);

					}

				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return hours;
	}

}

package com.mtaas.Model;

/*
 * Billing Module
 */

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class billing {

	public static void main(String[] args) {
		insert_deleteData();
		// String dateStart = "02/11/2015 08:23:00";
		// String dateStop = "04/25/2015 11:41:00";
		// Float time_based_charge = (float) 0.02;
		// String datediff = data_diff(dateStart, dateStop);
		// String Time_Amount = time_based(datediff, time_based_charge);
		// System.out.println("Total Charge is :" + time_based(datediff,(float)
		// 0.02) + "for total hours:" + );

	}

	public static void data_diff(String dateStart, String dateStop) {
		// HH converts hour in 24 hours format (0-23), day calculation
		/*
		 * SimpleDateFormat format = new
		 * SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		 * 
		 * Date d1 = null; Date d2 = null; String diff1 = "";
		 * 
		 * try { d1 = format.parse(dateStart); d2 = format.parse(dateStop);
		 * 
		 * // in milliseconds long diff = d2.getTime() - d1.getTime();
		 * 
		 * long diffSeconds = diff / 1000 % 60; long diffMinutes = diff / (60 *
		 * 1000) % 60; long diffHours = diff / (60 * 60 * 1000) % 24; long
		 * diffDays = diff / (24 * 60 * 60 * 1000);
		 * 
		 * diff1 = diffDays + ":" + diffHours + ":" + diffMinutes + ":" +
		 * diffSeconds;
		 * 
		 * // System.out.print(diffDays + " days, "); //
		 * System.out.print(diffHours + " hours, "); //
		 * System.out.print(diffMinutes + " minutes, "); //
		 * System.out.print(diffSeconds + " seconds.");
		 * 
		 * } catch (Exception e) { e.printStackTrace(); } return diff1;
		 */
	}

	public static String time_based(String datediff, float tm_charge) {
		String tm_amount = "";
		String days = datediff.split(":")[0];
		int a = Integer.parseInt(days) * 24;
		int b = Integer.parseInt(datediff.split(":")[1]) + a;
		float c = b * tm_charge;
		tm_amount = String.valueOf(c);
		System.out.println("Total Charges are :$" + String.format("%.2f", c)
				+ "  for total hours:" + String.valueOf(b));
		return tm_amount;

	}

	public static String volume_based(String datediff, float vm_charge) {

		String vm_amount = "";

		return vm_amount;

	}

	// Goudamy Modification
	public static void insert_deleteData() {
		String ip = "52.11.10.120";
		String tenant_Id = "tenant-124";
		float tariff = 50;

		String instanceName = "TS1-0";
		try {
			InsertBill ib = new InsertBill();
			float time = timeConsumed(instanceName);
			ib.getDetails(ip, tenant_Id, tariff, instanceName, time);

			// Code for deleting values in BillingDetails table
			// ib.deleting(tenant_Id);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

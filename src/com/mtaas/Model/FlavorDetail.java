package com.mtaas.Model;

import java.io.*;
import java.util.Hashtable;
import java.util.Iterator;
import java.sql.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.mtaas.Utilities.Dataproperties;

public class FlavorDetail {
	String flavorRef = null;
	static String name;
	static String id;
	
	static String ram;
	static long vcpu;
	static String vcpus;
	static String swap;
	static String euphemeral;
	static String disk;
	static long swap1;
	static long ram1;
	static long euphemeral1;
	static long disk1;
	static String publ;
	static boolean publ1;

	public static HttpResponse getTokenDetail(String hostUrl,
			String restEndpointUrl, String token) {
		HttpResponse httpResponse = null;

		try {
			HttpClient httpClient = HttpClientBuilder.create().build();

			String url = hostUrl + "/" + restEndpointUrl;

			HttpGet request = new HttpGet(url);
			if (token != null) {
				request.addHeader("X-Auth-Token", token);
			}
			httpResponse = httpClient.execute(request);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return httpResponse;
	}

	public static HttpResponse postValue(String hostUrl,
			String restEndpointUrl, String entity, String token) {
		HttpResponse httpResponse = null;

		try {
			HttpClient httpClient = HttpClientBuilder.create().build();
			String url = hostUrl + "/" + restEndpointUrl;
			HttpPost request = new HttpPost(url);

			request.addHeader("content-type", "application/json");
			if (token != null) {
				request.addHeader("X-Auth-Token", token);
			}

			if (entity != null) {
				StringEntity params = new StringEntity(entity);
				request.setEntity(params);
			}
			httpResponse = httpClient.execute(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return httpResponse;
	}

	public static Hashtable getDetails(HttpResponse httpResponse,String hostUrl) throws IOException {

		Dataproperties data = new Dataproperties();
		String url = data.ret_data("mysql1.connect");
		String driver = data.ret_data("mysql1.driver");	
		String userName = data.ret_data("mysql1.userName");
		String password = data.ret_data("mysql1.password");
		Connection conn = null;
		
		HttpEntity entity = httpResponse.getEntity();
		byte[] buffer = new byte[1024];
		if (entity != null) {
			try {
				InputStream inputStream = entity.getContent();
				BufferedInputStream bis = new BufferedInputStream(inputStream);

				String str = "";
				int bytesRead = 0;
				while ((bytesRead = bis.read(buffer)) != -1) {
					str += new String(buffer, 0, bytesRead);
				}
				JSONParser parser = new JSONParser();
				JSONObject obj = (JSONObject) parser.parse(str);
				JSONArray flavorArray = (JSONArray) obj.get("flavors");

				for (int i = 0; i < flavorArray.size(); i++) {
					JSONObject flavorObj = (JSONObject) flavorArray.get(i);
					name = (String) flavorObj.get("name");
					id = (String) flavorObj.get("id");
					//System.out.println(name + id);
					vcpu = (long) flavorObj.get("vcpus");
				//	swap1 = (long) flavorObj.get("swap");
					ram1 = (long) flavorObj.get("ram");
					disk1 = (long) flavorObj.get("disk");
					euphemeral1 = (long) flavorObj
							.get("OS-FLV-EXT-DATA:ephemeral");
					publ1 = (boolean) flavorObj
							.get("os-flavor-access:is_public");
					

					try {

						vcpus = Long.toString(vcpu);
						//swap = Long.toString(swap1);
						swap = "64";
						ram = Long.toString(ram1);
						disk = Long.toString(disk1);
						euphemeral = Long.toString(euphemeral1);
						if (publ1 = true) {
							publ = "Yes";
						}
						Class.forName(driver).newInstance();

						conn = (Connection) DriverManager
								.getConnection(url, userName, password);

						//System.out.println("Connection created");
						PreparedStatement ps = ((java.sql.Connection) conn)
								.prepareStatement("insert into instance_flavor(host,name,vcpus,ram,disk,euphemeral,swap,id,publ) values (?,?,?,?,?,?,?,?,?)");
						ps.setString(1, hostUrl);
						ps.setString(2, name);
						ps.setString(3, vcpus);
						ps.setString(4, ram);
						ps.setString(5, disk);
						ps.setString(6, euphemeral);
						ps.setString(7, swap);
						ps.setString(8, id);
						ps.setString(9, publ);
						ps.execute();
						ps.close();
						//System.out.println("Inserted");

					} catch (Exception e) {
						//System.out.println(e);
					}

				}

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
		}

		return null;
	}

	public static Hashtable getToken(HttpResponse httpResponse) {
		HttpEntity entity = httpResponse.getEntity();
		byte[] buffer = new byte[1024];
		if (entity != null) {
			try {
				InputStream inputStream = entity.getContent();
				BufferedInputStream bis = new BufferedInputStream(inputStream);

				String str = "";
				int bytesRead = 0;
				while ((bytesRead = bis.read(buffer)) != -1) {
					str += new String(buffer, 0, bytesRead);
				}

				JSONParser parser = new JSONParser();

				JSONObject obj = (JSONObject) parser.parse(str);
				JSONObject access = (JSONObject) obj.get("access");
				JSONObject token = (JSONObject) access.get("token");
				String tokenId = (String) token.get("id");
				JSONObject tenant = (JSONObject) token.get("tenant");
				String tenantId = (String) tenant.get("id");

				// String id = .get("id");

				Hashtable tokTable = new Hashtable();
				tokTable.put("tokenId", tokenId);
				tokTable.put("tenantId", tenantId);

				return tokTable;

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	public static void Flavor(String hostip) throws IOException {
		
		String entity = "{" + "\"auth\": {" + "\"tenantName\": \"admin\","
				+ "\"passwordCredentials\": {" + "\"username\": \"admin\","
				+ "\"password\": \"test1234\"" + "}}}";

		//String hostip ="52.11.10.120";
        String url = "http://"+hostip;
        String url1 = url+ ":5000";
		HttpResponse resp = postValue(url1, "v2.0/tokens",
				entity, null);


		Hashtable tokTable = getToken(resp);
		String tokenId = (String) tokTable.get("tokenId");
		String tenantId = (String) tokTable.get("tenantId");

		//System.out.println("tokenId : " + tokenId);
		//System.out.println("tenantId : " + tenantId);

		// 52.11.10.120:8774/v2/4b7fd0495b80452b96b8bb8e22224eb5/servers/detail?all_tenants=1
		String hostUrl = url+":8774";
		
		String endPointUrl = "v2/" + tenantId + "/flavors/detail";
		// String endPointUrl = "v2/" + tenantId + "/servers";
		resp = getTokenDetail(hostUrl, endPointUrl, tokenId);
		getDetails(resp,hostip);
		// printResponse(resp);

	}

}

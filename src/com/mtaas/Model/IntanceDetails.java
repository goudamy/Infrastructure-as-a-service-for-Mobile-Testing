package com.mtaas.Model;

import java.io.*;

import com.mtaas.Utilities.*;

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

public class IntanceDetails {
	String flavorRef = null;
	static String id;
	static String created;
	static String updated;
	static String status;
	static String tenantId;
	static String instanceName;
	static String zone;
	static String userId;
	static String name;
	static String flavor;

	public static HttpResponse get(String hostUrl, String restEndpointUrl,
			String token) {
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

	public static HttpResponse post(String hostUrl, String restEndpointUrl,
			String entity, String token) {
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

	/*public static HttpResponse put(String hostUrl, String restEndpointUrl,
			String entity) {
		HttpResponse httpResponse = null;

		try {
			HttpClient httpClient = HttpClientBuilder.create().build();
			String url = hostUrl + "/" + restEndpointUrl;
			HttpPut request = new HttpPut(url);
			StringEntity params = new StringEntity(entity);
			request.addHeader("content-type", "application/json");
			request.setEntity(params);
			httpResponse = httpClient.execute(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return httpResponse;
	}

	public static HttpResponse delete(String hostUrl, String restEndpointUrl) {
		HttpResponse httpResponse = null;

		try {
			HttpClient httpClient = HttpClientBuilder.create().build();
			String url = hostUrl + "/" + restEndpointUrl;
			HttpDelete request = new HttpDelete(url);
			httpResponse = httpClient.execute(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return httpResponse;
	}*/

	public static Hashtable getDetails(HttpResponse httpResponse) throws IOException {
		Dataproperties data = new Dataproperties();

		String url = data.ret_data("mysql.connect");

		String driver = data.ret_data("mysql.driver");
		String userName = data.ret_data("mysql.userName");
		String password = data.ret_data("mysql.password");

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
				JSONArray flavorArray = (JSONArray) obj.get("servers");

				for (int i = 0; i < flavorArray.size(); i++) {
					JSONObject flavorObj = (JSONObject) flavorArray.get(i);
					status = (String) flavorObj.get("status");
					updated = (String) flavorObj.get("updated");				
					id = (String) flavorObj.get("id");
					created = (String) flavorObj.get("created");
					tenantId = (String) flavorObj.get("tenant_id");
					instanceName = (String) flavorObj
							.get("OS-EXT-SRV-ATTR:instance_name");
					zone = (String) flavorObj
							.get("OS-EXT-AZ:availability_zone");
					userId = (String) flavorObj.get("user_id");
					name = (String) flavorObj.get("name");
					JSONObject imagedet = (JSONObject) flavorObj.get("image");
					flavor = (String) imagedet.get("id");
					

//					try {
//
//						Class.forName(driver).newInstance();
//
//						Connection conn = (Connection) DriverManager
//								.getConnection(url, "root", "root");
//
//						System.out.println("Connection created");
//						PreparedStatement ps = ((java.sql.Connection) conn)
//								.prepareStatement("insert into instance(id,created,updated,status,tenantId,instanceName,zone,userId,name,flavor) values (?,?,?,?,?,?,?,?,?,?)");
//						ps.setString(1, id);
//						ps.setString(2, created);
//						ps.setString(3, updated);
//						ps.setString(4, status);
//						ps.setString(5, tenantId);
//						ps.setString(6, instanceName);
//						ps.setString(7, zone);
//						ps.setString(8, userId);
//						ps.setString(9, name);
//						ps.setString(10, "flavor");
//
//						ps.execute();
//						ps.close();
//						System.out.println("Inserted");
//
//					} catch (Exception e) {
//						System.out.println(e);
//					}

				}

			} catch (Exception e) {
				e.printStackTrace();
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

	public void Instance() throws IOException {
		String entity = "{" + "\"auth\": {" + "\"tenantName\": \"admin\","
				+ "\"passwordCredentials\": {" + "\"username\": \"admin\","
				+ "\"password\": \"test1234\"" + "}}}";

		HttpResponse resp = post("http://52.11.10.120:5000", "v2.0/tokens",
				entity, null);

		Hashtable tokTable = getToken(resp);
		String tokenId = (String) tokTable.get("tokenId");
		String tenantId = (String) tokTable.get("tenantId");

		System.out.println("tokenId : " + tokenId);
		System.out.println("tenantId : " + tenantId);

		// 52.11.10.120:8774/v2/4b7fd0495b80452b96b8bb8e22224eb5/servers/detail?all_tenants=1

		String hostUrl = "http://52.11.10.120:8774";
		String endPointUrl = "v2/" + tenantId + "/servers/detail?all_tenants=1";
		// String endPointUrl = "v2/" + tenantId + "/servers";
		resp = get(hostUrl, endPointUrl, tokenId);
		getDetails(resp);
		//printResponse(resp);

	}
/*
	public static void printResponse(HttpResponse httpResponse) {
		HttpEntity entity = httpResponse.getEntity();

		byte[] buffer = new byte[2500];
		if (entity != null) {
			try {
				InputStream inputStream = entity.getContent();

				int bytesRead = 0;
				BufferedInputStream bis = new BufferedInputStream(inputStream);
				while ((bytesRead = bis.read(buffer)) != -1) {
					String chunk = new String(buffer, 0, bytesRead);
					System.out.println(chunk);
				

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}*/

}

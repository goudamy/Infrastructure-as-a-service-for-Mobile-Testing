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

public class ImageDetail {
	String flavorRef = null;
	static String name;
	static String id;
	//static String hostUrl = "http://52.11.10.120:8774";
	static long size1;
	static int size2;
	static String size;
	static String status;

	public static HttpResponse getTokenDetail(String hostUrl, String restEndpointUrl,
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

	public static HttpResponse postValue(String hostUrl, String restEndpointUrl,
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

	

	public static Hashtable getDetails(HttpResponse httpResponse,String hostUrl) throws IOException {

		Dataproperties data = new Dataproperties();
		String url = data.ret_data("mysql1.connect");
		String driver = data.ret_data("mysql1.driver");	
		String userName = data.ret_data("mysql1.userName");
		String password = data.ret_data("mysql1.password");

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
				JSONArray flavorArray = (JSONArray) obj.get("images");

				for (int i = 0; i < flavorArray.size(); i++) {
					JSONObject flavorObj = (JSONObject) flavorArray.get(i);
					name = (String) flavorObj.get("name");				
					id = (String) flavorObj.get("id");
					size1 = (long) flavorObj.get("OS-EXT-IMG-SIZE:size");
					status =(String) flavorObj.get("status");
					System.out.println(name+id);
					
				
			
					try {
               
                 size2 = (int) (size1/(1024*1024));
                 System.out.println(size2);
                 size = size2+"MB";
						Class.forName(driver).newInstance();

						Connection conn = (Connection) DriverManager
								.getConnection(url,userName,password);

						System.out.println("Connection created");
						PreparedStatement ps = ((java.sql.Connection) conn)
								.prepareStatement("insert into instance_image(host,name,id,size,status) values (?,?,?,?,?)");
						ps.setString(1, hostUrl);
						ps.setString(2, name);
						ps.setString(3, id);
						ps.setString(4, size);
						ps.setString(5, status);
						ps.execute();
						ps.close();
						System.out.println("Inserted");

					} catch (Exception e) {
						System.out.println(e);
					}

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

	public static void Image(String hostip) throws IOException {
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

		System.out.println("tokenId : " + tokenId);
		System.out.println("tenantId : " + tenantId);

		// 52.11.10.120:8774/v2/4b7fd0495b80452b96b8bb8e22224eb5/servers/detail?all_tenants=1

		String hostUrl = url+":8774";
		
		String endPointUrl = "v2/" + tenantId + "/images/detail";
		// String endPointUrl = "v2/" + tenantId + "/servers";
		resp = getTokenDetail(hostUrl, endPointUrl, tokenId);
		getDetails(resp,hostip);
		//printResponse(resp);

	}


}

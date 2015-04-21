package com.mtaas.Model;

import java.io.*;

import com.mtaas.Utilities.*;
import com.mysql.jdbc.AbandonedConnectionCleanupThread;

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
	static String image;

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

/*

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


	public static Hashtable getDetails(HttpResponse httpResponse,String url1) throws IOException {
	
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
				JSONArray flavorArray = (JSONArray) obj.get("servers");

				for (int i = 0; i < flavorArray.size(); i++) {
					JSONObject flavorObj = (JSONObject) flavorArray.get(i);
					status = (String) flavorObj.get("status");
					updated = (String) flavorObj.get("updated");
					JSONObject ip = (JSONObject)flavorObj.get("addresses");
					if(ip != null)
					{
						JSONArray ip1 = (JSONArray) ip.get("private");
						if(ip1 != null)
						{
							for (int j = 0; j < ip1.size(); j++) {
								JSONObject ip2 = (JSONObject) ip1.get(j);
								id = (String) ip2.get("addr");
								//System.out.println(id);
							}
						}
					}
					//id = (String) flavorObj.get("id");
					created = (String) flavorObj.get("created");
					tenantId = (String) flavorObj.get("tenant_id");
					instanceName = (String) flavorObj
							.get("OS-EXT-SRV-ATTR:instance_name");
					zone = (String) flavorObj
							.get("OS-EXT-AZ:availability_zone");
					userId = (String) flavorObj.get("user_id");
					name = (String) flavorObj.get("name");
					JSONObject imagedet = (JSONObject) flavorObj.get("image");
					image = (String) imagedet.get("id");
					JSONObject imagedet1 = (JSONObject) flavorObj.get("flavor");
					flavor = (String) imagedet1.get("id");
					
					Connection conn = null;
				
					try {

						Class.forName(driver).newInstance();

						conn = (Connection) DriverManager
								.getConnection(url,userName,password);

						//System.out.println("Connection created");
						
						
						PreparedStatement pst = conn
								.prepareStatement("SELECT * FROM instance_image where id =?");
				
						pst.setString(1,image);						
						ResultSet rs1 = pst.executeQuery();					
					
						if(rs1.next()){								
							image = rs1.getString("name");     
				    			
						}
						pst.close();
						PreparedStatement pst1 = conn
								.prepareStatement("SELECT * FROM instance_flavor where id =?");
					
						pst1.setString(1,flavor);
						ResultSet rs2 = pst1.executeQuery();					
					
						if(rs2.next()){								
							flavor = rs2.getString("name");     
				    			
						}
						pst1.close();
						//System.out.println(flavor+name);
						PreparedStatement ps = ((java.sql.Connection) conn)
								.prepareStatement("insert into instance_list(host,name,image,ip,flavor,status,zone,created) values (?,?,?,?,?,?,?,?)");
						ps.setString(1, url1);
						ps.setString(2, name);
						ps.setString(3, image);
						ps.setString(4, id);
						ps.setString(5, flavor);
						ps.setString(6, status);
						ps.setString(7, zone);
						ps.setString(8, created);
					
						ps.execute();
						ps.close();
						//System.out.println("Inserted");

					} catch (Exception e) {
						//System.out.println(e);
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

				}	

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
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

	public static void Instance(String hostip) throws IOException{
	
		deleting(hostip);
		
		String entity = "{" + "\"auth\": {" + "\"tenantName\": \"admin\","
				+ "\"passwordCredentials\": {" + "\"username\": \"admin\","
				+ "\"password\": \"test1234\"" + "}}}";
		//String hostip ="52.11.10.120";
        String url = "http://"+hostip;
        String url1 = url+ ":5000";
		HttpResponse resp = post(url1, "v2.0/tokens",
				entity, null);
		String tokenId = "";
		String tenantId = "";
		System.out.println(resp.toString());
		if(resp != null){
		Hashtable tokTable = getToken(resp);
		tokenId = (String) tokTable.get("tokenId");
		tenantId = (String) tokTable.get("tenantId");
		} else {return;}

		//System.out.println("tokenId : " + tokenId);
		//System.out.println("tenantId : " + tenantId);

		// 52.11.10.120:8774/v2/4b7fd0495b80452b96b8bb8e22224eb5/servers/detail?all_tenants=1

		String hostUrl = url+":8774";
		String endPointUrl = "v2/" + tenantId + "/servers/detail?all_tenants=1";
		// String endPointUrl = "v2/" + tenantId + "/servers";
		resp = get(hostUrl, endPointUrl, tokenId);
		//System.out.println(resp);
		getDetails(resp,hostip);
		//printResponse(resp);

	}
public static void deleting(String hostip) throws IOException {
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
			PreparedStatement pst1 = conn.prepareStatement("SET SQL_SAFE_UPDATES=0;");	
			pst1.execute();
			PreparedStatement pst = conn.prepareStatement("Delete FROM instance_list where host =?");	
			
			pst.setString(1,hostip);
			 pst.executeUpdate();

			pst.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
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

		}
	
}

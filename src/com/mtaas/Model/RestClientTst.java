package com.mtaas.Model;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

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


public class RestClientTst {

	public static HttpResponse get(String hostUrl, String restEndpointUrl,  String token)
	{
		HttpResponse httpResponse = null;

		try {
			HttpClient httpClient = HttpClientBuilder.create().build();


			String url = hostUrl + "/" + restEndpointUrl;

			HttpGet request = new HttpGet(url);
			if(token != null)
			{
				request.addHeader("X-Auth-Token", token);
			}
			httpResponse = httpClient.execute(request);

		} catch (Exception e) {
			e.printStackTrace();
		}  
		return httpResponse;
	}

	public static HttpResponse post(String hostUrl, String restEndpointUrl, String entity, String token)
	{
		HttpResponse httpResponse = null;

		try {
			HttpClient httpClient = HttpClientBuilder.create().build();
			String url = hostUrl + "/" + restEndpointUrl;
			HttpPost request = new HttpPost(url);

			request.addHeader("content-type", "application/json");
			if(token != null)
			{
				request.addHeader("X-Auth-Token", token);
			}

			if(entity != null)
			{
				StringEntity params = new StringEntity(entity);
				request.setEntity(params);
			}
			httpResponse = httpClient.execute(request);
		} catch (Exception e) {
			e.printStackTrace();
		}  
		return httpResponse;
	}

	public static HttpResponse put(String hostUrl, String restEndpointUrl, String entity)
	{
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

	public static HttpResponse delete(String hostUrl, String restEndpointUrl, String instanceId, String token)
	{
		HttpResponse httpResponse = null;

		try {
			HttpClient httpClient = HttpClientBuilder.create().build();
			String url = hostUrl + "/" + restEndpointUrl + "/" + instanceId;
			HttpDelete request = new HttpDelete(url);
			if(token != null)
			{
				request.addHeader("X-Auth-Token", token);
			}
			httpResponse = httpClient.execute(request);
		} catch (Exception e) {
			e.printStackTrace();
		}  
		return httpResponse;
	}

	public static Hashtable getToken(HttpResponse httpResponse)
	{
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

				JSONParser parser=new JSONParser();


				JSONObject obj = (JSONObject)parser.parse(str);
				JSONObject access = (JSONObject)obj.get("access");
				JSONObject token = (JSONObject)access.get("token");
				String tokenId = (String)token.get("id");
				JSONObject tenant = (JSONObject)token.get("tenant");
				String tenantId = (String)tenant.get("id");

				//String id = .get("id");


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


	public static Hashtable getFlavorRef(HttpResponse httpResponse, String flavorId)
	{
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

				JSONParser parser=new JSONParser();


				JSONObject obj = (JSONObject)parser.parse(str);
				JSONArray flavorArray = (JSONArray)obj.get("flavors");

				String flavorRef = null;

				for(int i = 0; i < flavorArray.size(); i++)
				{	
					JSONObject flavorObj = (JSONObject)flavorArray.get(i);
					String flavorIdTmp = (String)flavorObj.get("id");
					if(flavorIdTmp.equals(flavorId))
					{
						JSONArray linkArray = (JSONArray)flavorObj.get("links");
						JSONObject linkObj = (JSONObject)linkArray.get(0);
						flavorRef = (String)linkObj.get("href");
						break;
					}
				}

				Hashtable tokTable = new Hashtable();

				if(flavorRef != null)
				{
					tokTable.put("flavorRef", flavorRef);
					System.out.println("flavorRef : " + flavorRef);
				}

				return tokTable;


			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return null;
	}


	public static Hashtable getImageRef(HttpResponse httpResponse, String imageName)
	{
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

				JSONParser parser=new JSONParser();


				JSONObject obj = (JSONObject)parser.parse(str);
				JSONArray imageArray = (JSONArray)obj.get("images");

				String imageRef = null;

				for(int i = 0; i < imageArray.size(); i++)
				{	
					JSONObject imageObj = (JSONObject)imageArray.get(i);
					String imageIdTmp = (String)imageObj.get("name");
					if(imageIdTmp.equals(imageName))
					{
						JSONArray linkArray = (JSONArray)imageObj.get("links");
						JSONObject linkObj = (JSONObject)linkArray.get(0);
						imageRef = (String)linkObj.get("href");
						break;
					}
				}

				Hashtable tokTable = new Hashtable();

				if(imageRef != null)
				{
					tokTable.put("imageRef", imageRef);
					System.out.println("imageRef : " + imageRef);
				}

				return tokTable;


			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	public static Hashtable getTokenUsingCredentials(String hostIp, String tenantName, String username, String password)
	{
		String entity = 
				"{" + 
						"\"auth\": {" +
						"\"tenantName\": \""  + tenantName   + "\"," +				
						"\"passwordCredentials\": {" +
						"\"username\": \""  + username   + "\"," +
						"\"password\": \""  + password   + "\"" +
						"}}}";

		String hostUrl = "http://" + hostIp + ":5000";
		String endPointUrl = "v2.0/tokens";
		HttpResponse resp = post(hostUrl , endPointUrl, entity, null);

		Hashtable tokTable = getToken(resp);
		return tokTable;
	}

	public void createInstance(String hostIp, String tokenId, String tenantId, String flavorId, String imageName, String serverName)
	{
		String hostUrl = "http://" + hostIp + ":8774";
		String endPointUrl = "v2/" + tenantId + "/servers";  


		System.out.println("tokenId : " + tokenId);
		System.out.println("tenantId : " + tenantId);

		HttpResponse resp = get(hostUrl , endPointUrl,tokenId);
		printResponse(resp);

		endPointUrl = "v2/" + tenantId + "/flavors";  
		resp = get(hostUrl , endPointUrl,tokenId);
		//printResponse(resp);

		Hashtable tokTable = getFlavorRef(resp, flavorId);
		String flavorRef = (String)tokTable.get("flavorRef");

		endPointUrl = "v2/" + tenantId + "/images";  
		resp = get(hostUrl , endPointUrl,tokenId);

		tokTable = getImageRef(resp, imageName);
		String imageRef = (String)tokTable.get("imageRef");

		String entity =
				"{" +
						"\"server\": {" +
						"\"name\": \""      + serverName + "\","  +
						"\"imageRef\": \""  + imageRef   + "\"," +
						"\"flavorRef\": \"" + flavorRef  + "\"," +
						"\"metadata\": { " +
						"\"My Server Name\": \"ApacheTest\" " +
						"}}}";

		System.out.println("Entity : " + entity);

		endPointUrl = "v2/" + tenantId + "/servers";  
		resp = post(hostUrl , endPointUrl,entity, tokenId);
		printResponse(resp);
	}

	public static Hashtable deleteInstance(String hostIp, String tokenId, String tenantId, String serverName) {

		String hostUrl = "http://" + hostIp + ":8774";
		String endPointUrl = "v2/" + tenantId + "/servers";  


		HttpResponse httpResponse = get(hostUrl , endPointUrl,tokenId);

		String name = null;
		String instanceId = null;
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
				JSONArray instArray = (JSONArray) obj.get("servers");

				for (int i = 0; i < instArray.size(); i++) {
					JSONObject flavorObj = (JSONObject) instArray.get(i);					
					name = (String) flavorObj.get("name");
					if(name.equals(serverName))
					{
						instanceId = (String) flavorObj.get("id");
						delete(hostUrl , endPointUrl, instanceId, tokenId);
					}
				}	

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	//#########

	public final static void main(String[] args) throws IOException {	
//		Dataproperties data = new Dataproperties();
//		String tenantName = data.ret_data("stack.tenantName");
//		String username = data.ret_data("stack.username");
//		String password = data.ret_data("stack.password");
//		String hostIp = data.ret_data("stack.hostIp");
//
//		String flavorId = data.ret_data("stack.flavorId");
//		String imageName =  data.ret_data("stack.imageName");
//		String serverName = data.ret_data("stack.serverName");
//		
//		
//		Hashtable tokTable = getTokenUsingCredentials(hostIp, tenantName, username, password);
//
//
//		String tokenId = (String)tokTable.get("tokenId");
//		String tenantId = (String)tokTable.get("tenantId");

//		System.out.println("tokenId : " + tokenId);
//		System.out.println("tenantId : " + tenantId);

//		serverName = "new-server-test-11";
//		createInstance(hostIp, tokenId, tenantId, flavorId, imageName, serverName);
//		serverName = "new-server-test-12";
//		createInstance(hostIp, tokenId, tenantId, flavorId, imageName, serverName);
//		serverName = "new-server-test-13";
//		createInstance(hostIp, tokenId, tenantId, flavorId, imageName, serverName);

		try{
			Thread.sleep(5000);
		}
		catch(Exception e)
		{

		}

//		serverName = "new-server-test-12";
//		deleteInstance(hostIp, tokenId, tenantId, serverName);

	}

	public static void printResponse(HttpResponse httpResponse)
	{
		HttpEntity entity = httpResponse.getEntity();
		byte[] buffer = new byte[1024];
		if (entity != null) {
			try {
				InputStream inputStream = entity.getContent();

				int bytesRead = 0;
				BufferedInputStream bis = new BufferedInputStream(inputStream);
				String chunk = "";
				while ((bytesRead = bis.read(buffer)) != -1) {
					chunk = new String(buffer, 0, bytesRead);
				}
				System.out.println(chunk);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}

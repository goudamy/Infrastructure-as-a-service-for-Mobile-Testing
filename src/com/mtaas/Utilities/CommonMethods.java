package com.mtaas.Utilities;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.lang3.StringUtils;

public class CommonMethods {

	public CommonMethods() {
		super();
	}
	
	static Dataproperties data = new Dataproperties();
	
	public static String[] db_query_exec(String query, String dt, String type) throws IOException{
		
		String url = data.ret_data("mysql.connect");
		String driver = data.ret_data("mysql.driver");
		String userName = data.ret_data("mysql.userName");
		String password = data.ret_data("mysql.password");
		ResultSet rs = null;
		String[] result = new String[100];
		String[] error = {"false"};
		try {
			
			Class.forName(driver).newInstance();
			Connection conn = (Connection) DriverManager
					.getConnection(url, userName, password);
			PreparedStatement ps = ((java.sql.Connection) conn)
					.prepareStatement(query);
			if(type.equals("select")){
			String[] parts = dt.split(",");
			rs = ps.executeQuery(query);
			int j = 0;
			while (rs.next()) {
				String[] new1 = new String[parts.length];
				for(int i = 0; i < parts.length; i++){
					if(!parts[i].equals(null)){
						new1[i] = rs.getString(parts[i]);
					}
				}
				result[j] = StringUtils.join(new1, "%%#");
				j = j + 1;
			}
			} else {
				ps.execute();
			}
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
			return error;
		}
		return result;
	
	}

}

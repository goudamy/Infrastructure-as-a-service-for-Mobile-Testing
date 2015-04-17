package com.mtaas.Model;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;

import com.mtaas.Utilities.CommonMethods;

public class project {

	public static String list_inst_dropdown() throws IOException, SQLException{
		String sqlst = "SELECT name FROM instance_list";
		String sqlst1 = "SELECT idDevices FROM devices";
		//String sqlst2 = "SELECT name FROM instance_list";
		String result = "";
		String[] name = CommonMethods.db_query_exec(sqlst,"name","select");
		String[] nmemob = CommonMethods.db_query_exec(sqlst1,"idDevices","select");
		result = StringUtils.join(name, ",") + "#@##" + StringUtils.join(nmemob, ",");
		return result;	
	}
	
	public static String create_proj(String val) throws IOException, SQLException{
		java.util.Date dt = new java.util.Date();
		java.text.SimpleDateFormat sdf = 
		     new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = sdf.format(dt);
		String[] parts = val.split("##@#@");
		String sqlst = "INSERT INTO `projects` (`ProjectName`, `CreatedDate`, `Servername`, `EInst`, `PInst`, `Status`) VALUES ('"+parts[0]+"','"+currentTime+"','"+parts[1]+"','"+parts[2]+"','"+parts[3]+"','"+parts[4]+"');";
		String[] result = CommonMethods.db_query_exec(sqlst,"","insert");
		return result[0];	
	}
	
	public static String list_proj() throws IOException, SQLException{
		String sqlst = "SELECT * FROM projects";
		String[] result = CommonMethods.db_query_exec(sqlst,"ProjectName,CreatedDate,Servername,EInst,PInst,Status","select");
		String nresult = StringUtils.join(result, "#@@#");
		return nresult;	
	}

}

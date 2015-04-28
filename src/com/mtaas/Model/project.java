package com.mtaas.Model;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;

import com.mtaas.Utilities.CommonMethods;

public class project {

	public static String list_inst_dropdown() throws IOException, SQLException{
		String sqlst = "SELECT name FROM instance_list where image like 'cir%'";
		String sqlst2 = "SELECT name FROM instance_list where image like 'and%'";
		String sqlst1 = "SELECT devices.idDevices FROM devices where idDevices in (select PInst from projects where projects.status = 'inactive')  or devices.idDevices not in (select PInst from projects);";
		//String sqlst2 = "SELECT name FROM instance_list";
		String result = "";
		String[] name = CommonMethods.db_query_exec(sqlst,"name","select");
		String[] imgname = CommonMethods.db_query_exec(sqlst2,"name","select");
		String[] nmemob = CommonMethods.db_query_exec(sqlst1,"idDevices","select");
		result = StringUtils.join(name, ",") + "#@##" + StringUtils.join(nmemob, ",") + "#@##" + StringUtils.join(imgname, ",");
		return result;	
	}
	
	public static void create_proj(String val) throws IOException, SQLException{
		java.util.Date dt = new java.util.Date();
		java.text.SimpleDateFormat sdf = 
		     new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = sdf.format(dt);
		String[] parts = val.split("##@#@");
		String sqlst = "INSERT INTO `projects` (`ProjectName`, `CreatedDate`, `Servername`, `EInst`, `PInst`, `Status`) VALUES ('"+parts[0]+"','"+currentTime+"','"+parts[1]+"','"+parts[2]+"','"+parts[3]+"','"+parts[4]+"');";
		CommonMethods.db_query_exec(sqlst,"","insert");
	}
	
	public static String list_proj() throws IOException, SQLException{
		String sqlst = "SELECT * FROM projects";
		String[] result = CommonMethods.db_query_exec(sqlst,"ProjectName,CreatedDate,Servername,EInst,PInst,Status","select");
		String nresult = StringUtils.join(result, "#@@#");
		return nresult;	
	}
	
	public static String del_proj(String projname) throws IOException, SQLException{
		String sqlst = "DELETE from projects where ProjectName = \""+ projname +"\"";
		String[] result = CommonMethods.db_query_exec(sqlst,"","delete");
		String nresult = StringUtils.join(result, "#@@#");
		return nresult;	
	}
	

	public static String active_proj(String projname) throws IOException, SQLException{
		String sqlst = "update projects set Status = \"active\" where ProjectName = \""+ projname +"\"";
		String[] result = CommonMethods.db_query_exec(sqlst,"","update");
		String nresult = StringUtils.join(result, "#@@#");
		return nresult;	
	}
	
	public static String deactive_proj(String projname) throws IOException, SQLException{
		String sqlst = "update projects set Status = \"inactive\" where ProjectName = \""+ projname +"\"";
		String[] result = CommonMethods.db_query_exec(sqlst,"","update");
		String nresult = StringUtils.join(result, "#@@#");
		return nresult;	
	}
	
	

}
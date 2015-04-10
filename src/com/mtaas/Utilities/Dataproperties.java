package com.mtaas.Utilities;

import java.io.IOException;
import java.util.Properties;

public class Dataproperties {
	
	private Properties data() throws IOException{
	Properties prop = new Properties();
	prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("mtaas.properties"));
	return prop;
	}
	
	public String ret_data(String val) throws IOException{
		Properties valret = data();
		return valret.getProperty(val);
	}

}

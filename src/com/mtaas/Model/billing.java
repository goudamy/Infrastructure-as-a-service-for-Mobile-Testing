package com.mtaas.Model;
/*
 * Billing Module
 */

import java.text.SimpleDateFormat;
import java.util.Date;
 
public class billing {
	
	public static void main(String[] args) {
		 
		String dateStart = "02/11/2015 08:23:00";
		String dateStop = "04/25/2015 11:41:00";
		Float time_based_charge = (float) 0.02;
		String datediff = data_diff(dateStart,dateStop);
		String Time_Amount = time_based(datediff, time_based_charge);
//		System.out.println("Total Charge is :" + time_based(datediff,(float) 0.02) + "for total hours:" + );
		
		
	}
	
	public static String data_diff(String dateStart, String dateStop){
		//HH converts hour in 24 hours format (0-23), day calculation
				SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		 
				Date d1 = null;
				Date d2 = null;
				String diff1 = "";
		 
				try {
					d1 = format.parse(dateStart);
					d2 = format.parse(dateStop);
		 
					//in milliseconds
					long diff = d2.getTime() - d1.getTime();
		 
					long diffSeconds = diff / 1000 % 60;
					long diffMinutes = diff / (60 * 1000) % 60;
					long diffHours = diff / (60 * 60 * 1000) % 24;
					long diffDays = diff / (24 * 60 * 60 * 1000);
					
					diff1 = diffDays + ":" + diffHours + ":" + diffMinutes +":"+ diffSeconds;
					
		 
//					System.out.print(diffDays + " days, ");
//					System.out.print(diffHours + " hours, ");
//					System.out.print(diffMinutes + " minutes, ");
//					System.out.print(diffSeconds + " seconds.");
		 
				} catch (Exception e) {
					e.printStackTrace();
				}
				return diff1;
	}
	
	public static String time_based(String datediff, float tm_charge){
		String tm_amount = "";
		String days = datediff.split(":")[0];
		int a = Integer.parseInt(days)*24;
		int b = Integer.parseInt(datediff.split(":")[1]) + a;
		float c = b * tm_charge;
		tm_amount = String.valueOf(c);
		System.out.println("Total Charges are :$" + String.format("%.2f",c) + "  for total hours:" + String.valueOf(b) );
		return tm_amount;
		
	}
	
	public static String volume_based(String datediff, float vm_charge){
		String vm_amount = "";
		
		return vm_amount;
		
	}

}

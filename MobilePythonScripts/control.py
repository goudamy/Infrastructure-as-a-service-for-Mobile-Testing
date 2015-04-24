# Hello world python program
import subprocess
import re
import MySQLdb
import os
import time
import sys

#Reboot Device Status
	
def dcontrol(control,dID):
	cnx = MySQLdb.connect(host="sql4.freemysqlhosting.net", 
                     user="sql474573", 
                      passwd="bV6!nP9%",
                      db="sql474573", port=3306)
                   
	cursor = cnx.cursor()
	args1 = (dID)
	query1="select idDevices from devices where idDevices=%s"
	cursor.execute(query1,args1)
	if cursor.rowcount >0:
		print "Device exist"			
		proc = subprocess.Popen(['C:/adb/adb.exe','-s', dID, control],stdout=subprocess.PIPE)
		if control in ('reboot'):
			#Wait for 60 sec to check if device is active
			wait=0
			while True:
				time.sleep(5)
				proc = subprocess.Popen(['C:/adb/adb.exe','-s', dID , 'shell', 'getprop', 'gsm.sim.state'],stdout=subprocess.PIPE)
				status = proc.communicate()[0].strip('\n')				
				args1 = (status,dID)
				query1 = "update devices SET status=%s where idDevices = %s"
				cursor.execute(query1,args1)
				print "testing"+status
				if status.strip() != 'READY':
					if wait <60:
						wait=wait+5
					else:
					 break 	
				elif status.strip() == 'READY':
					break			
   	else:
   		print "Not a valid control";							
	
	cursor.close()	
	
dcontrol(sys.argv[1],sys.argv[2])


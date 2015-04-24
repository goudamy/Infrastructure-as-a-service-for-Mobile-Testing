# Hello world python program
import subprocess
import re
import MySQLdb
import os
import time
import sys

#Get the Device Status
	

def getStatus(dID):
	cnx = MySQLdb.connect(host="sql4.freemysqlhosting.net", 
                     user="sql474573", 
                      passwd="bV6!nP9%",
                      db="sql474573", port=3306)
                   
	cursor = cnx.cursor()
	
#For every line in out2.csv check if the device exist in db and if not add a new row. If the device exist ignore and move to next
	arg1= (dID)
	subprocess.call(['C:/Python27/python.exe','C:/test/devices.py'], shell=True)
	query1 = ("SELECT status from devices where idDevices=%s")
	cursor.execute(query1,arg1)
	cnx.commit()
	for type in cursor:
		for status in type:
			print status
			return
																													
	cursor.close()		
	
getStatus(sys.argv[1])


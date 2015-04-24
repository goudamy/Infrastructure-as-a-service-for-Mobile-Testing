#!c:\Python27\python.exe
import cgi
import subprocess
import re
import MySQLdb
import os
import time

# Get data from fields
form = cgi.FieldStorage() 
device_ID = form.getvalue('dID')
action1 = form.getvalue('action')
if action1 in ("list,LIST,REFRESH,List,Refresh,refresh"):
	proc= subprocess.call(['C:/Python27/python.exe','C:/test/devices.py'], shell=True)	
	print "Content-type:text/html\r\n\r\n"
	if proc == 0:
		print "Device Listing complete\r";
	else:
		print "Unable to List Device. Check the Logs";
elif action1 in ("reboot,restart,Reboot,Restart,REBOOT,RESTART"):
	if device_ID in(""):
		print "Content-type:text/html\r\n\r\n"
		print "Please add the serial number for the device"
	else:
		proc= subprocess.Popen(['C:/Python27/python.exe','C:/test/control.py','reboot',device_ID], shell=True)
		print "Content-type:text/html\r\n\r\n"
		print "Testing"+proc
elif action1 in ("getStatus"):
	if device_ID in(""):
		print "Content-type:text/html\r\n\r\n"
		print "Please add the serial number for the device"
	else:
		proc = subprocess.Popen(['C:/Python27/python.exe','C:/test/status.py',device_ID], shell=True,stdout=subprocess.PIPE)
		status = proc.communicate()[0].strip('\n')
		print "Content-type:text/html\r\n\r\n"
		print status+"\r";
elif action1 in ("install"):
	if device_ID in(""):
		print "Content-type:text/html\r\n\r\n"
		print "Please add the serial number for the device"
	else:
		proc = subprocess.Popen(['C:/Python27/python.exe','C:/test/install.py',device_ID], shell=True,stdout=subprocess.PIPE)
		status = proc.communicate()[0].strip('\n')
		print "Content-type:text/html\r\n\r\n"
		print status+"\r";
			
else:
		print "Content-type:text/html\r\n\r\n"
		print "Not a valid action.Please pass the valid arguments"




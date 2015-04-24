#!c:\Python27\python.exe
import cgi
import subprocess
import re
import MySQLdb
import os
import time

# Get data from fields
form = cgi.FieldStorage() 
name = form.getvalue('ename')
typ=form.getvalue('os')
mem=form.getvalue('mem')
action1 = form.getvalue('action')

if action1 in ("list,LIST,REFRESH,List,Refresh,refresh"):
	proc= subprocess.Popen(['C:/Python27/python.exe','C:/test/vm.py','list'], shell=True, stdout=subprocess.PIPE)
	#print "Printing"+proc
	vms= proc.communicate()[0]
	print "Content-type:text/html\r\n\r\n"
	print vms

elif action1 in ("create,Create,CREATE"):
	if name in(" "):
		print "Content-type:text/html\r\n\r\n"
		print "Please add the name for VM"
	else:
		proc= subprocess.Popen(['C:/Python27/python.exe','C:/test/vm.py','create',name,typ,mem], shell=True, stdout=subprocess.PIPE)
		status= proc.communicate()[0]
		print "Content-type:text/html\r\n\r\n"		
		print status

elif action1 in ("stop,STOP,Stop"):
	if name in(""):
		print "Content-type:text/html\r\n\r\n"
		print "Please provide the VM name"
	else:
		proc = subprocess.Popen(['C:/Python27/python.exe','C:/test/vm.py','stop',name], shell=True,stdout=subprocess.PIPE)
		status = proc.communicate()[0]
		print "Content-type:text/html\r\n\r\n"
		print status
elif action1 in ("start,START,Start"):
	if name in(""):
		print "Content-type:text/html\r\n\r\n"
		print "Please provide the VM name"
	else:
		proc = subprocess.Popen(['C:/Python27/python.exe','C:/test/vm.py','start',name], shell=True,stdout=subprocess.PIPE)
		status = proc.communicate()[0]
		print "Content-type:text/html\r\n\r\n"
		print status
elif action1 in ("delete,DELETE,Delete"):
	if name in(""):
		print "Content-type:text/html\r\n\r\n"
		print "Please provide the VM name"
	else:
		proc = subprocess.Popen(['C:/Python27/python.exe','C:/test/vm.py','delete',name], shell=True,stdout=subprocess.PIPE)
		status = proc.communicate()[0]
		print "Content-type:text/html\r\n\r\n"
		print status
elif action1 in ("checkStatus"):
	if name in(""):
		print "Content-type:text/html\r\n\r\n"
		print "Please provide the VM name"
	else:
		proc = subprocess.Popen(['C:/Python27/python.exe','C:/test/vm.py','running',name], shell=True,stdout=subprocess.PIPE)
		status = proc.communicate()[0]
		print "Content-type:text/html\r\n\r\n"
		print status
elif action1 in ("checkexist"):
	if name in(""):
		print "Content-type:text/html\r\n\r\n"
		print "Please provide the VM name"
	else:
		proc = subprocess.Popen(['C:/Python27/python.exe','C:/test/vm.py','exist',name], shell=True,stdout=subprocess.PIPE)
		status = proc.communicate()[0]
		print "Content-type:text/html\r\n\r\n"
		print status				
else:
		print "Content-type:text/html\r\n\r\n"
		print "Not a valid action.Please pass the valid arguments"




#!c:\Python27\python.exe

import subprocess
import re
import MySQLdb
import os
import time
import cgi

#Device Identification/refresh code 

def listDevices():
	
	# ADB commands to read the devices
	objw = open('out3.txt','w')
	subprocess.call(['C:/adb/adb.exe','devices'], shell=True, stdout=objw)
	objr = open('out3.txt','r')
	obja = open('out2.csv','a')
	for line in objr:
		dummy = line 	
 		for line in objr:	
 			if not line.isspace():	
 				nl = ":".join(line.split())
 				s1 = nl+":NULL"			
 				obja.write(s1+"\n")
		
	objr.close()
	objw.close()
	obja.close()
	loadDB()
	os.remove('out2.csv')
	os.remove('out3.txt')
			

def loadDB():
	logw = open('log.txt','w')
	cnx = MySQLdb.connect(host="sql4.freemysqlhosting.net", 
                     user="sql474573", 
                      passwd="bV6!nP9%",
                      db="sql474573", port=3306)
	cnx2 = MySQLdb.connect(host="sql4.freemysqlhosting.net", 
                     user="sql474573", 
                      passwd="bV6!nP9%",
                      db="sql474573", port=3306)                 
	cursor = cnx.cursor()
	
#For every line in out2.csv check if the device exist in db and if not add a new row. If the device exist ignore and move to next

	dfile = open('out2.csv','r')
	for line in dfile:
		if not line.isspace():	
				p1 = re.compile("\w+")
				devID1 = p1.match(line)
				dID1 = devID1.group();
				typ = "device"
				args10 = (dID1)
				query1="select idDevices from devices where idDevices=%s"
				cursor.execute(query1,args10)
				if cursor.rowcount >0:
					logw.write("Device exist\n")
					
				else:
					logw.write("New Device adding to the Device table..");
					args11 = (dID1,typ)
					query2 = "insert into devices(idDevices,type,status)VALUES(%s,%s,'READY')"
					cursor.execute(query2,args11)
					cnx.commit()
					
	query1 = ("SELECT idDevices from devices")
	cursor.execute(query1)
	cnx.commit()
	for type in cursor:
		for dID1 in type:
			proc = subprocess.Popen(['C:/adb/adb.exe','-s', dID1 , 'shell', 'getprop', 'gsm.sim.state'],stdout=subprocess.PIPE,stderr = logw)
			status = proc.communicate()[0].strip('\n')
			if status in('.*error.*'):
				args11 = (dID1)
				query11 = "update devices SET status='NOT READY' where idDevices = %s"
				#print "Device not Ready"
				cursor.execute(query11,args11)
				cnx.commit()
			else:
				try:
					proc = subprocess.Popen(['C:/adb/adb.exe','-s', dID1 , 'shell', 'getprop', 'ro.product.model'],stdout=subprocess.PIPE)
					model = proc.communicate()[0].strip('\n')
							
					proc = subprocess.Popen(['C:/adb/adb.exe','-s', dID1 , 'shell', 'getprop', 'ro.build.version.release'],stdout=subprocess.PIPE)
					version = proc.communicate()[0].strip('\n')
				
					proc = subprocess.Popen(['C:/adb/adb.exe','-s', dID1 , 'shell', 'getprop', 'gsm.operator.alpha'],stdout=subprocess.PIPE)
					operator = proc.communicate()[0].strip('\n')
							
					proc = subprocess.Popen(['C:/adb/adb.exe','-s', dID1 , 'shell', 'getprop', 'gsm.operator.iso-country'],stdout=subprocess.PIPE)
					country = proc.communicate()[0].strip('\n')
							
					proc = subprocess.Popen(['C:/adb/adb.exe','-s', dID1 , 'shell', 'getprop', 'gsm.network.type'],stdout=subprocess.PIPE)
					networkType = proc.communicate()[0].strip('\n')
							
					proc = subprocess.Popen(['C:/adb/adb.exe','-s', dID1 , 'shell', 'getprop', 'ro.product.brand'],stdout=subprocess.PIPE)
					brand = proc.communicate()[0].strip('\n')
								
					proc = subprocess.Popen(['C:/adb/adb.exe','-s', dID1 , 'shell', 'getprop', 'ro.product.cpu.abi'],stdout=subprocess.PIPE)
					cpu = proc.communicate()[0].strip('\n')
								
					proc = subprocess.Popen(['C:/adb/adb.exe','-s', dID1 , 'shell', 'getprop', 'ro.product.locale.language'],stdout=subprocess.PIPE)
					language = proc.communicate()[0].strip('\n')
								
					proc = subprocess.Popen(['C:/adb/adb.exe','-s', dID1 , 'shell', 'getprop', 'net.bt.name'],stdout=subprocess.PIPE)
					mos = proc.communicate()[0].strip('\n')
											
					proc = subprocess.Popen(['C:/adb/adb.exe','-s', dID1 , 'shell', 'getprop', 'gsm.sim.state'],stdout=subprocess.PIPE)
					status = proc.communicate()[0].strip('\n')			
					cursor=cnx.cursor()
					args4 = (status,dID1)
					query4 = "update devices SET status=%s where idDevices=%s"
					cursor.execute(query4,args4)
					cnx.commit()
					arg6=(dID1)
					query6="select dID from property where dID=%s"
					cursor.execute(query6,arg6)
					cnx.commit()							
					if cursor.rowcount >0:
						logw.write("Device exist in property table.Hence updating the properties\n");
						query7 = "UPDATE property SET dID=%s,OS=%s,version=%s,model=%s,brand=%s,operator=%s,network=%s,cpu=%s,country=%s,language=%s where dID=%s"
						args7 = (dID1,mos,version,model,brand,operator,networkType,cpu,country,language,dID1)
						cursor.execute(query7,args7)
						cnx.commit() 
					else:
						logw.write("Device does not exist in property table. Properties of this new device will be added\n")
						args8 = (dID1,mos,version,model,brand,operator,networkType,cpu,country,language)
						query8 = "INSERT INTO property (dID,OS,version,model,brand,operator,network,cpu,country,language) VALUES (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s); "
						cursor.execute(query8,args8)
						cnx.commit()																																																		
				except subprocess.CalledProcessError as exception:
					logw.write(exception.output+"\n");												
	cursor.close()

	logw.close()	
listDevices()


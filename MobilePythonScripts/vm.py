#!c:\Python27\python.exe
import subprocess
import re
import os
import time
import sys

virtualBox_Path="C:\Program Files\Oracle\VirtualBox\\"
virtualBox=virtualBox_Path+"VBoxManage.exe"
vmPath=r'C:\Users\vroopa\VirtualBox VMs'

def listVMS():
	
	out = subprocess.Popen([virtualBox,'list','vms'], shell=True, stdout=subprocess.PIPE)
	arr=[]
	for x in out.stdout :	
		val= x.split('"')
		arr.append(val[1])
	print	arr
	#return arr
def checkVMRunning(name):
	if(len(sys.argv) < 1):
		print "You must set argument!!!"
		print "Usage"+" "+sys.argv[0]+" "+"<VMName>"
		return 0
	else:
		out = subprocess.Popen([virtualBox,'list','runningvms'], shell=True,stdout=subprocess.PIPE)
		arr=[]
		for x in out.stdout :	
			val= x.split('"')
			arr.append(val[1])
		if name in arr:
			print "Running"
			return 1
		else:
			print "Not Running"
			#print "VM is NOT running"
			return 0
def checkVMExist(name):
	if(len(sys.argv) < 1):
		print "You must set argument!!!"
		print "Usage"+" "+sys.argv[0]+" "+"<VMName>"
		return 0
	else:
		vms=listVMS()
		if name in vms:
			print "Exist"
			#sys.stdout.flush()
			return 1
		else:
			print "not exist"
			return 0
def createVM(name,typ,mem):
	if(len(sys.argv) < 1):
		print "You must set the required arguments!!!"
		print "Usage"+" "+sys.argv[0]+" "+"<VM Name>"+" "+"<OS Type>"+" "+"<Memory>"
		return "Unable to create VM"
	else:
		if (checkVMExist(name)==0):
			clone_source=vmPath+"\\"+"android\\android.qcow"
			clone_dest=vmPath+"\\"+name+"\\"+name+".qcow"
			print clone_dest+"testing"
			#Create VM
			subprocess.call([virtualBox,'createvm','--register', '--name',name,'--ostype',typ], shell=True)
			#Set VM memory
			subprocess.call([virtualBox,'modifyvm',name,'--memory',mem], shell=True)
			#Add storage control
			subprocess.call([virtualBox,'storagectl',name,'--name','IDE Controller','--add','ide'], shell=True)
			#Clone HD
			#subprocess.call([virtualBox,'clonehd','--format','qcow',clone_source,clone_dest], shell=True)
			subprocess.call([virtualBox,'clonehd',clone_source,clone_dest], shell=True)
			#Modify HD
			subprocess.call([virtualBox,'modifyvm',name,'--hda',clone_dest], shell=True)
			if(checkVMExist(name)==1):
				startVM(name)
				print str(1)
				sys.stdout.flush()
				return 1
			else:
				print "0"
				return 0
		else:
			#print "VM already exist"
			return "VM Exist"
		
def startVM(name):
	if(len(sys.argv) < 1):
		print "You must set argument!!!"
		print "Usage"+" "+sys.argv[0]+" "+"<VMName>"
		return "Unable to Start"
	else:
		if checkVMRunning(name) ==0:
			subprocess.call([virtualBox,'startvm',name,'--type','gui'], shell=True)
			wait=0
			while True:
				if wait<60:
					time.sleep(5)
					if checkVMRunning(name) ==0:
						wait=wait+5
					else:
						print "Started"
						return "Started"
						break
				else:
					print "not started"
					return "Not Started"
		else:
			print "started"
			return "Started"	

def stopVM(name):
	if(len(sys.argv) < 1):
		print "You must set argument!!!"
		print "Usage"+" "+sys.argv[0]+" "+"<VMName>"
		return " Unable to Stop"
	else:
		if checkVMRunning(name) ==1:
			subprocess.call([virtualBox,'controlvm',name,'savestate'], shell=True)
			wait=0
			while True:
				if wait<60:
					time.sleep(5)
					if checkVMRunning(name) ==1:
						wait=wait+5
					else:
						print "stopped"
						return "Stopped"
						break
				else:
					print "not stopped"
					return " Not Stopped"
		else:
			print "stopped"
			return "Stopped"	
def deleteVM(name):
	if(len(sys.argv) < 1):
		print "You must set argument!!!"
		print "Usage"+" "+sys.argv[0]+" "+"<VMName>"
		return "Unable to delete"
	else:
		stopVM(name)
		if checkVMExist(name) == 1:
			if checkVMRunning(name)==0:
				subprocess.call([virtualBox,'unregistervm',name,'--delete'], shell=True)
				if(checkVMExist(name)==0):
					print "Success"
					return "Deleted"
				else:
					print "Failed"
					return "Not Deleted"
			else:
				return "VM is Running. Not Deleted"
		else:
			print "Success"
			return "Deleted"

# Function Calling

if sys.argv[1].strip()=='list':
	listVMS()
elif sys.argv[1]=="running":
	 checkVMRunning(sys.argv[2])
elif sys.argv[1]=="exist":
	 checkVMExist(sys.argv[2])
elif sys.argv[1]=="create":
	 createVM(sys.argv[2],sys.argv[3],sys.argv[4])
elif sys.argv[1]=="start":
	 startVM(sys.argv[2])
elif sys.argv[1]=="stop":
	 stopVM(sys.argv[2])
elif sys.argv[1]=="delete":
	deleteVM(sys.argv[2])
else:
	print "Not a valid argument"

	
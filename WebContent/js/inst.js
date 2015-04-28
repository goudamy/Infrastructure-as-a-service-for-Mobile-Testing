//Launch Instance
function lnch_inst() {
	try{
		
		
		
		 BootstrapDialog.show({
            title: 'Launch Instances',
            message: function(dialog) {
                var $message = $('<div></div>');
                var pageToLoad = dialog.getData('pageToLoad');
                $message.load(pageToLoad);
                return $message;
            },
            data: {
                'pageToLoad': './html/launch_instance.html'
            },
            buttons: [{
                label: 'Cancel',
                action: function(dialog){
                    dialog.close();
                }
            }, {
                label: 'Launch',
                cssClass: 'btn-primary',
                action: function(dialog) {
                	
                	if($("#flavor").val().trim() === "VMEmulator"){
                		try{t.open();}catch(e){console.log(e)}
                		setTimeout(function(){dialog.close(); },2000);
                		 $.ajax({
      					   url: 'data',
      					   data: {
      					      type: 'instance',
      					      action: 'em_create',
      					      name: $("#instanceName").val(),
      					      mobileHub_name: 'MobileHub1'
      					   },
      					   async:   false,
      					   error: function() {
      					      console.log("Error in Emulator Create.")
      					      t.close();
      					   },
      					   success:function(data1){},
      					   complete:function(){
      						 setTimeout(function(){t.close(); },2000);
              			   
      						 list_remote();
              			   
      					   },
      					   type:'POST'
                		 });
                		 return;
                	}
                	
                	if($("#instanceName").val().trim() === "" || $("#count").val() === ""){
                		try{BootstrapDialog.show({
                			type:BootstrapDialog.TYPE_WARNING,
                	        title: 'Warning',
                	        message: 'Please enter all fields marked with (*).'
                	    });}catch(e){console.log("Check Launch params.")}
                	    return;
                		}
                	var instName = $("#instanceName").val();
                	var instCount = $("#count").val();
                	var instFlavor = $("#flavor").val();
                	var imgName = $("#imageName").val();
                	var types = ["m1.nano","m1.micro","m1.tiny","m1.small"];
                	var zone = $("#selectzone").val();
                	var algor = $('#selectalgo').val().trim().toUpperCase();
                	if(algor === "NONE") algor = null;
                	$.each(types, function(index, type){
	                	if(instFlavor === type.toString()){
	                		
	                		switch(index){
	                			case 0: instFlavor = 42; break;
	                			case 1: instFlavor = 84; break;
	                			case 2: instFlavor = 1; break;
	                			case 3: instFlavor = 2; break;
	                		}
	                	}
                	});
                	try{t.open();}catch(e){console.log(e)}
                	setTimeout(function(){dialog.close(); },2000);
                	$.ajax({
                		   url: 'data',
                		   data: {
                		      type: 'instance',
                		      action: 'launch',
                		      count: instCount,
                		      flavor: instFlavor,
                		      instanceName: instName,
                		      regionName: zone,
                		      imageName: encodeURI(imgName),
                		      algo:algor
                		   },
                		   error: function() {
                		      console.log("Error in Launch Instance.")
                		   },
                		   success: function(data) {
                			   setTimeout(function(){t.close(); },2000);
                		      list_inst();
                		      
                		   },
                		   type: 'POST'
                		});  
                }
            }]
        });
		 
		

	} catch(e)
	{console.log("Issue with loading Launch Instance dialog." + e)
		 
	} finally {
		function algoenable(){
		     
			 setTimeout(function(){
				 if( $("#selectzone").length > 0){
			 $("#selectzone").prop("disabled", false);
				$('#selectalgo').on("change",function() {
					  if($("#selectalgo").val().trim() === "None"){
						  $("#selectzone").prop("disabled", false);
					  } else {
						  $("#selectzone").prop("disabled", true);
					  }
					  
					});  
				
				 
				flavor
				
				$("#flavor").prop("disabled", false);
				$('#flavor').on("change",function() {
					  if($("#flavor").val().trim() === "VMEmulator"){
						 
						  $("#flavor").prop("disabled", true);
						  $("#count").prop("disabled", true);
						  $("#selectzone").prop("disabled", true);
						  $("#selectError").prop("disabled", true);
						  $("#imageName").val("android");
						  $("#imageName").prop("disabled", true);
					  } else {
						  $("#flavor").prop("disabled", false);
						  $("#count").prop("disabled", false);
						  $("#selectzone").prop("disabled", false);
						  $("#selectError").prop("disabled", false);
						  
					  }
					  
					}); 
				 
				 
				 } else {algoenable();}
			 }, 500);
		    
				
			 }
			 algoenable();	
	}
}

//Add Hub

function add_hub(){
	
	try{
		

		 BootstrapDialog.show({
          title: 'Add Mobile Hub',
          message: function(dialog) {
              var $message = $('<div></div>');
              var pageToLoad = dialog.getData('pageToLoad');
              $message.load(pageToLoad);
      
              return $message;
          },
          data: {
              'pageToLoad': './html/create_mhub.html'
          },
          buttons: [{
              label: 'Cancel',
              action: function(dialog){
                  dialog.close();
              }
          }, {
              label: 'Add',
              cssClass: 'btn-primary',
              action: function(dialog) {
            	  if($("#mhubname").val().trim() === "" || $("#mhubip").val().trim() === ""){
          			try{BootstrapDialog.show({
          				type:BootstrapDialog.TYPE_WARNING,
          		        title: 'Warning',
          		        message: 'Please enter all the fields marked with (*).'
          		    });}catch(e){console.log("Mobile Hub Add")}
          		    return;
          			}
          		var mobileHub_name = $("#mhubname").val().trim();
          		var mobileHub_ip = $("#mhubip").val().trim();
            	  
            	  try{
            			try{t.open();}catch(e){console.log(e)}
            			
            	                	$.ajax({
            	                		   url: 'data',
            	                		   data: {
            	                		      type: 'mobilehub',
            	                		      action: 'add',
            	                		      mobileHub_name: mobileHub_name,
            	                		      mobileHub_ip: mobileHub_ip
            	                		   },
            	                		   error: function() {
            	                		      console.log("Error in Add hub.")
            	                		   },
            	                		   success: function(data) {
            	                			   setTimeout(function(){t.close(); },2000);
            	                		      list_inst();
            	                		      
            	                		   },
            	                		   type: 'POST'
            	                		});  
            		} catch(e)
            		{console.log("Issue with add hub ." + e)}
    
              }
          }]
      });
	

	} catch(e)
	{console.log("Issue with loading Add Image dialog.")}
}


//Launch Instance
function delete_inst(instName,hostip) {
	try{
		try{t.open();}catch(e){console.log(e)}
		
                	$.ajax({
                		   url: 'data',
                		   data: {
                		      type: 'instance',
                		      action: 'delete',
                		      host:encodeURI(hostip),
                		      instanceName: instName
                		   },
                		   error: function() {
                		      console.log("Error in Delete Instance.")
                		   },
                		   before: function(){
                			   try{t.open();}catch(e){console.log(e)}
                		   },
                		   success: function(data) {
                			   setTimeout(function(){t.close(); },2000);
                		      list_inst();
                		      
                		   },
                		   type: 'POST'
                		});  
	} catch(e)
	{console.log("Issue with deleting Instance ." + e)}
}


//Loading Img
var t;
loadingpg();
function loadingpg(){
	 
	try{

		t = new BootstrapDialog({
          title: 'Loading Page...Please wait!',
          message:  $('<div></div>').load('./html/loading.html'),
          closable: false,
          closeByBackdrop: false,
          closeByKeyboard: false	
      });

	} catch(e)
	{console.log("Issue with loading Add Image dialog.")}
}


//List Instance
function list_inst() {
	
	$( "#content" ).load( "html/list_instances.html" );
	try{t.open();}catch(e){console.log(e)}
	

}

//Create Project

function creat_proj() {
	$("#content").load("./html/project_create.html");
	try{t.open();}catch(e){console.log(e)}
	$.ajax({
		   url: 'data',
		   data: {
		      type: 'dropdown',
		      action: 'inst'
		   },
		   error: function() {
		      console.log("Error in getting dropdown data for Create Project.")
		      t.close();
		   },
		   success: function(data) {
			  //Test Server Populate
			   $("#TestServer").empty();
			   var s = data.split('#@##')[0];
			   var ts = s.split(',');
			   for (i=0; i<ts.length - 1; i++){
				   if(ts[i] != "")
				   $("#TestServer").append("<option>"+ts[i]+"</option>");
			   }
			   
			 //Pmob Server Populate
			   var demo1 = $("#pmob_select").bootstrapDualListbox({ nonSelectedListLabel: 'Available Resources',
				   selectedListLabel: 'Selected Resources', bootstrap2compatible : true });
			   $("#Pmobile select:first").empty();
			   demo1.bootstrapDualListbox('refresh');
			   var t1 = data.split('#@##')[1];
			   var ps = t1.split(',');
			   for (i=0; i<ps.length - 1; i++){
				   if(ps[i] != "")
				   demo1.append("<option value='"+ps[i]+"'>"+ps[i]+"</option>");
				   demo1.bootstrapDualListbox('refresh');
			   }
			     
			   var demo2 = $("#emob_select").bootstrapDualListbox({ nonSelectedListLabel: 'Available Resources',
				   selectedListLabel: 'Selected Resources', bootstrap2compatible : true });
			   $("#Emobile select:first").empty();
			   demo2.bootstrapDualListbox('refresh');
			   var t1 = data.split('#@##')[2];
			   var ps = t1.split(',');
			   for (i=0; i<ps.length - 1; i++){
				   if(ps[i] != "")
				   demo2.append("<option value='"+ps[i]+"'>"+ps[i]+"</option>");
				   demo2.bootstrapDualListbox('refresh');
			   }
			   
			   t.close();
		   },
		   type: 'POST'
		});  
	
	

}

//Action button Create Proj

function crt_proj(){
	
	if($("#projname").val().trim() === ""){
	try{BootstrapDialog.show({
		type:BootstrapDialog.TYPE_WARNING,
        title: 'Warning',
        message: 'Please enter a valid project name.'
    });}catch(e){console.log("Mobile Proj Name")}
    return;
	}
	
	if($("#TestServer").val().trim() === ""){
		try{BootstrapDialog.show({
			type:BootstrapDialog.TYPE_WARNING,
	        title: 'Warning',
	        message: 'Please select a Test Server for the project.'
	    });}catch(e){console.log("Mobile Test Server")}
	    return;
		}
	
	if($("#Pmobile .box2 select").html().trim() === "" && $("#Emobile .box2 select").html().trim() === ""){
		try{BootstrapDialog.show({
			type:BootstrapDialog.TYPE_WARNING,
	        title: 'Warning',
	        message: 'Please select a resource to allocate.'
	    });}catch(e){console.log("Resource selection")}
	return;	
	}
	
	var n = [], m = [], o = "";
	$("#Pmobile .box2 select option").each(function(){n.push($(this).val())});
	$("#Emobile .box2 select option").each(function(){m.push($(this).val())});
	o = $("#projname").val().trim();
	p = n.join();
	q = m.join();
	r = $("#TestServer").val();
	try{t.open();}catch(e){console.log(e)}
	$.ajax({
		   url: 'data',
		   data: {
		      type: 'createproj',
		      action: 'insert',
		      value: o+"##@#@"+r+"##@#@"+q+"##@#@"+p+"##@#@"+"inactive"
		   },
		   error: function() {
		      console.log("Error in Project Creation.")
		      t.close();
		   },
		   success:function() {
			   setTimeout(function(){t.close();},2000);
			   list_proj();
			   try{BootstrapDialog.show({
					type:BootstrapDialog.TYPE_WARNING,
			        title: 'Warning',
			        message: 'Please activate the newly created project.  Project Name: '+o
			    });}catch(e){console.log("Mobile Proj Create")}
			    
			   },
		   type:'POST'
		   });
	
}

//Delete Project

function del_proj(prj){
	try{t.open();}catch(e){console.log(e)}
	$.ajax({
		   url: 'data',
		   data: {
		      type: 'createproj',
		      action: 'delete',
		      value: prj
		   },
		   error: function() {
		      console.log("Error in Project Creation.")
		      t.close();
		   },
		   success:function() {
			   setTimeout(function(){t.close(); },2000);
			   
		   },
		   complete: function(){list_proj();},
		   type:'POST'
		   });
}


//List Remote

function list_remote(){
	
	$("#content").load("./html/list_remote.html");
	try{t.open();}catch(e){console.log(e)}
	$.ajax({
		   url: 'data',
		   data: {
		      type: 'instance',
		      action: 'em_list',
		      mobileHub_name: 'MobileHub1'
		   },
		   error: function() {
		      console.log("Error in Emulator listing.")
		      t.close();
		   },
		   success:function(data) {
			   var i = data.trim();
			   var n = i.substring(1, i.length-1);
			   var m = n.split(",");
			   var jk = "";
			   for(j=0;j<m.length;j++){
				   
				   jk = m[j].trim().substring(1,m[j].trim().toString().length-1);
				   $.ajax({
					   url: 'data',
					   data: {
					      type: 'instance',
					      action: 'em_checkStatus',
					      name: jk,
					      mobileHub_name: 'MobileHub1'
					   },
					   async:   false,
					   error: function() {
					      console.log("Error in Emulator Check status.")
					      t.close();
					   },
					   success:function(data1) {
						   if(data1.trim() === "Running"){
							   //<td class="center">'+""+'</td>
						   $("#list_emulator tbody").append('<tr><td>'+jk+'</td><td class="center">1</td><td class="center">512MB</td><td class="center">Android 4.4</td><td class="center"><a href="#" onclick="start_stp_emu(\''+jk+'\',this,1)"><span class="label label-success">Running</span></a></td><td class="center"><a class="btn btn-danger" href="javascript:del_remote(\''+jk+'\')"><i class="halflings-icon white trash"></i> </a></td></tr></tr>');
						   } else {
							   $("#list_emulator tbody").append('<tr><td>'+jk+'</td><td class="center">1</td><td class="center">512MB</td><td class="center">Android 4.4</td><td class="center"><a href="#" onclick="start_stp_emu(\''+jk+'\',this,0)"><span class="label">Shutdown</span></a></td><td class="center"><a class="btn btn-danger" href="javascript:del_remote(\''+jk+'\')"><i class="halflings-icon white trash"></i> </a></td></tr></tr>');
						   }
						   },
					   type:'POST'
				   });
				   
				   
			   }
			   
		   },
		   complete:function(){setTimeout(function(){t.close(); },2000);},
		   type:'POST'
		   });
	
	$.ajax({
		 	url: 'data',
		 	dataType: 'json',
		   data: {
		      type: 'instance',
		      action: 'phone_list',
		      mobileHub_name: 'MobileHub1'
		   },
		   error: function() {
		      console.log("Error in Phone listing.")
		      t.close();
		   },
		   success:function(data) {
			   var mhub = data;
			   for(i=0; i<mhub.topic.details.length;i++){
				   if(mhub.topic.details[i].status === "READY"){
					   $("#list_pmobile tbody").append('<tr><td>'+mhub.topic.details[i].dID+'</td><td class="center">'+mhub.topic.details[i].os+'</td><td class="center">'+mhub.topic.details[i].version+'</td><td class="center">'+mhub.topic.details[i].cpu+'</td><td class="center"><span class="label label-success">'+mhub.topic.details[i].status+'</span></td><td class="center"><a class="btn btn-warning" href="javascript:physical_reboot(\''+mhub.topic.details[i].dID+'\')"><i alt="Refresh" class="halflings-icon white refresh"></i> </a></td></tr></tr>');
				   }else{
					   $("#list_pmobile tbody").append('<tr><td>'+mhub.topic.details[i].dID+'</td><td class="center">'+mhub.topic.details[i].os+'</td><td class="center">'+mhub.topic.details[i].version+'</td><td class="center">'+mhub.topic.details[i].cpu+'</td><td class="center"><span class="label">'+mhub.topic.details[i].status+'</span></td><td class="center"><a class="btn btn-warning" href="javascript:physical_reboot(\''+mhub.topic.details[i].dID+'\')"><i alt="Refresh" class="halflings-icon white refresh"></i> </a></td></tr></tr>');   
				   }
			   }
			   
		   },
		   complete:function(){},
		   type:'POST'
	});
	
	
}

//Reboot physcial mobile
function physical_reboot(serial){
	
	$.ajax({
	 	url: 'data',
	   data: {
	      type: 'instance',
	      action: 'phone_reboot',
	      device_id: serial,
	      mobileHub_name: 'MobileHub1'
	   },
	   error: function() {
	      console.log("Error in Phone reboot.")
	      t.close();
	   },
	   success:function(data) {
	   },
	   complete:function(){},
	   type:'POST'
});

	
}

//Start_stop Remote
function start_stp_emu(val,obj,i){
	if(i === 1){
		//Stop emulator
		try{t.open();}catch(e){console.log(e)}
		$.ajax({
			   url: 'data',
			   data: {
			      type: 'instance',
			      action: 'em_stop',
			      name: val,
			      mobileHub_name: 'MobileHub1'
			    	  
			   },
			   error: function() {
			      console.log("Error in Emulator Stop.")
			      t.close();
			   },
			   success:function(data) {
				   
				   setTimeout(function(){t.close(); },2000);
				   
			   },
			   complete:function(){setTimeout(function(){t.close(); },2000);
			   $(obj).parent().find("a").attr("onclick", "start_stp_emu('"+val+"',this,0)");
			   $(obj).find("span").html("Shutdown");
			   $(obj).find("span").toggleClass( "label-success" );
			   },
			   type:'POST'
			   });
		
		
	} else {
		try{t.open();}catch(e){console.log(e)}
		$.ajax({
			   url: 'data',
			   data: {
			      type: 'instance',
			      action: 'em_start',
			      name: val,
			      mobileHub_name: 'MobileHub1'
			    	  
			   },
			   error: function() {
			      console.log("Error in Emulator Start.")
			      t.close();
			   },
			   success:function(data) {
				   
				   setTimeout(function(){t.close(); },2000);
				   
			   },
			   complete:function(){setTimeout(function(){t.close(); },2000);
			   $(obj).parent().find("a").attr("onclick", "start_stp_emu('"+val+"',this,1)");
			   $(obj).parent().find("span").html("Running");
			   $(obj).parent().find("span").toggleClass( "label-success" );
			   },
			   type:'POST'
			   });
	}
	
	
	
}

//Delete Remote

function del_remote(vmname){
	
	try{t.open();}catch(e){console.log(e)}
	$.ajax({
		   url: 'data',
		   data: {
		      type: 'instance',
		      action: 'em_delete',
		      name: vmname,
		      mobileHub_name: 'MobileHub1'
		    	  
		   },
		   error: function() {
		      console.log("Error in Emulator delete.")
		      t.close();
		   },
		   success:function(data) {
			   
			   setTimeout(function(){t.close(); },2000);
			   
		   },
		   complete:function(){setTimeout(function(){t.close(); },2000); list_remote();},
		   type:'POST'
		   });
	
	
}



//Billing Tariff

function bill_tariff(){
	
	$("#content").load("./html/billing_tariff.html");

}

//Billing Generate

function bill_gen(){
	
	$("#content").load("./html/billing_gen.html");
	try{t.open();}catch(e){console.log(e)}
	
	$.ajax({
		   url: 'data',
		   dataType: 'json',
		   data: {
		      type: 'billing',
		      action: 'list'
		   },
		   error: function() {
		      console.log("Error in listing bill")
		      t.close();
		   },
		   success:function(data) {
			   
		   t.close(); 
		   var bill = data;
		  
		   var us =0;
		   var india =0;
		   var aus =0;
		   var china =0;
		   for(i=0; i< bill.topic.details.length;i++){	
			   if(bill.topic.details[i].regionname == "US"){
				  us = us+parseFloat(bill.topic.details[i].total); 
			   }
			   if(bill.topic.details[i].regionname == "China"){
					  china = china+parseFloat(bill.topic.details[i].total); 
				   }
			   if(bill.topic.details[i].regionname == "India"){
					  india =india+ parseFloat(bill.topic.details[i].total); 
				   }
			   if(bill.topic.details[i].regionname == "Australia"){
					  aus =aus+ parseFloat(bill.topic.details[i].total); 
				   }
			   var total_Amount = us+china+india+aus;
			   $("#bill_gen tbody").append('<tr><td class="center">'+bill.topic.details[i].BillId+'</td><td class="center">'+bill.topic.details[i].regionid+'</td><td class="center">'+bill.topic.details[i].regionname+'</td><td class="center">$'+bill.topic.details[i].tariff+'</td><td class="center">'+bill.topic.details[i].time+'</td><td class="center">'+bill.topic.details[i].InstanceName+'</td><td class="center">$'+bill.topic.details[i].total+'</td></tr>');
		   }
		 	
			   $("#bill_gen1 tbody").append('<tr><td class="center">'+"US"+'</td><td class="center">$'+us+'</td></tr><tr><td class="center">'+"China"+'</td><td class="center">$'+china+'</td></tr><tr><td class="center">'+"India"+'</td><td class="center">$'+india+'</td></tr><tr><td class="center">'+"Australia"+'</td><td>$'+aus+'</td><tr><td>Grand Total</td><td><span class=\"label label-success\">$'+total_Amount+'</td></span></tr>');   
		   
		   loading_datatable();
		   },
		   type:'POST'
		   });
}

//List Project

function list_proj() {
	$("#content").load("./html/list_project.html");
	try{t.open();}catch(e){console.log(e)}
	
	$.ajax({
		   url: 'data',
		   data: {
		      type: 'createproj',
		      action: 'list'
		   },
		   error: function() {
		      console.log("Error in Project list.")
		      t.close();
		   },
		   success:function(data) {
			   t.close(); 
		   var z  = data.split('#@@#');
		   $("#list_projtable tbody").empty();
		   for(i=0; i<z.length;i++){
			   var x = z[i].split('%%#');
			   if(x[0].trim() === "") continue;
			   var a ="",b="";
			   if(x[3]){var y = x[3].split(',');
			   for(p=0;p<y.length;p++){
				   a += "<div>"+y[p]+"</div>";
			   }
			   }
			   if(x[4]){
				   var w = x[4].split(',');
				   for(p=0;p<w.length;p++){
				   b += "<div>"+w[p]+"</div>";
//				   console.log(b);
			   }
			   }
			   c = "label-success";
			   d = "Active";
			   e = "Inactive";
			   if(x[5] === "active")
			   {$("#list_projtable tbody").append('<tr><td class="center">'+x[0]+'</td><td class="center">'+x[1]+'</td><td class="center"><a href="javascript:void()">Click Here</a></td><td class="center">'+x[2]+'</td><td class="center">'+a+'</td><td class="center">'+b+'</td><td class="center"><a href="javascript:deact_proj(\''+x[0]+'\',0)"><span class="label label-success">'+d+'</span></a></td><td class="center"><a class="btn btn-danger" href="javascript:del_proj(\''+x[0]+'\')"><i class="halflings-icon white trash"></i> </a></td></tr>');}
			   else 
			   {$("#list_projtable tbody").append('<tr><td class="center">'+x[0]+'</td><td class="center">'+x[1]+'</td><td class="center"><a href="javascript:void()">Click Here</a></td><td class="center">'+x[2]+'</td><td class="center">'+a+'</td><td class="center">'+b+'</td><td class="center"><a href="javascript:active_proj(\''+x[0]+'\',0)"><span class="label">'+e+'</span></a></td><td class="center"><a class="btn btn-danger" href="javascript:del_proj(\''+x[0]+'\')"><i class="halflings-icon white trash"></i> </a></td></tr>');}
			   
			   
		   }loading_datatable();
		   },
		   type:'POST'
		   });

}

//Deacivate Project

function deact_proj(prjnm,val){
	
	try{t.open();}catch(e){console.log(e)}
	$.ajax({
		   url: 'data',
		   data: {
		      type: 'createproj',
		      action: 'deactivate',
		      value: prjnm
		   },
		   error: function() {
		      console.log("Error in Project deactivation.")
		      t.close();
		   },
		   success:function() {
			   setTimeout(function(){t.close();},2000);
			   if(val === 0) list_proj();
			   if(val === 1) index_list_proj();
		   },
		   type:'POST'
		   });
	
}

//Activate Project

function active_proj(prjnm,val){
	
	try{t.open();}catch(e){console.log(e)}
	$.ajax({
		   url: 'data',
		   data: {
		      type: 'createproj',
		      action: 'activate',
		      value: prjnm
		   },
		   error: function() {
		      console.log("Error in Project activation.")
		      t.close();
		   },
		   success:function() {
			   setTimeout(function(){t.close();},2000);
			   if(val === 0) list_proj();
			   if(val === 1) index_list_proj();
		   },
		   type:'POST'
		   });
	
}

//Parse Adv options for table

function loading_datatable(){
	
	setTimeout(function(){
		if($('.datatable')){
$('.datatable').dataTable({
	"sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span12'i><'span12 center'p>>",
	"sPaginationType": "bootstrap",
	"oLanguage": {
	"sLengthMenu": "_MENU_ records per page"
	}
} );
}  else {loading_datatable()}

	},500);
}


//Listing Proj on Index file
function index_list_proj(){
	try{t.open();}catch(e){console.log(e)}
	$("#mob_projtable tbody").empty();
	$.ajax({
		   url: 'data',
		   data: {
		      type: 'createproj',
		      action: 'list'
		   },
		   error: function() {
		      console.log("Error in Project list.")
		      t.close();
		   },
		   success:function(data) {t.close(); 
		   var z  = data.split('#@@#');
		   
		   for(i=0; i<z.length;i++){
			   var x = z[i].split('%%#');
			   if(x[0].trim() === "") continue;
			   d = "Active";
			   e = "Inactive";
			   if(x[5] === "active")
			   {$("#mob_projtable tbody").append('<tr><td>'+x[0]+'</td><td class="center">'+x[1]+'</td><td class="center"><a href="javascript:deact_proj(\''+x[0]+'\',1)"><span class="label label-success">'+d+'</span></a></td></tr>');}
			   else 
			   {$("#mob_projtable tbody").append('<tr><td>'+x[0]+'</td><td class="center">'+x[1]+'</td><td class="center"><a href="javascript:active_proj(\''+x[0]+'\',1)"><span class="label">'+e+'</span></a></td></tr>');}
			   
		   }
		   },
		   type:'POST'
		   });
}

//Add Images
function add_img() {
	try{

		 BootstrapDialog.show({
            title: 'Add Image',
            message: function(dialog) {
                var $message = $('<div></div>');
                var pageToLoad = dialog.getData('pageToLoad');
                $message.load(pageToLoad);
        
                return $message;
            },
            data: {
                'pageToLoad': './html/add_image.html'
            },
            buttons: [{
                label: 'Cancel',
                action: function(dialog){
                    dialog.close();
                }
            }, {
                label: 'Add',
                cssClass: 'btn-primary',
                action: function(dialog) {
//                    dialog.setTitle('Title 2');
                }
            }]
        });

	} catch(e)
	{console.log("Issue with loading Add Image dialog.")}
}


//list Images 
function list_img() {
	$("#content").load("./html/listImage.html");

}


//list MHub 
function list_hub() {
	$("#content").load("./html/listMhub.html");
	try{t.open();}catch(e){console.log(e)}
	
	$.ajax({
		   url: 'data',
		   dataType: 'json',
		   data: {
		      type: 'mobilehub',
		      action: 'list'
		   },
		   error: function() {
		      console.log("Error in MobileHub list.")
		      t.close();
		   },
		   success:function(data) {
			   
		   t.close(); 
		   var mhub = data;
		   for(i=0; i<mhub.topic.details.length;i++){
			   $("#list_mhub tbody").append('<tr><td class="center">'+mhub.topic.details[i].mobileHub_id+'</td><td class="center">'+mhub.topic.details[i].mobileHub_name+'</td><td class="center">'+mhub.topic.details[i].mobileHub_ip+'</td></tr>');
		   }
		     
		   loading_datatable();
		   },
		   type:'POST'
		   });

}


//Create Flavor
function create_flv(){
	try{

		 BootstrapDialog.show({
           title: 'Create Flavor',
           message: function(dialog) {
               var $message = $('<div></div>');
               var pageToLoad = dialog.getData('pageToLoad');
               $message.load(pageToLoad);
       
               return $message;
           },
           data: {
               'pageToLoad': './html/create_flv.html'
           },
           buttons: [{
               label: 'Cancel',
               action: function(dialog){
                   dialog.close();
               }
           }, {
               label: 'Create',
               cssClass: 'btn-primary',
               action: function(dialog) {
//                   dialog.setTitle('Title 2');
               }
           }]
       });

	} catch(e)
	{console.log("Issue with loading Add Image dialog.")}
}



//List Flavor
function list_flv(){
	$("#content").load("./html/list_flavor.html");
	
}

//Billing
function list_bill(){
	$("#content").load("./html/billing.html");
}


//Admin Action Instance
function admin_inst() {
	try{
		
		 BootstrapDialog.show({
            title: 'Admin Action',
            message: function(dialog) {
                var $message = $('<div></div>');
                var pageToLoad = dialog.getData('pageToLoad');
                $message.load(pageToLoad);
                return $message;
            },
            data: {
                'pageToLoad': './html/admin_instance.html'
            },
            buttons: [{
                label: 'Cancel',
                action: function(dialog){
                    dialog.close();
                }
            }, {
                label: 'Admin action',
                cssClass: 'btn-primary',
                action: function(dialog) {
                	if($("#instanceName").val().trim() === ""){
                		try{BootstrapDialog.show({
                			type:BootstrapDialog.TYPE_WARNING,
                	        title: 'Warning',
                	        message: 'Please enter all fields marked with (*).'
                	    });}catch(e){console.log("Check admin aciton params.")}
                	    return;
                		}
                	var instName = $("#instanceName").val();
                	var adminAction = $("#adminAction").val();
                	var types = ["stop","start","suspend","resume","pause","unpause","delete"];
                	$.each(types, function(index, type){
	                	if(adminAction === type.toString()){
	                		
	                		switch(index){
	                			case 0: adminAction = "os-stop"; break;
	                			case 1: adminAction = "os-start"; break;
	                			case 2: adminAction = "suspend"; break;
	                			case 3: adminAction = "resume"; break;
	                			case 4: adminAction = "pause"; break;
	                			case 5: adminAction = "unpause"; break;
	                			case 5: adminAction = "delete"; break;
	                		}
	                	}
                	});
                	try{t.open();}catch(e){console.log(e)}
                	$.ajax({
                		   url: 'data',
                		   data: {
                		      type: 'instance',
                		      action: adminAction,
                		      instanceName: instName
                		   },
                		   error: function() {
                		      console.log("Error in admin action for Instance")
                		   },
                		   success: function(data) {
                			   t.close();
                			   dialog.close();
                		      list_inst();
                		      
                		   },
                		   type: 'POST'
                		});  
                }
            }]
        });
		 $("#count").val(1);

	} catch(e)
	{console.log("Issue with loading Admin action Instance dialog." + e)}
}

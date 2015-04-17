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
                	var instName = $("#instanceName").val();
                	var instCount = $("#count").val();
                	var instFlavor = $("#flavor").val();
                    dialog.setTitle('Launching Instance');                    
                    //dialog.setTitle(t);
                	$.ajax({
                		   url: 'data',
                		   data: {
                		      type: 'instance',
                		      action: 'launch',
                		      //action: 'testLaunch',
                		      count: instCount,
                		      flavor: instFlavor,
                		      instanceName: instName
                		      
                		   },
                		   error: function() {
                		      $('#info').html('<p>An error has occurred</p>');
                		   },
                		   success: function(data) {
                		      $('#info').html('<p>Instance creation succeeded</p>');
                		   },
                		   type: 'POST'
                		});  
                }
            }]
        });

	} catch(e)
	{console.log("Issue with loading Launch Instance dialog.")}
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

	$( "#content" ).load( "html/list_instance.html" );
	

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
			   
			   t.close();
		   },
		   type: 'POST'
		});  
	
	

}

//Action button Create Proj

function crt_proj(){
	
	if($("input").val().trim() === ""){
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
	o = $("input").val().trim();
	p = n.join();
	q = m.join();
	r = $("#TestServer").val();
	try{t.open();}catch(e){console.log(e)}
	$.ajax({
		   url: 'data',
		   data: {
		      type: 'createproj',
		      action: 'insert',
		      value: o+"##@#@"+r+"##@#@"+q+"##@#@"+p+"##@#@"+"active"
		   },
		   error: function() {
		      console.log("Error in Project Creation.")
		      t.close();
		   },
		   success:function() {t.close(); list_proj();},
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
		   success:function(data) {t.close(); 
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
			   {$("#list_projtable tbody").append('<tr><td class="center">'+x[0]+'</td><td class="center">'+x[1]+'</td><td class="center"><a href="javascript:void()">Click Here</a></td><td class="center">'+x[2]+'</td><td class="center">'+a+'</td><td class="center">'+b+'</td><td class="center"><span class="label label-success">'+d+'</span></td><td class="center"><a class="btn btn-danger" href="#"><i class="halflings-icon white trash"></i> </a></td></tr>');}
			   else 
			   {$("#list_projtable tbody").append('<tr><td class="center">'+x[0]+'</td><td class="center">'+x[1]+'</td><td class="center"><a href="javascript:void()">Click Here</a></td><td class="center">'+x[2]+'</td><td class="center">'+a+'</td><td class="center">'+b+'</td><td class="center"><span class="label">'+e+'</span></td><td class="center"><a class="btn btn-danger" href="#"><i class="halflings-icon white trash"></i> </a></td></tr>');}
			   
			   
		   }loading_data();
		   },
		   type:'POST'
		   });
	
	function loading_data(){
	
		setTimeout(function(){
			if($('.datatable')){
	$('.datatable').dataTable({
		"sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span12'i><'span12 center'p>>",
		"sPaginationType": "bootstrap",
		"oLanguage": {
		"sLengthMenu": "_MENU_ records per page"
		}
	} );
	}  else {loading_data()}
	
		},500);
	}
	
	

}

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
			   
			   if(x[5] === "active")
			   {$("#mob_projtable tbody").append('<tr><td>'+x[0]+'</td><td class="center">'+x[1]+'</td><td class="center"><span class="label label-success">Active</span></td></tr>');}
			   else 
			   {$("#list_projtable tbody").append('<tr><td>'+x[0]+'</td><td class="center">'+x[1]+'</td><td class="center"><span class="label">Inactive</span></td></tr>');}
			   
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
	$("#content").load("./html/list_img.html");

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
	$("#content").load("./html/list_flv.html");
	
}

//Billing
function list_bill(){
	$("#content").load("./html/billing.html");
}
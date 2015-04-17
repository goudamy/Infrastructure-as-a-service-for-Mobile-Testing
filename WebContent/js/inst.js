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


//List Instance
function list_inst() {

	$( "#content" ).load( "html/list_instance.html" );

}

//Create Project

function creat_proj() {
	$("#content").load("./html/project_create.html");

}

//List Project

function list_proj() {
	$("#content").load("./html/list_project.html");

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
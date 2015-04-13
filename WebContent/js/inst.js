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
                    dialog.setTitle('Title 2');
                }
            }]
        });

	} catch(e)
	{console.log("Issue with loading Launch Instance dialog.")}
}


//List Instance
function list_inst() {
    try{

         var list = new BootstrapDialog({
            title: 'List Instances',
            buttons: [{
                label: 'Close',
                action: function(dialog){
                    //dialog.close();
                    dialog.setSize(BootstrapDialog.SIZE_WIDE);
                }
            }]
        });
         
         
         checkdg();
        
         //list.setSize(BootstrapDialog.SIZE_WIDE);
         function checkdg(){
        setTimeout(function(){list.open();if($('.bootstrap-dialog')){$('.bootstrap-dialog').css({'width': '850px'}); list.setMessage($('<div></div>').load('./html/list_instance.html'));} else {checkdg();}},500);
        
    }
        

        

    } catch(e)
    {console.log("Issue with loading List Instance dialog." + e)}

    
}

//Create Project

function creat_proj() {
	try{

		 BootstrapDialog.show({
			id:"createprojdia",
            title: 'Create Project',
            message: function(dialog) {
                var $message = $('<div></div>');
                var pageToLoad = dialog.getData('pageToLoad');
                $message.load(pageToLoad);
        
                return $message;
            },
            data: {
                'pageToLoad': './html/project_create.html'
            },
            buttons: [{
            	id: 'btn-1',
                label: 'Cancel',
                action: function(dialog){
                	dialog.close();
                }
            }, {
            	id: 'btn-2',
                label: 'Create',
                cssClass: 'btn-primary',
                action: function(dialog) {
                	
                }
            }]
        });
		 
		 

	} catch(e)
	{console.log("Issue with loading Launch Instance dialog.")} 
	
	checkdg();
    
    //list.setSize(BootstrapDialog.SIZE_WIDE);
    function checkdg(){
    	setTimeout(function(){if($('.bootstrap-dialog')){$(".chosen-select").chosen();} else {checkdg();}},500); 
    }
	
}
        

        

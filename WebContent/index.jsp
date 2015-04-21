<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- 
Created for: CMPE 281
Create by: Deepesh
Date: 03/30/2015
-->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Mobile Test InfraStructure As A Service (MTIaaS)</title>
	<meta name="description" content="Bootstrap Metro Dashboard">
	<meta name="author" content="Admin">
	<meta name="keyword" content="Metro, Metro UI, Dashboard, Bootstrap, Admin, Template, Theme, Responsive, Fluid, Retina">
	<!-- end: Meta -->
	
	<!-- start: Mobile Specific -->
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!-- end: Mobile Specific -->
	
	<!-- start: CSS -->
	<link id="bootstrap-style" href="css/bootstrap.min.css" rel="stylesheet">
	<link href="css/bootstrap-responsive.min.css" rel="stylesheet">
	<link href="css/bootstrap-dialog.css" rel="stylesheet">
	<link id="base-style" href="css/style.css" rel="stylesheet">
	<link id="base-style-responsive" href="css/style-responsive.css" rel="stylesheet">
	<link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800&subset=latin,cyrillic-ext,latin-ext' rel='stylesheet' type='text/css'>
	<link href="css/bootstrap-duallistbox.min.css" rel="stylesheet">
	<!-- end: CSS -->
	

	<!-- The HTML5 shim, for IE6-8 support of HTML5 elements -->
	<!--[if lt IE 9]>
	  	<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
		<link id="ie-style" href="css/ie.css" rel="stylesheet">
	<![endif]-->
	
	<!--[if IE 9]>
		<link id="ie9style" href="css/ie9.css" rel="stylesheet">
	<![endif]-->
		
	<!-- start: Favicon -->
	<link rel="shortcut icon" href="img/favicon.ico">
	<!-- end: Favicon -->
	
		
		
		
</head>

<body>
		<!-- start: Header -->
	<div class="navbar">
		<div class="navbar-inner">
			<div class="container-fluid">
				<a class="btn btn-navbar" data-toggle="collapse" data-target=".top-nav.nav-collapse,.sidebar-nav.nav-collapse">
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</a>
				<a class="brand" href="index.jsp"><span>Mobile Test InfraStructure As A Service (MTIaaS)</span></a>
								
				<!-- start: Header Menu -->
				<div class="nav-no-collapse header-nav">
					<ul class="nav pull-right">
						
						<!-- start: Notifications Dropdown -->
						<li class="dropdown hidden-phone">
							<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
								<i class="halflings-icon white tasks"></i>
							</a>
							<ul class="dropdown-menu tasks">
								<li class="dropdown-menu-title">
 									<span>You have 2 tasks in progress</span>
									<a href="#refresh"><i class="icon-repeat"></i></a>
								</li>
								<li>
                                    <a href="#">
										<span class="header">
											<span class="title">iOS Mobile Resources Available</span>
											<span class="percent"></span>
										</span>
                                        <div class="taskProgress progressSlim red">0</div> 
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
										<span class="header">
											<span class="title">Android Mobile Resources Available</span>
											<span class="percent"></span>
										</span>
                                        <div class="taskProgress progressSlim green">100</div> 
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
										<span class="header">
											<span class="title">Windows Mobile Resources Available</span>
											<span class="percent"></span>
										</span>
                                        <div class="taskProgress progressSlim yellow">0</div> 
                                    </a>
                                </li>
								<li>
                            		<a class="dropdown-menu-sub-footer">View all tasks</a>
								</li>	
							</ul>
						</li>

						<li class="dropdown">
							<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
								<i class="halflings-icon white user"></i> Admin
								<span class="caret"></span>
							</a>
							<ul class="dropdown-menu">
								<li class="dropdown-menu-title">
 									<span>Settings</span>
								</li>
								<li><a href="login.jsp"><i class="halflings-icon off"></i> Logout</a></li>
							</ul>
						</li>
						<!-- end: User Dropdown -->
					</ul>
				</div>
				<!-- end: Header Menu -->
				
			</div>
		</div>
	</div>
	<!-- start: Header -->
	
		<div class="container-fluid-full">
		<div class="row-fluid">
				
			<!-- start: Main Menu -->
			<div id="sidebar-left" class="span2">
				<div class="nav-collapse sidebar-nav">
					<ul class="nav nav-tabs nav-stacked main-menu">
						<li><a href="index.jsp"><i class="icon-bar-chart"></i><span class="hidden-tablet"> Dashboard</span></a></li>
						<li>
							<a class="dropmenu" href="#"><i class="icon-tasks"></i><span class="hidden-tablet"> Instances</span></a>
							<ul>
								<li><a class="submenu" href="javascript: lnch_inst()"><i class="icon-file-alt"></i><span class="hidden-tablet"> Launch Instance</span></a></li>
								<li><a class="submenu" href="javascript: list_inst()"><i class="icon-file-alt"></i><span class="hidden-tablet"> List Instances</span></a></li>
								<li><a class="submenu" href="javascript: list_remote()"><i class="icon-file-alt"></i><span class="hidden-tablet"> List Remote Resources</span></a></li>
							</ul>	
						</li>
						<li>
							<a class="dropmenu" href="#"><i class="icon-tasks"></i><span class="hidden-tablet"> Images</span></a>
							<ul>
								<li><a class="submenu" href="javascript: add_img()"><i class="icon-file-alt"></i><span class="hidden-tablet"> Add Image</span></a></li>
								<li><a class="submenu" href="javascript: list_img()"><i class="icon-file-alt"></i><span class="hidden-tablet"> List Images</span></a></li>
							</ul>	
						</li>
						<li>
							<a class="dropmenu" href="#"><i class="icon-tasks"></i><span class="hidden-tablet"> Flavors</span></a>
							<ul>
								<li><a class="submenu"  href="javascript: create_flv()"><i class="icon-file-alt"></i><span class="hidden-tablet"> Create Flavor</span></a></li>
								<li><a class="submenu"  href="javascript: list_flv()"><i class="icon-file-alt"></i><span class="hidden-tablet"> List Flavors</span></a></li>
							</ul>	
						</li>
						<li>
							<a class="dropmenu" href="#"><i class="icon-tasks"></i><span class="hidden-tablet">Mobile Projects</span></a>
							<ul>
								<li><a class="submenu" href="javascript:creat_proj()"><i class="icon-file-alt"></i><span class="hidden-tablet"> Create Mobile Project</span></a></li>
								<li><a class="submenu" href="javascript: list_proj()"><i class="icon-file-alt"></i><span class="hidden-tablet"> List Mobile Projects</span></a></li>
							</ul>	
						</li>
						<li>
							<a class="dropmenu" href="#"><i class="icon-tasks"></i><span class="hidden-tablet">Mobile Hub</span></a>
							<ul>
								<li><a class="submenu" href="javascript:add_hub()"><i class="icon-file-alt"></i><span class="hidden-tablet"> Add Mobile Hub </span></a></li>
								<li><a class="submenu" href="javascript: list_hub()"><i class="icon-file-alt"></i><span class="hidden-tablet"> List Mobile Hubs</span></a></li>
							</ul>	
						</li>
						<li>
							<a class="dropmenu" href="#"><i class="icon-tasks"></i><span class="hidden-tablet">Billing</span></a>
							<ul>
								<li><a class="submenu" href="javascript:bill_tariff()"><i class="icon-file-alt"></i><span class="hidden-tablet"> Billing Tariffs </span></a></li>
								<li><a class="submenu" href="javascript:bill_gen()"><i class="icon-file-alt"></i><span class="hidden-tablet"> Generate Bill</span></a></li>
							</ul>	
						</li>
						
					</ul>
				</div>
			</div>
			<!-- end: Main Menu -->
			
			<noscript>
				<div class="alert alert-block span10">
					<h4 class="alert-heading">Warning!</h4>
					<p>You need to have <a href="http://en.wikipedia.org/wiki/JavaScript" target="_blank">JavaScript</a> enabled to use this site.</p>
				</div>
			</noscript>
			
	<!-- start: Content -->
			<div id="content" class="span10">
			
			
			<ul class="breadcrumb">
				<li>
					<i class="icon-home"></i>
					<a href="index.jsp">Home</a> 
					<i class="icon-angle-right"></i>
				</li>
				<li><a href="#">Dashboard</a></li>
			</ul>

		
 
 			<div class="row-fluid sortable">
				<div class="box span6">
					<div class="box-header">
						<h2><i class="halflings-icon align-justify"></i><span class="break"></span>Mobile Projects Active/Inactive</h2>
					</div>
					<div class="box-content" style="overflow-y:scroll; height:241px;">
						<table id="mob_projtable" class="table table-striped">
							  <thead>
								  <tr>
									  <th>Mobile Project Name</th>
									  <th>Created Date</th>
									  <th>Status</th>                                          
								  </tr>
							  </thead>
							  
							  <tbody>
								
							  </tbody>
						 </table>  
						 
					</div>
				</div><!--/span-->
				
				<div class="box span6">
					<div class="box-header">
						<h2><i class="halflings-icon align-justify"></i><span class="break"></span>Users</h2>
						
					</div>
					<div class="box-content">
						<table class="table table-striped">
							  <thead>
								  <tr>
									  <th>Username</th>
									  <th>Date registered</th>
									  <th>Role</th>
									  <th>Status</th>                                          
								  </tr>
							  </thead>   
							  <tbody>
								<tr>
									<td>Admin</td>
									<td class="center">03/29/2015</td>
									<td class="center">Member</td>
									<td class="center">
										<span class="label label-success">Active</span>
									</td>                                       
								</tr>
								<tr>
									<td>Demo</td>
									<td class="center">03/28/2015</td>
									<td class="center">Member</td>
									<td class="center">
										<span class="label">Inactive</span>
									</td>                                       
								</tr>
								<tr>
									<td>&nbsp</td>
									<td class="center">&nbsp</td>
									<td class="center">&nbsp</td>
									<td class="center">&nbsp
									</td>                                        
								</tr>
								<tr>
									<td>&nbsp</td>
									<td class="center">&nbsp</td>
									<td class="center">&nbsp</td>
									<td class="center">&nbsp
									</td>                                       
								</tr>
								<tr>
									<td>&nbsp</td>
									<td class="center">&nbsp</td>
									<td class="center">&nbsp</td>
									<td class="center">&nbsp
									</td>                                        
								</tr>                                   
							  </tbody>
						 </table>  
					</div>
				</div><!--/span-->
			</div><!--/row-->
			
			<div class="row-fluid hideInIE8 circleStats">
				
				<div class="span3" onTablet="span4" onDesktop="span3">
                	<div class="circleStatsItemBox yellow">
						<div class="header">Disk Space Usage</div>
						<span class="percent">percent</span>
						<div class="circleStat" id="dskusg">
                    		<input type="text" value="48" class="whiteCircle" />
						</div>		
						<div class="footer">
							<span class="count">
								<span class="number">20000</span>
								<span class="unit">MB</span>
							</span>
							<span class="sep"> / </span>
							<span class="value">
								<span class="number">50000</span>
								<span class="unit">MB</span>
							</span>	
						</div>
                	</div>
				</div>

				<div class="span3" onTablet="span4" onDesktop="span3">
                	<div class="circleStatsItemBox green">
						<div class="header">Bandwidth Usage</div>
						<span class="percent">percent</span>
						<div class="circleStat">
                    		<input type="text" value="78" class="whiteCircle" />
						</div>
						<div class="footer">
							<span class="count">
								<span class="number">5000</span>
								<span class="unit">GB</span>
							</span>
							<span class="sep"> / </span>
							<span class="value">
								<span class="number">5000</span>
								<span class="unit">GB</span>
							</span>	
						</div>
                	</div>
				</div>

				<div class="span3" onTablet="span4" onDesktop="span3">
                	<div class="circleStatsItemBox greenDark">
						<div class="header">Memory Usage</div>
						<span class="percent">percent</span>
                    	<div class="circleStat">
                    		<input type="text" value="60" class="whiteCircle" />
						</div>
						<div class="footer">
							<span class="count">
								<span class="number">32</span>
								<span class="unit">GB</span>
							</span>
							<span class="sep"> / </span>
							<span class="value">
								<span class="number">32</span>
								<span class="unit">GB</span>
							</span>	
						</div>
                	</div>
				</div>

				<div class="span3 noMargin" onTablet="span4" onDesktop="span3">
                	<div class="circleStatsItemBox pink">
						<div class="header">VCPU Usage</div>
						<span class="percent">percent</span>
                    	<div class="circleStat">
                    		<input type="text" value="83" class="whiteCircle" />
						</div>
						<div class="footer">
							<span class="count">
								<span class="number">64</span>
								<span class="unit">GHz</span>
							</span>
							<span class="sep"> / </span>
							<span class="value">
								<span class="number">3.2</span>
								<span class="unit">GHz</span>
							</span>	
						</div>
                	</div>
				</div>

			
						
			</div>		
						
			<div class="row-fluid">
				
				<div class="widget blue span6" onTablet="span6" onDesktop="span6">
					
					<h2><span class="glyphicons globe"><i></i></span> Demographics Usage</h2>
					
					<hr>
					
					<div class="content">
						
						<div class="verticalChart">
							
							<div class="singleBar">
							
								<div class="bar">
								
									<div class="value">
										<span>20%</span>
									</div>
								
								</div>
								
								<div class="title">US</div>
							
							</div>
							
							<div class="singleBar">
							
								<div class="bar">
								
									<div class="value">
										<span>16%</span>
									</div>
								
								</div>
								
								<div class="title">China</div>
							
							</div>
							
							<div class="singleBar">
							
								<div class="bar">
								
									<div class="value">
										<span>12%</span>
									</div>
								
								</div>
								
								<div class="title">India</div>
							
							</div>
							
							<div class="singleBar">
							
								<div class="bar">
								
									<div class="value">
										<span>19%</span>
									</div>
								
								</div>
								
								<div class="title">Australia</div>
							
							</div>
							
							
							
							<div class="clearfix"></div>
							
						</div>
					
					</div>
					
				</div><!--/span-->
				
			
				<div class="widget blue span6 noMargin" onTablet="span12" onDesktop="span6">
					<h2><span class="glyphicons fire"><i></i></span> Mobile Cloud Load</h2>
					<hr>
					<div class="content">
						 <div id="serverLoad2" style="height:224px;"></div>
					</div>
				</div>
			
			</div>
		
       

			</div><!--/.fluid-container-->
	
	<!-- end: Content -->
		</div><!--/#content.span10-->
		</div><!--/fluid-row-->
		
	<div class="modal hide fade" id="myModal">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h3>Settings</h3>
		</div>
		<div class="modal-body">
			<p>Here settings can be configured...</p>
		</div>
		<div class="modal-footer">
			<a href="#" class="btn" data-dismiss="modal">Close</a>
			<a href="#" class="btn btn-primary">Save changes</a>
		</div>
	</div>
	
	<!-- <div class="clearfix"></div> -->
	
	<footer>

		<p>
			<span style="text-align:left;float:left">&copy; 2015 <a href="http://my.sjsu.edu" alt="San_Jose_State_University">San Jose State University</a></span>
			
		</p>

	</footer>
	
	<!-- start: JavaScript-->

		
		<script src="js/jquery-1.9.1.min.js"></script>
		
		<script src="js/jquery-migrate-1.0.0.min.js"></script>
	
		<script src="js/jquery-ui-1.10.0.custom.min.js"></script>
		
	
		<script src="js/jquery.ui.touch-punch.js"></script>
	
		<script src="js/modernizr.js"></script>
	
		<script src="js/bootstrap.min.js"></script>
	
		<script src="js/jquery.cookie.js"></script>
	
		<script src='js/fullcalendar.min.js'></script>
	
		<script src='js/jquery.dataTables.min.js'></script>

		<script src="js/excanvas.js"></script>
	<script src="js/jquery.flot.js"></script>
	<script src="js/jquery.flot.pie.js"></script>
	<script src="js/jquery.flot.stack.js"></script>
	<script src="js/jquery.flot.resize.min.js"></script>
	
		<script src="js/jquery.chosen.min.js"></script>
	
		<script src="js/jquery.uniform.min.js"></script>
		
		<script src="js/jquery.cleditor.min.js"></script>
	
		<script src="js/jquery.noty.js"></script>
	
		<script src="js/jquery.elfinder.min.js"></script>
	
		<script src="js/jquery.raty.min.js"></script>
	
		<script src="js/jquery.iphone.toggle.js"></script>
	
		<script src="js/jquery.uploadify-3.1.min.js"></script>
	
		<script src="js/jquery.gritter.min.js"></script>
	
		<script src="js/jquery.imagesloaded.js"></script>
	
		<script src="js/jquery.masonry.min.js"></script>
	
		<script src="js/jquery.knob.modified.js"></script>
	
		<script src="js/jquery.sparkline.min.js"></script>
		
	
		<script src="js/counter.js"></script>
	
		<script src="js/retina.js"></script>

		<script src="js/custom.js"></script>

		<script src="js/bootstrap-dialog.js"></script>
		<script src="js/inst.js"></script>
		<script src="js/bootstrap-duallistbox.min.js"></script>
		<script>index_list_proj();</script>
		
	<!-- end: JavaScript-->
</body>
</html>
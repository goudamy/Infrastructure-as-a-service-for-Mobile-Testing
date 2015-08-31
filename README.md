Our aim here is to develop a cloud infrastructure solution (MTIaaS – Mobile Test Infrastructure as a Service) to support Mobile application development and testing.  Mobile Infrastructure and resource allocation on a Mobile Cloud is a challenging aspect.  Mobile Test Infrastructure focuses on providing a scalable Cloud solution for large scale Mobile application development and testing.  Mobile Test Infrastructure as a Service will aim at providing seamless and large-scale solution for Mobile device allocation for customers based on what type of mobile infrastructure is requested. 


The application is built for allocating 3 different type of Mobile Resources: 

1.	OpenStack Mobile Instances (Emulated Mobile Device)
2.	Oracle VirtualBox Instances (Emulated Mobile Device)
3.	Physical Mobile Device

Tenants using the MTIaaS Cloud Solution will be using the Dashboard to allocate/de-allocate mobile devices (emulated/physical).  The Dashboard forwards the user requests to controller and controller based on the type of allocation and allocation/request routing algorithm decides whether to use Load Balancer to allocate Resources directly in a region.  This allows the architecture to clearly control the flow of resource allocation based on how the request needs to be routed to a Region or the Infrastructure.

The Load balancer here uses two different type of algorithms (Honey Bee Algorithm & Token Ring Algorithm) to load balance the incoming user requests.  Resources are then allocated based on the type of resource requested from either a particular Infrastructure directly or a Region. As discussed earlier users could request for an Emulated device (OpenStack Mobile instance/ VirtualBox Mobile Instance) or Physical device.  A Mobile Hub is used to further control and allocate the mobile devices.

The Emulated Mobile Devices based on OpenStack are controlled using OpenStack RESTful APIs and other emulated devices with VirtualBox are controlled with python scripts on a webserver (Mobile Hub).  The Physical devices as well are controlled using a Mobile Hub with python scripts executing ADB (Android Debugger Bridge) commands. Currently there is a limitation of only Android based devices being supported for Physical device allocation.

The user is provided with a web interface for console viewing where the user is able to monitor and control each and every mobile device allocated through GUI (Graphical user Interface).  All the allocation details are stored in a MySQL database with creation date and usage details.  This data is further used by the Billing module to calculate the billing based on Region and flavour requested by the user.

<!doctype html>
<html lang="en">

<head>
    <meta charset="utf-8" />
    <link rel="apple-touch-icon" sizes="76x76" href="<?php echo base_url();?>assets/image/apple-icon.png" />
    <link rel="icon" type="image/png" href="<?php echo base_url();?>assets/image/favicon.png" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <title>Dashboard | FuelPay</title>
    <meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0' name='viewport' />
    <meta name="viewport" content="width=device-width" />
    <!-- Bootstrap core CSS     -->
    <link href="<?php echo base_url();?>assets/css/bootstrap.min.css" rel="stylesheet" />
    <!--  Material Dashboard CSS    -->
    <link href="<?php echo base_url();?>assets/css/material-dashboard.css" rel="stylesheet" />
    <!--  CSS for Demo Purpose, don't include it in your project     -->
    <link href="<?php echo base_url();?>assets/css/demo.css" rel="stylesheet" /><!-- 
    <link rel="stylesheet" type="text/css" href="<?php echo base_url();?>assets/css/bootstrap.min.css"> -->

    <!--     Fonts and icons     -->
    <link href="<?php echo base_url();?>assets/css/font-awesome.min.css" rel="stylesheet">
    <link href='https://fonts.googleapis.com/css?family=Roboto:400,700,300|Material+Icons' rel='stylesheet' type='text/css'>
    <script src="<?php echo base_url();?>assets/js/qrcode.min.js"></script>

</head>

<body>
    <div class="wrapper">
        <div class="sidebar" data-color="purple" data-image="<?php echo base_url();?>assets/image/sidebar.jpg">
            <div class="logo">
                <a href="" class="simple-text">
                    Fuel Pay
                </a>
            </div>
            <div class="sidebar-wrapper">
                <ul class="nav">
                    <li class="<?php if($panelHeading =='Dashboard'){echo 'active';}?>">
                        <a href="<?php echo base_url().'Home/dashboard'?>">
                            <i class="material-icons">dashboard</i>
                            <p>Dashboard</p>
                        </a>
                    </li>
                    <li class="<?php if($panelHeading =='View User'){echo 'active';}?>">
                        <a href="<?php echo base_url().'User/viewUser'?>">
                            <i class="material-icons">person</i>
                            <p>View User</p>
                        </a>
                    </li>
                    <li class="<?php if($panelHeading =='Add Money'){echo 'active';}?>">
                        <a href="<?php echo base_url().'AddMoney/viewAddMoney'?>">
                            <i class="material-icons">add_circle_outline</i>
                            <p>Add Money</p>
                        </a>
                    </li>
                    <li class="<?php if($panelHeading =='View Station Setup'){echo 'active';}?>">
                        <a href="<?php echo base_url().'FuelStationSetup/viewSetup'?>">
                            <i class="material-icons">settings</i>
                            <p>Station Setup</p>
                        </a>
                    </li>
                    <li class="<?php if($panelHeading =='View Fuel Rate'){echo 'active';}?>">
                        <a href="<?php echo base_url().'FuelRate/viewFuelRate'?>">
                            <i class="material-icons">trending_up</i>
                            <p>Fuel Rate</p>
                        </a>
                    </li>
                    <li>
                        <a href="<?php echo base_url()?>Login/doLogout">
                            <i class="material-icons">exit_to_app</i>
                            <p>Logout</p>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
        <div class="main-panel">
            <nav class="navbar navbar-transparent navbar-absolute">
                <div class="container-fluid">
                    <div class="navbar-header">
                        <button type="button" class="navbar-toggle" data-toggle="collapse">
                            <span class="sr-only">Toggle navigation</span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
                        <a class="navbar-brand" href="#"><?php echo $panelHeading; ?></a>
                    </div>
                    <div class="collapse navbar-collapse">
                        <ul class="nav navbar-nav navbar-right">
                            <li>
                                <a href="<?php echo base_url().'Home/dashboard'?>">
                                    <i class="material-icons">dashboard</i>
                                    <p class="hidden-lg hidden-md">Dashboard</p>
                                </a>
                            </li>
                            <li>
                                <a href="#pablo" class="dropdown-toggle" data-toggle="dropdown">
                                    <i class="material-icons">person</i>
                                    <p class="hidden-lg hidden-md">Profile</p>
                                </a>
                            </li>
                            <li class="dropdown">
                                <a href="<?php echo base_url()?>Login/doLogout">
                                    <i class="material-icons">exit_to_app</i>
                                    <p class="hidden-lg hidden-md">Notifications</p>
                                </a>
                                
                            </li>
                            
                        </ul>
                    </div>
                </div>
            </nav>
                <div class="content">
                    <div class="container-fluid">
                   <?php
                     $this->load->view($bodyTemplate);
                            ?>
                    
                       
                    </div>
                </div>
            </div>
            
        </div>
    </div>
</body>
<!--   Core JS Files   -->
<script src="<?php echo base_url(); ?>assets/js/jquery.min.js" type="text/javascript"></script>
<script src="<?php echo base_url(); ?>assets/js/bootstrap.js"></script>
<script src="<?php echo base_url();?>assets/js/material.min.js" type="text/javascript"></script>
<!--  Charts Plugin
<script src="<?php echo base_url();?>assets/js/chartist.min.js"></script>
 -->
<!--  Dynamic Elements plugin -->
<script src="<?php echo base_url();?>assets/js/arrive.min.js"></script>
<!--  PerfectScrollbar Library -->
<script src="<?php echo base_url();?>assets/js/perfect-scrollbar.jquery.min.js"></script>
<!--  Notifications Plugin    -->
<script src="<?php echo base_url();?>assets/js/bootstrap-notify.js"></script>
<!--  Google Maps Plugin  
<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=YOUR_KEY_HERE"></script>
  -->
<!-- Material Dashboard javascript methods -->
<script src="<?php echo base_url();?>assets/js/material-dashboard.js?v=1.2.0"></script>
<!-- Material Dashboard DEMO methods, don't include it in your project! -->
<script src="<?php echo base_url();?>assets/js/demo.js"></script>
<script type="text/javascript">
/*
    $(document).ready(function() {

        // Javascript method's body can be found in assets/js/demos.js
        demo.initDashboardPageCharts();

    });
    */
</script>

</html>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
    <link rel="apple-touch-icon" sizes="76x76" href="<?php echo base_url();?>assets/image/apple-icon.png" />
    <link rel="icon" type="image/png" href="<?php echo base_url();?>assets/image/favicon.png" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <title>Login | FuelPay</title>
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
    </head>

    <body background=".//assets/image/loginback.jpg" style="background-size: cover;">
    <div class="container">
    	<div class="row">
    		<div class="col-md-7"></div>
    			<div class="col-md-4">
    				<div class="card" style="margin-top: 150px">
    					<div class="card-header" data-background-color="purple">
                                    <h4 class="title">Sign In</h4>
                        </div>
	    				<div class="card-content">
	        				<div class="col-md-12">           
	            				<div id="loginback">               
	                				<form action="<?php echo base_url()?>Login/login_process" method="post">
		                				<div class="form-group">
									    	<label >Username</label>
									    	<input type="text" class="form-control" name="username" >
										</div>
							 			<div class="form-group">
								     		<label>Password</label>
								     		<input type="password" class="form-control" name="password">
							 			</div>
											<button type="submit" class="btn btn-sm btn-primary">Login</button><br><br>
												<?php 
												if (!$message == '') {
													?>
													
													<div class="alert alert-danger" role="alert">
													  <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
													  <span class="sr-only">Error:</span>
													  <?php echo $this->session->flashdata('message'); ?>
												</div>
												<?php
												}?>
	                				</form>
	            				</div>
	        				</div>
	    				</div>
					</div>
				</div>
			</div>
		
</div>
</body>
 <script src="<?php echo base_url(); ?>assets/js/jquery.min.js" type="text/javascript"></script>
<script src="<?php echo base_url(); ?>assets/js/bootstrap.js"></script>
<script src="<?php echo base_url();?>assets/js/material.min.js" type="text/javascript"></script>
<!--  Charts Plugin -->
<script src="<?php echo base_url();?>assets/js/chartist.min.js"></script>
<!--  Dynamic Elements plugin -->
<script src="<?php echo base_url();?>assets/js/arrive.min.js"></script>
<!--  PerfectScrollbar Library -->
<script src="<?php echo base_url();?>assets/js/perfect-scrollbar.jquery.min.js"></script>
<!--  Notifications Plugin    -->
<script src="<?php echo base_url();?>assets/js/bootstrap-notify.js"></script>
<!--  Google Maps Plugin    -->
<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=YOUR_KEY_HERE"></script>
<!-- Material Dashboard javascript methods -->
<script src="<?php echo base_url();?>assets/js/material-dashboard.js?v=1.2.0"></script>
<!-- Material Dashboard DEMO methods, don't include it in your project! -->
<script src="<?php echo base_url();?>assets/js/demo.js"></script>
<script type="text/javascript">
    $(document).ready(function() {

        // Javascript method's body can be found in assets/js/demos.js
        demo.initDashboardPageCharts();

    });
</script>
</html>

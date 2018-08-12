<div class="col-sm-12 col-md-12">
    <div class="card">
                                <div class="card-header" data-background-color="purple">
                                    <h4 class="title"><?php echo $panelHeading; ?></h4>
                                </div>
    
        
        <br><br>

            <div id="station_qr" class="qr_code" style="width:150px; height:150px;
            margin: 0 auto;"></div>
    
        <div class="card-content table-responsive">
            <table class="table" id="dataTable">
                <tr>
                    <td>Name</td>
                    <td><?php echo $stationDetails['name'];?></td>
                </tr>
                <tr>
                    <td>Location</td>
                    <td><?php echo $stationDetails['location'];?></td>
                </tr>
                <tr>
                    <td>Start_Time</td>
                    <td><?php echo $stationDetails['start_time'];?></td>
                </tr>
                <tr>
                    <td>End_Time</td>
                    <td><?php echo $stationDetails['end_time'];?></td>
                </tr>
                <tr>
                    <td>Mobile</td>
                    <td><?php echo $stationDetails['mobile_no'];?></td>
                </tr>
                <tr>
                    <td>Account_No</td>
                    <td><?php echo $stationDetails['account_no'];?></td>
                </tr>                
                                    
            </table>
        </div> 
    </div>
    
</div>

    <script>
        new QRCode("station_qr", {
            text: "<?php echo $stationDetails['station_id'];?>$$",
            colorDark : "#000000",
            colorLight : "#ffffff"
        });
    </script>
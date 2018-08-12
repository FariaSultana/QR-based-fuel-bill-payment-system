<div class="col-sm-12 col-md-12">
    <div class="card">
                                <div class="card-header" data-background-color="purple">
                                    <h4 class="title"><?php echo $panelHeading; ?></h4>
                                    <!-- <p class="category">Here is a subtitle for this table</p> -->
                                </div>
    
        <form class="form" action="<?php echo base_url() ?>FuelStationSetup/stationSetup" method="POST">
            <input type="submit" style="margin-left: 15px;" class="btn btn-primary btn-sm" value="Add Station">
        </form>
        <br><br>
        <div class="card-content table-responsive">
            <table class="table" id="dataTable">
                <thead>
                    <tr>
                        <th>Number</th>
                        <th>Name</th>
                        <th>Location</th>
                        <th>Start_Time</th>
                        <th>End_Time</th>
                        <th>Mobile</th>
                        <th>Latitude</th>
                        <th>Longitude</th>
                        <th>Account_No</th>
                         <th></th>
                        <th></th>


                    </tr>
                </thead>
                <tbody>
                <?php
                    $count=1;
                    foreach ($SetupList as $SetupList) {
                        echo "<tr>";
                            echo "<td>$count</td>";
                            echo "<td>".$SetupList['name']."</td>";
                            echo "<td>".$SetupList['location']."</td>";
                            echo "<td>".$SetupList['start_time']."</td>";
                            
                            echo "<td>".$SetupList['end_time']."</td>";
                            echo "<td>".$SetupList['mobile_no']."</td>";
                              echo "<td>".$SetupList['latitude']."</td>";
                                echo "<td>".$SetupList['longitude']."</td>";
                                  echo "<td>".$SetupList['account_no']."</td>";
                            echo "<td><a class='btn btn-sm btn-primary' href='" . base_url() . "FuelStationSetup/stationDetails/" . 
                            $SetupList['station_id']. "'>QR Code</a></td>";
                            
                            $count++;
                        echo "</tr>";
                    }
                  ?>
                </tbody>                        
            </table>
        </div> 
    </div>
    
</div>
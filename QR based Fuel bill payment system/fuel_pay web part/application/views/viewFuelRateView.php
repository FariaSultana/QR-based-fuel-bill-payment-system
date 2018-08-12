<div class="col-sm-12 col-md-12">
    <div class="card">
                                <div class="card-header" data-background-color="purple">
                                    <h4 class="title"><?php echo $panelHeading; ?></h4>
                                    <!-- <p class="category">Here is a subtitle for this table</p> -->
                                </div>
    
        <form class="form" action="<?php echo base_url() ?>FuelRate/addRate" method="POST">
            <input type="submit" style="margin-left: 15px;" class="btn btn-primary btn-sm" value="Add Fuel Rate">
        </form>
        <div class="card-content table-responsive">
            <table class="table" id="dataTable">
                <thead class="text-primary">
                    <tr>
                        <th>Number</th>
                        <th>Weight Measurements</th>
                        <th>Fuel Type</th>
                        <th>Price</th>
                    </tr>
                </thead>
                <tbody>
                <?php
                    $count=1;
                    foreach ($FuelRates as $FuelRate) {
                        echo "<tr>";
                            echo "<td>$count</td>";
                            echo "<td>".$FuelRate['weight_measurements']."</td>";
                            echo "<td>".$FuelRate['fuel_type']."</td>";
                            echo "<td>".$FuelRate['amount']."</td>";         

                            $count++;
                        echo "</tr>";
                    }
                  ?>
                </tbody>                        
            </table>
        </div> 
    </div>

</div>
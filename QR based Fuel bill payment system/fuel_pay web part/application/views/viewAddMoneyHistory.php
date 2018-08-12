<div class="col-sm-12 col-md-12">
    <div class="card">
                                <div class="card-header" data-background-color="purple">
                                    <h4 class="title"><?php echo $panelHeading; ?></h4>
                                    <!-- <p class="category">Here is a subtitle for this table</p> -->
                                </div>
    
        <form class="form" action="<?php echo base_url() ?>AddMoney/viewAddMoney" method="POST">
            <input type="submit" style="margin-left: 15px;" class="btn btn-primary btn-sm" value="Add Money">
        </form>
        <div class="card-content table-responsive">
            <table class="table" id="dataTable">
                <thead class="text-primary">
                    <tr>
                        <th>Number</th>
                        <th>Account Number</th>
                        <th>Amount</th>
                        <th>Status</th>
                        <th>Date Time</th>
                        
                    </tr>
                </thead>
                <tbody>
                <?php
                    $count=1;
                    foreach ($userGroups as $userGroup) {
                        echo "<tr>";
                            echo "<td>$count</td>";
                            echo "<td>".$userGroup['accountNumber']."</td>";
                            echo "<td>".$userGroup['amount']."</td>";
                            echo "<td>".$userGroup['status']."</td>";
                            echo "<td>".$userGroup['dateTime']."</td>";
                            
                            $count++;
                        echo "</tr>";
                    }
                  ?>
                </tbody>                        
            </table>
        </div> 
    </div>

</div>
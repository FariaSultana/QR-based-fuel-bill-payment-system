<div class="col-sm-12 col-md-12">
    <div class="card">
                                <div class="card-header" data-background-color="purple">
                                    <h4 class="title"><?php echo $panelHeading; ?></h4>
                                </div>
    
        <form class="form" action="<?php echo base_url() ?>User/addUser" method="POST">
            <input type="submit" style="margin-left: 15px;" class="btn btn-primary btn-sm" value="Add User">
        </form>
        <div class="card-content table-responsive">
            <table class="table" id="dataTable">
                <thead class="text-primary">
                    <tr>
                        <th>Number</th>
                        <th>User Name</th>
                        <th>Full Name</th>
                        <th>Contact Number</th>
                        <th>Email Address</th>
                    </tr>
                </thead>
                <tbody>
                <?php
                    $count=1;
                    foreach ($userGroups as $userGroup) {
                        echo "<tr>";
                            echo "<td>$count</td>";
                            echo "<td>".$userGroup['username']."</td>";
                            echo "<td>".$userGroup['full_name']."</td>";
                            echo "<td>".$userGroup['contact_no']."</td>";
                            echo "<td>".$userGroup['email']."</td>";
                            
                            $count++;
                        echo "</tr>";
                    }
                  ?>
                </tbody>                        
            </table>
        </div> 
    </div>

</div>
<div class="col-md-12 col-sm-12 col-xs-12">
    <?php
    if (!$message == '') {
        ?>

        <div class="<?= $message == 'Atation Data Added Successfully!' ? "alert alert-success" : "alert alert-danger" ?>" role="alert">
            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
            <span class="sr-only">Error:</span>
            <?php echo $this->session->flashdata('message'); ?>
        </div>
        <?php
    }
    ?>

     <div class="card">
                                <div class="card-header" data-background-color="purple">
                                    <h4 class="title"><?php echo $panelHeading; ?></h4>
                                    <!-- <p class="category">Here is a subtitle for this table</p> -->
                                </div>
        <div class="card-content">
    
            <form action="<?php echo base_url()?>FuelStationSetup/saveSetup" method="post">
                <div class="row">
                    <div class="col-md-4">
                        <label>Name</label>
                        <input type="text" class="form-control" name="StationName" id="UsernameId" required="">
                    </div>
                    
                    <div class="col-md-4">
                        <label>Location</label>
                        <input type="text" class="form-control" name="Location" id="LocationId" required="">
                    </div>
                    <div class="col-md-4">
                        <label>Start Time</label>
                        <input type="time" class="form-control" name="StartTime" id="StarttimeId" required="">
                    </div>
                </div>
                <div class="row">
                   
                    <div class="col-md-4">
                        <label>End Time</label>
                        <input type="time" class="form-control" name="EndTime" id="EndtimeId" required="">
                    </div>
                    <div class="col-md-4">
                        <label>Mobile No</label>
                        <input type="number" class="form-control" name="MobileNo" id="MobilenoId" required="">
                    </div>
                    <div class="col-md-4">
                        <label>Latitude</label>
                        <input type="text" class="form-control" name="Latitude" id="LatitudeId" required="">
                    </div>
                </div>
                <div class="row">
                    
                     <div class="col-md-4">
                        <label>Longitude</label>
                        <input type="text" class="form-control" name="Longitude" id="LongitudeId" required="">
                    </div>
                    
                </div>

                <!-- <div class="form-group">
                    <input type="button" value="Save" id="submitBtn" class="btn btn-sm btn-success">
                </div> -->
                <button type="submit" class="btn btn-sm btn-primary">Save</button>
            </form>
        </div>
    </div>

</div>

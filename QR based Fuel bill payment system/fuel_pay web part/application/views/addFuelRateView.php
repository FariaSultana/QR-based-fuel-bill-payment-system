<div class="col-md-12 col-sm-12 col-xs-12">
    <?php
    if (!$message == '') {
        ?>

        <div class="<?= $message == 'Fuel Rate Added Successfully!' ? "alert alert-success" : "alert alert-danger" ?>" role="alert">
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
    <form action="<?php echo base_url()?>FuelRate/saveRate" method="post">
        <div class="row">
            <div class="col-md-4">
                <label>Weight Measurements</label>
                <input type="text" class="form-control" name="weight" id="UsernameId" required="">
            </div>
            <div class="col-md-4">
                <label>Fuel Type</label>
                <input type="text" class="form-control" name="fuel_type" id="PasswordId" required="">
            </div>
            <div class="col-md-4">
                <label>Price</label>
                <input type="text" class="form-control" name="amount" id="fullNameId" required="">
            </div>
        </div>
        
        <button type="submit" class="btn btn-sm btn-primary">Save</button>
    </form>
</div>
</div>

</div>

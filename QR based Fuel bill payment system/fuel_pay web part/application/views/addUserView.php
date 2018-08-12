<div class="col-md-12 col-sm-12 col-xs-12">
    <?php
    if (!$message == '') {
        ?>

        <div class="<?= $message == 'User Added Successfully!' ? "alert alert-success" : "alert alert-danger" ?>" role="alert">
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
    <form action="<?php echo base_url()?>User/saveUser" method="post">
        <div class="row">
            <div class="col-md-4">
                <label>Username</label>
                <input type="text" class="form-control" name="username" id="UsernameId" required="">
            </div>
            <div class="col-md-4">
                <label>Password</label>
                <input type="password" class="form-control" name="password" id="PasswordId" required="">
            </div>
            <div class="col-md-4">
                <label>Full Name</label>
                <input type="text" class="form-control" name="full_name" id="fullNameId" required="">
            </div>
        </div>
        <div class="row">
            <div class="col-md-4">
                <label>Contact Number</label>
                <input type="number" class="form-control" name="contact_no" id="contactId" required="">
            </div>
            <div class="col-md-4">
                <label>Email</label>
                <input type="email" class="form-control" name="email" id="emailId" required="">
            </div>
           <!--  <div class="col-md-4">
                <button type="submit" class="btn btn-sm btn-success">Save</button>
            </div> -->


            
        </div>
        <button type="submit" class="btn btn-sm btn-primary">Save</button>
    </form>
</div>
</div>

</div>

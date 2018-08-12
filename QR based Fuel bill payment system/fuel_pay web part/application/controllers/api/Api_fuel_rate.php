<?php defined('BASEPATH') OR exit('No direct script access allowed');

class Api_fuel_rate extends CI_Controller {

    function __construct(){
        parent::__construct();
        date_default_timezone_set('Asia/Dhaka');
        $this->load->model('Api_rate_model');
    }

    function fuel_rate() {       

        $result = $this->Api_rate_model->fuelRateInfo();

        if (!$result):
            $info = "No data found";
            $success = "false";
        else:
            $info = $result->result();
            $success = "true";
        endif;

        $json = array(
            "success" => $success,
            "info" => $info
        );

        echo json_encode($json);
    }

    function fuel_calculator() {       

        $params['fuelType'] = $this->input->post('fuelType', TRUE); 
        

        $result = $this->Api_rate_model->getFueltoPriceCalculatorInfo($params);       
        
        

        if (!$result):
            $info = "No data found";
            $success = "false";
        else:
            $info = $result->result();
            $success = "true";
        endif;

        $json = array(
            "success" => $success,
            "info" => $info
        );

        echo json_encode($json);
    }
}
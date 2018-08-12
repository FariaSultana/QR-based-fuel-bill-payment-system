<?php defined('BASEPATH') OR exit('No direct script access allowed');

class Api_payment extends CI_Controller {

    function __construct(){
        parent::__construct();
        date_default_timezone_set('Asia/Dhaka');
        $this->load->model('Api_payment_model');

        // if(!$this->session->userdata('validated')){
        //     redirect('Login');
        // }

    }

    function station_info() {
        $params['station_id'] = $this->input->post('station_id', TRUE);
       
      
        $this->load->library('form_validation');
       // $this->form_validation->CI = & $this;

        $this->form_validation->set_rules('station_id', 'Station Id', 'required|trim|callback_checkstationid');
      

        
        if ($this->form_validation->run() == FALSE) {
            $info = "Station Not Available, Please try again";
            $json = array(
                "success" => false,
                "msg" => $info
            );

            echo json_encode($json);
            die();
        }

        $res = array();

        $result = $this->Api_payment_model->getStationInfo($params);

        /*var_dump($result);
        die();*/

        if (!$result):
            $info = "Station Not Available, Please try again";
            $success = "false";
        else:
            $res = $result->row();
            $info[] = $res;
            $success = "true";
        endif;

        $json = array(
            "success" => $success,
            "info" => $info
        );

        echo json_encode($json);
    }

    function payment() {
        $params['toAcc'] = $this->input->post('toAcc', TRUE);
        $params['fromAcc'] = $this->input->post('fromAcc', TRUE);
        $params['amount'] = $this->input->post('amount', TRUE); 
        $params['stationName'] = $this->input->post('stationName', TRUE); 
        $date = date("Y-m-d H:i:s");
        $params['create_dateTime'] = $date;  
       
        $res = array();

        $result = $this->Api_payment_model->getPayment($params);

       
        $msg = "Paymeny Successful.";
        $success = "true";
        

        $json = array(
            "success" => $success,
            "msg" => $msg
        );

        echo json_encode($json);
    }
    
    

    function checkstationid($pw) {
        
        $this->db->where("station_id", $pw);
        $result = $this->db->get("station_setup");

        if ($result->num_rows() > 0) {
            return true;
        } else {
            $this->form_validation->set_message('checkstationid', 'Station Not Available, Please try again');
            return FALSE;
        }
    }
    
}
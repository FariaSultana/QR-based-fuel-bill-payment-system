<?php defined('BASEPATH') OR exit('No direct script access allowed');

class Api_register extends CI_Controller {

    function __construct(){
        parent::__construct();
        date_default_timezone_set('Asia/Dhaka');
        $this->load->model('Api_register_model');

        // if(!$this->session->userdata('validated')){
        //     redirect('Login');
        // }

    }

    function register() {
        $params['user_name'] = $this->input->post('user_name', TRUE);
        $params['password'] = md5($this->input->post('password', TRUE));
        $params['monbile_no'] = $this->input->post('monbile_no', TRUE);
        $params['email'] = $this->input->post('email', TRUE);
        $params['fuel_type'] = $this->input->post('fuel_type', TRUE);
        $params['vehicle_type'] = $this->input->post('vehicle_type', TRUE);
        $params['vehicle_name'] = $this->input->post('vehicle_name', TRUE);
        $params['fuel_per_km'] = $this->input->post('fuel_per_km', TRUE);
        $params['accountNumber'] = $this->input->post('monbile_no', TRUE);
        $params['amount'] = 0;
        $params['active'] = 1;

        $date = date("Y-m-d H:i:s");
        $params['create_dt_tm'] = $date;
        $params['update_dt_tm'] = $date;

      /*  $params['imei_number'] = $this->input->post('imei_number', TRUE);
        $params['os_code'] = $this->input->post('os_code', TRUE);
        $params['device_info'] = $this->input->post('device_info', TRUE);
*/
      
        $this->load->library('form_validation');
       // $this->form_validation->CI = & $this;

        $this->form_validation->set_rules('user_name', 'User Name', 'required|trim|callback_checkUser');
        $this->form_validation->set_rules('email', 'Email', 'trim|required|callback_checkEmail');
        $this->form_validation->set_rules('monbile_no', 'Monbile No', 'trim|required|callback_checkMobile');


        if ($this->form_validation->run() == FALSE) {

            $info = "Incorrect Register Details";
            $json = array(
                "success" => false,               
                "msg"=>$info
            );

            echo json_encode($json);
            die();
        }

        $res = array();

        $result = $this->Api_register_model->insertRegisterInfo($params);

        $info = "Inserted Successfully.";
        $success = "true";

        $json = array(
            "success" => $success,
            "msg" => $info
        );

        echo json_encode($json);
    }
    
    function checkEmail($pw) {
        
        $this->db->where("email", $pw);
        $result = $this->db->get("apps_user_registration");

        if ($result->num_rows() > 0) {
           
            return FALSE;
        } else {

            return true;
        }
    }

    function checkMobile($pw) {
        
        $this->db->where("monbile_no", $pw);
        $result = $this->db->get("apps_user_registration");

        if ($result->num_rows() > 0) {

            return FALSE;
        } else {

            return true;
        }
    }

    function checkUser($pw) {
        
        $this->db->where("user_name", $pw);
        $result = $this->db->get("apps_user_registration");

        if ($result->num_rows() > 0) {
            return FALSE;
        } else {

            return true;
        }
    }
    
}
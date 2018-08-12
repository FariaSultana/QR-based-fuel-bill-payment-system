<?php defined('BASEPATH') OR exit('No direct script access allowed');

class Api_login extends CI_Controller {

    function __construct(){
        parent::__construct();
        date_default_timezone_set('Asia/Dhaka');
        $this->load->model('Api_login_model');

        // if(!$this->session->userdata('validated')){
        //     redirect('Login');
        // }

    }

    function login() {
        $params['user_name'] = $this->input->post('user_name', TRUE);
        $params['password'] = md5($this->input->post('password', TRUE));
      /*  $params['imei_number'] = $this->input->post('imei_number', TRUE);
        $params['os_code'] = $this->input->post('os_code', TRUE);
        $params['device_info'] = $this->input->post('device_info', TRUE);
*/
      
        $this->load->library('form_validation');
       // $this->form_validation->CI = & $this;

        $this->form_validation->set_rules('user_name', 'User Name', 'required|trim|callback_checkUser');
        $this->form_validation->set_rules('password', 'Password', 'trim|required|callback_checkPassword');

        
        if ($this->form_validation->run() == FALSE) {
            $info = "Invalid User Name or Password.";
            $json = array(
                "success" => false,
                "msg" => $info
            );

            echo json_encode($json);
            die();
        }

        $res = array();

        $result = $this->Api_login_model->checkLoginInfo($params);

        /*var_dump($result);
        die();*/

        if (!$result):
            $info = "Incorrect Login Details";
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

    function amount() {
        $params['accountNumber'] = $this->input->post('accountNumber', TRUE);
        

        $res = array();

        $result = $this->Api_login_model->getAmount($params);

        /*var_dump($result);
        die();*/

        if (!$result):
            $info = "Incorrect Login Details";
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
    
    function checkPassword($pw) {
        
        $this->db->where("password", md5($pw));
        $result = $this->db->get("apps_user_registration");

        if ($result->num_rows() > 0) {
            return true;
        } else {
            $this->form_validation->set_message('checkPassword', 'The %s you entered is not correct.');
            return FALSE;
        }
    }

    function checkUser($pw) {
        
        $this->db->where("user_name", $pw);
        $result = $this->db->get("apps_user_registration");

        if ($result->num_rows() > 0) {
            return true;
        } else {
            $this->form_validation->set_message('checkPassword', 'The %s you entered is not correct.');
            return FALSE;
        }
    }
    
}
<?php defined('BASEPATH') OR exit('No direct script access allowed');

class Api_add_money extends CI_Controller {

    function __construct(){
        parent::__construct();
        date_default_timezone_set('Asia/Dhaka');
        $this->load->model('Api_add_money_model');

        // if(!$this->session->userdata('validated')){
        //     redirect('Login');
        // }

    }

    function addmoney() {
        $params['accountNumber'] = $this->input->post('accountNumber', TRUE);
        $params['amount'] = $this->input->post('amount', TRUE);        
        $params['status'] = "pending";

        $date = date("Y-m-d H:i:s");
        $params['dateTime'] = $date;

     
        $this->load->library('form_validation');
       
        $this->form_validation->set_rules('accountNumber', 'Account Number', 'required|trim|callback_checkAccountNumber');
        
        
        if ($this->form_validation->run() == FALSE) {
            $info = "Already Applied, please wait for approve.";
            $json = array(
                "success" => false,
                "msg" => validation_errors('<p>', '</p>'),
            );

            echo json_encode($json);
            die();
        }

        $res = array();

        $result = $this->Api_add_money_model->addMoneyInfo($params);

        $info = "Applied Successfully, please wait for approve.";
        $success = "true";

        $json = array(
            "success" => $success,
            "msg" => $info
        );

        echo json_encode($json);
    }

    function add_money_history() {
        $params['accountNumber'] = $this->input->post('accountNumber', TRUE); 
        

        $result = $this->Api_add_money_model->addMoneyHistoryInfo($params);
       
        $res=array();
        if($result){
            foreach($result->result() as $addmoney){
                
                $res["accountNumber"]=$addmoney->accountNumber;
                $res["amount"]=$addmoney->amount;
                $res["status"]=$addmoney->status;
                $res["dateTime"]=$addmoney->dateTime;
                $json[]=$res;
            }
            echo json_encode(
                array(
                    "success"=>"true",
                    "info"=>$json
                )
            );
            die();
        }

        echo json_encode(
            array(
                "success"=>"false",
                "info"=>"There are no data found."
                )
            );
            die();

    }   
    
    function checkAccountNumber($an) {
        
        $this->db->where("accountNumber", $an);
        $this->db->where("status", "pending");
        $result = $this->db->get("add_money");

        if ($result->num_rows() > 0) {
            $this->form_validation->set_message("checkAccountNumber", "Already Applied, please wait for approve.");
            return FALSE;
        } else {
            return true;
        }
    }
    
}
<?php defined('BASEPATH') OR exit('No direct script access allowed');

class Api_transaction extends CI_Controller {

    function __construct(){
        parent::__construct();
        date_default_timezone_set('Asia/Dhaka');
        $this->load->model('Api_transaction_model');

        // if(!$this->session->userdata('validated')){
        //     redirect('Login');
        // }

    }

   

    function transaction() {
        $params['fromAcc'] = $this->input->post('accountNumber', TRUE); 
        
        $result = $this->Api_transaction_model->getTransactionInfo($params);
       
        $res=array();
        if($result){
        	foreach($result->result() as $station){
        		
				
				$res["toAcc"]=$station->toAcc;
				$res["amount"]=$station->amount;
				$res["create_dateTime"]=$station->create_dateTime;
                $res["stationName"]=$station->stationName;
				
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


    function cost_calculator() {       

        $params['fromAcc'] = $this->input->post('accountNumber', TRUE); 
        $date = date("Y-m-d H:i:s");
        $params['toDateTime'] = $date;

        $fromdate = date("Y-m-d"). ' 00:00:00';
        $params['fromDateTime'] = $fromdate;

       /* echo $fromdate;
        die();*/
        

        $d365 = date('Y-m-d H:i:s', strtotime('-365 days'));
        $params['fromDateTime365'] = $d365;
        $d30 = date('Y-m-d H:i:s', strtotime('-30 days'));
        $params['fromDateTime30'] = $d30;
        $d7 = date('Y-m-d H:i:s', strtotime('-7 days'));
        $params['fromDateTime7'] = $d7;

        /*echo $date;
        echo $d2;
        die();*/

        $result = $this->Api_transaction_model->getCostCalculatorInfo($params);       
        $json[]=$result;
        if ($result){
            echo json_encode(
                array(
                    "success"=>true,
                    "info"=>$json
                )
            );
            die();
        }

        echo json_encode(
            array(
                "success"=>false,
                "msg"=>"There are no data found."
            )
        );
            
    }
    
}
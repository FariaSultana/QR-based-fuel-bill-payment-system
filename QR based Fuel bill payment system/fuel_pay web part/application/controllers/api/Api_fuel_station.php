<?php defined('BASEPATH') OR exit('No direct script access allowed');

class Api_fuel_station extends CI_Controller {

    function __construct(){
        parent::__construct();
        date_default_timezone_set('Asia/Dhaka');
        $this->load->model('Api_station_model');

        // if(!$this->session->userdata('validated')){
        //     redirect('Login');
        // }

    }

    function station_area() {       

        $result = $this->Api_station_model->areaListInfo();

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

    function station_entry() {       

        $params['stationId'] = $this->input->post('stationId', TRUE); 
        
        $result = $this->Api_station_model->entryInfo($params);

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

    function station_details_area() {
        $params['location'] = $this->input->post('location', TRUE); 
        //$params['active'] = $this->input->post('active', TRUE); 
        $params['time'] = date('H:i:s');

        $result = $this->Api_station_model->areaDetailsListInfo($params);
        $dates=date("H:i:s");
        $res=array();
        if($result){
        	foreach($result->result() as $station){
        		
				if($station->start_time<$dates && $station->end_time>$dates){
					$res["is_active"]="true";
				}
				else{
					$res["is_active"]="false";
				}
				$res["start_time"]=$station->start_time;
				$res["end_time"]=$station->end_time;
				$res["name"]=$station->name;
				$res["location"]=$station->location;
				$res["mobile_no"]=$station->mobile_no;
                $res["traffic"]=$station->traffic;
                $res["station_id"]=$station->station_id;
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

    function station_map() {       

        $result = $this->Api_station_model->allAreaInfo();

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
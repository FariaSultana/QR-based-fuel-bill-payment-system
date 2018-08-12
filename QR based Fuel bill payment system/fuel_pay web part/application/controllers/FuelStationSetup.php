<?php defined('BASEPATH') OR exit('No direct script access allowed');

class FuelStationSetup extends CI_Controller {

    function __construct(){
        parent::__construct();
         $this->load->model('Station_model');

        if(!$this->session->userdata('validated')){
            redirect('Login');
        }

    }

    public function stationSetup(){
        $data['panelHeading'] = 'Fuel Station Setup';
        $data['bodyTemplate'] = 'stationSetupView';
        $data['message'] = $this->session->flashdata('message');

        $this->load->view('siteTemplate', $data);
    }

     public function viewSetup(){
        $data['panelHeading'] = 'View Station Setup';
        $data['bodyTemplate'] = 'viewSetupView';
        $data['message'] = $this->session->flashdata('message');
        $data['SetupList'] = $this->Station_model->getSetupList();

        $this->load->view('siteTemplate', $data);
    }

    public function stationDetails($stationId){
        $data['panelHeading'] = 'Station Details';
        $data['bodyTemplate'] = 'viewStationDetails';
        $data['message'] = $this->session->flashdata('message');

        $taskResult = $this->Station_model->getStationDetails($stationId); 

        //var_dump($taskResult);
        //die();     

        $data['stationDetails'] = $taskResult;        

        $this->load->view('siteTemplate', $data);
    }



     function saveSetup() {
        
        $taskInfo = array();

        $taskInfo['name'] = $this->input->post('StationName',TRUE);
        $taskInfo['location'] = $this->input->post('Location',true);
        $taskInfo['start_time'] = $this->input->post('StartTime',TRUE);
        $taskInfo['end_time'] = $this->input->post('EndTime',TRUE);
        $taskInfo['mobile_no'] = $this->input->post('MobileNo',TRUE);
        $taskInfo['Latitude'] = $this->input->post('Latitude',TRUE);
        $taskInfo['Longitude'] = $this->input->post('Longitude',TRUE);
        $taskInfo['account_no'] = $this->input->post('MobileNo',TRUE);
        $taskInfo['status'] = "Active";
        $taskInfo['amount'] = 0;
        $taskInfo['traffic'] = 0;
        $date = date("Y-m-d H:i:s");
        $taskInfo['create_dateTime'] = $date;
        $taskInfo['update_dateTime'] = $date;


        $this->load->library('form_validation');

        $this->form_validation->set_rules('name', 'Station Name', 'required|callback___checkStationName');
        $this->form_validation->set_rules('mobile_no', 'Mobile No', 'required|callback___checkMobileNo');
//        $this->form_validation->set_rules('password', 'Password', 'required');       
//        $this->form_validation->set_rules('contact_no', 'Contact No:', 'required|integer');
//        $this->form_validation->set_rules('email', 'Email', 'required');

        if ($this->form_validation->run() == FALSE) {

            $json = array(
                "success" => false,
                "msg" => validation_errors('<p>', '</p>')
            );

            echo json_encode($json);
           // $this->session->set_flashdata('message', 'Data insert error');
           // redirect('FuelStationSetup/stationSetup');
           // die();
        }

//        $date = date("Y-m-d H:i:s");
//
//        $docData = array(
//            "username" => (int) $folder_id,
//            "folder_name" => $folder_name,
//            "machine_name" => $machine_name,
//            "parent_id" => (int) $parent_id,
//            "created" => $date,
//            "updated" => $date
//        );
        
        $rDocument = $this->Station_model->saveSetup($taskInfo);
        
//        echo json_encode($rDocument);
        $this->session->set_flashdata('message', 'Station Added Successfully!');
            redirect('FuelStationSetup/stationSetup');
            die();

    }
    
    function __checkStationName($name)
    {
        $this->db->where("name",$name);
//        if((int)$this->uri->segment(4) > 0):
//            $this->db->where("user_id <>",(int)$this->uri->segment(4));
//        endif;
        $result = $this->db->get('station_setup');
        
        if($result->num_rows() > 0):
            $this->form_validation->set_message("__checkUserName","Please select a different station name.");            
            return false;
        endif;    
        return true;
    }

    function __checkMobileNo($mobile)
    {
        $this->db->where("mobile_no",$mobile);
        $result = $this->db->get('station_setup');
        
        if($result->num_rows() > 0):
            $this->form_validation->set_message("__checkMobileNo","Please select a different mobile number.");            
            return false;
        endif;    
        return true;
    }
}
<?php defined('BASEPATH') OR exit('No direct script access allowed');

class FuelRate extends CI_Controller {

    function __construct(){
        parent::__construct();
         $this->load->model('Rate_model');

        if(!$this->session->userdata('validated')){
            redirect('Login');
        }

    }


    public function addRate(){
        $data['panelHeading'] = 'Add Fuel Rate';
        $data['bodyTemplate'] = 'addFuelRateView';
        $data['message'] = $this->session->flashdata('message');

        $this->load->view('siteTemplate', $data);
    }    

    public function viewFuelRate(){
        $data['panelHeading'] = 'View Fuel Rate';
        $data['bodyTemplate'] = 'viewFuelRateView';
        $data['message'] = $this->session->flashdata('message');
        $data['FuelRates'] = $this->Rate_model->getRateList();

        $this->load->view('siteTemplate', $data);
    }

   
    function saveRate() {
        
        $taskInfo = array();

        $taskInfo['weight_measurements'] = $this->input->post('weight',TRUE);
        $taskInfo['fuel_type'] = $this->input->post('fuel_type',true);
        $taskInfo['amount'] = $this->input->post('amount',TRUE);
       
        $this->load->library('form_validation');

        $this->form_validation->set_rules('fuel_type', 'Fuel Type', 'required|callback___checkFuelType');


        if ($this->form_validation->run() == FALSE) {

            $json = array(
                "success" => false,
                "msg" => validation_errors('<p>', '</p>')
            );

            $this->session->set_flashdata('message', 'Fuel Type is in Use');
            redirect('FuelRate/addRate');
            die();
        }

        
        $rDocument = $this->Rate_model->saveFuelRate($taskInfo);
        

        $this->session->set_flashdata('message', 'Fuel Rate Added Successfully!');
            redirect('FuelRate/addRate');
            die();

    }



    
    
    function __checkFuelType($FuelType)
    {
        $this->db->where("fuel_type",$FuelType);
        $result = $this->db->get('fuel_rate');
        
        if($result->num_rows() > 0):
            $this->form_validation->set_message("__checkFuelType","Please select a different fuel type.");            
            return false;
        endif;    
        return true;
    }
}
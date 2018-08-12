<?php defined('BASEPATH') OR exit('No direct script access allowed');

class AddMoney extends CI_Controller {

    function __construct(){
        parent::__construct();
         $this->load->model('Add_Money_model');

        if(!$this->session->userdata('validated')){
            redirect('Login');
        }

    }        

    public function viewAddMoney(){
        $data['panelHeading'] = 'Add Money';
        $data['bodyTemplate'] = 'viewAddMoney';
        $data['message'] = $this->session->flashdata('message');
        $data['userGroups'] = $this->Add_Money_model->getAddMoneyList();

        $this->load->view('siteTemplate', $data);
    }

    public function viewAddMoneyHistory(){
        $data['panelHeading'] = 'Add Money History';
        $data['bodyTemplate'] = 'viewAddMoneyHistory';
        $data['message'] = $this->session->flashdata('message');
        $data['userGroups'] = $this->Add_Money_model->getAddMoneyHistoryList();

        $this->load->view('siteTemplate', $data);
    }

    public function submitAddMoney($accNo){
        /*$data['panelHeading'] = 'Edit User';
        $data['bodyTemplate'] = 'editUserView';
        $data['message'] = $this->session->flashdata('message');*/

        $taskResult = $this->Add_Money_model->sendMoney($accNo);   

        redirect('AddMoney/viewAddMoney');
    }

}
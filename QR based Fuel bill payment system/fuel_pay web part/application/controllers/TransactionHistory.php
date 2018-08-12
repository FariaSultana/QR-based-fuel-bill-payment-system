<?php defined('BASEPATH') OR exit('No direct script access allowed');

class TransactionHistory extends CI_Controller {

    function __construct(){
        parent::__construct();
         $this->load->model('Transaction_History_model');

        if(!$this->session->userdata('validated')){
            redirect('Login');
        }

    }


   
    public function history(){
        $data['panelHeading'] = 'Transaction History';
        $data['bodyTemplate'] = 'viewTransactionHistory';
        $data['message'] = $this->session->flashdata('message');
        $data['userGroups'] = $this->Transaction_History_model->getHistoryList();

        $this->load->view('siteTemplate', $data);
    }


}
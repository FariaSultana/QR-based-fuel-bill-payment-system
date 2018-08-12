<?php defined('BASEPATH') OR exit('No direct script access allowed');

class Home extends CI_Controller {

    function __construct(){
        parent::__construct();
        $this->load->model('Home_model');

        if(!$this->session->userdata('validated')){
            redirect('Login');
        }

    }
    
    public function index(){
        $data['panelHeading'] = 'Dashboard';
        $data['bodyTemplate'] = 'homeView';
        $this->load->view('siteTemplate', $data);
    }

    public function dashboard(){
        $data['panelHeading'] = 'Dashboard';
        $data['bodyTemplate'] = 'homeView';
        $this->load->view('siteTemplate', $data);
    }
    

    public function login_process()
    {
        //header('Content-Type: application/json');

        $userInfo['username'] = $this->input->post('username');
        $userInfo['password'] = $this->input->post('password');



        // ************* new **************
        $loginResult = $this->Home_model->doLogin($userInfo);
        
        if ($loginResult){

            $this->load->view('adminView',$data);

        }
        else{
            $response['ISSUCCESS'] = "N";
            $response['RESULT'] = "";
            $response['MESSAGE'] = "Invalid Username";
        }
        
    }
    
}




<?php defined('BASEPATH') OR exit('No direct script access allowed');

class Login extends CI_Controller {

    function __construct(){
        parent::__construct();
        $this->load->model('Login_model');

        // if(!$this->session->userdata('validated')){
        //     redirect('Login');
        // }

    }
    
    public function index(){
        // echo "mezbah";
        $data = array(
 
            'title'     =>   'Hello World news',
            'content'   =>   'This is the content',
            'posts'     =>   array('Post 1', 'Post 2', 'Post 3'),
            'message'   =>   $this->session->flashdata('message')
        );

        $this->load->view('loginView', $data);
        // $this->load->view('adminView', $data);
    }



    public function login_process()
    {

        $userInfo['username'] = $this->input->post('username');
        $userInfo['password'] = $this->input->post('password');



        // ************* new **************
        $loginResult = $this->Login_model->doLogin($userInfo);
        
        if ($loginResult){

            echo "login done";
            $data = array(
                    'users_id' => $loginResult->users_id,
                    'username' => $loginResult->username,
                    'full_name' => $loginResult->full_name,
                    'contact_no' => $loginResult->contact_no,
                    'email' => $loginResult->email,
                    'validated' => true
                    );
                $this->session->set_userdata($data);




            redirect('Home/dashboard');

        }
        else{
            $response['ISSUCCESS'] = "N";
            $response['RESULT'] = "";
            $response['MESSAGE'] = "Invalid Username or Password";

            //add flash data 
         $this->session->set_flashdata('message','Invalid Username or Password');

            redirect('Login');
        }

        echo json_encode($response);
        
    }

    public function doLogout(){
        $this->session->sess_destroy();
        redirect('Home');
    }
}
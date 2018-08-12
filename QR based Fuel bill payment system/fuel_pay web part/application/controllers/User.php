<?php defined('BASEPATH') OR exit('No direct script access allowed');

class User extends CI_Controller {

    function __construct(){
        parent::__construct();
         $this->load->model('User_model');

        if(!$this->session->userdata('validated')){
            redirect('Login');
        }

    }


    public function addUser(){
        $data['panelHeading'] = 'Add User';
        $data['bodyTemplate'] = 'addUserView';
        $data['message'] = $this->session->flashdata('message');

        $this->load->view('siteTemplate', $data);
    }

    public function viewUser(){
        $data['panelHeading'] = 'View User';
        $data['bodyTemplate'] = 'viewUserView';
        $data['message'] = $this->session->flashdata('message');
        $data['userGroups'] = $this->User_model->getUserList();

        $this->load->view('siteTemplate', $data);
    }

    
    
    function saveUser() {
        
        $taskInfo = array();

        $taskInfo['username'] = $this->input->post('username',TRUE);
        $taskInfo['full_name'] = $this->input->post('full_name',true);
        $taskInfo['password'] = md5($this->input->post('password',TRUE));
        $taskInfo['contact_no'] = $this->input->post('contact_no',TRUE);
        $taskInfo['email'] = $this->input->post('email',TRUE);

        $this->load->library('form_validation');

        $this->form_validation->set_rules('username', 'User Name', 'required|callback___checkUserName');

        if ($this->form_validation->run() == FALSE) {

            $json = array(
                "success" => false,
                "msg" => validation_errors('<p>', '</p>')
            );
            $this->session->set_flashdata('message', 'Username is in Use');
            redirect('User/addUser');
            die();
        }
        
        $rDocument = $this->User_model->saveUser($taskInfo);
        

        $this->session->set_flashdata('message', 'User Added Successfully!');
            redirect('User/addUser');
            die();

    } 
    
    function __checkUserName($userName)
    {
        $this->db->where("username",$userName);

        $result = $this->db->get('users');
        
        if($result->num_rows() > 0):
            $this->form_validation->set_message("__checkUserName","Please select a different user name.");            
            return false;
        endif;    
        return true;
    }
}
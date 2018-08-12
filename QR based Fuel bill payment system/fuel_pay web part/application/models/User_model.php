<?php if (!defined('BASEPATH')) exit('No direct script access allowed');

class User_model extends CI_Model{
    
    function saveUser($data = array()) {
        $this->db->insert('users', $data);
    }

    function getUserList(){
        $query = $this->db->get('users');
        $result=$query->result_array();
        return $result;
    }

    
}



<?php if (!defined('BASEPATH')) exit('No direct script access allowed');

class Login_model extends CI_Model{
    

    function doLogin($userInfo){

        $this->db->where('username', $userInfo['username']);
        $this->db->where('password', MD5($userInfo['password']));

        $query = $this->db->get('users');
        return ($query->num_rows()> 0) ? $query->row() : false;
    }
}

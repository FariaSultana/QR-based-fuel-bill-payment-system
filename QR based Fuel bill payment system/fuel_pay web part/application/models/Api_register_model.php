<?php if (!defined('BASEPATH')) exit('No direct script access allowed');

class Api_register_model extends CI_Model{
    
    function insertRegisterInfo($data = array()) {
       $this->db->insert('apps_user_registration', $data);
    }
}

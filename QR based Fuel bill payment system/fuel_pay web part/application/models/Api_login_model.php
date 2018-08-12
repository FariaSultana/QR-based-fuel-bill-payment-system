<?php if (!defined('BASEPATH')) exit('No direct script access allowed');

class Api_login_model extends CI_Model{


    function checkLoginInfo($params = array()) {
        $this->db->select('*', FALSE)
                ->from("apps_user_registration")
                ->where("user_name", $params['user_name'])
                ->where("password", $params['password']);

        $result = $this->db->get();
        if ($result->num_rows() > 0) {
            return $result;
        }
        return false;      
    }

    function getAmount($params = array()) {
        $this->db->select('amount')
                ->from("apps_user_registration")
                ->where("accountNumber", $params['accountNumber']);

        $result = $this->db->get();
        if ($result->num_rows() > 0) {
            return $result;
        }
        return false;      
    }
}

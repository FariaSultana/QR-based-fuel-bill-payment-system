<?php if (!defined('BASEPATH')) exit('No direct script access allowed');

class Api_add_money_model extends CI_Model{
    
    function addMoneyInfo($data = array()) {
       $this->db->insert('add_money', $data);
    }

     function addMoneyHistoryInfo($params = array()) {    

        $this->db->select('*');
        $this->db->where("accountNumber", $params['accountNumber']);

        $result = $this->db->get('add_money');
        
       
        if ($result->num_rows() > 0) {
            return $result;
        }
        return false;
    }
}

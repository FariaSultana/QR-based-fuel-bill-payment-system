<?php if (!defined('BASEPATH')) exit('No direct script access allowed');

class Rate_model extends CI_Model{
    
    function saveFuelRate($data = array()) {
        $this->db->insert('fuel_rate', $data);
    }

    function getRateList(){
        $query = $this->db->get('fuel_rate');
        $result=$query->result_array();
        return $result;
    }  
    
}



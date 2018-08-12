<?php if (!defined('BASEPATH')) exit('No direct script access allowed');

class Transaction_History_model extends CI_Model{
    
    function getHistoryList(){
        $query = $this->db->get('transactions');
        $result=$query->result_array();
        return $result;
    } 
    
}



<?php if (!defined('BASEPATH')) exit('No direct script access allowed');

class Api_transaction_model extends CI_Model{
    

    function getTransactionInfo($params = array()) {    

        $this->db->select('*');
        $this->db->where("fromAcc", $params['fromAcc']); 
        
        $result = $this->db->get('transactions');
       
        if ($result->num_rows() > 0) {
            return $result;
        }
        return false;
    }

    function getCostCalculatorInfo($params = array()) {  



    	$this->db->select_sum('amount');
		$this->db->where("fromAcc", $params['fromAcc']);
		$this->db->where('create_dateTime <=',$params['toDateTime']);
		$this->db->where('create_dateTime >=',$params['fromDateTime365']);
        $result365 = $this->db->get('transactions');

    	$this->db->select_sum('amount');
		$this->db->where("fromAcc", $params['fromAcc']);
		$this->db->where('create_dateTime <=',$params['toDateTime']);
		$this->db->where('create_dateTime >=',$params['fromDateTime30']);
        $result30 = $this->db->get('transactions');


        $this->db->select_sum('amount');
		$this->db->where("fromAcc", $params['fromAcc']);
		$this->db->where('create_dateTime <=',$params['toDateTime']);
		$this->db->where('create_dateTime >=',$params['fromDateTime7']);
        $result7 = $this->db->get('transactions');

        $this->db->select_sum('amount');
		$this->db->where("fromAcc", $params['fromAcc']);
		$this->db->where('create_dateTime <=',$params['toDateTime']);
		$this->db->where('create_dateTime >=',$params['fromDateTime']);
        $resultday = $this->db->get('transactions');
        
        if ($result365->num_rows() > 0 && $result30->num_rows() > 0 && $result7->num_rows() > 0) {
            return array(
        	"year"=>$result365->row()->amount,
        	"month"=>$result30->row()->amount,
			"week"=>$result7->row()->amount,
			"day"=>$resultday->row()->amount
        	);
        }
        return false;
    }
}

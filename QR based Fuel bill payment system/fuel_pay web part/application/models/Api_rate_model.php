<?php if (!defined('BASEPATH')) exit('No direct script access allowed');

class Api_rate_model extends CI_Model{
    function fuelRateInfo() {    

        $this->db->select('*');
        $result = $this->db->get('fuel_rate');
       
        if ($result->num_rows() > 0) {
            return $result;
        }
        return false;
    }

     function getFueltoPriceCalculatorInfo($data = array()) {

       
		$result=$this->db->select("amount")
                    ->where("fuel_type", $data['fuelType'])
                    ->get("fuel_rate");
        
        if ($result->num_rows() > 0) {
            return $result;
        }
        return false;

    }   


   
}

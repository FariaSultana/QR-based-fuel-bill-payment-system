<?php if (!defined('BASEPATH')) exit('No direct script access allowed');

class Add_Money_model extends CI_Model{
    
    
    function getAddMoneyList(){
        $this->db->select('*');
        $this->db->where("status", "pending");
        $query = $this->db->get('add_money');

        $result=$query->result_array();
        return $result;
    }

    function getAddMoneyHistoryList(){
        
        $query = $this->db->get('add_money');

        $result=$query->result_array();
        return $result;
    }

    function sendMoney($params) {    

        $moneyQuery=$this->db->select("amount")
                    ->where("accountNumber", $params)
                    ->where("status", "pending")
                    ->get("add_money");

        $this->db->where("accountNumber", $params)
                    ->where("status", "pending")
                    ->update("add_money",array("status"=>"send"));


        $registerQuery=$this->db->select("accountNumber, amount")
                    ->where("accountNumber",$params)
                    ->get("apps_user_registration");   

        $moneyRow=$moneyQuery->row();
        $registerRow=$registerQuery->row();

        $newAmount=$moneyRow->amount + $registerRow->amount;

        $this->db->where("accountNumber", $params)
                    ->update("apps_user_registration",array("amount"=>$newAmount));


       
       
       /*
        if ($query->num_rows() > 0) {
            return $query->row();
        } else {
            return 0;
        }*/
    }
    

    
    
    
}



<?php if (!defined('BASEPATH')) exit('No direct script access allowed');

class Api_payment_model extends CI_Model{


    function getStationInfo($params = array()) {
        $this->db->select('account_no,location,name')
                ->from("station_setup")
                ->where("station_id", $params['station_id']);

        $result = $this->db->get();
        if ($result->num_rows() > 0) {
            return $result;
        }
        return false;      
    }

    function getPayment($data = array()) {

        // insert on transaction table
        $this->db->insert('transactions', $data);


        
        //station amount add
        $stationQuery=$this->db->select("amount")
                    ->where("account_no", $data['toAcc'])
                    ->get("station_setup");

        $stationRow=$stationQuery->row();        
        $stationAmount=$stationRow->amount + $data['amount'];

        $this->db->where("account_no", $data['toAcc'])
                    ->update("station_setup",array("amount"=>$stationAmount));


        //user amount substract
        $userQuery=$this->db->select("amount")
                    ->where("accountNumber", $data['fromAcc'])
                    ->get("apps_user_registration");

        $userRow=$userQuery->row();        
        $userAmount=$userRow->amount - $data['amount'];

        $this->db->where("accountNumber", $data['fromAcc'])
                    ->update("apps_user_registration",array("amount"=>$userAmount));

        //station qeueue out 
        $stationQuery=$this->db->select("traffic")
                    ->where("name", $data['stationName'])
                    ->get("station_setup");

        $stationRow=$stationQuery->row();        
        $stationTraffic=$stationRow->traffic - 1;

        $this->db->where("name", $data['stationName'])
                    ->update("station_setup",array("traffic"=>$stationTraffic));

       
    }
}

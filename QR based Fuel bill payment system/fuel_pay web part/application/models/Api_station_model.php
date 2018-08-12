<?php if (!defined('BASEPATH')) exit('No direct script access allowed');

class Api_station_model extends CI_Model{
    function areaListInfo() {    

        $this->db->select('station_id,location');
        $this->db->group_by('location');

        $result = $this->db->get('station_setup');
       
        if ($result->num_rows() > 0) {
            return $result;
        }
        return false;
    }

    function allAreaInfo() {    

        $this->db->select('*');
        $result = $this->db->get('station_setup');
       
        if ($result->num_rows() > 0) {
            return $result;
        }
        return false;
    }

    function areaDetailsListInfo($params = array()) {    

        $this->db->select('*');
        $this->db->where("location", $params['location']);
        // $this->db->where('s.start_time >=', $params['time']);
        // $this->db->where('s.end_time <=', $params['time']);

        $result = $this->db->get('station_setup');
        
       
        if ($result->num_rows() > 0) {
            return $result;
        }
        return false;
    }

    function entryInfo($params = array()) {           



        $stationQuery=$this->db->select("traffic")
                    ->where("station_id", $params['stationId'])
                    ->get("station_setup");

        $stationRow=$stationQuery->row();        
        $stationTraffic=$stationRow->traffic + 1;

        $this->db->where("station_id", $params['stationId'])
                    ->update("station_setup",array("traffic"=>$stationTraffic));
        
       
        if ($stationQuery->num_rows() > 0) {
            return $stationQuery;
        }
        return false;
    }
}

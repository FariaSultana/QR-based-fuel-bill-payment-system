<?php if (!defined('BASEPATH')) exit('No direct script access allowed');

class Station_model extends CI_Model{
   
	function saveSetup($data = array()) {
        $this->db->insert('station_setup', $data);
    }

    function getSetupList(){
        $query = $this->db->get('station_setup');
        $result=$query->result_array();
        return $result;
    }
    function getStationDetails($params) {    

        $this->db->select('*');
        $this->db->where("station_id", $params);
        $query = $this->db->get('station_setup');
       
       
        if ($query->num_rows() > 0) {
            return $query->row_array();
        } else {
            return 0;
        }
    }

}
    
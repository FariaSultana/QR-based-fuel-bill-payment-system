package project.afinal.fuelpay.helper;

/**
 * Created by ASUS on 11/24/2017.
 */

public class StationDetailsAreaHelper {

    private String name;
    private String location;
    private String start_time;
    private String end_time;
    private String mobile_no;
    private String status;
    private String stationid;
    private String traffic;

    public StationDetailsAreaHelper(String name, String location,
                                    String start_time, String end_time, String mobile_no, String status, String traffic, String station_id) {
        this.name = name;
        this.location = location;
        this.start_time = start_time;
        this.end_time = end_time;
        this.mobile_no = mobile_no;
        this.status = status;
        this.traffic = traffic;
        this.stationid = station_id;
    }

    public StationDetailsAreaHelper(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public String getTraffic() {
        return traffic;
    }

    public void setTraffic(String traffic) {
        this.traffic = traffic;
    }

   /* public StationDetailsAreaHelper(String name, String location,
                                    String start_time, String end_time, String mobile_no, String status,String stationid) {
        this.name = name;
        this.location = location;
        this.start_time = start_time;
        this.end_time = end_time;
        this.mobile_no = mobile_no;
        this.status = status;
        this.stationid = stationid;
    }*/

    public String getStationid() {
        return stationid;
    }

    public void setStationid(String stationid) {
        this.stationid = stationid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}

package project.afinal.fuelpay.helper;

/**
 * Created by ASUS on 11/24/2017.
 */

public class StationAreaHelper {

    private String location;
    private String stationid;

    public StationAreaHelper(String location, String stationid) {
        this.location = location;
        this.stationid = stationid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStationid() {
        return stationid;
    }

    public void setStationid(String stationid) {
        this.stationid = stationid;
    }
}

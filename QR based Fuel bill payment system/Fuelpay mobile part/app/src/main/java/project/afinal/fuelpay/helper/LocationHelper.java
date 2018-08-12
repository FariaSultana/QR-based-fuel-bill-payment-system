package project.afinal.fuelpay.helper;


import android.os.Parcel;
import android.os.Parcelable;

public class LocationHelper implements Parcelable {

	private String categoryName = "";
	private String latitude = "";
	private String longitude = "";
	private String address = "";
	private boolean distanceIsString = false;

	public boolean isDistanceIsString() {
		return distanceIsString;
	}

	public void setDistanceIsString(boolean distanceIsString) {
		this.distanceIsString = distanceIsString;
	}



	private double distance;
	private String distanceString;
	private String valueDs;

	public String getValueDs() {
		return valueDs;
	}

	public void setValueDs(String value) {
		this.valueDs = value;
	}

	public String getDistanceString() {
		return distanceString;
	}

	public void setDistanceString(String distanceString) {
		this.distanceString = distanceString;
	}

	public LocationHelper(String cat, String lati, String longi, String add) {
		this.categoryName = cat;
		this.latitude = lati;
		this.longitude = longi;
		this.address = add;
	}



	// The following methods that are required for using Parcelable
	private LocationHelper(Parcel in) {
		// This order must match the order in writeToParcel()
		categoryName = in.readString();
		latitude = in.readString();
		longitude = in.readString();
		address = in.readString();

		// Continue doing this for the rest of your member data
	}

	public LocationHelper(String name, String address, String lat, String longi,
                          double dist) {

		this.categoryName = name;
		this.address = address;
		this.latitude = lat;
		this.longitude = longi;
		this.distance = dist;
	}

	public LocationHelper(String name, String address1, String address2, String lat, String longi,
                          double dist) {

		this.categoryName = name;
		this.address = address1;
		this.latitude = lat;
		this.longitude = longi;
		this.distance = dist;
	}

	public LocationHelper(Double distance2) {
		this.distance = distance2;
	}

	public LocationHelper(String distance2, String valueString) {
		this.distanceString = distance2;
		this.valueDs = valueString;
	}

	public LocationHelper(String name, String address3, String address22,
                          String latitudeString, String longitudeString,
                          String distanceString2, String valueDs2, boolean distanceIsString) {
		this.categoryName = name;
		this.address = address3;
		this.latitude = latitudeString;
		this.longitude = longitudeString;
		this.distanceString = distanceString2;
		this.valueDs = valueDs2;
		this.distanceIsString = distanceIsString;
		}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}



	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCategoryName() {
		return this.categoryName;
	}

	public String getLatitude() {
		return this.latitude;
	}

	public String getLongitude() {
		return this.longitude;
	}

	@Override
	public String toString() {
		return "LocationInfo [Category=" + categoryName + ", Latitude="
				+ latitude + ", Longitude=" + longitude + ", Address="
				+ address +  "]";
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(categoryName);
		dest.writeString(latitude);
		dest.writeString(longitude);
		dest.writeString(address);
	}

	public static final Creator<LocationHelper> CREATOR = new Creator<LocationHelper>() {
		public LocationHelper createFromParcel(Parcel in) {
			return new LocationHelper(in);
		}

		public LocationHelper[] newArray(int size) {
			return new LocationHelper[size];
		}
	};

	/*@Override
	public int compareTo(LocationInfo info) {
		//let's sort the employee based on id in ascending order
        //returns a negative integer, zero, or a positive integer as this employee id
        //is less than, equal to, or greater than the specified object.
        return (int) (this.distance - info.distance);
	}*/
	
	/**
     * Comparator to sort employees list or array in order of Salary
     */
//    public static Comparator<LocationInfo> DoubleComparator = new Comparator<LocationInfo>() {
// 
//        @Override
//        public int compare(LocationInfo l1, LocationInfo l2) {
//            return (int) (l1.getDistance() - l2.getDistance());
//        }
//    };

}

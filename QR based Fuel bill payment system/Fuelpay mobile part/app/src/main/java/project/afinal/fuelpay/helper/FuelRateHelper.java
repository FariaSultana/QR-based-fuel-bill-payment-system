package project.afinal.fuelpay.helper;

/**
 * Created by ASUS on 11/24/2017.
 */

public class FuelRateHelper {

    private String weight;
    private String fuelType;
    private String price;



    public FuelRateHelper(String weight, String fuelType,
                          String price) {
        this.weight = weight;
        this.fuelType = fuelType;
        this.price = price;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}

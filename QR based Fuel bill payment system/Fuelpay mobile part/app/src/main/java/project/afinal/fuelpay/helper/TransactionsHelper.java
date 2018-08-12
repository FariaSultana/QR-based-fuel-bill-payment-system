package project.afinal.fuelpay.helper;

/**
 * Created by ASUS on 11/24/2017.
 */

public class TransactionsHelper {

    private String toAcc;
    private String amount;
    private String create_dateTime;
    private String stationName;

    public TransactionsHelper(String toAcc, String amount,
                              String create_dateTime,String stationName) {
        this.toAcc = toAcc;
        this.amount = amount;
        this.create_dateTime = create_dateTime;
        this.stationName = stationName;

    }

    public String getToAcc() {
        return toAcc;
    }

    public void setToAcc(String toAcc) {
        this.toAcc = toAcc;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCreate_dateTime() {
        return create_dateTime;
    }

    public void setCreate_dateTime(String create_dateTime) {
        this.create_dateTime = create_dateTime;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }



}

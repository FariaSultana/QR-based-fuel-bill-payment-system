package project.afinal.fuelpay.helper;

/**
 * Created by ASUS on 11/24/2017.
 */

public class AddMoneyHistoryHelper {

    private String accountNumber;
    private String amount;
    private String status;
    private String dateTime;

    public AddMoneyHistoryHelper(String accountNumber, String amount,
                                 String status, String dateTime) {
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.status = status;
        this.dateTime = dateTime;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

}

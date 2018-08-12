package project.afinal.fuelpay;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by ASUS on 1/28/2018.
 */

public class HomeMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_main);

    }
    public void myAccount(View v) {
        Intent intent = new Intent(getBaseContext(), ProfileActivity.class);
        startActivity(intent);
    }
    public void addMoney(View v) {
        Intent intent = new Intent(getBaseContext(), AddMoneyActivity.class);
        startActivity(intent);
    }
    public void qrScan(View v) {
        Intent intent = new Intent(getBaseContext(), QRScanActivity.class);
        startActivity(intent);
    }
    public void transaction(View v) {
        Intent intent = new Intent(getBaseContext(), TransactionActivity.class);
        startActivity(intent);
    }
    public void stationTraffic(View v) {
        Intent intent = new Intent(getBaseContext(), TrafficAreaSearchActivity.class);
        startActivity(intent);
    }
    public void expense(View v) {
        Intent intent = new Intent(getBaseContext(), ExpenseActivity.class);
        startActivity(intent);
    }
    public void fuelRate(View v) {
        Intent intent = new Intent(getBaseContext(), FuelRateActivity.class);
        startActivity(intent);
    }
    public void fuelCalculator(View v) {
        Intent intent = new Intent(getBaseContext(), FuelCalculatorActivity.class);
        startActivity(intent);
    }
    public void mapSearch(View v) {
        /*Intent intent = new Intent(getBaseContext(), MapAllStationSearchActivity.class);
        startActivity(intent);*/
    }
    public void stationSearch(View v) {
        Intent intent = new Intent(getBaseContext(), StationAreaSearchActivity.class);
        startActivity(intent);
    }
    public void reminder(View v) {
        /*Intent intent = new Intent(getBaseContext(), MapInputDistanceActivity.class);
        startActivity(intent);*/
    }
    public void alarm(View v) {
        /*Intent intent = new Intent(getBaseContext(), FuelAlarmActivity.class);
        startActivity(intent);*/
    }
    public void mood(View v) {
        /*Intent intent = new Intent(getBaseContext(), MyMoodActivity.class);
        startActivity(intent);*/
    }
    public void exit(View v) {
        Intent intent = new Intent(getBaseContext(),
                LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}

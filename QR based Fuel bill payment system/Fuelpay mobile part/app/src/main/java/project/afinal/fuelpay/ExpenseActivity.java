package project.afinal.fuelpay;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

import cn.pedant.SweetAlert.SweetAlertDialog;
import project.afinal.fuelpay.library.HttpConnectionClass;
import project.afinal.fuelpay.library.InternetConnectionDetector;
import project.afinal.fuelpay.library.SharedPreferencesClass;

/**
 * Created by ASUS on 1/18/2018.
 */

public class ExpenseActivity extends Activity {
    String calculateUrl, serverResponse, Result, success, day,week,month,year;
    TextView todayText,weekText,monthText,yearText;
    private ProgressDialog pDialog;
    HttpConnectionClass httpClass;
    JSONObject calculateInfoJSON = null;
    private Handler handler = new Handler();
    SharedPreferencesClass storePreference;
    InternetConnectionDetector internetDetector = new InternetConnectionDetector(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_cost_calculator);

        initialize();

    }

    public void initialize() {
        calculateUrl = getString(R.string.server_address)
                + "api/api_transaction/cost_calculator";
        httpClass = new HttpConnectionClass(ExpenseActivity.this);
        storePreference = new SharedPreferencesClass(getApplicationContext());

        todayText = findViewById(R.id.today);
        weekText = findViewById(R.id.week);
        monthText = findViewById(R.id.month);
        yearText = findViewById(R.id.year);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("loading...");
        pDialog.setCancelable(false);
        if (internetValidation()) {
            new Thread(new LoadcalCalculateTask()).start();
            showpDialog();
        }
    }


    private class LoadcalCalculateTask implements Runnable {


        LoadcalCalculateTask() {
        }


        @Override
        public void run() {


            try {
                URL url = new URL(calculateUrl); // here is your URL path

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("accountNumber", storePreference.getString("accountNumber"));

                serverResponse = httpClass.httpPostConnection(postDataParams, url);

                calculateInfoJSON = new JSONObject(serverResponse);

                    /*JSONObject eachObjFromJSONArray = userLoginInfoJSON
                            .getJSONObject("otp");*/


                success = calculateInfoJSON
                        .getString("success");
                if(success.equals("true")) {
                    JSONArray loginArray = calculateInfoJSON
                            .getJSONArray("info");

                    JSONObject eachObjFromJSONArray = loginArray
                            .getJSONObject(0);
                    day = eachObjFromJSONArray
                            .getString("day");
                    week = eachObjFromJSONArray
                            .getString("week");
                    month = eachObjFromJSONArray
                            .getString("month");
                    year = eachObjFromJSONArray
                            .getString("year");

                }
                Result = "";

            } catch (Exception ex) {
                Result = "Exception";
            }


            handler.post(new Runnable() {
                @Override
                public void run() {
                    hidepDialog();
                    if (Result.equals("Exception")) {
                        new SweetAlertDialog(ExpenseActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText(getString(R.string.errorHeader))
                                .setContentText(getString(R.string.errorMessage))
                                .show();
                    }  else {
                        if (success.equals("true")) {
                            todayText.setText("BDT "+day);
                            weekText.setText("BDT "+week);
                            monthText.setText("BDT "+month);
                            yearText.setText("BDT "+year);
                        } else {
                            new SweetAlertDialog(ExpenseActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText(getString(R.string.errorHeader))
                                    .setContentText(getString(R.string.errorMessage))
                                    .show();
                        }
                    }
                }
            });
        }

    }

    public boolean internetValidation() {
        if (!internetDetector.isConnectedToInternet()) {
            new SweetAlertDialog(ExpenseActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText(getString(R.string.internetHeader))
                    .setContentText(getString(R.string.internetMessage))
                    .show();
            return false;
        }
        return true;
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}

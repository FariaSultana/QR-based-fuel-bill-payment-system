package project.afinal.fuelpay;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

import cn.pedant.SweetAlert.SweetAlertDialog;
import project.afinal.fuelpay.library.HttpConnectionClass;
import project.afinal.fuelpay.library.InternetConnectionDetector;


/**
 * Created by ASUS on 1/17/2018.
 */

public class FuelCalculatorActivity extends Activity {
    String calculateUrl, serverResponse, Result, success, dataAmount;
    Spinner conversionType, fuelType;
    EditText etInput;
    TextView resultText,approxText;
    private ProgressDialog pDialog;
    HttpConnectionClass httpClass;
    JSONObject calculateInfoJSON = null;
    private Handler handler = new Handler();
    InternetConnectionDetector internetDetector = new InternetConnectionDetector(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_fuel_calculator);

        initialize();

    }

    public void initialize() {
        calculateUrl = getString(R.string.server_address)
                + "api/api_fuel_rate/fuel_calculator";
        httpClass = new HttpConnectionClass(FuelCalculatorActivity.this);
        resultText = findViewById(R.id.resultText);
        conversionType = findViewById(R.id.conversionType);
        fuelType = findViewById(R.id.fuelType);
        etInput = findViewById(R.id.etInput);
        approxText = findViewById(R.id.approxText);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("loading...");
        pDialog.setCancelable(false);
    }

    public void calculate(View v) {
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
                postDataParams.put("fuelType", fuelType.getSelectedItem().toString());

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
                    dataAmount = eachObjFromJSONArray
                            .getString("amount");

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
                        new SweetAlertDialog(FuelCalculatorActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText(getString(R.string.errorHeader))
                                .setContentText(getString(R.string.errorMessage))
                                .show();
                    }  else {
                        if (success.equals("true")) {
                            int fuel = Integer.parseInt(etInput.getText().toString());
                            int price = Integer.parseInt(dataAmount);
                            resultText.setVisibility(View.VISIBLE);

                            if (conversionType.getSelectedItem().toString().equals("Fuel to Price")) {
                                approxText.setVisibility(View.GONE);
                                int finalAmount = fuel * price;
                                resultText.setText("BDT " + String.valueOf(finalAmount));
                            } else {
                                approxText.setVisibility(View.VISIBLE);
                                double finalAmount = fuel / price;
                                resultText.setText(String.valueOf(finalAmount) + " Litre");
                            }
                        } else {
                            new SweetAlertDialog(FuelCalculatorActivity.this, SweetAlertDialog.ERROR_TYPE)
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
            new SweetAlertDialog(FuelCalculatorActivity.this, SweetAlertDialog.ERROR_TYPE)
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

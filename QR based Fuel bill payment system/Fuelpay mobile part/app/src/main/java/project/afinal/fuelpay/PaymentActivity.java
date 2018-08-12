package project.afinal.fuelpay;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
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
 * Created by ASUS on 1/16/2018.
 */

public class PaymentActivity extends Activity {
    String paymenturl = "", stationUrl = "", serverResponse = "", Result = "", msg = "", success1 = "", success = "", location, toAcc, stationName;
    private Handler handler = new Handler();
    private ProgressDialog pDialog;
    InternetConnectionDetector internetDetector = new InternetConnectionDetector(this);
    JSONObject userLoginInfoJSON = null;
    JSONObject paymentInfoJSON = null;
    HttpConnectionClass httpClass;
    SharedPreferencesClass storePreference;
    EditText acc_no, amounttext;
    TextView textlocation, textStationName, textaccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        initialize();
    }

    public void initialize() {
        stationUrl = getString(R.string.server_address)
                + "api/api_payment/station_info";
        paymenturl = getString(R.string.server_address)
                + "api/api_payment/payment";
        httpClass = new HttpConnectionClass(this);
        storePreference = new SharedPreferencesClass(getApplicationContext());
        textStationName = (TextView) findViewById(R.id.textStationName);
        textaccount = (TextView) findViewById(R.id.textaccount);
        textlocation = (TextView) findViewById(R.id.textlocation);

        acc_no = (EditText) findViewById(R.id.acc_no);
        amounttext = (EditText) findViewById(R.id.amount);
        String accNumbr = storePreference.getString("accountNumber");
        acc_no.setText(accNumbr);
        acc_no.setKeyListener(null);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("loading...");
        pDialog.setCancelable(false);

        if (internetValidation()) {
            new Thread(new LoadStationInfoTask()).start();
            showpDialog();
        }
    }

    public void requestPayment(View v) {
        if (internetValidation()) {
            new Thread(new LoadPaymentTask()).start();
            showpDialog();
        }
    }

    private class LoadPaymentTask implements Runnable {


        LoadPaymentTask() {
        }


        @Override
        public void run() {


            try {
                URL url = new URL(paymenturl); // here is your URL path

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("toAcc", textaccount.getText().toString());
                postDataParams.put("fromAcc", acc_no.getText().toString());
                postDataParams.put("amount", amounttext.getText().toString());
                postDataParams.put("stationName", textStationName.getText().toString());

                // Log.e("params", postDataParams.toString());

                serverResponse = httpClass.httpPostConnection(postDataParams, url);

                paymentInfoJSON = new JSONObject(serverResponse);

                    /*JSONObject eachObjFromJSONArray = userLoginInfoJSON
                            .getJSONObject("otp");*/


                success1 = paymentInfoJSON
                        .getString("success");
                msg = paymentInfoJSON
                        .getString("msg");
                msg = msg.replaceAll("\\<.*?\\>", "");

                Result = "";

            } catch (Exception ex) {
                Result = "Exception";
            }


            handler.post(new Runnable() {
                @Override
                public void run() {
                    hidepDialog();
                    if (Result.equals("Exception")) {
                        new SweetAlertDialog(PaymentActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText(getString(R.string.errorHeader))
                                .setContentText(getString(R.string.errorMessage))
                                .show();
                    } else {
                        if (success1.equals("true")) {
                            new SweetAlertDialog(PaymentActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Success!")
                                    .setContentText(msg)
                                    .setConfirmText("Done")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            startActivity(new Intent(PaymentActivity.this, HomeMainActivity.class));
                                            finish();
                                        }
                                    })
                                    .show();
                        } else {
                            new SweetAlertDialog(PaymentActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText(getString(R.string.errorHeader))
                                    .setContentText(getString(R.string.errorMessage))
                                    .show();
                        }
                    }
                }
            });
        }

    }

    private class LoadStationInfoTask implements Runnable {


        LoadStationInfoTask() {
        }


        @Override
        public void run() {


            try {
                URL url = new URL(stationUrl); // here is your URL path

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("station_id", getIntent().getStringExtra("qr_text_code"));

                // Log.e("params", postDataParams.toString());

                serverResponse = httpClass.httpPostConnection(postDataParams, url);

                userLoginInfoJSON = new JSONObject(serverResponse);

                    /*JSONObject eachObjFromJSONArray = userLoginInfoJSON
                            .getJSONObject("otp");*/


                success = userLoginInfoJSON
                        .getString("success");
                if (success.equals("true")) {
                    JSONArray loginArray = userLoginInfoJSON
                            .getJSONArray("info");

                    JSONObject eachObjFromJSONArray = loginArray
                            .getJSONObject(0);
                    stationName = eachObjFromJSONArray
                            .getString("name");
                    toAcc = eachObjFromJSONArray
                            .getString("account_no");
                    location = eachObjFromJSONArray
                            .getString("location");

                }

                Result = "";

            }  catch (Exception ex) {
                Result = "Exception";
            }


            handler.post(new Runnable() {
                @Override
                public void run() {
                    hidepDialog();
                    if (Result.equals("Exception")) {
                        new SweetAlertDialog(PaymentActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText(getString(R.string.errorHeader))
                                .setContentText(getString(R.string.errorMessage))
                                .show();
                    } else {
                        if (success.equals("true")) {
                            textStationName.setText(stationName);
                            textaccount.setText(toAcc);
                            textlocation.setText(location);
                        } else {
                            new SweetAlertDialog(PaymentActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText(getString(R.string.errorHeader))
                                    .setContentText(getString(R.string.errorMessage))
                                    .show();
                        }
                    }
                }
            });
        }

    }


    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    public boolean internetValidation() {
        if (!internetDetector.isConnectedToInternet()) {
            new SweetAlertDialog(PaymentActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText(getString(R.string.internetHeader))
                    .setContentText(getString(R.string.internetMessage))
                    .show();
            return false;
        }
        return true;
    }
}

package project.afinal.fuelpay;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
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

public class ProfileActivity extends Activity {

    TextView accno, amount, name, mobile, email, vehicle_name, fuel_type, fuel_per_km, vehicle_type;
    SharedPreferencesClass storePreference;
    InternetConnectionDetector internetDetector = new InternetConnectionDetector(this);
    private Handler handler = new Handler();
    private ProgressDialog pDialog;
    String amountUrl,serverResponse = "", Result = "",newAmount="",success="";
    JSONObject amountJSON = null;
    HttpConnectionClass httpClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initialize();
    }

    public void initialize() {
        storePreference = new SharedPreferencesClass(getApplicationContext());
        amountUrl = getString(R.string.server_address)
                + "api/api_login/amount";
        httpClass = new HttpConnectionClass(this);

        accno = findViewById(R.id.accno);
        accno.setText(storePreference.getString("accountNumber"));

        amount = findViewById(R.id.amount);
       // amount.setText("BDT "+storePreference.getString("amount"));

        name = findViewById(R.id.name);
        name.setText(storePreference.getString("user_name"));

        mobile = findViewById(R.id.mobile);
        mobile.setText(storePreference.getString("monbile_no"));

        email = findViewById(R.id.email);
        email.setText(storePreference.getString("email"));

        vehicle_name = findViewById(R.id.vehicle_name);
        vehicle_name.setText(storePreference.getString("vehicle_name"));

        fuel_type = findViewById(R.id.fuel_type);
        fuel_type.setText(storePreference.getString("fuel_type"));

        vehicle_type = findViewById(R.id.vehicle_type);
        vehicle_type.setText(storePreference.getString("vehicle_type"));

        fuel_per_km = findViewById(R.id.fuel_per_km);
        fuel_per_km.setText(storePreference.getString("fuel_per_km"));

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("loading...");
        pDialog.setCancelable(false);
        if (internetValidation()) {
            new Thread(new LoadAmountTask()).start();
            showpDialog();
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
            new SweetAlertDialog(ProfileActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText(getString(R.string.internetHeader))
                    .setContentText(getString(R.string.internetMessage))
                    .show();
            return false;
        }
        return true;
    }

    private class LoadAmountTask implements Runnable {


        LoadAmountTask() {
        }


        @Override
        public void run() {


            try {
                URL url = new URL(amountUrl); // here is your URL path

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("accountNumber", storePreference.getString("accountNumber"));

                serverResponse = httpClass.httpPostConnection(postDataParams, url);

                amountJSON = new JSONObject(serverResponse); /* {"success":"true","info":[{"amount":"465"}]}*/

                success = amountJSON
                        .getString("success");
                if (success.equals("true")) {
                    JSONArray loginArray = amountJSON
                            .getJSONArray("info");

                    JSONObject eachObjFromJSONArray = loginArray
                            .getJSONObject(0);
                    newAmount = eachObjFromJSONArray
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
                        new SweetAlertDialog(ProfileActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText(getString(R.string.errorHeader))
                                .setContentText(getString(R.string.errorMessage))
                                .show();
                    }  else {
                        amount.setText("BDT "+newAmount);
                    }
                }
            });
        }

    }
}

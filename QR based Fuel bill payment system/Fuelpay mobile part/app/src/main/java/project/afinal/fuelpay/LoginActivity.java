package project.afinal.fuelpay;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

import cn.pedant.SweetAlert.SweetAlertDialog;
import project.afinal.fuelpay.library.HttpConnectionClass;
import project.afinal.fuelpay.library.InternetConnectionDetector;
import project.afinal.fuelpay.library.SharedPreferencesClass;

/**
 * Created by Soma on 10/3/2017.
 */

public class LoginActivity extends Activity {

    String loginUrl = "", serverResponse = "", Result = "", success = "",registerId="",accountNumber="",
            amount,monbile_no,email,user_name,fuel_type,vehicle_type,vehicle_name,fuel_per_km;
    CheckBox saveUser;
    private Handler handler = new Handler();
    private ProgressDialog pDialog;
    InternetConnectionDetector internetDetector = new InternetConnectionDetector(this);
    JSONObject userLoginInfoJSON = null;
    HttpConnectionClass httpClass;
    SharedPreferencesClass storePreference;
    EditText etUserName, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialize();
    }

    public void initialize() {
        loginUrl = getString(R.string.server_address)
                + "api/api_login/login";
        httpClass = new HttpConnectionClass(this);
        storePreference = new SharedPreferencesClass(getApplicationContext());
        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        saveUser= (CheckBox) findViewById(R.id.checkboxRemember);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("loading...");
        pDialog.setCancelable(false);

        etUserName.setText(storePreference.getString("userName"));
        checkbox();
    }

    public void checkbox(){
        if(!storePreference.getString("userName").equals("")) {
            saveUser.setChecked(true);
        }
        saveUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    storePreference.putString("userName", etUserName.getText().toString());

                } else {
                    storePreference.putString("userName", "");
                }
            }
        });
    }

    public void register(View v) {
        Intent intent = new Intent(LoginActivity.this,
                RegisterActivity.class);
        startActivity(intent);
    }

    public void signIn(View v) {
        if (internetValidation()) {
            new Thread(new LoadLoginTask()).start();
            showpDialog();
        }
    }


    private class LoadLoginTask implements Runnable {


        LoadLoginTask() {
        }


        @Override
        public void run() {


            try {
                URL url = new URL(loginUrl); // here is your URL path

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("user_name", etUserName.getText().toString());
                postDataParams.put("password", etPassword.getText().toString());
                // Log.e("params", postDataParams.toString());

                serverResponse = httpClass.httpPostConnection(postDataParams, url);

                userLoginInfoJSON = new JSONObject(serverResponse);

                    /*JSONObject eachObjFromJSONArray = userLoginInfoJSON
                            .getJSONObject("otp");*/


                success = userLoginInfoJSON
                        .getString("success");
                if(success.equals("true")) {
                    JSONArray loginArray = userLoginInfoJSON
                            .getJSONArray("info");

                    JSONObject eachObjFromJSONArray = loginArray
                            .getJSONObject(0);
                    registerId = eachObjFromJSONArray
                            .getString("register_id");
                    accountNumber = eachObjFromJSONArray
                            .getString("accountNumber");
                    amount = eachObjFromJSONArray
                            .getString("amount");
                    monbile_no = eachObjFromJSONArray
                            .getString("monbile_no");
                    email = eachObjFromJSONArray
                            .getString("email");
                    user_name = eachObjFromJSONArray
                            .getString("user_name");


                    fuel_type = eachObjFromJSONArray
                            .getString("fuel_type");
                    vehicle_type = eachObjFromJSONArray
                            .getString("vehicle_type");
                    vehicle_name = eachObjFromJSONArray
                            .getString("vehicle_name");
                    fuel_per_km = eachObjFromJSONArray
                            .getString("fuel_per_km");

                    storePreference.putString("registerId", registerId);
                    storePreference.putString("accountNumber", accountNumber);

                    storePreference.putString("amount", amount);
                    storePreference.putString("monbile_no", monbile_no);
                    storePreference.putString("email", email);
                    storePreference.putString("user_name", user_name);

                    storePreference.putString("fuel_type", fuel_type);
                    storePreference.putString("vehicle_type", vehicle_type);
                    storePreference.putString("vehicle_name", vehicle_name);
                    storePreference.putString("fuel_per_km", fuel_per_km);
                }

                Result = "";

            }catch (Exception ex) {
                Result = "Exception";
            }


            handler.post(new Runnable() {
                @Override
                public void run() {
                    hidepDialog();
                    if (Result.equals("Exception")) {
                        new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText(getString(R.string.errorHeader))
                                .setContentText(getString(R.string.errorMessage))
                                .show();
                    } else {
                        if (success.equals("true")) {
                            successAlert();
                        } else {
                            new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText(getString(R.string.loginFailHeader))
                                    .setContentText(getString(R.string.lofinFailMessage))
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
            new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText(getString(R.string.internetHeader))
                    .setContentText(getString(R.string.internetMessage))
                    .show();
            return false;
        }
        return true;
    }

    public void successAlert() {
        new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Success!")
                .setContentText("Login successful. Please wait...")
                .show();
        Thread delay = new Thread() {

            public void run() {

                try {
                    sleep(2000);
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    startActivity(new Intent(getBaseContext(),
                            HomeMainActivity.class));
                    finish();
                }
            }
        };
        delay.start();

    }
}

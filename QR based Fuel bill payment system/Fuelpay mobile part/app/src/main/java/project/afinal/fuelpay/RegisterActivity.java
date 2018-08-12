package project.afinal.fuelpay;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

import cn.pedant.SweetAlert.SweetAlertDialog;
import project.afinal.fuelpay.library.HttpConnectionClass;
import project.afinal.fuelpay.library.InternetConnectionDetector;

/**
 * Created by Rajesh on 10/3/2017.
 */

public class RegisterActivity extends Activity {

    String registerUrl, serverResponse, success,msg, Result;
    EditText name, password, conpassword, mobile, vehicleName, fuelPerkm, email;
    Spinner vehicleType, fuelType;
    private ProgressDialog pDialog;
    InternetConnectionDetector internetDetector = new InternetConnectionDetector(this);
    HttpConnectionClass httpClass;
    JSONObject userRegisterInfoJSON = null;
    private Handler handler = new Handler();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initialize();
    }

    public void initialize() {
        registerUrl = getString(R.string.server_address)
                + "api/api_register/register";

        name = findViewById(R.id.etName);
        password = findViewById(R.id.etPassword);
        conpassword = findViewById(R.id.etConPassword);
        mobile = findViewById(R.id.etMobile);
        email = findViewById(R.id.etEmail);
        vehicleType = findViewById(R.id.vehicleType);
        vehicleName = findViewById(R.id.etVehicleName);
        fuelType = findViewById(R.id.fuelType);
        fuelPerkm = findViewById(R.id.etFuelPerkm);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("loading...");
        pDialog.setCancelable(false);

        httpClass = new HttpConnectionClass(RegisterActivity.this);
    }

    public void register(View v) {
        if (internetValidation() && passwordValidation()) {
            new Thread(new LoadRegisterTask()).start();
            showpDialog();
        }
    }

    public boolean internetValidation() {
        if (!internetDetector.isConnectedToInternet()) {
            new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText(getString(R.string.internetHeader))
                    .setContentText(getString(R.string.internetMessage))
                    .show();
            return false;
        }
        return true;
    }

    public boolean passwordValidation() {
        if (password.getText().toString().equals(conpassword.getText().toString())) {
            return true;
        } else {
            new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText(getString(R.string.passwordHeader))
                    .setContentText(getString(R.string.passwordMessage))
                    .show();
            return false;
        }
    }

    private class LoadRegisterTask implements Runnable {


        LoadRegisterTask() {
        }


        @Override
        public void run() {


            try {
                URL url = new URL(registerUrl); // here is your URL path

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("user_name", name.getText().toString());
                postDataParams.put("password", password.getText().toString());
                postDataParams.put("monbile_no", mobile.getText().toString());
                postDataParams.put("email", email.getText().toString());
                postDataParams.put("fuel_type", fuelType.getSelectedItem().toString());
                postDataParams.put("vehicle_type", vehicleType.getSelectedItem().toString());
                postDataParams.put("vehicle_name", vehicleName.getText().toString());
                postDataParams.put("fuel_per_km", fuelPerkm.getText().toString());

                serverResponse = httpClass.httpPostConnection(postDataParams, url);

                userRegisterInfoJSON = new JSONObject(serverResponse);

                    /*JSONObject eachObjFromJSONArray = userLoginInfoJSON
                            .getJSONObject("otp");*/


                success = userRegisterInfoJSON
                        .getString("success");
                msg = userRegisterInfoJSON
                        .getString("msg");

                Result = "";

            }catch (Exception ex) {
                Result = "Exception";
            }


            handler.post(new Runnable() {
                @Override
                public void run() {
                    hidepDialog();
                    if (Result.equals("Exception")) {
                        new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText(getString(R.string.errorHeader))
                                .setContentText(getString(R.string.errorMessage))
                                .show();
                    }  else {
                        if (success.equals("true")) {
                            successAlert();
                        } else {
                            new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText(getString(R.string.loginFailHeader))
                                    .setContentText(msg)
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

    public void successAlert() {
        new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Success!")
                .setContentText("Registration successful. Please wait...")
                .show();
        Thread delay = new Thread() {

            public void run() {

                try {
                    sleep(2000);
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    startActivity(new Intent(getBaseContext(),
                            LoginActivity.class));
                    finish();
                }
            }
        };
        delay.start();

    }
}


package project.afinal.fuelpay;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import project.afinal.fuelpay.adapter.FuelRateAdapter;
import project.afinal.fuelpay.helper.FuelRateHelper;
import project.afinal.fuelpay.library.HttpConnectionClass;
import project.afinal.fuelpay.library.InternetConnectionDetector;

/**
 * Created by ASUS on 11/24/2017.
 */

public class FuelRateActivity extends Activity {

    ListView lv;
    private Handler handler = new Handler();
    private ProgressDialog pDialog;
    JSONObject fuelRateInfoJSON = null;
    InternetConnectionDetector internetDetector = new InternetConnectionDetector(this);
    HttpConnectionClass httpClass;
    String fuelRateUrl, serverResponse, Result, weight, fuelType, amount, success;
    ArrayList<FuelRateHelper> listFuelRate = new ArrayList<FuelRateHelper>();
    private FuelRateAdapter mAdapter;
    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_station_area_search);
        initialize();
        // listViwePopulate();
    }

    public void initialize() {
        fuelRateUrl = getString(R.string.server_address)
                + "api/api_fuel_rate/fuel_rate";
        httpClass = new HttpConnectionClass(this);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("loading...");
        pDialog.setCancelable(false);
        lv = (ListView) findViewById(R.id.listView);
        search = (EditText) findViewById(R.id.search);
        search.setVisibility(View.GONE);

        if (internetValidation()) {
            new Thread(new LoadFuelRateTask()).start();
            showpDialog();
        }
    }

    private class LoadFuelRateTask implements Runnable {


        LoadFuelRateTask() {
        }


        @Override
        public void run() {


            try {
                URL url = new URL(fuelRateUrl); // here is your URL pat

                serverResponse = httpClass.httpGetConnection(url);

                fuelRateInfoJSON = new JSONObject(serverResponse);
                success = fuelRateInfoJSON
                        .getString("success");

                JSONArray eachObjFromJSONArray = fuelRateInfoJSON
                        .getJSONArray("info");

                for (int i = 0; i < eachObjFromJSONArray.length(); i++) {
                    JSONObject eachObjFromJSONOb = eachObjFromJSONArray.getJSONObject(i);

                    weight = eachObjFromJSONOb
                            .getString("weight_measurements");
                    fuelType = eachObjFromJSONOb
                            .getString("fuel_type");
                    amount = eachObjFromJSONOb
                            .getString("amount");
                    listFuelRate.add(new FuelRateHelper(
                            weight, fuelType, amount));

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
                        new SweetAlertDialog(FuelRateActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText(getString(R.string.errorHeader))
                                .setContentText(getString(R.string.errorMessage))
                                .show();
                    }  else {
                        if (success.equals("true")) {
                            mAdapter = new FuelRateAdapter(getBaseContext(),
                                    listFuelRate);
                            lv.setAdapter(mAdapter);
                        } else {
                            new SweetAlertDialog(FuelRateActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText(getString(R.string.loginFailHeader))
                                    .setContentText(getString(R.string.lofinFailMessage))
                                    .show();
                        }
                    }
                }
            });
        }

    }

    public boolean internetValidation() {
        if (!internetDetector.isConnectedToInternet()) {
            new SweetAlertDialog(FuelRateActivity.this, SweetAlertDialog.ERROR_TYPE)
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

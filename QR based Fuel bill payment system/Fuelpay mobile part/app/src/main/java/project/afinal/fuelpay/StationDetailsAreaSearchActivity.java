package project.afinal.fuelpay;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import project.afinal.fuelpay.adapter.StationDetailsAreaAdapter;
import project.afinal.fuelpay.helper.StationDetailsAreaHelper;
import project.afinal.fuelpay.library.HttpConnectionClass;
import project.afinal.fuelpay.library.InternetConnectionDetector;

/**
 * Created by ASUS on 11/24/2017.
 */

public class StationDetailsAreaSearchActivity extends Activity {

    ListView lv;
    private Handler handler = new Handler();
    private ProgressDialog pDialog;
    JSONObject detailsAreaListInfoJSON = null;
    InternetConnectionDetector internetDetector = new InternetConnectionDetector(this);
    HttpConnectionClass httpClass;
    String areaListUrl, serverResponse, Result, location, locationName, open, close, mobile,status,traffic,station_id, success;
    ArrayList<StationDetailsAreaHelper> listStationDetailsAreaDetail = new ArrayList<StationDetailsAreaHelper>();
    private StationDetailsAreaAdapter mAdapter;
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
        areaListUrl = getString(R.string.server_address)
                + "api/api_fuel_station/station_details_area";
        httpClass = new HttpConnectionClass(this);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("loading...");
        pDialog.setCancelable(false);
        lv = (ListView) findViewById(R.id.listView);
        search = (EditText) findViewById(R.id.search);
        search.setVisibility(View.GONE);


        if (internetValidation()) {
            new Thread(new LoadDetailsAreaListTask()).start();
            showpDialog();
        }
    }

    private class LoadDetailsAreaListTask implements Runnable {


        LoadDetailsAreaListTask() {
        }


        @Override
        public void run() {


            try {
                URL url = new URL(areaListUrl); // here is your URL path


                JSONObject postDataParams = new JSONObject();
                postDataParams.put("location", getIntent().getStringExtra("location"));

                serverResponse = httpClass.httpPostConnection(postDataParams, url);

                detailsAreaListInfoJSON = new JSONObject(serverResponse);
                listStationDetailsAreaDetail.clear();
                success = detailsAreaListInfoJSON
                        .getString("success");

                JSONArray eachObjFromJSONArray = detailsAreaListInfoJSON
                        .getJSONArray("info");

                for (int i = 0; i < eachObjFromJSONArray.length(); i++) {
                    JSONObject eachObjFromJSONOb = eachObjFromJSONArray.getJSONObject(i);

                    locationName = eachObjFromJSONOb
                            .getString("name");
                    location = eachObjFromJSONOb
                            .getString("location");
                    open = eachObjFromJSONOb
                            .getString("start_time");
                    close = eachObjFromJSONOb
                            .getString("end_time");
                    mobile = eachObjFromJSONOb
                            .getString("mobile_no");
                    status = eachObjFromJSONOb
                            .getString("is_active");
                    traffic = eachObjFromJSONOb
                            .getString("traffic");
                    station_id = eachObjFromJSONOb
                            .getString("station_id");
                    listStationDetailsAreaDetail.add(new StationDetailsAreaHelper(
                            locationName, location, open, close, mobile,status,traffic,station_id));

                    Log.i("each element", locationName.toString() + location.toString());
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
                        new SweetAlertDialog(StationDetailsAreaSearchActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText(getString(R.string.errorHeader))
                                .setContentText(getString(R.string.errorMessage))
                                .show();
                    }else {
                        if (success.equals("true")) {
                            mAdapter = new StationDetailsAreaAdapter(getBaseContext(),
                                    listStationDetailsAreaDetail);
                            lv.setAdapter(mAdapter);
                        } else {
                            new SweetAlertDialog(StationDetailsAreaSearchActivity.this, SweetAlertDialog.ERROR_TYPE)
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
            new SweetAlertDialog(StationDetailsAreaSearchActivity.this, SweetAlertDialog.ERROR_TYPE)
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

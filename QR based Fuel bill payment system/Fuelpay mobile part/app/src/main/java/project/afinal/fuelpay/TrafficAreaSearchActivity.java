package project.afinal.fuelpay;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import project.afinal.fuelpay.adapter.StationAreaAdapter;
import project.afinal.fuelpay.helper.StationAreaHelper;
import project.afinal.fuelpay.library.HttpConnectionClass;
import project.afinal.fuelpay.library.InternetConnectionDetector;

public class TrafficAreaSearchActivity extends Activity {

    private Handler handler = new Handler();
    private ProgressDialog pDialog;
    JSONObject areaListInfoJSON = null;
    HttpConnectionClass httpClass;
    String areaListUrl, serverResponse, Result, location, success, station_id;
    InternetConnectionDetector internetDetector = new InternetConnectionDetector(this);
    ArrayList<StationAreaHelper> listStationAreaDetail = new ArrayList<StationAreaHelper>();
    private StationAreaAdapter mAdapter;
    ListView lv;
    EditText search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_station_area_search);
        initialize();
        listViwePopulate();
    }

    public void initialize() {
        areaListUrl = getString(R.string.server_address)
                + "api/api_fuel_station/station_area";
        httpClass = new HttpConnectionClass(this);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("loading...");
        pDialog.setCancelable(false);
        lv = (ListView) findViewById(R.id.listView);
        search = (EditText) findViewById(R.id.search);

        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mAdapter.getFilter().filter(s.toString());
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        if (internetValidation()) {
            new Thread(new LoadAreaListTask()).start();
            showpDialog();
        }
    }

    public void listViwePopulate() {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                String location = listStationAreaDetail.get(position).getLocation();

                Bundle localBundle = new Bundle();
                localBundle.putString("location", location);

                Intent rowntent = new Intent(TrafficAreaSearchActivity.this,
                        TrafficAreaSearchDetailsActivity.class);
                rowntent.putExtras(localBundle);
                startActivity(rowntent);
            }
        });
    }

    private class LoadAreaListTask implements Runnable {


        LoadAreaListTask() {
        }


        @Override
        public void run() {


            try {
                URL url = new URL(areaListUrl); // here is your URL path


                serverResponse = httpClass.httpGetConnection(url);

                areaListInfoJSON = new JSONObject(serverResponse);
                success = areaListInfoJSON
                        .getString("success");
                if(success.equals("true")) {
                    if(!listStationAreaDetail.isEmpty()){
                        listStationAreaDetail.clear();
                    }
                    JSONArray eachObjFromJSONArray = areaListInfoJSON
                            .getJSONArray("info");

                    for (int i = 0; i < eachObjFromJSONArray.length(); i++) {
                        JSONObject eachObjFromJSONOb = eachObjFromJSONArray.getJSONObject(i);


                        station_id = eachObjFromJSONOb
                                .getString("station_id");
                        location = eachObjFromJSONOb
                                .getString("location");
                        listStationAreaDetail.add(new StationAreaHelper(location, station_id));

                        Log.i("each element", station_id.toString() + location.toString());
                    }
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
                        new SweetAlertDialog(TrafficAreaSearchActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText(getString(R.string.errorHeader))
                                .setContentText(getString(R.string.errorMessage))
                                .show();
                    } else {
                        if (success.equals("true")) {
                            mAdapter = new StationAreaAdapter(TrafficAreaSearchActivity.this,
                                    listStationAreaDetail);
                            lv.setAdapter(mAdapter);
                        } else {
                            new SweetAlertDialog(TrafficAreaSearchActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText(getString(R.string.failHeader))
                                    .setContentText(getString(R.string.failMessage))
                                    .show();
                        }
                    }
                }
            });
        }

    }

    public boolean internetValidation() {
        if (!internetDetector.isConnectedToInternet()) {
            new SweetAlertDialog(TrafficAreaSearchActivity.this, SweetAlertDialog.ERROR_TYPE)
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

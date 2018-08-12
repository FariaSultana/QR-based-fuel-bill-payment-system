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
import project.afinal.fuelpay.adapter.TransactionAdapter;
import project.afinal.fuelpay.helper.TransactionsHelper;
import project.afinal.fuelpay.library.HttpConnectionClass;
import project.afinal.fuelpay.library.InternetConnectionDetector;
import project.afinal.fuelpay.library.SharedPreferencesClass;

/**
 * Created by ASUS on 1/17/2018.
 */

public class TransactionActivity extends Activity {

    ListView lv;
    private Handler handler = new Handler();
    private ProgressDialog pDialog;
    JSONObject transactionListInfoJSON = null;
    InternetConnectionDetector internetDetector = new InternetConnectionDetector(this);
    HttpConnectionClass httpClass;
    String transactionListUrl, serverResponse, Result, create_dateTime,stationName, amount, toAcc, success;
    ArrayList<TransactionsHelper> listTransactionDetail = new ArrayList<TransactionsHelper>();
    private TransactionAdapter mAdapter;
    SharedPreferencesClass storePreference;
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
        transactionListUrl = getString(R.string.server_address)
                + "api/api_transaction/transaction";
        httpClass = new HttpConnectionClass(this);
        storePreference = new SharedPreferencesClass(getApplicationContext());
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("loading...");
        pDialog.setCancelable(false);
        lv = (ListView) findViewById(R.id.listView);
        search = (EditText) findViewById(R.id.search);
        search.setVisibility(View.GONE);

        if (internetValidation()) {
            new Thread(new LoadTransactionListUrlTask()).start();
            showpDialog();
        }
    }

    private class LoadTransactionListUrlTask implements Runnable {


        LoadTransactionListUrlTask() {
        }


        @Override
        public void run() {


            try {
                URL url = new URL(transactionListUrl); // here is your URL path


                JSONObject postDataParams = new JSONObject();
                postDataParams.put("accountNumber", storePreference.getString("accountNumber"));

                serverResponse = httpClass.httpPostConnection(postDataParams, url);

                transactionListInfoJSON = new JSONObject(serverResponse);
                success = transactionListInfoJSON
                        .getString("success");
                listTransactionDetail.clear();
                if(success.equals("true")) {
                    JSONArray eachObjFromJSONArray = transactionListInfoJSON
                            .getJSONArray("info");

                    for (int i = 0; i < eachObjFromJSONArray.length(); i++) {
                        JSONObject eachObjFromJSONOb = eachObjFromJSONArray.getJSONObject(i);

                        toAcc = eachObjFromJSONOb
                                .getString("toAcc");
                        amount = eachObjFromJSONOb
                                .getString("amount");
                        create_dateTime = eachObjFromJSONOb
                                .getString("create_dateTime");
                        stationName = eachObjFromJSONOb
                                .getString("stationName");

                        listTransactionDetail.add(new TransactionsHelper(
                                toAcc, amount, create_dateTime, stationName));

                        //Log.i("each element", locationName.toString() + location.toString());
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
                        new SweetAlertDialog(TransactionActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText(getString(R.string.errorHeader))
                                .setContentText(getString(R.string.errorMessage))
                                .show();
                    }  else {
                        if (!listTransactionDetail.isEmpty()) {
                            mAdapter = new TransactionAdapter(getBaseContext(),
                                    listTransactionDetail);
                            lv.setAdapter(mAdapter);
                        } else {
                            new SweetAlertDialog(TransactionActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText(getString(R.string.loginFailHeader))
                                    .setContentText("No transaction yet")
                                    .show();
                        }
                    }
                }
            });
        }

    }

    public boolean internetValidation() {
        if (!internetDetector.isConnectedToInternet()) {
            new SweetAlertDialog(TransactionActivity.this, SweetAlertDialog.ERROR_TYPE)
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

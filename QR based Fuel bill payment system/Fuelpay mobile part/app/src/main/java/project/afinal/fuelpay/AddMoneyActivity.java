package project.afinal.fuelpay;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import project.afinal.fuelpay.adapter.AddMoneyHistoryAdapter;
import project.afinal.fuelpay.helper.AddMoneyHistoryHelper;
import project.afinal.fuelpay.library.HttpConnectionClass;
import project.afinal.fuelpay.library.InternetConnectionDetector;
import project.afinal.fuelpay.library.SharedPreferencesClass;

/**
 * Created by ASUS on 1/15/2018.
 */

public class AddMoneyActivity extends Activity {

    String addMoneyUrl = "", addMoneyHistoryUrl = "", serverResponse = "", Result = "", success = "", msg = "",
            accountNumber = "", amount = "", status = "", dateTime = "";

    private Handler handler = new Handler();
    private ProgressDialog pDialog;
    InternetConnectionDetector internetDetector = new InternetConnectionDetector(this);
    JSONObject addMoneyInfoJSON = null;
    JSONObject addMoneyHistoryInfoJSON = null;
    HttpConnectionClass httpClass;
    SharedPreferencesClass storePreference;
    EditText etAmount;
    ArrayList<AddMoneyHistoryHelper> listAddMoneyHistoryDetail = new ArrayList<AddMoneyHistoryHelper>();
    ListView lv;
    private AddMoneyHistoryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money);
        initialize();
    }

    public void initialize() {
        addMoneyUrl = getString(R.string.server_address)
                + "api/api_add_money/addmoney";
        addMoneyHistoryUrl = getString(R.string.server_address)
                + "api/api_add_money/add_money_history";
        httpClass = new HttpConnectionClass(this);
        etAmount = findViewById(R.id.etAmount);
        lv = (ListView) findViewById(R.id.listView);

        storePreference = new SharedPreferencesClass(getApplicationContext());
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("loading...");
        pDialog.setCancelable(false);

        addMoneyHistory();
    }

    public void addMoneyHistory() {
        if (internetValidation()) {
            new Thread(new LoadMoneyHistoryTask()).start();
            showpDialog();
        }
    }


    public void addMoney(View v) {
        if (internetValidation()) {
            new Thread(new LoadAddMoneyTask()).start();
            showpDialog();
        }
    }

    private class LoadMoneyHistoryTask implements Runnable {


        LoadMoneyHistoryTask() {
        }


        @Override
        public void run() {


            try {
                URL url = new URL(addMoneyHistoryUrl); // here is your URL path


                JSONObject postDataParams = new JSONObject();
                postDataParams.put("accountNumber", storePreference.getString("accountNumber"));

                serverResponse = httpClass.httpPostConnection(postDataParams, url);

                addMoneyHistoryInfoJSON = new JSONObject(serverResponse);
                success = addMoneyHistoryInfoJSON
                        .getString("success");
                if (success.equals("true")) {
                    JSONArray eachObjFromJSONArray = addMoneyHistoryInfoJSON
                            .getJSONArray("info");

                    listAddMoneyHistoryDetail.clear();

                    for (int i = 0; i < eachObjFromJSONArray.length(); i++) {
                        JSONObject eachObjFromJSONOb = eachObjFromJSONArray.getJSONObject(i);

                        accountNumber = eachObjFromJSONOb
                                .getString("accountNumber");
                        amount = eachObjFromJSONOb
                                .getString("amount");
                        status = eachObjFromJSONOb
                                .getString("status");
                        dateTime = eachObjFromJSONOb
                                .getString("dateTime");

                        listAddMoneyHistoryDetail.add(new AddMoneyHistoryHelper(
                                accountNumber, amount, status, dateTime));


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
                        new SweetAlertDialog(AddMoneyActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText(getString(R.string.errorHeader))
                                .setContentText(getString(R.string.errorMessage))
                                .show();
                    } else if (listAddMoneyHistoryDetail.isEmpty()) {
                        new SweetAlertDialog(AddMoneyActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText(getString(R.string.errorHeader))
                                .setContentText("No add money request found yet.")
                                .show();
                    } else {
                        if (success.equals("true")) {
                            mAdapter = new AddMoneyHistoryAdapter(getBaseContext(),
                                    listAddMoneyHistoryDetail);
                            lv.setAdapter(mAdapter);
                        } else {
                            new SweetAlertDialog(AddMoneyActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText(getString(R.string.loginFailHeader))
                                    .setContentText("No data found")
                                    .show();
                        }
                    }
                }
            });
        }

    }


    private class LoadAddMoneyTask implements Runnable {


        LoadAddMoneyTask() {
        }


        @Override
        public void run() {


            try {
                URL url = new URL(addMoneyUrl); // here is your URL path

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("amount", etAmount.getText().toString());
                postDataParams.put("accountNumber", storePreference.getString("accountNumber"));
                // Log.e("params", postDataParams.toString());

                serverResponse = httpClass.httpPostConnection(postDataParams, url);

                addMoneyInfoJSON = new JSONObject(serverResponse);


                success = addMoneyInfoJSON
                        .getString("success");
                msg = addMoneyInfoJSON
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
                        new SweetAlertDialog(AddMoneyActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText(getString(R.string.errorHeader))
                                .setContentText(getString(R.string.errorMessage))
                                .show();
                    }  else {
                        if (success.equals("true")) {
                            addMoneyHistory();
                            new SweetAlertDialog(AddMoneyActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Success!")
                                    .setContentText(msg)
                                    .show();
                        } else {
                            new SweetAlertDialog(AddMoneyActivity.this, SweetAlertDialog.ERROR_TYPE)
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

    public boolean internetValidation() {
        if (!internetDetector.isConnectedToInternet()) {
            new SweetAlertDialog(AddMoneyActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText(getString(R.string.internetHeader))
                    .setContentText(getString(R.string.internetMessage))
                    .show();
            return false;
        }
        return true;
    }

}

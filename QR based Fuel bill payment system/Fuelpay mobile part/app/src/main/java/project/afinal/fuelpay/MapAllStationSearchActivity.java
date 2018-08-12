package project.afinal.fuelpay;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

import cn.pedant.SweetAlert.SweetAlertDialog;
import project.afinal.fuelpay.helper.LocationHelper;
import project.afinal.fuelpay.library.HttpConnectionClass;
import project.afinal.fuelpay.library.InternetConnectionDetector;
import project.afinal.fuelpay.library.SharedPreferencesClass;

public class MapAllStationSearchActivity extends FragmentActivity implements OnMapReadyCallback,
        OnInfoWindowClickListener, OnMyLocationButtonClickListener {

    private ProgressBar mProgressBar;
    private final Handler handler = new Handler();
    private int mDelay = 500;

    private static final String TAG_BRANCH = "branch";

    private static final String KEY_BRANCHNAME = "branchName";

    private static String KEY_LONGITUDE = "longitude";
    private static String KEY_LATIUDE = "latitude";
    private static String KEY_ADDRESS = "addressLine1";
    private static String KEY_ADDRESS2 = "addressLine2";
    private static String KEY_CITY = "city";

    public ArrayList<LocationHelper> finalLocationList = new ArrayList<LocationHelper>();


    JSONArray catagoriesArray = null;

    private GoogleMap mMap;
    ArrayList<LatLng> allPlaces = new ArrayList<LatLng>();
    ArrayList<String> branchNameArrayList = new ArrayList<String>();
    ArrayList<String> branchLatitudeArrayList = new ArrayList<String>();
    ArrayList<String> branchLongitudeArrayList = new ArrayList<String>();

    private Context mContext;

    // Alert dialog manager
    //AlertDialogManager alert = new AlertDialogManager();
    String Result = "";
    InternetConnectionDetector internetDetector;
    SharedPreferencesClass storePreference;
    LocationManager lm = null;
    private ProgressDialog pDialog;
    AutoCompleteTextView autoCompleteTextView;
    ImageView search;
    String branch, lat_s, lon_s, mapUrl;
    double lat, lon;

    HttpConnectionClass httpClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_map_all_search);

        mapUrl = getString(R.string.server_address)
                + "api/api_fuel_station/station_map";

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Map is loading...");
        pDialog.setCancelable(false);
        showpDialog();

        httpClass = new HttpConnectionClass(this);

        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        search = (ImageView) findViewById(R.id.search);

		/*CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(23.7505129, 90.3950225)).zoom(7).build();*/
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        if (mMap == null) {
            //mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
            /*mMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));*/
        }
        internetDetector = new InternetConnectionDetector(this);
        storePreference = new SharedPreferencesClass(this);
        storePreference.putString("buttonAction", "1");

//		tag = getIntent().getStringExtra("Tag");
//		module = getIntent().getStringExtra("module");
//		Log.e("Tag:", tag);

        mContext = this;

        if (lm == null)
            lm = (LocationManager) this
                    .getSystemService(Context.LOCATION_SERVICE);
        enableGPSNetwork();

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        new Thread(new LoadLocationTask()).start();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.auto_complete_text_view, branchNameArrayList);
        autoCompleteTextView.setAdapter(adapter);

        autoCompleteTextView
                .setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1,
                                            int arg2, long arg3) {

                        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        in.hideSoftInputFromWindow(arg1.getWindowToken(), 0);
                        in.hideSoftInputFromWindow(
                                arg1.getApplicationWindowToken(), 0);

                    }
                });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String st = "";
                if (autoCompleteTextView.getText().toString().length() > 1) {
                    st = autoCompleteTextView.getText().toString();
                    if (branchNameArrayList.indexOf(st) != -1) {

                        Log.e("HI", "index: " + branchNameArrayList.indexOf(st));
                        int j = branchNameArrayList.indexOf(st);

                        Log.e("search: " + st, "index: " + j);
                        branch = branchNameArrayList.get(j);
                        lat_s = branchLatitudeArrayList.get(j);
                        lon_s = branchLongitudeArrayList.get(j);

                        lat = Double.parseDouble(lat_s);
                        lon = Double.parseDouble(lon_s);

                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                .target(new LatLng(lat, lon)).zoom(16).build();
                        mMap.animateCamera(CameraUpdateFactory
                                .newCameraPosition(cameraPosition));
                    } else {
                        Toast.makeText(getApplicationContext(), "Type the Branch/location Name",
                                Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Type the Branch/location Name",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void setUpMapIfNeeded() {
        // Check if we were successful in obtaining the map.
        if (mMap != null) {
            setUpMap();
        }
    }

    private void setUpMap() {
        // Hide the zoom controls as the button panel will cover it.

        mMap.getUiSettings().setZoomControlsEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);

        retrieveLocationForMap();


        mMap.setOnInfoWindowClickListener(this);

        // Pan to see all markers in view.
        // Cannot zoom to bounds until the map has a size
        final View mapView = getSupportFragmentManager().findFragmentById(
                R.id.map).getView();
        if (mapView.getViewTreeObserver().isAlive()) {
            mapView.getViewTreeObserver().addOnGlobalLayoutListener(
                    new OnGlobalLayoutListener() {
                        // We use the new method when supported
                        @SuppressLint("NewApi")
                        // We check which build version we are using.
                                LatLngBounds.Builder builder = new LatLngBounds.Builder();

                        @SuppressWarnings("deprecation")
                        @SuppressLint("NewApi")
                        @Override
                        public void onGlobalLayout() {
                            for (LatLng place : allPlaces) {
                                builder.include(place);
                            }
                            LatLngBounds bounds = builder.build();
                            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                                mapView.getViewTreeObserver()
                                        .removeGlobalOnLayoutListener(this);
                            } else {
                                mapView.getViewTreeObserver()
                                        .removeOnGlobalLayoutListener(this);
                            }
                            CameraUpdate cu = CameraUpdateFactory
                                    .newLatLngBounds(bounds, 70);
                            // mMap.moveCamera(cu);
                            hidepDialog();
                            // mMap.animateCamera(cu);
                        }
                    });
        }
    }

    static LatLng currentPosition;
    Marker markerName = null;

    private void retrieveMyLocation() {

        Location location = mMap.getMyLocation();
        currentPosition = new LatLng(location.getLatitude(),
                location.getLongitude());
        if (markerName != null) {
            markerName.remove();
        }
        markerName = mMap.addMarker(new MarkerOptions()
                .position(currentPosition)
                .title("My Location")
                .snippet(
                        "Lat:" + location.getLatitude() + "Lng:"
                                + location.getLongitude()));
    }

    private void retrieveLocationForMap() {

        for (int i = 0; i < finalLocationList.size(); i++) {

            String placeName = finalLocationList.get(i).getCategoryName();
            Log.e("placeName", placeName);

            String latitudeString = finalLocationList.get(i).getLatitude();
            String longitudeString = finalLocationList.get(i).getLongitude();
            String address = finalLocationList.get(i).getAddress();

            Double latitude = Double.valueOf(latitudeString);
            Double longitude = Double.valueOf(longitudeString);

            LatLng place = new LatLng(latitude, longitude);
            Log.e("place LatLng", place.toString());

            allPlaces.add(place);
            branchNameArrayList.add(placeName);
            branchLatitudeArrayList.add(String.valueOf(latitude));
            branchLongitudeArrayList.add(String.valueOf(longitude));

            // Add markers to the map.
            addMarkersToMap(place, placeName, address);

        }

    }

    Marker mMarker;
    ArrayList<Marker> markers = new ArrayList<Marker>();

    private void addMarkersToMap(LatLng place, String placeName,
                                 String address) {

        StringBuilder addressBuilder = new StringBuilder(200);
        addressBuilder.append(address);
        String addressFinal = addressBuilder.toString();

        mMarker = mMap.addMarker(new MarkerOptions()
                .position(place)
                .title(placeName)
                .snippet(addressFinal)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        markers.add(mMarker);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng hcmus = new LatLng(23.7505129, 90.3950225);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hcmus, 12));
		/*originMarkers.add(mMap.addMarker(new MarkerOptions()
				.title("Đại học Khoa học tự nhiên")
				.position(hcmus)));*/

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

    private class LoadLocationTask implements Runnable {

        boolean internetchecked;

        @SuppressLint("NewApi")
        @Override
        public void run() {

            handler.post(new Runnable() {
                @Override
                public void run() {
                    mProgressBar.setVisibility(ProgressBar.VISIBLE);
                    storePreference.putString("buttonAction", "0");
                }
            });

            // Getting the boolean call back from thread to check Internet
            // status

            @SuppressWarnings({"unchecked", "rawtypes"})
            /**
             * AsyncTask to check whether Internet connection is working.
             **/
                    RunnableFuture internetStatus = new FutureTask(
                    new Callable<Boolean>() {

                        @Override
                        public Boolean call() throws Exception {
                            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                            NetworkInfo netInfo = cm.getActiveNetworkInfo();
                            if (netInfo != null && netInfo.isConnected()) {
                                try {
                                    URL url = new URL("http://www.google.com");
                                    HttpURLConnection urlc = (HttpURLConnection) url
                                            .openConnection();
                                    urlc.setConnectTimeout(3000);
                                    urlc.connect();
                                    if (urlc.getResponseCode() == 200
                                            || urlc.getResponseCode() == 302) {
                                        return true;
                                    }
                                } catch (MalformedURLException e1) {
                                    e1.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            return false;
                        }
                    });
            // start the thread to execute it
            new Thread(internetStatus).start();
            try {
                // Get the result
                internetchecked = (Boolean) internetStatus.get();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            if (internetchecked) {
                try {

                    URL url = new URL(mapUrl); // here is your URL pat

                    String serverResponse = httpClass.httpGetConnection(url);

                    JSONObject JSONObjecttt = new JSONObject(serverResponse);
                    catagoriesArray = JSONObjecttt
                            .getJSONArray("info");

                    if (!finalLocationList.isEmpty()) {
                        finalLocationList.clear();
                    }

                    for (int i = 0; i < catagoriesArray.length(); i++) {

                        JSONObject eachObjFromJSONArray = catagoriesArray
                                .getJSONObject(i);
                        Log.e("Number " + i + "JSONArray",
                                eachObjFromJSONArray.toString());

                        String branchName = eachObjFromJSONArray
                                .getString("name");
                        String latitude = eachObjFromJSONArray
                                .getString("latitude");
                        String longitude = eachObjFromJSONArray
                                .getString("longitude");
                        String address = eachObjFromJSONArray
                                .getString("location");

                        finalLocationList.add(new LocationHelper(branchName,
                                latitude, longitude, address));

                    }


                } catch (Exception ex) {
                    Result = "Exception";
                }
            } else {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // Internet Connection is not present
                        new SweetAlertDialog(MapAllStationSearchActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText(getString(R.string.internetHeader))
                                .setContentText(getString(R.string.internetMessage))
                                .show();

                    }
                });
            }

            handler.post(new Runnable() {
                @Override
                public void run() {
                    mProgressBar.setVisibility(ProgressBar.INVISIBLE);
                    storePreference.putString("buttonAction", "1");
                    hidepDialog();
                    if (Result.equals("Exception")) {
                        new SweetAlertDialog(MapAllStationSearchActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText(getString(R.string.errorHeader))
                                .setContentText(getString(R.string.errorMessage))
                                .show();
                    } else if (internetchecked) {
                        if (!finalLocationList.isEmpty()) {
                            showpDialog();
                            setUpMapIfNeeded();
                        } else {
                            // Internet Connection is not present
                            new SweetAlertDialog(MapAllStationSearchActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText(getString(R.string.internetHeader))
                                    .setContentText(getString(R.string.internetMessage))
                                    .show();
                        }
                    }
                }
            });

        }

    }

    private void sleep() {
        try {
            Thread.sleep(mDelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static boolean gps_enabled = false, network_enabled = false;

    @Override
    public boolean onMyLocationButtonClick() {
        // Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT)
        // .show();
        enableGPSNetwork();

        if (gps_enabled && network_enabled) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    retrieveMyLocation();
                }
            }, 3000);

        } else {
            // Internet Connection is not present
            // Internet Connection is not present
            new SweetAlertDialog(MapAllStationSearchActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Enable Location Settings")
                    .setContentText("Please Enable your GPS Settings")
                    .show();

        }
        // Return false so that we don't consume the event and the default
        // behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();

        enableGPSNetwork();

    }


    @Override
    public void onInfoWindowClick(Marker marker) {
        // Toast.makeText(this, "Click Info Window", Toast.LENGTH_SHORT).show();

    }

    public void enableGPSNetwork() {

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            network_enabled = lm
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }
    }

    @Override
    public void onBackPressed() {
        if ((storePreference.getString("buttonAction").equals("1"))
                || (!internetDetector.isConnectedToInternet())) {
            Intent intent = new Intent(getApplicationContext(),
                    HomeMainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(getApplicationContext(),
                    "Please wait while content is loading", Toast.LENGTH_LONG)
                    .show();
        }
    }

    public void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    public void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}

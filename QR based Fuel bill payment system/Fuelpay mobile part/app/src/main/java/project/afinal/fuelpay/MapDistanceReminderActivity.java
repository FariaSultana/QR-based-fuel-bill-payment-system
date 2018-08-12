package project.afinal.fuelpay;

import android.Manifest;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import project.afinal.fuelpay.library.DirectionsJSONParser;
import project.afinal.fuelpay.library.SharedPreferencesClass;

import static android.graphics.Color.BLUE;

public class MapDistanceReminderActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    private Button button;
    private TextView txtDistance, txtTime,currentFuel,currentKm,remining;
    private GoogleApiClient googleApiClient;
    private LocationRequest mLocationRequest;
    ArrayList markerPoints = new ArrayList();
    LinearLayout layout;
    String getFuel;
    int getKm;
    SharedPreferencesClass storePreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance_reminder);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        storePreference = new SharedPreferencesClass(getApplicationContext());

        layout =findViewById(R.id.layout1);

        button = (Button) findViewById(R.id.button);
        txtDistance = (TextView) findViewById(R.id.distance);
        txtTime = (TextView) findViewById(R.id.time);
        currentFuel = (TextView) findViewById(R.id.currentFuel);
        currentKm = (TextView) findViewById(R.id.currentKm);
        remining = (TextView) findViewById(R.id.remining);

        getFuel = getIntent().getStringExtra("currentFuel");
        currentFuel.setText(getFuel+" Litre");

        getKm = Integer.parseInt(getFuel) * Integer.parseInt(storePreference.getString("fuel_per_km"));
        currentKm.setText(getKm+" Km");

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrentLocation();
            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        CameraPosition cameraPosition = new CameraPosition.Builder().target(
                new LatLng(23.768748, 90.425642)).zoom(16).build();
        MarkerOptions marker = (new MarkerOptions().position(new LatLng(23.768748, 90.425642)).title("Marker in my location"));
        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        markerPoints.add(new LatLng(23.768748, 90.425642));
        mMap.addMarker(marker);
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                addPath(latLng);
            }
        });
    }

    private void addPath(LatLng latLng) {
        if (markerPoints.size() > 1) {
            markerPoints.clear();
            mMap.clear();
            txtTime.setText("0 sec");
            txtDistance.setText("0 Km");
            layout.setVisibility(View.GONE);
        }

        // Adding new item to the ArrayList
        markerPoints.add(latLng);

        // Creating MarkerOptions
        MarkerOptions options = new MarkerOptions();

        // Setting the position of the marker
        options.position(latLng);

        if (markerPoints.size() == 1) {
            options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
        } else if (markerPoints.size() == 2) {
            options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        }

        // Add new marker to the Google Map Android API V2
        mMap.addMarker(options);

        // Checks, whether start and end locations are captured
        if (markerPoints.size() >= 2) {
            LatLng origin = (LatLng) markerPoints.get(0);
            LatLng dest = (LatLng) markerPoints.get(1);

            // Getting URL to the Google Directions API
            String url = getDirectionsUrl(origin, dest);
            Log.d("DistanceMapsActivity", "getDirectionsUrl: " + url);
            Toast.makeText(this, url, Toast.LENGTH_SHORT).show();
            DownloadTask downloadTask = new DownloadTask();

            // Start downloading json data from Google Directions API
            downloadTask.execute(url);
        }
    }

    public void getCurrentLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if (location == null) {
                LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, mLocationRequest, this);
            } else {
                moveMap(location);
            }
        }
    }


    private void moveMap(Location location) {

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);


        Toast.makeText(this, latitude + "" + longitude, Toast.LENGTH_LONG).show();
        addPath(latLng);
    }

    private class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            String data = "";

            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();


            parserTask.execute(result);

        }
    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList points = null;
            PolylineOptions lineOptions = new PolylineOptions();

            lineOptions.width(8);
            lineOptions.color(BLUE);
            MarkerOptions markerOptions = new MarkerOptions();

            points = new ArrayList();
            try {
                List<HashMap<String, String>> path = result.get(0);
                List<HashMap<String, String>> distance = result.get(1);
                List<HashMap<String, String>> duration = result.get(2);

                // To draw path between two location
                drawPath(path);
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                lineOptions.addAll(points);
                lineOptions.geodesic(true);

                // To count distance between two location
                int totalDistance = 0;
                for (int j = 0; j < distance.size(); j++) {
                    HashMap<String, String> point = distance.get(j);

                    int dist = Integer.parseInt(point.get("distance"));
                    totalDistance = totalDistance + dist;
                }
                String totalDist =String.valueOf(totalDistance / 1000 + "Km");
                txtDistance.setText(totalDist);

                //for visible info layout
                layout.setVisibility(View.VISIBLE);
                int finalDist = totalDistance/1000;
                if(getKm>finalDist) {
                    int existFuel = getKm-finalDist;
                    layout.setBackgroundColor(getResources().getColor(R.color.success));
                    remining.setText("You can drive "+existFuel+" Km more after arriving your destination.");
                }else{
                    int existFuel = finalDist-getKm;
                    layout.setBackgroundColor(getResources().getColor(R.color.warning));
                    remining.setText("You want to drive "+finalDist+" km but need fuel for "+existFuel+" Km extra");
                }
                // To count duration between two location
                int totalDuration = 0;
                for (int k = 0; k < duration.size(); k++) {
                    HashMap<String, String> point = duration.get(k);

                    int dist = Integer.parseInt(point.get("duration"));
                    totalDuration = totalDistance + dist;
                }
                convertSecondToHour(totalDuration);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }

            // for covering the path drawn in display
            boolean hasPoints = false;
            Double maxLat = null, minLat = null, minLon = null, maxLon = null;

            if (lineOptions != null && lineOptions.getPoints() != null) {
                List<LatLng> pts = lineOptions.getPoints();
                for (LatLng coordinate : pts) {
                    // Find out the maximum and minimum latitudes & longitudes
                    // Latitude
                    maxLat = maxLat != null ? Math.max(coordinate.latitude, maxLat) : coordinate.latitude;
                    minLat = minLat != null ? Math.min(coordinate.latitude, minLat) : coordinate.latitude;

                    // Longitude
                    maxLon = maxLon != null ? Math.max(coordinate.longitude, maxLon) : coordinate.longitude;
                    minLon = minLon != null ? Math.min(coordinate.longitude, minLon) : coordinate.longitude;

                    hasPoints = true;
                }
            }

            if (hasPoints) {
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                builder.include(new LatLng(maxLat, maxLon));
                builder.include(new LatLng(minLat, minLon));
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 48));
                mMap.addPolyline(lineOptions);
            }
        }
    }

    private void drawPath(List<HashMap<String, String>> path) {
    }

    private void convertSecondToHour(int totalDuration) {
        int hours = totalDuration / 3600;
        int minutes = (totalDuration % 3600) / 60;
        int seconds = (totalDuration % 3600) % 60;

        String totalTime = String.valueOf(hours) + ":" +
                String.valueOf(minutes) + ":" + String.valueOf(seconds);
        txtTime.setText(totalTime);
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";
        String mode = "mode=driving";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        return url;
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;

    }

    @Override
    protected void onStart() {
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
        super.onStart();
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
            googleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient,
                    mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, 9000);
                Log.d("DistanceMapsActivity", "onConnectionFailed: ");
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
                Log.e("DistanceMapsActivity", "onConnectionFailed: ");
            }
        } else {
            Log.i("DistanceMapsActivity", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        // moveMap(location);
    }
}

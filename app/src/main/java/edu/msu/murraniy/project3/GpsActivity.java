package edu.msu.murraniy.project3;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import edu.msu.murraniy.project3.Cloud.Cloud;
import edu.msu.murraniy.project3.Cloud.Models.LocationList;

public class GpsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";

    private MapView mMapView = null;
    private GpsView mGpsView = null;
    private GoogleMap googleMap;

    private SharedPreferences settings = null;
    private final static String TO = "to";
    private final static String TOLAT = "tolat";
    private final static String TOLONG = "tolong";

    private LocationManager locationManager = null;
    private ActiveListener activeListener = new ActiveListener();

    // The user's most recently updated location
    private double latitude = 0;
    private double longitude = 0;
    private boolean valid = false;

    // user ID passed here if the login process is completed
    private int userID;

    public static class LocationInfo {
        private String name = null;
        private int id = -1;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public LocationInfo(String name, int id) {
            this.name = name;
            this.id = id;
        }
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_gps);

        settings = PreferenceManager.getDefaultSharedPreferences(this);

        Bundle mapViewBundle = null;
        if (bundle != null) {
            mapViewBundle = bundle.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mMapView = (MapView) findViewById(R.id.mapView);
        mMapView.onCreate(mapViewBundle);
        mMapView.getMapAsync(this);

        // Get the location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //mGpsView = (GpsView)findViewById(GpsView.generateViewId());

        // get the user ID
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userID = extras.getInt("userID");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);

        //getPuzzleView().saveInstanceState(bundle);
    }

    /**
     * Get the gps view
     * @return GpsView reference
     */
    //private GpsView getGpsView() {
    //return (GpsView)this.findViewById(R.id.gpsView);
    //}

    // Handle the I WAS HERE button click
    public void onHere(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Cloud cloud = new Cloud();
                final LocationInfo locInfo;
                try {
                    locInfo = cloud.checkHere(latitude, longitude);

                    if (locInfo == null) {
                        /*
                         * If validation fails, display a toast
                         */
                        view.post(new Runnable() {

                            @Override
                            public void run() {
                                Toast.makeText(view.getContext(),
                                        R.string.check_fail,
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        // will need to activate putting in your own comment here was well
                        grabComments(view, locInfo.getId());
                    }
                } catch (Exception e) {
                    // Error condition! Something went wrong
                    Log.e("onHere", "Something went with attempting a here claim", e);
                    view.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(view.getContext(), R.string.check_fail, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }

    // Gets the comments of a location after a successful HERE check
    // called inside of a thread already so no further threading needed
    public void grabComments(View view, int locId) {
        // Get the comment list view
        ListView commentList = (ListView) view.findViewById(R.id.commentList);
        final Cloud.CommentCatalogAdapter adapter = new Cloud.CommentCatalogAdapter(commentList);
        adapter.setLocId(locId);
        commentList.setAdapter(adapter);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;

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
        googleMap.setMyLocationEnabled(true);

        //Create a new custom icon for the marker
        int height = 100;
        int width = 100;
        BitmapDrawable bDraw = (BitmapDrawable)getResources().getDrawable(R.drawable.green_marker);
        Bitmap b = bDraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

        new Thread(new Runnable() {
            @Override
            public void run() {

                Cloud cloud = new Cloud();
                try {
                    LocationList locationsList = cloud.getLocations();

                    for(edu.msu.murraniy.project3.Cloud.Models.Location locations : locationsList.getItems()){

                        // Create the marker based on locations from the cloud
                        LatLng latlng = new LatLng(locations.getLat(), locations.getLng());
                        MarkerOptions marker = new MarkerOptions();
                        marker.title(locations.getName());
                        marker.position(latlng);

                        // Set the new icon
                        marker.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                googleMap.addMarker(marker);
                            }
                        });
                    }
                }catch (Exception e){
                    Log.e("GetLocations", "Something went wrong when getting the locations", e);
                    mMapView.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mMapView.getContext(), R.string.location_fail, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();

    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();

        registerListeners();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onPause() {
        unregisterListeners();

        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    private void onLocation(Location location) {
        if(location == null) {
            return;
        }

        latitude = location.getLatitude();
        longitude = location.getLongitude();
        valid = true;

        //setUI();
    }

    private void registerListeners() {
        unregisterListeners();

        // Create a Criteria object
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_HIGH);
        criteria.setAltitudeRequired(true);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(false);
        String bestAvailable = locationManager.getBestProvider(criteria, true);
        if(bestAvailable != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.requestLocationUpdates(bestAvailable, 500, 1, activeListener);
            Location location = locationManager.getLastKnownLocation(bestAvailable);
            onLocation(location);
        }

    }

    private void unregisterListeners() {
        locationManager.removeUpdates(activeListener);
    }

    private class ActiveListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            onLocation(location);
        }

        @Override
        public void onStatusChanged(String s, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {
            registerListeners();
        }
    };
}

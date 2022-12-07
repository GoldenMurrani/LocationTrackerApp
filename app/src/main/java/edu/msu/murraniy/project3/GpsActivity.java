package edu.msu.murraniy.project3;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GpsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";

    private MapView mMapView = null;
    private GpsView mGpsView = null;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_gps);

        Bundle mapViewBundle = null;
        if (bundle != null) {
            mapViewBundle = bundle.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mMapView = (MapView)findViewById(R.id.mapView);
        mMapView.onCreate(mapViewBundle);
        mMapView.getMapAsync(this);

        //mGpsView = (GpsView)findViewById(R.id.gpsView);

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

    }

    @Override
    public void onMapReady(GoogleMap map) {

    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onPause() {
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
}

package com.example.singh.whereami;

import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class
        map extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void showCurrentLocation(Location location, Object latlng){

        String msgg="";
        mMap.clear();

        Geocoder g=new Geocoder(getBaseContext(),Locale.getDefault());
        List<Address> lst = null;

        try {
            lst = g.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            for(Address a:lst){
                msgg=a.getAddressLine(1);
            }

        } catch (IOException ioException) {
           ioException.printStackTrace();
        }


        LatLng currentPosition = new LatLng(location.getLatitude(),location.getLongitude());



            mMap.addMarker(new MarkerOptions()
                .position(currentPosition)
                .snippet("You Are At: "+msgg)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.mark))
                .flat(true)
                .title("I'm here!"));


        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 18));
    }
    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {


        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);

        String provider = locationManager.getBestProvider(criteria, true);
        LocationListener locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                //showCurrentLocation(location, null);

            }
            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
            }
            @Override
            public void onProviderEnabled(String s) {
            }
            @Override
            public void onProviderDisabled(String s) {
            }
        };

        locationManager.requestLocationUpdates(provider, 2000, 0, locationListener);

        // Getting initial Location
        Location location = locationManager.getLastKnownLocation(provider);
        // Show the initial location
        if(location != null)
        {
            showCurrentLocation(location, null);
        }
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }
}

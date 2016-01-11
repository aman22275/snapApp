package com.example.singh.whereami;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MainActivity extends ActionBarActivity {
    private GoogleMap map; // Might be null if Google Play services APK is not available.

    ImageView iv;
//    ImageButton ib1,ib2,ib3;
    Location location=null;

    double lat;
    double lon;
    String link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv=(ImageView)findViewById(R.id.iv);
        iv.setImageResource(R.mipmap.loc);

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        if (map != null) {

     setUpMap();

        }


    }

    private void setUpMap() {


        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);

        String provider = locationManager.getBestProvider(criteria, true);
        LocationListener locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                showCurrentLocation(location, null);

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
        map.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }



    private void showCurrentLocation(Location location, Object latlng){

        String msgg="";
        map.clear();

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



        map.addMarker(new MarkerOptions()
                .position(currentPosition)
                .snippet("You Are At: "+msgg)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.mark))
                .flat(true)
                .title("I'm here!"));


        // Zoom in, animating the camera.
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 15));
        //map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
    }

    public void send(View v) {
        String p1, p2, p3, p4, p5;
        SharedPreferences sp = getSharedPreferences("mydata", MODE_PRIVATE);
  /*      LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);

        String provider = locationManager.getBestProvider(criteria, true);


        LocationListener locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location loc) {
                //showCurrentLocation(location, null);
                location = loc;
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

        //


        locationManager.requestLocationUpdates(provider, 0, 0, locationListener);

        // Getting initial Location
        location = locationManager.getLastKnownLocation(provider);
        // Show the initial location

        String msgg = "";
        if (location != null) {

            Geocoder g = new Geocoder(getBaseContext(), Locale.getDefault());
            List<Address> lst = null;

            try {
                lst = g.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                for (Address a : lst) {
                    msgg = a.getAddressLine(1);
                }

            } catch (IOException ioException) {
                ioException.printStackTrace();
                String network = "No location Available";
                Toast.makeText(getBaseContext(), network, Toast.LENGTH_LONG).show();

            }
//            Toast.makeText(getBaseContext(), msgg, Toast.LENGTH_LONG).show();

        } else
            Toast.makeText(getBaseContext(), "location empty", Toast.LENGTH_LONG).show();
*/

        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();

        p1 = sp.getString("phone1", "");
        p2 = sp.getString("phone2", "");
        p3 = sp.getString("phone3", "");
       /* p4=sp.getString("phone4","");
        p5=sp.getString("phone5","");
*/
        //int i;
        final String[] num = new String[5];
        num[0] = p1;
        num[1] = p2;
        num[2] = p3;


        SmsManager s = SmsManager.getDefault();

        if (num[0] == "") {

        } else {
            lat = location.getLatitude();
            lon = location.getLongitude();
            link = "location" + "\n" +  "Your relative is at " + "\nhttp://www.google.com/maps/place/" + latitude + "," + longitude + "\nCall on his/her Number its an emergency";
            s.sendTextMessage(num[0], null, link, null, null);
            //  s.sendTextMessage("7838079707", null, "hello sir", null, null);

            //}
        }
    }
/*
        if(num[1]==""){

        }else {
            lat=location.getLatitude();
            lon=location.getLongitude();
            link="location"+"\n"+msgg+"Your relative is at "+"\nhttp://www.google.com/maps/place/"+lat+","+lon+"\nCall on his/her Number its an emergency";
            s.sendTextMessage(num[1], null, link, null, null);
        }


        if(num[2]==""){

        }else {
            lat=location.getLatitude();
            lon=location.getLongitude();
            link="location"+"\n"+msgg+"Your relative is at "+"\nhttp://www.google.com/maps/place/"+lat+","+lon+"\nCall on his/her Number its an emergency";
            s.sendTextMessage(num[2], null, link, null, null);
        }

    }//send method ends here
*/
    public void update(View v){
        Intent i=new Intent(this,update.class);
        startActivity(i);
    }


    public void where(View v){

        Intent i=new Intent(this,map.class);
        startActivity(i);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

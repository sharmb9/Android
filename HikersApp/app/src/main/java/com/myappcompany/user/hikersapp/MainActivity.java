package com.myappcompany.user.hikersapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    LocationManager locationManager;
    LocationListener locationListener;

    TextView latitudeView;
    TextView longitudeView;
    TextView accuView;
    TextView altView;
    TextView addressView;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==1){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 10, locationListener);
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        latitudeView= (TextView) findViewById(R.id.latitudeView);
        longitudeView= (TextView) findViewById(R.id.longitudeView);
        accuView= (TextView) findViewById(R.id.accuView);
        altView= (TextView) findViewById(R.id.altView);
        addressView= (TextView) findViewById(R.id.addressView);


        locationManager= (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener= new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.i("My Location", location.toString());

                String latitude= String.valueOf(location.getLatitude());
                String longitude= String.valueOf(location.getLongitude());
                String accuracy= String.valueOf(location.getAccuracy());
                String altitude= String.valueOf(location.getAltitude());

                //reverse geocode to get address of given location(using longitude and latitude)
                Geocoder geocoder= new Geocoder(getApplicationContext(), Locale.getDefault());
                try {
                    List<Address> addressList= geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    if (addressList != null && addressList.size() != 0){
                        //strore address in string
                        String address= "";

                        if (addressList.get(0).getFeatureName()!= null){
                            address+= addressList.get(0).getFeatureName() + " ";
                        }
                        if (addressList.get(0).getThoroughfare()!= null){
                            address+= addressList.get(0).getThoroughfare() + " ";
                        }
                        if (addressList.get(0).getLocality()!= null){
                            address+= addressList.get(0).getLocality() + " ";
                        }
                        if (addressList.get(0).getAdminArea()!= null){
                            address+= addressList.get(0).getAdminArea() + " ";
                        }
                        if (addressList.get(0).getCountryCode()!= null){
                            address+= addressList.get(0).getCountryCode() + " ";
                        }
                        if (addressList.get(0).getPostalCode()!= null){
                            address+= addressList.get(0).getPostalCode();
                        }

                        latitudeView.setText("Latitude: " + latitude);
                        longitudeView.setText("Longitude: " + longitude);
                        accuView.setText("Accuracy: " + accuracy);
                        altView.setText("Altitude: " + altitude);
                        addressView.setText("Address: " + address);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }



            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        //checking if we actually have location permission otherwise ask(the dialogue box)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        //if the permission is granted
        else
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 10, locationListener);
        }

    }
}

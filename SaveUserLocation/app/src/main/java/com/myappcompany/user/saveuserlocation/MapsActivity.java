package com.myappcompany.user.saveuserlocation;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.myappcompany.user.saveuserlocation.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;

    LocationManager locationManager;
    LocationListener locationListener;

    String address= "";

    //This interface is the contract for receiving the results for permission requests.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //checking if we actually have location permission
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
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapLongClickListener(this);

        locationManager= (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener= new LocationListener() {



            @Override
            public void onLocationChanged(Location location) {

                // Add a marker at user location and move the camera
                LatLng userLocation= new LatLng(location.getLatitude(), location.getLongitude());
                mMap.addMarker(new MarkerOptions().position(userLocation).title("User Location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 14));

                //Get address of user using reverse geo-coding
                Geocoder geocoder= new Geocoder(getApplicationContext(), Locale.getDefault());



                try {
                    //put data about user location in List
                    List<Address> addressList= geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);


                    if (addressList != null && addressList.size() != 0){


                        //street address (980)
                        if (addressList.get(0).getFeatureName()!= null){
                            address+= addressList.get(0).getFeatureName() + " ";
                        }
                        //main street west
                        if (addressList.get(0).getThoroughfare()!= null){
                            address+= addressList.get(0).getThoroughfare() + " ";
                        }
                        //Hamilton
                        if (addressList.get(0).getLocality()!= null){
                            address+= addressList.get(0).getLocality() + " ";
                        }
                        //ON
                        if (addressList.get(0).getAdminArea()!= null){
                            address+= addressList.get(0).getAdminArea() + " ";
                        }
                        //CA
                        if (addressList.get(0).getCountryCode()!= null){
                            address+= addressList.get(0).getCountryCode() + " ";
                        }
                        //L8S 1B2
                        if (addressList.get(0).getPostalCode()!= null){
                            address+= addressList.get(0).getPostalCode();
                        }

                        Toast.makeText(MapsActivity.this, address, Toast.LENGTH_SHORT).show();

                    }

                }
                catch (Exception e) {
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

    @Override
    public void onMapLongClick(LatLng latLng) {
        MainActivity.address.add(address);

    }
}


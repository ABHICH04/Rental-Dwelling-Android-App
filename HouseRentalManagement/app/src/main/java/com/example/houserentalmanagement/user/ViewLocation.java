package com.example.houserentalmanagement.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.houserentalmanagement.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;

public class ViewLocation extends AppCompatActivity {

    SupportMapFragment smf_googleMap;
    FusedLocationProviderClient client;

    private static final int REQUEST_LOCATION = 100;

    Double latitude;
    Double longitude;
    String address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_location2);

        Intent intent = getIntent();
        String houseId = intent.getStringExtra("houseId");
        String noOfRoom = intent.getStringExtra("noOfRoom");
        String rentPerRoom = intent.getStringExtra("rentPerRoom");
        String houseDescription = intent.getStringExtra("houseDescription");
        String houseLocation = intent.getStringExtra("houseLocation");
        String houseImage = intent.getStringExtra("houseImage");
        String userId = intent.getStringExtra("userId");
        String ownerContact=intent.getStringExtra("ownerContact");

        address=houseLocation;

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        smf_googleMap = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_maps);
        client = LocationServices.getFusedLocationProviderClient(this);
        getMyLocation();
        latitude = 29.390945;
        longitude = 76.963501;

        try {
            buttonGetCoordinates();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(ViewLocation.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                getMyLocation();
            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
            }
        }

    }
    public void buttonGetCoordinates() throws IOException {
        Geocoder geocoder=new Geocoder(this);
        List<Address> addressList;
        try {
        addressList=geocoder.getFromLocationName(address,1);
        if(addressList!=null)
        {
            double doubleLat=addressList.get(0).getLatitude();
            double doubleLong=addressList.get(0).getLongitude();
            latitude=doubleLat;
            longitude=doubleLong;

        }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getMyLocation();
            }
        }
    }

    private void getMyLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                smf_googleMap.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(@NonNull GoogleMap googleMap) {
                        LatLng latLng = new LatLng(latitude, longitude);
                        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Your destination is here..");

                        googleMap.addMarker(markerOptions);
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 100));
                    }
                });

            }
        });
    }

}
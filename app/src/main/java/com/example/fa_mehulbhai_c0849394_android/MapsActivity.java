package com.example.fa_mehulbhai_c0849394_android;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.fa_mehulbhai_c0849394_android.databinding.ActivityMapsBinding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int REQUEST_CODE = 1;
    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    Button addLocation,satelliteMap,hybridMap,terrainMap;

    LocationListener locationListener;
    LocationManager locationManager;
    Marker marker;
    List<Marker> markerList;

    LatLng myPlace =new LatLng(43.736400,-79.259320);

    DatabaseHelperClass databaseHelperClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        addLocation = findViewById(R.id.btnAddLocation);
        hybridMap = findViewById(R.id.btnHybridMap);
        satelliteMap = findViewById(R.id.btnSatelliteMap);
        terrainMap = findViewById(R.id.btnTerrainMap);

        hybridMap.setOnClickListener(view -> mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID));

        satelliteMap.setOnClickListener(view -> mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE));

        terrainMap.setOnClickListener(view -> mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN));

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        databaseHelperClass = new DatabaseHelperClass(this);
        mMap = googleMap;

        markerList = new ArrayList<>();
        List<ModelClass> placeList = databaseHelperClass.getAllPlaces();

        markerList.add(mMap.addMarker(new MarkerOptions().position(myPlace).title("Scarborough")));

        for(Marker m : markerList){
            // Add a marker in Sydney and move the camera
            LatLng latLng = new LatLng(m.getPosition().latitude, m.getPosition().longitude);
            mMap.addMarker(new MarkerOptions().position(latLng) );
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,12));
        }

        for (Marker marker1 : markerList) {
            LatLng latLng =new LatLng(marker1.getPosition().latitude,marker1.getPosition().longitude);
            mMap.addMarker(new MarkerOptions().position(latLng));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,20));
        }

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                MarkerOptions markerOptions = new MarkerOptions().position(latLng);
                marker = mMap.addMarker(markerOptions);
                addLocation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Double lattitude = marker.getPosition().latitude;
                        Double laongitude = marker.getPosition().longitude;
                        Intent i = new Intent(MapsActivity.this, FavouritePlaceActivity.class);
                        i.putExtra("lattitude",lattitude);
                        i.putExtra("longitude",laongitude);
                        startActivity(i);
                    }
                });
            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                Intent i = new Intent(MapsActivity.this,MarkerClickActivity.class);
                i.putExtra("latitude",marker.getPosition().latitude);
                i.putExtra("longitude",marker.getPosition().longitude);
                i.putExtra("placeName",marker.getTitle());
                i.putExtra("id",marker.getZIndex());
                startActivity(i);
                return false;
            }
        });
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (REQUEST_CODE == requestCode) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListener);
            }
        }
    }


}
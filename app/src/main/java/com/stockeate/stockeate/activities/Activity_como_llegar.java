package com.stockeate.stockeate.activities;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.stockeate.stockeate.R;

public class Activity_como_llegar extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_como_llegar);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        mMap.getUiSettings().setMyLocationButtonEnabled (true);

        // Add a marker in San Francisco and move the camera
        LatLng san_francisco = new LatLng(-31.4249815, -62.0840299);
        mMap.addMarker(new MarkerOptions().position(san_francisco).title("San Francisco"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(san_francisco));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(san_francisco, 30));

        LatLng hiper = new LatLng(-31.42773493119209, -62.11414910012128);
        mMap.addMarker(new MarkerOptions().position(hiper).title("Hipermercado Anselmi"));
        LatLng pinguino = new LatLng(-31.6629403, -63.0473148);
        mMap.addMarker(new MarkerOptions().position(pinguino).title("Pingüino"));
        LatLng chapulin_castelli = new LatLng(-31.4222692, -62.0885534);
        mMap.addMarker(new MarkerOptions().position(chapulin_castelli).title("Chapulin"));
        LatLng chapulin_9_julio = new LatLng(-31.4266457, -63.2006849);
        mMap.addMarker(new MarkerOptions().position(chapulin_9_julio).title("Chapulin"));
        LatLng vea = new LatLng(-31.428872563164568, -62.08971817117201);
        mMap.addMarker(new MarkerOptions().position(vea).title("Vea"));
        LatLng norte = new LatLng(-31.417809772868658, -62.08937372955097);
        mMap.addMarker(new MarkerOptions().position(norte).title("Norte"));
    }
}
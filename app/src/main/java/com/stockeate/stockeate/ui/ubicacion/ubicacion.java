package com.stockeate.stockeate.ui.ubicacion;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.stockeate.stockeate.R;

import static com.stockeate.stockeate.R.layout.fragment_ubicacion;

public class ubicacion extends Fragment implements OnMapReadyCallback {

    private UbicacionViewModel viewModelUbicacion;
    private GoogleMap mMap;

    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @NonNull Bundle savedInstanceState) {
        viewModelUbicacion = new ViewModelProvider(this).get(UbicacionViewModel.class);
        View root = inflater.inflate(fragment_ubicacion, container, false);
        Log.d("Aca", "Por aca paso - oncreateView");
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);
        return root;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.d("Aca", "Por aca paso");
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        Log.d("Aca", "Por aca paso post if");
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled (true);

        MarkerOptions markerOptions = new MarkerOptions();
        LatLng san_francisco = new LatLng(-31.4249815, -62.0840299);
        markerOptions.position(san_francisco);
        markerOptions.title(san_francisco.latitude + " : " + san_francisco.longitude);
        mMap.clear();
        mMap.animateCamera(CameraUpdateFactory.newLatLng(san_francisco));
        mMap.addMarker(markerOptions);

        Log.d("Aca", "Por aca paso post mMap");

        // Add a marker in San Francisco and move the camera
        /*LatLng san_francisco = new LatLng(-31.4249815, -62.0840299);
        mMap.addMarker(new MarkerOptions().position(san_francisco).title("San Francisco"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(san_francisco));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(san_francisco, 30));*/

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
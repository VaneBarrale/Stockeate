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
import com.google.android.gms.maps.model.CameraPosition;
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

        LatLng san_francisco = new LatLng(-31.4249815, -62.0840299);
        mMap.addMarker(new MarkerOptions().position(san_francisco).title("San Francisco"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(san_francisco));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(san_francisco, 15));
    }
}
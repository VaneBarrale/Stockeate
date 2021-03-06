package com.stockeate.stockeate.ui.ubicacion;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.stockeate.stockeate.R;
import com.stockeate.stockeate.utiles.ParserTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.stockeate.stockeate.R.layout.fragment_ubicacion;

//PARA VER LA LINEA DESDE COMO LLEGAR AL HIPER POR EJEMPLO DESCOMENTAR LAS LINEAS 111, 112, 113 LatLng destino = new LatLng(-31.42773493119209, -62.11414910012128);

public class ubicacion extends Fragment implements OnMapReadyCallback {

    private UbicacionViewModel viewModelUbicacion;
    private GoogleMap mMap;
    private Marker marcador;
    private double lat = 0.0, lon = 0.0;

    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @NonNull Bundle savedInstanceState) {
        viewModelUbicacion = new ViewModelProvider(this).get(UbicacionViewModel.class);
        View root = inflater.inflate(fragment_ubicacion, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);
        getLocalicacion();
        return root;
    }

    private void getLocalicacion() {
        int permiso = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION);
        if(permiso == PackageManager.PERMISSION_DENIED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)){
            }else{
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        miUbicacion();
        /*LatLng san_francisco = new LatLng(-31.4249815, -62.0840299);
        mMap.addMarker(new MarkerOptions().position(san_francisco).title("San Francisco"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(san_francisco));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(san_francisco, 15));*/

    }

    public void miUbicacion() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationManager locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        actualizarUbicacion(location);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,15000,0,locationListener);
    }


    private void actualizarUbicacion(Location location) {
        if (location != null) {
            lat = location.getLatitude();
            lon = location.getLongitude();
            ubicacionActual(lat, lon);
        }
    }

    private void ubicacionActual(double lat, double lon) {

        LatLng coordenadas = new LatLng(lat, lon);
        //LatLng destino = new LatLng(-31.42773493119209, -62.11414910012128);
        //mMap.addMarker(new MarkerOptions().position(destino).title("Hipermercado Anselmi"));
        //comoLlegar(coordenadas, destino);
        CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(coordenadas, 16);
        if (marcador != null) {
            marcador.remove();
        }
        marcador = mMap.addMarker(new MarkerOptions().position(coordenadas).title("Mi posicion"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(coordenadas));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordenadas, 15));
        mMap.animateCamera(miUbicacion);
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            actualizarUbicacion(location);
        }

        @Override
        public void onProviderEnabled(@NonNull String provider) {
        }

        @Override
        public void onProviderDisabled(@NonNull String provider) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    public void comoLlegar(LatLng origen, LatLng destino){

        //https://jonathanmelgoza.com/blog/trazar-ruta-punto-a-otro-google-maps-android/

        MarkerOptions marcadorDestino= new MarkerOptions();
        marcadorDestino.position(destino);
        marcadorDestino.title("Este es tu destino");

        String url = obtenerDireccionesURL(origen, destino);
        DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute(url);
    }

    private String obtenerDireccionesURL(LatLng origin, LatLng dest){
        String str_origin = "origin="+origin.latitude+","+origin.longitude;
        String str_dest = "destination="+dest.latitude+","+dest.longitude;
        String sensor = "sensor=false";
        String key = "key=AIzaSyBjuy-FREuZA1HLk2xwI37JOfWkLYWgmhc";
        String parameters = str_origin+"&"+str_dest+"&"+key;
        //prueba de que la URL funciona con los parametros del ejemplo https://maps.googleapis.com/maps/api/directions/json?origin=-31.407625,-62.08034166666666&destination=-31.4249992,-62.0841599&key=AIzaSyBjuy-FREuZA1HLk2xwI37JOfWkLYWgmhc
        String output = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;
        return url;
    }

    private class DownloadTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... url) {

            String data = "";

            try{
                data = downloadUrl(url[0]);
            }catch(Exception e){
            }
            return data;
        }

        private String downloadUrl(String strUrl) throws IOException {
            String data = "";
            InputStream iStream = null;
            HttpURLConnection urlConnection = null;
            try{
                URL url = new URL(strUrl);

                // Creamos una conexion http
                urlConnection = (HttpURLConnection) url.openConnection();

                // Conectamos
                urlConnection.connect();

                // Leemos desde URL
                iStream = urlConnection.getInputStream();

                BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

                StringBuffer sb = new StringBuffer();

                String line = "";
                while( ( line = br.readLine()) != null){
                    sb.append(line);
                }

                data = sb.toString();

                br.close();

            }catch(Exception e){
            }finally{
                iStream.close();
                urlConnection.disconnect();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask(mMap);

            parserTask.execute(result);
        }
    }

}
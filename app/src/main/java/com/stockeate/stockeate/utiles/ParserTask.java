package com.stockeate.stockeate.utiles;

import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{

    private GoogleMap gMap;
    public ParserTask(GoogleMap googleMap) {
        this.gMap = googleMap;
    }

    @Override
    protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

        Log.d("Pasa por aca", "Entro ParserTask");

        JSONObject jObject;
        List<List<HashMap<String, String>>> routes = null;

        try{
            jObject = new JSONObject(jsonData[0]);
            DirectionsJSONParser parser = new DirectionsJSONParser();

            Log.d("Entro parser ", parser.toString());

            routes = parser.parse(jObject);
        }catch(Exception e){
            e.printStackTrace();
        }
        return routes;
    }

    @Override
    protected void onPostExecute(List<List<HashMap<String, String>>> result) {
        ArrayList<LatLng> points = new ArrayList<LatLng>();
        PolylineOptions lineOptions = new PolylineOptions();

        for(int i=0;i<result.size();i++){
            points = new ArrayList<LatLng>();

            List<HashMap<String, String>> path = result.get(i);

            for(int j=0;j<path.size();j++){
                HashMap<String,String> point = path.get(j);

                double lat = Double.parseDouble(point.get("lat"));
                double lng = Double.parseDouble(point.get("lng"));
                LatLng position = new LatLng(lat, lng);

                points.add(position);
            }

            Log.d("Points", "Entro " + points);
            lineOptions.addAll(points);
            lineOptions.width(4);
            lineOptions.color(Color.rgb(0,0,255));
        }

        Log.d("Entro ParserTask", "Entro " + lineOptions);
        Log.d("Entro gMap", "Entro " + gMap);

        if(lineOptions!=null && gMap != null) {
            gMap.addPolyline(lineOptions);
        }
    }
}
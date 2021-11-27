package com.stockeate.stockeate.ui.locales;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.stockeate.stockeate.R;
import com.stockeate.stockeate.ui.reportes.Fragment_reportes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Fragment_locales extends Fragment {

    private LocalesViewModel localesViewModel;
    private Button btn_volver;
    TextView txvLocales;
    RequestQueue requestQueue;
    String URL_SERVIDOR = "https://stockeateapp.com.ar/api/markets";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        localesViewModel = new ViewModelProvider(this).get(LocalesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_locales, container, false);
        localesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        btn_volver = root.findViewById(R.id.btn_Volver);
        txvLocales = root.findViewById(R.id.txvLocales);

        requestQueue = Volley.newRequestQueue(getContext());

        cargarDatosTabla();

        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_reportes fragment_reportes = new Fragment_reportes();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_locales, fragment_reportes);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    return root;
    }

    //Esto SI funciona!!!
    public void cargarDatosTabla() {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
            Request.Method.GET,
            URL_SERVIDOR,
            null,
            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    int size = response.length();
                    for(int i=0; i<size; i++){
                        try {
                            JSONObject jsonObject = new JSONObject(response.get(i).toString());
                            String locales = jsonObject.getString("name");
                            txvLocales.append(locales + "\n");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "ERROR AL CARGAR LOCALES", Toast.LENGTH_LONG).show();
                    Log.d("Error response", "Error response " + error);
                }
            }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + "10|QzbPyRDIRDNT5dQkI5rqDV7p7WYRdOL0M8if8jcu");
                return params;
            }
        };
        requestQueue.add(jsonArrayRequest);
    }
}
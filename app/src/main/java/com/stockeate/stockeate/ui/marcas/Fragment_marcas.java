package com.stockeate.stockeate.ui.marcas;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.stockeate.stockeate.Adapter.Adapter_Top10Marcas;
import com.stockeate.stockeate.R;
import com.stockeate.stockeate.clases.class_top_10;
import com.stockeate.stockeate.ui.categoria.CategoriasViewModel;
import com.stockeate.stockeate.ui.reportes.Fragment_reportes;
import com.stockeate.stockeate.utiles.utiles;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Fragment_marcas extends Fragment {

    private MarcasViewModel marcasViewModel;
    private Button btn_volver;
    TextView txvMarcas;
    RequestQueue requestQueue;
    String URL_SERVIDOR = "https://stockeateapp.com.ar/api/brands";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        marcasViewModel = new ViewModelProvider(this).get(MarcasViewModel.class);
        View root = inflater.inflate(R.layout.fragment_marcas, container, false);
        marcasViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        this.btn_volver = root.findViewById(R.id.btn_Volver);
        this.txvMarcas = root.findViewById(R.id.txvMarcas);

        requestQueue = Volley.newRequestQueue(getContext());

        cargarDatosTabla();

        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_reportes fragment_reportes = new Fragment_reportes();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_marcas, fragment_reportes);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

    return root;

    }

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
                                String marcas = jsonObject.getString("description");
                                txvMarcas.append(marcas + "\n");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "ERROR AL CARGAR MARCAS", Toast.LENGTH_LONG).show();
                        Log.d("Error response", "Error response " + error);
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + "11|QMuCyTS9qdS2SgEc3IlGpEQDeTzbgPVkk5E82WBZ");
                return params;
            }
        };
        requestQueue.add(jsonArrayRequest);
    }
}
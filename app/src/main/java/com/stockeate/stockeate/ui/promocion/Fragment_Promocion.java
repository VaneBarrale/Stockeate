package com.stockeate.stockeate.ui.promocion;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.stockeate.stockeate.Adapter.Adapter_promociones;
import com.stockeate.stockeate.R;
import com.stockeate.stockeate.clases.class_promociones;
import com.stockeate.stockeate.ui.agregar_promocion.Fragment_Agregar_Promocion;
import com.stockeate.stockeate.ui.home.HomeFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Fragment_Promocion extends Fragment {

    private PromocionViewModel viewModelPromocion;
    private Button btn_volver, btn_agregar;
    private ArrayAdapter<class_promociones> mArrayAdapterPromociones;
    private ArrayList<class_promociones> mPromocionesList;
    private RecyclerView RecyclePromociones;
    RequestQueue requestQueue;
    String URL_SERVIDOR = "https://stockeateapp.com.ar/api/promotions";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModelPromocion = new ViewModelProvider(this).get(PromocionViewModel.class);
        View root = inflater.inflate(R.layout.fragment_promocion, container, false);
        viewModelPromocion.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        this.btn_volver = root.findViewById(R.id.btn_Volver);
        this.btn_agregar = root.findViewById(R.id.btn_Agregar);
        this.RecyclePromociones = root.findViewById(R.id.RecyclePromocioes);

        RecyclePromociones.setLayoutManager(new LinearLayoutManager(getContext()));
        requestQueue = Volley.newRequestQueue(getContext());

        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment home = new HomeFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_promocion, home);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btn_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_Agregar_Promocion agregar_promocion = new Fragment_Agregar_Promocion();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_promocion, agregar_promocion);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        listarproductos();

        return root;
    }

    private void listarproductos() {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL_SERVIDOR,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        mPromocionesList = new ArrayList<class_promociones>();

                        int size = response.length();
                        Log.d("Longitud", "Longitud arreglo " + size);

                        for (int i = 0; i < size; i++) {
                            try {
                                class_promociones promociones = new class_promociones();

                                JSONObject jsonObject = new JSONObject(response.get(i).toString());
                                JSONArray detalle = jsonObject.getJSONArray("details");

                                Log.d("1 JSONObject", "1 JSONObject " + jsonObject);

                                JSONObject detalleProducto = detalle.getJSONObject(i);

                                Log.d("2 JSONObject", "2 JSONObject " + jsonObject);

                                JSONObject productos = detalleProducto.getJSONObject("product");
                                JSONObject locales = detalleProducto.getJSONObject("market");

                                promociones.setTipo_promocion(jsonObject.getString("type"));

                                for (int k = 0; k < detalleProducto.length(); k++) {
                                    for (int j = 0; j < productos.length(); j++) {
                                        promociones.setCategoria(productos.getString("name"));
                                        promociones.setMarca(productos.getString("brand"));
                                        promociones.setPresentacion(productos.getString("presentation"));
                                    }

                                    for (int l = 0; l < locales.length(); l++) {
                                        promociones.setLocal(locales.getString("name"));
                                    }
                                }
                                mPromocionesList.add(promociones);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Adapter_promociones adapter_promociones = new Adapter_promociones(mPromocionesList);
                        RecyclePromociones.setAdapter(adapter_promociones);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "ERROR AL CARGAR PROMOCIONES", Toast.LENGTH_LONG).show();
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
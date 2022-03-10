package com.stockeate.stockeate.ui.promocion;

import android.os.Bundle;
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
import com.stockeate.stockeate.Adapter.Adapter_detalle_promociones;
import com.stockeate.stockeate.Adapter.Adapter_promociones;
import com.stockeate.stockeate.R;
import com.stockeate.stockeate.clases.class_detalle_promocion;
import com.stockeate.stockeate.clases.class_promociones;
import com.stockeate.stockeate.ui.agregar_promocion.Fragment_Agregar_Promocion;
import com.stockeate.stockeate.ui.home.HomeFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Fragment_Promocion extends Fragment{

    private TextView txtTipoPromocion;
    private PromocionViewModel viewModelPromocion;
    private Button btn_volver, btn_agregar;
    private ArrayList<class_promociones> mPromocionesList;
    private ArrayList<class_detalle_promocion> detallesPromocion;
    private RecyclerView RecyclePromociones, RecycleDetallePromociones;
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

        this.txtTipoPromocion = root.findViewById(R.id.txtDetallePromocion);
        this.btn_volver = root.findViewById(R.id.btn_Volver);
        this.btn_agregar = root.findViewById(R.id.btn_Agregar);
        this.RecyclePromociones = root.findViewById(R.id.RecyclePromocioes);
        this.RecycleDetallePromociones = root.findViewById(R.id.RecycleDetallePromocioes);

        RecyclePromociones.setLayoutManager(new LinearLayoutManager(getContext()));
        RecycleDetallePromociones.setLayoutManager(new LinearLayoutManager(getContext()));
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

                        for (int i = 0; i < size; i++) {
                            try {
                                class_promociones promocion = new class_promociones();

                                JSONObject jsonObject = new JSONObject(response.get(i).toString());
                                
                                promocion.setTipoPromocion(jsonObject.getString("type"));
                                promocion.setId(Integer.parseInt(jsonObject.getString("id")));
                                mPromocionesList.add(promocion);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Adapter_promociones adapter_promociones = new Adapter_promociones(mPromocionesList);
                        RecyclePromociones.setAdapter(adapter_promociones);

                        adapter_promociones.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               int posicion = RecyclePromociones.getChildAdapterPosition(view);
                               try {
                                   buscarDetalle(posicion);
                               } catch (IOException | JSONException e) {
                                   e.printStackTrace();
                               }
                           }
                        });
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

    public void buscarDetalle(int posicion) throws IOException, JSONException {
        detallesPromocion = new ArrayList<class_detalle_promocion>();

        int id_promocion = mPromocionesList.get(posicion).getId();

        if(id_promocion!=0){
            txtTipoPromocion.setText("ID " + id_promocion + " - Tipo " + mPromocionesList.get(posicion).getTipo_promocion());
            limpiarDatos();
            String URL_LISTA = URL_SERVIDOR+"/"+id_promocion;

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL_LISTA,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        int size = response.length();
                        for (int i = 0; i < size; i++) {
                            try {
                                JSONArray jsonArray = response.getJSONArray("details");
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                class_detalle_promocion detalles = new class_detalle_promocion();
                                detalles.setCategoria(jsonObject.getString("category"));
                                detalles.setMarca(jsonObject.getString("brand"));
                                detalles.setPresentacion(jsonObject.getString("presentation"));
                                detalles.setLocal(jsonObject.getString("market"));
                                detallesPromocion.add(detalles);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Adapter_detalle_promociones adapter_detalle_promociones = new Adapter_detalle_promociones(detallesPromocion);
                            RecycleDetallePromociones.setAdapter(adapter_detalle_promociones);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "ERROR AL CARGAR PRODUCTOS", Toast.LENGTH_LONG).show(); }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String>  params = new HashMap<String, String>();
                        params.put("Authorization", "Bearer " + "11|QMuCyTS9qdS2SgEc3IlGpEQDeTzbgPVkk5E82WBZ");
                        return params;
                    }
                };
            requestQueue.add(jsonObjectRequest);
        }
    }

    private void limpiarDatos() {
        this.detallesPromocion.clear();
        Adapter_detalle_promociones adapter_detalle_promociones = new Adapter_detalle_promociones(detallesPromocion);
        this.RecycleDetallePromociones.setAdapter(adapter_detalle_promociones);
    }
}
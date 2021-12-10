package com.stockeate.stockeate.ui.mis_listas_compras;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.stockeate.stockeate.Adapter.Adapter_detalle_MIS_lista_compras;
import com.stockeate.stockeate.Adapter.Adapter_detalle_lista_compras;
import com.stockeate.stockeate.Adapter.Adapter_mis_listas;
import com.stockeate.stockeate.R;
import com.stockeate.stockeate.clases.class_detalle_lista_compras;
import com.stockeate.stockeate.clases.class_lista_compras;
import com.stockeate.stockeate.clases.class_producto;
import com.stockeate.stockeate.ui.lista_compras.Fragment_lista_compras;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Fragment_mis_listas_compras extends Fragment {

    private ViewModel_mis_listas_compras viewModel_mis_listas_compras;
    private Button btn_volver, btn_detalle;
    private TextView txt_lista;
    private ArrayList<class_detalle_lista_compras> mDetalleLista;
    private ArrayList<class_lista_compras> mMisListas;
    private RecyclerView RecycleDetalleMisListas, RecycleMisListas;
    RequestQueue requestQueue;
    String URL_SERVIDOR = "https://stockeateapp.com.ar/api/orders";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel_mis_listas_compras =
                new ViewModelProvider(this).get(ViewModel_mis_listas_compras.class);
        View root = inflater.inflate(R.layout.fragment_mis_listas_compras, container, false);
        viewModel_mis_listas_compras.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        this.txt_lista = root.findViewById(R.id.txt_lista);
        this.btn_volver = root.findViewById(R.id.btn_Volver);
        this.btn_detalle = root.findViewById(R.id.btn_Detalle);
        this.RecycleDetalleMisListas = root.findViewById(R.id.RecycleDetalleMisListas);
        this.RecycleMisListas = root.findViewById(R.id.RecycleMisListas);

        requestQueue = Volley.newRequestQueue(getContext());
        RecycleDetalleMisListas.setLayoutManager(new LinearLayoutManager(getContext()));
        RecycleMisListas.setLayoutManager(new LinearLayoutManager(getContext()));

        mDetalleLista = new ArrayList<class_detalle_lista_compras>();

        btn_detalle.setEnabled(false);

        mostrarLista();

        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_lista_compras lista_compras = new Fragment_lista_compras();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_mis_listas_compras, lista_compras);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return root;
    }

    private void mostrarLista() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL_SERVIDOR,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int size = response.length();
                        mMisListas = new ArrayList<class_lista_compras>();
                        for(int i=0; i<size; i++) {
                            try {
                                class_lista_compras lista_compras = new class_lista_compras();
                                JSONObject jsonObject = new JSONObject(response.get(i).toString());
                                lista_compras.setId(jsonObject.getString("id"));
                                mMisListas.add(lista_compras);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Adapter_mis_listas adapter_mis_listas = new Adapter_mis_listas(mMisListas);
                        RecycleMisListas.setAdapter(adapter_mis_listas);

                        adapter_mis_listas.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int posicion = RecycleMisListas.getChildAdapterPosition(view);
                                try {
                                    buscarProducto(posicion);
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
                        Toast.makeText(getContext(), "ERROR AL CARGAR MIS LISTAS", Toast.LENGTH_LONG).show();
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

    public void buscarProducto(int posicion) throws IOException, JSONException {
        mDetalleLista = new ArrayList<class_detalle_lista_compras>();
        mDetalleLista.clear();

        String id_lista = mMisListas.get(posicion).getId();

        if(!id_lista.isEmpty()){
            btn_detalle.setEnabled(true);
            txt_lista.setText("Lista de compras " + id_lista);
        }

        btn_detalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiarDatos();
                String URL_LISTA = URL_SERVIDOR+"/"+id_lista;
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                            Request.Method.GET,
                            URL_LISTA,
                            null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        JSONArray jsonArray = response.getJSONArray("order_lines");
                                        int size = response.length();
                                        for(int i=0; i<size; i++) {
                                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                                            class_detalle_lista_compras detalle_lista_compras = new class_detalle_lista_compras();
                                            detalle_lista_compras.setCategoria(jsonObject.getString("description"));
                                            detalle_lista_compras.setCantidad(jsonObject.getString("quantity"));
                                            mDetalleLista.add(detalle_lista_compras);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                Adapter_detalle_MIS_lista_compras adapter_detalle_mis_lista_compras = new Adapter_detalle_MIS_lista_compras(mDetalleLista);
                                RecycleDetalleMisListas.setAdapter(adapter_detalle_mis_lista_compras);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getContext(), "ERROR AL CARGAR PRODUCTOS", Toast.LENGTH_LONG).show(); }
                        }
                ) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String>  params = new HashMap<String, String>();
                        params.put("Authorization", "Bearer " + "11|QMuCyTS9qdS2SgEc3IlGpEQDeTzbgPVkk5E82WBZ");
                        return params;
                    }
                };
                requestQueue.add(jsonObjectRequest);
            }
        });
    }

    private void limpiarDatos() {
        this.mDetalleLista.clear();
        Adapter_detalle_MIS_lista_compras adapter_detalle_mis_lista_compras = new Adapter_detalle_MIS_lista_compras(mDetalleLista);
        this.RecycleDetalleMisListas.setAdapter(adapter_detalle_mis_lista_compras);
    }
}

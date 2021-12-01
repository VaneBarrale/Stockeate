package com.stockeate.stockeate.ui.agregar_promocion;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.stockeate.stockeate.Adapter.Adapter_mis_listas;
import com.stockeate.stockeate.Adapter.Adapter_productos;
import com.stockeate.stockeate.Adapter.Adapter_promociones;
import com.stockeate.stockeate.R;
import com.stockeate.stockeate.clases.class_producto;
import com.stockeate.stockeate.ui.promocion.Fragment_Promocion;
import com.stockeate.stockeate.utiles.utiles;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Fragment_Agregar_Promocion extends Fragment {

    private ViewModel_agregar_promocion AgregarPromocionViewModel;
    private Button btn_volver, btn_buscar, btn_buscar_codigo_barra, btn_agregar;
    private EditText categoria, marca, presentacion, cantidad;
    private Spinner comercio, promociones;
    private ArrayList<class_producto> mProductosList;
    private RecyclerView RecycleProductos;
    RequestQueue requestQueue;
    String URL_SERVIDOR = "https://stockeateapp.com.ar/api/products";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        AgregarPromocionViewModel = new ViewModelProvider(this).get(ViewModel_agregar_promocion.class);
        View root = inflater.inflate(R.layout.fragment_agregar_promocion, container, false);
        AgregarPromocionViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        this.btn_volver = root.findViewById(R.id.btn_Volver);
        this.btn_buscar = root.findViewById(R.id.btn_buscar);
        this.btn_buscar_codigo_barra = root.findViewById(R.id.btn_buscar_codigo_barra);
        this.btn_agregar = root.findViewById(R.id.btn_agregar_promocion);
        this.RecycleProductos = root.findViewById(R.id.RecycleProductos);
        this.categoria = root.findViewById(R.id.etxtCodigoProducto);
        this.marca = root.findViewById(R.id.etxtMarca);
        this.presentacion = root.findViewById(R.id.etxtPresentacion);
        this.cantidad = root.findViewById(R.id.etxtCantidad);
        this.comercio = root.findViewById(R.id.sComercios);
        this.promociones = root.findViewById(R.id.sPromociones);

        mProductosList = new ArrayList<class_producto>();
        RecycleProductos.setLayoutManager(new LinearLayoutManager(getContext()));
        requestQueue = Volley.newRequestQueue(getContext());

        comercio.setVisibility(View.INVISIBLE);
        promociones.setVisibility(View.INVISIBLE);
        btn_agregar.setEnabled(false);

        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_Promocion promocion = new Fragment_Promocion();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_agregar_promocion, promocion);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    listarproductos();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        btn_buscar_codigo_barra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                escanear();
            }
        });

        /*listaResultado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int i = 0, j = 0;

                btn_agregar.setEnabled(true);

                btn_agregar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if ((cantidad.getText().toString()).equals("") || (comercio.getSelectedItem().toString().equals("Local")) || promociones.getSelectedItem().toString().equals("Tipo promocion")) {
                            Toast.makeText(getContext(), "Complete cantidad, tipo promocion y/o comercio", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Promoción agregada con éxito", Toast.LENGTH_SHORT).show();
                            limpiarDatos();
                        }
                    }
                });
            }
        });*/


        return root;
    }

    private void listarproductos() throws IOException, JSONException {
        mProductosList.clear();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL_SERVIDOR,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        class_producto productos = new class_producto();
                        int size = response.length();
                        for(int i=0; i<size; i++){
                            try {
                                JSONObject jsonObject = new JSONObject(response.get(i).toString());
                                String category = jsonObject.getString("name");
                                String brand = jsonObject.getString("brand");
                                String presentation = jsonObject.getString("presentation");
                                String code = jsonObject.getString("code");
                                if (!categoria.getText().toString().isEmpty() ||
                                        !marca.getText().toString().isEmpty() ||
                                        !presentacion.getText().toString().isEmpty()) {
                                    if (!categoria.getText().toString().isEmpty()) {
                                        if (category.equals(categoria.getText().toString())) {
                                            productos.setCategoria(category);
                                            productos.setMarca(brand);
                                            productos.setPresentacion(presentation);
                                            productos.setCodigo_barra(code);
                                            mProductosList.add(productos);
                                        }
                                    }
                                    if (!marca.getText().toString().isEmpty()) {
                                        if (brand.equals(marca.getText().toString())) {
                                            productos.setCategoria(category);
                                            productos.setMarca(brand);
                                            productos.setPresentacion(presentation);
                                            productos.setCodigo_barra(code);
                                            mProductosList.add(productos);
                                        }
                                    }
                                    if (!presentacion.getText().toString().isEmpty()) {
                                        if (presentation.equals(presentacion.getText().toString())) {
                                            productos.setCategoria(category);
                                            productos.setMarca(brand);
                                            productos.setPresentacion(presentation);
                                            productos.setCodigo_barra(code);
                                            mProductosList.add(productos);
                                        }
                                    }
                                }
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        Adapter_productos adapter_productos = new Adapter_productos(mProductosList);
                        RecycleProductos.setAdapter(adapter_productos);
                        RecycleProductos.setVisibility(View.VISIBLE);
                        comercio.setVisibility(View.VISIBLE);
                        promociones.setVisibility(View.VISIBLE);

                        adapter_productos.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                btn_agregar.setEnabled(true);

                                btn_agregar.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if ((cantidad.getText().toString()).equals("") || (comercio.getSelectedItem().toString().equals("Local")) || promociones.getSelectedItem().toString().equals("Tipo promocion")) {
                                            Toast.makeText(getContext(), "Complete cantidad, tipo promocion y/o comercio", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getContext(), "Promoción agregada con éxito", Toast.LENGTH_SHORT).show();
                                            limpiarDatos();
                                        }
                                    }
                                });
                            }
                        });
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "ERROR AL CARGAR PRODUCTOS", Toast.LENGTH_LONG).show();
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

    public void escanear(){
        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(Fragment_Agregar_Promocion.this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("ESCANEAR CODIGO");
        integrator.setCameraId(0); //0 es la camara trasera
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(true); //habilito para que pueda leer correctamente.
        integrator.initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        Toast.makeText(getContext(), result.getContents(), Toast.LENGTH_SHORT).show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL_SERVIDOR,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        class_producto productos = new class_producto();
                        int size = response.length();
                        for(int i=0; i<size; i++){
                            try {
                                JSONObject jsonObject = new JSONObject(response.get(i).toString());
                                String code = jsonObject.getString("code");
                                if (code.equals((result.getContents()))) {
                                    productos.setCategoria(jsonObject.getString("name"));
                                    productos.setMarca(jsonObject.getString("brand"));
                                    productos.setPresentacion(jsonObject.getString("presentation"));
                                    productos.setCodigo_barra(code);
                                    mProductosList.add(productos);
                                }
                            } catch (JSONException e) { e.printStackTrace(); }
                        }
                        if (!mProductosList.isEmpty()) {
                            RecycleProductos.setVisibility(View.VISIBLE);
                            Adapter_productos adapter_productos = new Adapter_productos(mProductosList);
                            RecycleProductos.setAdapter(adapter_productos);
                        } else {
                            Toast.makeText(getContext(), "No existen productos con ese cod. de barra", Toast.LENGTH_LONG).show();
                            }
                        }
                    },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getContext(), "ERROR AL CARGAR PRODUCTOS", Toast.LENGTH_LONG).show();
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

    private void limpiarDatos() {
        categoria.setText("");
        marca.setText("");
        presentacion.setText("");
        cantidad.setText("");
        comercio.setVisibility(View.INVISIBLE);
        promociones.setVisibility(View.INVISIBLE);
        RecycleProductos.setVisibility(View.INVISIBLE);
    }
}
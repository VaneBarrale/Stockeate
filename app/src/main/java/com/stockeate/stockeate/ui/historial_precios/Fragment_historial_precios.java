package com.stockeate.stockeate.ui.historial_precios;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
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
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.stockeate.stockeate.Adapter.Adapter_historial_precios;
import com.stockeate.stockeate.Adapter.Adapter_productos;
import com.stockeate.stockeate.R;
import com.stockeate.stockeate.clases.class_detalle_lista_compras;
import com.stockeate.stockeate.clases.class_historial_precios;
import com.stockeate.stockeate.clases.class_lista_compras;
import com.stockeate.stockeate.clases.class_producto;
import com.stockeate.stockeate.ui.lista_compras.Fragment_lista_compras;
import com.stockeate.stockeate.ui.reportes.Fragment_reportes;
import com.stockeate.stockeate.utiles.utiles;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Fragment_historial_precios extends Fragment {

    private HistorialPreciosViewModel historialPreciosViewModel;
    private Button btn_buscar, btn_buscar_codigo_barra, btn_volver;
    private EditText categoria, marca, presentacion;
    private TextView historial;
    private Spinner locales;
    private RecyclerView RecycleProductos, RecycleHistorialPrecios;
    private ArrayList<class_producto> mProductosList;
    private ArrayList<class_historial_precios> mHistorialPrecio;
    RequestQueue requestQueue;
    String URL_SERVIDOR = "https://stockeateapp.com.ar/api/products";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        historialPreciosViewModel = new ViewModelProvider(this).get(HistorialPreciosViewModel.class);
        View root = inflater.inflate(R.layout.fragment_historial_precios, container, false);
        historialPreciosViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        this.btn_buscar = root.findViewById(R.id.btn_buscar);
        this.btn_buscar_codigo_barra = root.findViewById(R.id.btn_buscar_codigo_barra);
        this.btn_volver = root.findViewById(R.id.btn_Volver);
        this.categoria = root.findViewById(R.id.etxtCodigoProducto);
        this.marca = root.findViewById(R.id.etxtMarca);
        this.presentacion = root.findViewById(R.id.etxtPresentacion);
        this.locales = root.findViewById(R.id.sLocales);
        this.historial = root.findViewById(R.id.txt_historial);
        this.RecycleHistorialPrecios = root.findViewById(R.id.RecycleHistorialPrecios);
        this.RecycleProductos = root.findViewById(R.id.RecycleProductos);

        mProductosList = new ArrayList<class_producto>();
        requestQueue = Volley.newRequestQueue(getContext());
        RecycleProductos.setLayoutManager(new LinearLayoutManager(getContext()));
        RecycleHistorialPrecios.setLayoutManager(new LinearLayoutManager(getContext()));

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

        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_reportes fragment_reportes = new Fragment_reportes();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_historial_precios, fragment_reportes);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

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
                        mProductosList = new ArrayList<class_producto>();
                        int size = response.length();
                        for(int i=0; i<size; i++){
                            try {
                                JSONObject jsonObject = new JSONObject(response.get(i).toString());
                                class_producto productos = new class_producto();
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

                        if(!mProductosList.isEmpty()){
                            locales.setVisibility(View.VISIBLE);
                        } else {
                            locales.setVisibility(View.INVISIBLE);
                            Toast.makeText(getContext(), "No hay productos con ese codigo", Toast.LENGTH_LONG).show();
                        }

                        adapter_productos.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int posicion = RecycleProductos.getChildAdapterPosition(v);
                                try {
                                    buscarProducto(posicion);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
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

    public void buscarProducto(int posicion) throws IOException, JSONException {
        mHistorialPrecio = new ArrayList<class_historial_precios>();
        mHistorialPrecio.clear();

        String jsonFileContent = utiles.leerJson(getContext(), "HistorialPrecios.json");
        JSONArray jsonArray = new JSONArray(jsonFileContent);
        if(locales.getSelectedItem().toString().equals("Local")){
            Toast.makeText(getContext(), "Seleccione un local", Toast.LENGTH_SHORT).show();
        } else {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = null;
                jsonObj = jsonArray.getJSONObject(i);
                if (jsonObj.getString("categoria").equals(mProductosList.get(posicion).getCategoria())
                        && jsonObj.getString("marca").equals(mProductosList.get(posicion).getMarca())
                        && jsonObj.getString("presentacion").equals(mProductosList.get(posicion).getPresentacion())
                        && jsonObj.getString("unidad").equals(mProductosList.get(posicion).getUnidad())
                        && jsonObj.getString("comercio").equals(locales.getSelectedItem().toString())) {

                    class_historial_precios historial_precios = new class_historial_precios();
                    historial_precios.setCategoria(mProductosList.get(posicion).getCategoria());
                    historial_precios.setMarca(mProductosList.get(posicion).getMarca());
                    historial_precios.setPresentacion(mProductosList.get(posicion).getPresentacion());
                    historial_precios.setUnidad(mProductosList.get(posicion).getUnidad());
                    historial_precios.setComercio(locales.getSelectedItem().toString());
                    historial_precios.setPrecio(Float.parseFloat(jsonObj.getString("precio")));
                    mHistorialPrecio.add(historial_precios);
                    Log.d("Precio", "Precio " + mHistorialPrecio.toString());
                }
            }
            Adapter_historial_precios adapter_historial_precios = new Adapter_historial_precios(mHistorialPrecio);
            RecycleHistorialPrecios.setAdapter(adapter_historial_precios);
            int cantidad = adapter_historial_precios.getItemCount();
            if(cantidad == 0){
                historial.setText("No existe historial de precios para las opciones seleccioadas");
            } else { historial.setText("");}
        }
    }

    public void escanear(){
        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(Fragment_historial_precios.this);
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
                        mProductosList = new ArrayList<class_producto>();
                        int size = response.length();
                        for (int i = 0; i < size; i++) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.get(i).toString());
                                String code = jsonObject.getString("code");
                                class_producto productos = new class_producto();
                                if (code.equals((result.getContents()))) {
                                    productos.setCategoria(jsonObject.getString("name"));
                                    productos.setMarca(jsonObject.getString("brand"));
                                    productos.setPresentacion(jsonObject.getString("presentation"));
                                    productos.setCodigo_barra(code);
                                    mProductosList.add(productos);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        RecycleProductos.setVisibility(View.VISIBLE);
                        Adapter_productos adapter_productos = new Adapter_productos(mProductosList);
                        RecycleProductos.setAdapter(adapter_productos);

                        if (!mProductosList.isEmpty()) {
                            locales.setVisibility(View.VISIBLE);
                        } else {
                            locales.setVisibility(View.INVISIBLE);
                        }

                        adapter_productos.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int posicion = RecycleProductos.getChildAdapterPosition(v);
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
}
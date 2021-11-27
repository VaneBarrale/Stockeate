package com.stockeate.stockeate.ui.lista_compras;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.stockeate.stockeate.Adapter.Adapter_detalle_lista_compras;
import com.stockeate.stockeate.Adapter.Adapter_productos;
import com.stockeate.stockeate.R;
import com.stockeate.stockeate.clases.class_detalle_lista_compras;
import com.stockeate.stockeate.clases.class_producto;
import com.stockeate.stockeate.ui.mis_listas_compras.Fragment_mis_listas_compras;
import com.stockeate.stockeate.ui.comparar_precios.Fragment_Comparar_Precios;
import com.stockeate.stockeate.ui.home.HomeFragment;
import com.stockeate.stockeate.utiles.utiles;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Fragment_lista_compras extends Fragment {

    private ViewModel_lista_compras viewModelListacompras;
    private Button btn_comparar, btn_volver, btn_agregar, btn_buscar, btn_guardar, btn_listas, btn_cod_barra;
    private EditText categoria, marca, presentacion, cantidad;
    private ArrayList<class_producto> mProductosList;
    private ArrayList<class_detalle_lista_compras> mDetalleLista;
    private RecyclerView RecycleProductos, RecyclerProductosAgregados;
    private Adapter_detalle_lista_compras adapter_detalle_lista_compras;
    RequestQueue requestQueue;
    String URL_SERVIDOR = "https://stockeateapp.com.ar/api/products";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModelListacompras =
                new ViewModelProvider(this).get(ViewModel_lista_compras.class);
        View root = inflater.inflate(R.layout.fragment_lista_compras, container, false);
        viewModelListacompras.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        this.btn_comparar = root.findViewById(R.id.btn_Comparar);
        this.btn_volver = root.findViewById(R.id.btn_Volver);
        this.btn_agregar = root.findViewById(R.id.btn_Agregar);
        this.btn_buscar = root.findViewById(R.id.btn_Buscar);
        this.btn_guardar = root.findViewById(R.id.btn_Guardar);
        this.btn_listas = root.findViewById(R.id.btn_MisListas);
        this.btn_cod_barra = root.findViewById(R.id.btn_BuscarCodigoBarra);
        this.categoria = root.findViewById(R.id.etxCategoria);
        this.marca = root.findViewById(R.id.etxtMarca);
        this.presentacion = root.findViewById(R.id.etxtPresentacion);
        this.cantidad = root.findViewById(R.id.etxtCantidad);
        this.RecyclerProductosAgregados = root.findViewById(R.id.RecyclerProductosAgregados);
        this.RecycleProductos = root.findViewById(R.id.RecycleProductos);
        this.adapter_detalle_lista_compras = new Adapter_detalle_lista_compras(mDetalleLista);

        mProductosList = new ArrayList<class_producto>();
        mDetalleLista = new ArrayList<class_detalle_lista_compras>();
        requestQueue = Volley.newRequestQueue(getContext());
        RecycleProductos.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerProductosAgregados.setLayoutManager(new LinearLayoutManager(getContext()));

        btn_comparar.setEnabled(false);
        btn_guardar.setEnabled(false);

        adapter_detalle_lista_compras.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mDetalleLista.remove(RecyclerProductosAgregados.getChildAdapterPosition(view));
                //Adapter_detalle_lista_compras adapter_detalle_lista_compras = new Adapter_detalle_lista_compras(mDetalleLista);
                RecyclerProductosAgregados.setAdapter(adapter_detalle_lista_compras);
                return true;
            }
        });

        btn_listas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_mis_listas_compras fragment_mis_listas_compras = new Fragment_mis_listas_compras();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_lista_compras, fragment_mis_listas_compras);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btn_comparar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ASI SI PASARA DE UN FRAGMENT A UNA ACT.
                // Intent i = new Intent(getActivity(), Activity_Comparar_Precios.class);
                // startActivity(i);
                Fragment_Comparar_Precios fragment_comparar_precios = new Fragment_Comparar_Precios();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_lista_compras, fragment_comparar_precios);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment homeFragment = new HomeFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_lista_compras, homeFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { listarproductos();}
        });

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mDetalleLista.isEmpty()) {
                    int i = 0;

                    class_detalle_lista_compras detalle_lista_compras = new class_detalle_lista_compras();

                    try {
                        JSONObject json = new JSONObject();
                        InputStream is = Fragment_lista_compras.this.getClass().getClassLoader().getResourceAsStream("assets/" + "detalle_lista_compras.json");

                        json.put("id", i++);
                        json.put("id_lista_compras", detalle_lista_compras.getId_lista_compras());
                        json.put("id_producto", detalle_lista_compras.getId_producto());
                        json.put("id_usuario", "1");
                        json.put("categoria", detalle_lista_compras.getCategoria());
                        json.put("marca", detalle_lista_compras.getMarca());
                        json.put("presentacion", detalle_lista_compras.getPresentacion());
                        json.put("unidad", detalle_lista_compras.getUnidad());
                        json.put("cantidad", cantidad);
                        Log.i("Json", json.toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getContext(), "Guardado con exito", Toast.LENGTH_SHORT).show();
                    limpiarDatos();
                    //mArrayAdapterProducto.clear();
                } else {
                    Toast.makeText(getContext(), "Agregue productos a la lista", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_cod_barra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                escanear();
            }
        });

        return root;

    }

    public void escanear(){
        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(Fragment_lista_compras.this);
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
                                mProductosList = new ArrayList<class_producto>();
                                productos.setCategoria(jsonObject.getString("name"));
                                productos.setMarca(jsonObject.getString("brand"));
                                productos.setPresentacion(jsonObject.getString("presentation"));
                                productos.setCodigo_barra(code);
                                mProductosList.add(productos);
                            }
                        } catch (JSONException e) { e.printStackTrace(); }
                    }
                    RecycleProductos.setVisibility(View.VISIBLE);
                    Adapter_productos adapter_productos = new Adapter_productos(mProductosList);
                    RecycleProductos.setAdapter(adapter_productos);

                    adapter_productos.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            class_detalle_lista_compras detalle_lista_compras = new class_detalle_lista_compras();

                            for (int f = 0; f < mProductosList.size(); f++) {
                                if (f == RecycleProductos.getChildAdapterPosition(v)) {
                                    detalle_lista_compras.setId_producto(mProductosList.get(f).getId());
                                    detalle_lista_compras.setCategoria(mProductosList.get(f).getCategoria());
                                    detalle_lista_compras.setMarca(mProductosList.get(f).getMarca());
                                    detalle_lista_compras.setPresentacion(mProductosList.get(f).getPresentacion());
                                    detalle_lista_compras.setUnidad(mProductosList.get(f).getCodigo_barra());
                                    detalle_lista_compras.setCantidad(cantidad.getText().toString());

                                    btn_agregar.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (!detalle_lista_compras.getCategoria().isEmpty()) {
                                                String _cantidad = cantidad.getText().toString();
                                                if (_cantidad.equals("")) {
                                                    cantidad.setError("Cantidad requerida");
                                                } else {
                                                    mDetalleLista.add(detalle_lista_compras);
                                                    Adapter_detalle_lista_compras adapter_detalle_lista_compras = new Adapter_detalle_lista_compras(mDetalleLista);
                                                    RecyclerProductosAgregados.setAdapter(adapter_detalle_lista_compras);
                                                }
                                            }
                                            else {
                                                Toast.makeText(getContext(), "Seleccione un producto de la lista", Toast.LENGTH_SHORT).show();
                                            }
                                            btn_comparar.setEnabled(true);
                                            btn_guardar.setEnabled(true);
                                        }
                                    });
                                }
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

    private void listarproductos(){

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

                    adapter_productos.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            class_detalle_lista_compras detalle_lista_compras = new class_detalle_lista_compras();

                            for (int f = 0; f < mProductosList.size(); f++) {
                                if (f == RecycleProductos.getChildAdapterPosition(v)) {
                                    detalle_lista_compras.setId_producto(mProductosList.get(f).getId());
                                    detalle_lista_compras.setCategoria(mProductosList.get(f).getCategoria());
                                    detalle_lista_compras.setMarca(mProductosList.get(f).getMarca());
                                    detalle_lista_compras.setPresentacion(mProductosList.get(f).getPresentacion());
                                    detalle_lista_compras.setUnidad(mProductosList.get(f).getCodigo_barra());
                                    detalle_lista_compras.setCantidad(cantidad.getText().toString());

                                    btn_agregar.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (!detalle_lista_compras.getCategoria().isEmpty()) {
                                                String _cantidad = cantidad.getText().toString();
                                                if (_cantidad.equals("")) {
                                                    cantidad.setError("Cantidad requerida");
                                                } else {
                                                    mDetalleLista.add(detalle_lista_compras);
                                                    Adapter_detalle_lista_compras adapter_detalle_lista_compras = new Adapter_detalle_lista_compras(mDetalleLista);
                                                    RecyclerProductosAgregados.setAdapter(adapter_detalle_lista_compras);
                                                }
                                            }
                                            else {
                                                Toast.makeText(getContext(), "Seleccione un producto de la lista", Toast.LENGTH_SHORT).show();
                                            }
                                            btn_comparar.setEnabled(true);
                                            btn_guardar.setEnabled(true);
                                        }
                                    });
                                }
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

    private void limpiarDatos() {
        this.categoria.setText("");
        this.marca.setText("");
        this.presentacion.setText("");
        this.cantidad.setText("");
        this.mProductosList.clear();
        Adapter_productos adapter_productos = new Adapter_productos(mProductosList);
        this.RecycleProductos.setAdapter(adapter_productos);
        this.mDetalleLista.clear();
        //Adapter_detalle_lista_compras adapter_detalle_lista_compras = new Adapter_detalle_lista_compras(this.mDetalleLista);
        this.RecyclerProductosAgregados.setAdapter(adapter_detalle_lista_compras);
    }
}
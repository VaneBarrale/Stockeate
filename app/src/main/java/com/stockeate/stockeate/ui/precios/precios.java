package com.stockeate.stockeate.ui.precios;

import android.annotation.SuppressLint;
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
import com.stockeate.stockeate.Adapter.Adapter_productos;
import com.stockeate.stockeate.R;
import com.stockeate.stockeate.clases.class_producto;
import com.stockeate.stockeate.ui.escanear_codigos_barra.Fragment_escanear_codigos_barra;
import com.stockeate.stockeate.ui.home.HomeFragment;
import com.stockeate.stockeate.utiles.utiles;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.stockeate.stockeate.R.layout.fragment_precios;
import static com.stockeate.stockeate.R.layout.lista_productos;
import static com.stockeate.stockeate.R.layout.text_view_with_line_height_from_appearance;

public class precios extends Fragment {

    private EditText categoria, marca, presentacion, precio_nuevo;
    private Spinner local;
    private Button btn_actualizar, btn_buscar, btn_volver, btn_buscar_cod_barra;
    private PreciosViewModel viewModelPrecios;
    private ArrayList<class_producto> mProductosList;
    private TextView txv_precio_actual, txt_precios;
    private RecyclerView RecycleProductos;
    RequestQueue requestQueue;
    String URL_SERVIDOR = "https://stockeateapp.com.ar/api/products";

    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @NonNull Bundle savedInstanceState) {
        viewModelPrecios = new ViewModelProvider(this).get(PreciosViewModel.class);
        View root = inflater.inflate(fragment_precios, container, false);
        viewModelPrecios.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        this.categoria = root.findViewById(R.id.etxtCodigoProducto);
        this.marca = root.findViewById(R.id.etxtMarca);
        this.presentacion = root.findViewById(R.id.etxtPresentacion);
        this.precio_nuevo = root.findViewById(R.id.txvPrecionuevo);
        this.btn_actualizar = root.findViewById(R.id.btn_actualizar);
        this.btn_buscar = root.findViewById(R.id.btn_Buscar);
        this.btn_volver = root.findViewById(R.id.btn_Volver);
        this.btn_buscar_cod_barra = root.findViewById(R.id.btn_BuscarCodigoBarra);
        this.txv_precio_actual = root.findViewById(R.id.txvprecio_actual);
        this.txt_precios = root.findViewById(R.id.txt_precios);
        this.local = root.findViewById(R.id.sComercios);
        this.RecycleProductos = root.findViewById(R.id.RecycleProductos);

        mProductosList = new ArrayList<class_producto>();
        RecycleProductos.setLayoutManager(new LinearLayoutManager(getContext()));
        requestQueue = Volley.newRequestQueue(getContext());

        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    listar_productos();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment homeFragment = new HomeFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_precios, homeFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btn_buscar_cod_barra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                escanear();
            }
        });

        /*listaResultado.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mArrayAdapterProducto.isEmpty()) {
                    txv_precio_actual.setText("");
                    txt_precios.setText("No existen precios para las opciones seleccioadas");
                } else {
                    txt_precios.setText("");

                    String jsonFileContent = null;
                    try {
                        jsonFileContent = utiles.leerJson(getContext(), "productos.json");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    JSONArray jsonArray = null;
                    try {
                        jsonArray = new JSONArray(jsonFileContent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (local.getSelectedItem().toString().equals("Local")) {
                        Toast.makeText(getContext(), "Seleccione un local", Toast.LENGTH_SHORT).show();
                    } else {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObj = null;
                            try {
                                jsonObj = jsonArray.getJSONObject(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                if (jsonObj.getString("categoria").equals(mProductosList.get(position).getCategoria())
                                        && jsonObj.getString("marca").equals(mProductosList.get(position).getMarca())
                                        && jsonObj.getString("presentacion").equals(mProductosList.get(position).getPresentacion())
                                        && jsonObj.getString("unidad").equals(mProductosList.get(position).getUnidad())
                                        && jsonObj.getString("comercio").equals(local.getSelectedItem().toString())) {
                                    class_producto producto = new class_producto();
                                    producto.setCategoria(mProductosList.get(position).getCategoria());
                                    producto.setMarca(mProductosList.get(position).getMarca());
                                    producto.setPresentacion(mProductosList.get(position).getPresentacion());
                                    producto.setUnidad(mProductosList.get(position).getUnidad());
                                    producto.setComercio(local.getSelectedItem().toString());
                                    String precio_actual = String.valueOf(mProductosList.get(position).getPrecio());
                                    txv_precio_actual.setText(precio_actual);
                                    actualizarPrecios();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });*/

        return root;

    }

    public void escanear(){
        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(precios.this);
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
                                class_producto productos = new class_producto();
                                String code = jsonObject.getString("code");
                                if (code.equals((result.getContents()))) {
                                    mProductosList = new ArrayList<class_producto>();
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

                        int cantidad = adapter_productos.getItemCount();
                        if(cantidad == 0){
                            txt_precios.setText("No existe historial de precios para las opciones seleccioadas");
                        } else { txt_precios.setText("");}

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

    private void listar_productos() throws IOException, JSONException {
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

                        int cantidad = adapter_productos.getItemCount();
                        if(cantidad == 0){
                            txt_precios.setText("No existe historial de precios para las opciones seleccioadas");
                        } else { txt_precios.setText("");}

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
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL_SERVIDOR,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (local.getSelectedItem().toString().equals("Local")) {
                            Toast.makeText(getContext(), "Seleccione un local", Toast.LENGTH_SHORT).show();
                        } else {
                            int size = response.length();
                            for (int i = 0; i < size; i++) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response.get(i).toString());
                                    String category = jsonObject.getString("name");
                                    String brand = jsonObject.getString("brand");
                                    String presentation = jsonObject.getString("presentation");
                                    String market = "Careglio Hnos Castelli";
                                    if (category.equals(mProductosList.get(posicion).getCategoria())
                                            && brand.equals(mProductosList.get(posicion).getMarca())
                                            && presentation.equals(mProductosList.get(posicion).getPresentacion())
                                            && market.equals(local.getSelectedItem().toString())) {
                                        class_producto producto = new class_producto();
                                        producto.setCategoria(mProductosList.get(posicion).getCategoria());
                                        producto.setMarca(mProductosList.get(posicion).getMarca());
                                        producto.setPresentacion(mProductosList.get(posicion).getPresentacion());
                                        producto.setUnidad(mProductosList.get(posicion).getUnidad());
                                        producto.setComercio(local.getSelectedItem().toString());
                                        String precio_actual = String.valueOf(mProductosList.get(posicion).getPrecio());
                                        txv_precio_actual.setText(precio_actual);
                                        actualizarPrecios();
                                    } else{
                                        Toast.makeText(getContext(), "No existen precios cargados para ese producto", Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
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
        mProductosList.clear();
        txv_precio_actual.setText("");
        precio_nuevo.setText("");
        local.setSelection(0);
    }

    private void actualizarPrecios(){
        btn_actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!txv_precio_actual.getText().toString().isEmpty()) {
                    if (!precio_nuevo.getText().toString().isEmpty()) {
                        limpiarDatos();
                        Toast.makeText(getContext(), "Actualizado con exito", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getContext(), "Coloque un precio para actualizar", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Seleccione un producto para actualizar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
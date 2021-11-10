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

import static com.stockeate.stockeate.R.layout.fragment_precios;
import static com.stockeate.stockeate.R.layout.lista_productos;
import static com.stockeate.stockeate.R.layout.text_view_with_line_height_from_appearance;

public class precios extends Fragment {

    private EditText categoria, marca, presentacion, precio_nuevo;
    private Spinner local;
    private Button btn_actualizar, btn_buscar, btn_volver, btn_buscar_cod_barra;
    private PreciosViewModel viewModelPrecios;
    private ArrayList<class_producto> mProductosList = null;
    private TextView txv_precio_actual, txt_precios;
    private RecyclerView RecycleProductos;

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

        RecycleProductos.setLayoutManager(new LinearLayoutManager(getContext()));

        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProductosList = new ArrayList<class_producto>();
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
        String resultadoEscaneo = null;
        boolean guardar;
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        Toast.makeText(getContext(), result.getContents(), Toast.LENGTH_SHORT).show();
        resultadoEscaneo = (result.getContents());

        Log.d("Resultado escaneo ", "Resultado " + resultadoEscaneo);

        String jsonFileContent = null;
        try {
            jsonFileContent = utiles.leerJson(getContext(), "productos.json");
        } catch (IOException e) {
            e.printStackTrace(); }

        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(jsonFileContent);
        } catch (JSONException e) {
            e.printStackTrace(); }

        for (int i = 0; i < jsonArray.length(); i++) {
            class_producto productos = new class_producto();
            try {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                guardar = true;
                if (jsonObj.getString("codigo_barra").equals(resultadoEscaneo)) {
                    if (guardar) {
                        guardar = true;
                    } else {
                        guardar = false;
                    };
                }
                else {
                    guardar = false;
                }
                if (guardar) {
                    mProductosList = new ArrayList<class_producto>();
                    productos.setId(jsonObj.getString("id"));
                    productos.setCategoria(jsonObj.getString("categoria"));
                    productos.setMarca(jsonObj.getString("marca"));
                    productos.setPresentacion(jsonObj.getString("presentacion"));
                    productos.setUnidad(jsonObj.getString("unidad"));
                    productos.setId(jsonObj.getString("codigo_barra"));
                    productos.setPrecio(jsonObj.getDouble("precio"));
                    mProductosList.add(productos);
                }
            } catch (JSONException e) {
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

    private void listar_productos() throws IOException, JSONException {

        mProductosList.clear();
        Boolean guardar;

        //Asi lee los datos del json estatico
        String jsonFileContent = utiles.leerJson(getContext(), "productos.json");
        JSONArray jsonArray = new JSONArray(jsonFileContent);
        Log.d("Longitud json ", String.valueOf(jsonArray.length()));
        Log.d("json ", jsonArray.toString());

        for (int i = 0; i < jsonArray.length(); i++) {
            class_producto productos = new class_producto();
            Log.d("dentro del for ", String.valueOf(i));
            JSONObject jsonObj = jsonArray.getJSONObject(i);

            guardar = true;

            if (!categoria.getText().toString().isEmpty() ||
                    !marca.getText().toString().isEmpty() ||
                    !presentacion.getText().toString().isEmpty() ||
                    !presentacion.getText().toString().isEmpty() &&
                    !local.getSelectedItem().toString().equals("Local")) {
                if (!categoria.getText().toString().isEmpty()) {
                    if (jsonObj.getString("categoria").equals(categoria.getText().toString())) {
                        if (guardar) {
                            guardar = true;
                        } else {
                            guardar = false;
                        }
                        ;
                    } else {
                        guardar = false;
                    }
                }
                if (!marca.getText().toString().isEmpty()) {
                    if (jsonObj.getString("marca").equals(marca.getText().toString())) {
                        if (guardar) {
                            guardar = true;
                        } else {
                            guardar = false;
                        }
                        ;
                    } else {
                        guardar = false;
                    }
                }
                if (!presentacion.getText().toString().isEmpty()) {
                    if (jsonObj.getString("presentacion").equals(presentacion.getText().toString())) {
                        if (guardar) {
                            guardar = true;
                        } else {
                            guardar = false;
                        }
                        ;
                    } else {
                        guardar = false;
                    }
                }
                if (!local.getSelectedItem().toString().equals("Local")) {
                    if (jsonObj.getString("comercio").equals(local.getSelectedItem().toString())) {
                        if (guardar) {
                            guardar = true;
                        } else {
                            guardar = false;
                        }
                        ;
                    } else {
                        guardar = false;
                    }
                }
                if (guardar) {
                    productos.setId(jsonObj.getString("id"));
                    productos.setCategoria(jsonObj.getString("categoria"));
                    productos.setMarca(jsonObj.getString("marca"));
                    productos.setPresentacion(jsonObj.getString("presentacion"));
                    productos.setUnidad(jsonObj.getString("unidad"));
                    productos.setCodigo_barra(jsonObj.getString("codigo_barra"));
                    productos.setPrecio(jsonObj.getDouble("precio"));
                    mProductosList.add(productos);
                }
            }
        }
        mProductosList.removeAll(Collections.singleton(null));
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

    public void buscarProducto(int posicion) throws IOException, JSONException {
        String jsonFileContent = null;
        jsonFileContent = utiles.leerJson(getContext(), "productos.json");
        JSONArray jsonArray = null;
        jsonArray = new JSONArray(jsonFileContent);
        if (local.getSelectedItem().toString().equals("Local")) {
            Toast.makeText(getContext(), "Seleccione un local", Toast.LENGTH_SHORT).show();
        } else {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = null;
                jsonObj = jsonArray.getJSONObject(i);
                if (jsonObj.getString("categoria").equals(mProductosList.get(posicion).getCategoria())
                        && jsonObj.getString("marca").equals(mProductosList.get(posicion).getMarca())
                        && jsonObj.getString("presentacion").equals(mProductosList.get(posicion).getPresentacion())
                        && jsonObj.getString("unidad").equals(mProductosList.get(posicion).getUnidad())
                        && jsonObj.getString("comercio").equals(local.getSelectedItem().toString())) {
                    class_producto producto = new class_producto();
                    producto.setCategoria(mProductosList.get(posicion).getCategoria());
                    producto.setMarca(mProductosList.get(posicion).getMarca());
                    producto.setPresentacion(mProductosList.get(posicion).getPresentacion());
                    producto.setUnidad(mProductosList.get(posicion).getUnidad());
                    producto.setComercio(local.getSelectedItem().toString());
                    String precio_actual = String.valueOf(mProductosList.get(posicion).getPrecio());
                    txv_precio_actual.setText(precio_actual);
                    actualizarPrecios();
                }
            }
        }
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
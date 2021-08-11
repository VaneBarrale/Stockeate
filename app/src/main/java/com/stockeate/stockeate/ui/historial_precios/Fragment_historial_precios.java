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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
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

public class Fragment_historial_precios extends Fragment {

    private HistorialPreciosViewModel historialPreciosViewModel;
    private Button btn_buscar, btn_buscar_codigo_barra, btn_buscar_local, btn_volver;
    private EditText categoria, marca, presentacion, locales;
    private ListView listaResultado;
    private ArrayAdapter<class_producto> mArrayAdapterProducto;
    private ArrayList<class_producto> mProductosList = null;
    private ArrayList<class_historial_precios> mHistorialPrecio = null;
    private ArrayAdapter<class_historial_precios> mAdapterHistorialPrecio;
    private ListView productos_historial;

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
        this.btn_buscar_local = root.findViewById(R.id.btn_buscar_local);
        this.btn_volver = root.findViewById(R.id.btn_Volver);
        this.categoria = root.findViewById(R.id.etxtCodigoProducto);
        this.marca = root.findViewById(R.id.etxtMarca);
        this.presentacion = root.findViewById(R.id.etxtPresentacion);
        this.locales = root.findViewById(R.id.txtLocales);
        this.listaResultado = root.findViewById(R.id.ListaProductos);
        this.productos_historial = root.findViewById(R.id.listaHistorial);

        btn_buscar_local.setEnabled(false);

        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProductosList = new ArrayList<class_producto>();
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

        listaResultado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Boolean guardar = true;
                mHistorialPrecio = new ArrayList<class_historial_precios>();

                String jsonFileContent = null;
                try {
                    jsonFileContent = utiles.leerJson(getContext(), "HistorialPrecios.json");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(jsonFileContent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObj = null;
                    try {
                        jsonObj = jsonArray.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (jsonObj.getString("categoria").equals(mProductosList.get(position).getCategoria())
                                &&jsonObj.getString("marca").equals(mProductosList.get(position).getMarca())
                                &&jsonObj.getString("presentacion").equals(mProductosList.get(position).getPresentacion())
                                &&jsonObj.getString("unidad").equals(mProductosList.get(position).getUnidad())
                                &&jsonObj.getString("id_comercio").equals(locales.getText().toString())) {

                            class_historial_precios historial_precios = new class_historial_precios();
                            historial_precios.setCategoria(mProductosList.get(position).getCategoria());
                            historial_precios.setMarca(mProductosList.get(position).getMarca());
                            historial_precios.setPresentacion(mProductosList.get(position).getPresentacion());
                            historial_precios.setUnidad(mProductosList.get(position).getUnidad());
                            historial_precios.setComercio(jsonObj.getString("comercio"));
                            historial_precios.setPrecio(Float.parseFloat(jsonObj.getString("precio")));
                            mHistorialPrecio.add(historial_precios);
                            Log.d("Precio", "Precio " + mHistorialPrecio.toString());
                            btn_buscar_local.setEnabled(true);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                btn_buscar_local.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAdapterHistorialPrecio = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mHistorialPrecio);
                        productos_historial.setAdapter(mAdapterHistorialPrecio);
                    }
                });
            }
        });

        return root;

    }

    private void listarproductos() throws IOException, JSONException {
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
                    !presentacion.getText().toString().isEmpty() ||
                    !locales.getText().toString().isEmpty()) {
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
                if (!locales.getText().toString().isEmpty()) {
                    if (jsonObj.getString("id_comercio").equals(locales.getText().toString())) {
                        if (guardar) {
                            guardar = true;
                        } else {
                            guardar = false;
                        }
                        ;
                    } else {
                        Toast.makeText(getContext(), "Ingrese un local", Toast.LENGTH_SHORT).show();
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
                    mProductosList.add(productos);
                }
            }
        }

        mProductosList.removeAll(Collections.singleton(null));
        mArrayAdapterProducto = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mProductosList);
        listaResultado.setAdapter(mArrayAdapterProducto);
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

        mProductosList.removeAll(Collections.singleton(null));
        mArrayAdapterProducto = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mProductosList);
        listaResultado.setAdapter(mArrayAdapterProducto);

    }
}
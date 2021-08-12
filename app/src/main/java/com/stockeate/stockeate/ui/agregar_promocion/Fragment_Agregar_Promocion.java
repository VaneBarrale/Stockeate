package com.stockeate.stockeate.ui.agregar_promocion;

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
import com.stockeate.stockeate.clases.class_lista_compras;
import com.stockeate.stockeate.clases.class_producto;
import com.stockeate.stockeate.ui.detalle_lista_precios.Fragment_Detalle_Lista_Precios;
import com.stockeate.stockeate.ui.lista_compras.Fragment_lista_compras;
import com.stockeate.stockeate.ui.promocion.Fragment_Promocion;
import com.stockeate.stockeate.ui.ubicacion.ubicacion;
import com.stockeate.stockeate.utiles.utiles;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Fragment_Agregar_Promocion extends Fragment {

    private ViewModel_agregar_promocion AgregarPromocionViewModel;
    private Button btn_volver, btn_buscar, btn_buscar_codigo_barra, btn_agregar;
    private EditText categoria, marca, presentacion, cantidad;
    private Spinner comercio, promociones;
    private ArrayAdapter<class_producto> mArrayAdapterProducto;
    private ArrayList<class_producto> mProductosList = null;
    private ListView listaResultado;

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
        this.listaResultado = root.findViewById(R.id.ListViewResultado);
        this.categoria = root.findViewById(R.id.etxtCodigoProducto);
        this.marca = root.findViewById(R.id.etxtMarca);
        this.presentacion = root.findViewById(R.id.etxtPresentacion);
        this.cantidad = root.findViewById(R.id.etxtCantidad);
        this.comercio = root.findViewById(R.id.sComercios);
        this.promociones = root.findViewById(R.id.sPromociones);

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

        listaResultado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                    !presentacion.getText().toString().isEmpty()){
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
        comercio.setVisibility(View.VISIBLE);
        promociones.setVisibility(View.VISIBLE);
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

    private void limpiarDatos() {
        categoria.setText("");
        marca.setText("");
        presentacion.setText("");
        cantidad.setText("");
        comercio.setVisibility(View.INVISIBLE);
        promociones.setVisibility(View.INVISIBLE);
        mArrayAdapterProducto.clear();
    }
}
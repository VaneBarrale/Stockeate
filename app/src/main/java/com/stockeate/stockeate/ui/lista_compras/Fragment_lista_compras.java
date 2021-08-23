package com.stockeate.stockeate.ui.lista_compras;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.stockeate.stockeate.Adapter.Adapter_detalle_lista_compras;
import com.stockeate.stockeate.Adapter.Adapter_productos;
import com.stockeate.stockeate.R;
import com.stockeate.stockeate.clases.class_detalle_lista_compras;
import com.stockeate.stockeate.clases.class_lista_compras;
import com.stockeate.stockeate.clases.class_producto;
import com.stockeate.stockeate.ui.escanear_codigos_barra.Fragment_escanear_codigos_barra;
import com.stockeate.stockeate.ui.mis_listas_compras.Fragment_mis_listas_compras;
import com.stockeate.stockeate.ui.comparar_precios.Fragment_Comparar_Precios;
import com.stockeate.stockeate.ui.home.HomeFragment;
import com.stockeate.stockeate.ui.precios.precios;
import com.stockeate.stockeate.utiles.utiles;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;

public class Fragment_lista_compras extends Fragment {

    private ViewModel_lista_compras viewModelListacompras;
    private Button btn_comparar, btn_volver, btn_agregar, btn_buscar, btn_guardar, btn_listas, btn_cod_barra;
    private EditText categoria, marca, presentacion, cantidad, unidad;
    private ArrayList<class_producto> mProductosList;
    private ArrayList<class_detalle_lista_compras> mDetalleLista = null;
    private RecyclerView RecycleProductos, RecyclerProductosAgregados;

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
        this.unidad = root.findViewById(R.id.etxtUnidad);
        this.RecyclerProductosAgregados = root.findViewById(R.id.RecyclerProductosAgregados);
        this.RecycleProductos = root.findViewById(R.id.RecycleProductos);

        RecycleProductos.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerProductosAgregados.setLayoutManager(new LinearLayoutManager(getContext()));

        btn_comparar.setEnabled(false);
        btn_guardar.setEnabled(false);

        //todos los metodos que usen firebase deben ir abajo del inicializador

        mDetalleLista = new ArrayList<class_detalle_lista_compras>();

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
        RecycleProductos.setVisibility(View.VISIBLE);
        Adapter_productos adapter_productos = new Adapter_productos(mProductosList);
        RecycleProductos.setAdapter(adapter_productos);

        adapter_productos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                class_detalle_lista_compras detalle_lista_compras = new class_detalle_lista_compras();

                Log.d("Detalle", "Detalle Seleccionado" + RecycleProductos.getChildAdapterPosition(v));

                for (int f = 0; f < mProductosList.size(); f++) {
                    if (f == RecycleProductos.getChildAdapterPosition(v)) {
                        detalle_lista_compras.setId_producto(mProductosList.get(f).getId());
                        detalle_lista_compras.setCategoria(mProductosList.get(f).getCategoria());
                        detalle_lista_compras.setMarca(mProductosList.get(f).getMarca());
                        detalle_lista_compras.setPresentacion(mProductosList.get(f).getPresentacion());
                        detalle_lista_compras.setUnidad(mProductosList.get(f).getUnidad());
                        detalle_lista_compras.setCantidad(cantidad.getText().toString());
                        Log.d("Detalle", "Detalle " + detalle_lista_compras);
                        Log.d("Detalle", "Detalle Size" + mProductosList.size());

                        btn_agregar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!detalle_lista_compras.getId_producto().isEmpty()) {
                                    String _cantidad = cantidad.getText().toString();
                                    if (_cantidad.equals("")) {
                                        cantidad.setError("Cantidad requerida");
                                    } else {
                                        mDetalleLista.add(detalle_lista_compras);
                                        Adapter_detalle_lista_compras adapter_detalle_lista_compras = new Adapter_detalle_lista_compras(mDetalleLista);
                                        RecyclerProductosAgregados.setAdapter(adapter_detalle_lista_compras);
                                        Log.i("Detalle Lista", mDetalleLista.toString());
                                    }
                                }
                                //ESTE MENSAJE NO SALE!
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

    private void listarproductos() throws IOException, JSONException {
        mProductosList = new ArrayList<class_producto>();
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
                    !unidad.getText().toString().isEmpty()) {
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
                if (!unidad.getText().toString().isEmpty()) {
                    if (jsonObj.getString("unidad").equals(unidad.getText().toString())) {
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
                    Log.d("Productos", "Productos" + mProductosList.toString());
                }
            }
        }

        Adapter_productos adapter_productos = new Adapter_productos(mProductosList);
        RecycleProductos.setAdapter(adapter_productos);

        adapter_productos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                class_detalle_lista_compras detalle_lista_compras = new class_detalle_lista_compras();

                Log.d("Detalle", "Detalle Seleccionado" + RecycleProductos.getChildAdapterPosition(v));

                for (int f = 0; f < mProductosList.size(); f++) {
                    if (f == RecycleProductos.getChildAdapterPosition(v)) {
                        detalle_lista_compras.setId_producto(mProductosList.get(f).getId());
                        detalle_lista_compras.setCategoria(mProductosList.get(f).getCategoria());
                        detalle_lista_compras.setMarca(mProductosList.get(f).getMarca());
                        detalle_lista_compras.setPresentacion(mProductosList.get(f).getPresentacion());
                        detalle_lista_compras.setUnidad(mProductosList.get(f).getUnidad());
                        detalle_lista_compras.setCantidad(cantidad.getText().toString());
                        Log.d("Detalle", "Detalle " + detalle_lista_compras);
                        Log.d("Detalle", "Detalle Size" + mProductosList.size());

                        btn_agregar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!detalle_lista_compras.getId_producto().isEmpty()) {
                                    String _cantidad = cantidad.getText().toString();
                                    if (_cantidad.equals("")) {
                                        cantidad.setError("Cantidad requerida");
                                    } else {
                                        mDetalleLista.add(detalle_lista_compras);
                                        Adapter_detalle_lista_compras adapter_detalle_lista_compras = new Adapter_detalle_lista_compras(mDetalleLista);
                                        RecyclerProductosAgregados.setAdapter(adapter_detalle_lista_compras);
                                        adapter_detalle_lista_compras.setOnLongClickListener(new View.OnLongClickListener() {
                                            @Override
                                            public boolean onLongClick(View view) {
                                                mDetalleLista.remove(RecyclerProductosAgregados.getChildAdapterPosition(view));
                                                Adapter_detalle_lista_compras adapter_detalle_lista_compras = new Adapter_detalle_lista_compras(mDetalleLista);
                                                RecyclerProductosAgregados.setAdapter(adapter_detalle_lista_compras);
                                                Log.d("LONGCLICK", "Entro al LONGCLICK");
                                                return true;
                                            }
                                        });
                                        Log.i("Detalle Lista", mDetalleLista.toString());
                                    }
                                }
                                //ESTE MENSAJE NO SALE!
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

    private void limpiarDatos() {
        this.categoria.setText("");
        this.marca.setText("");
        this.presentacion.setText("");
        this.cantidad.setText("");
        this.unidad.setText("");
        this.mProductosList.clear();
        Adapter_productos adapter_productos = new Adapter_productos(mProductosList);
        this.RecycleProductos.setAdapter(adapter_productos);
        this.mDetalleLista.clear();
        Adapter_detalle_lista_compras adapter_detalle_lista_compras = new Adapter_detalle_lista_compras(this.mDetalleLista);
        this.RecyclerProductosAgregados.setAdapter(adapter_detalle_lista_compras);
    }
}
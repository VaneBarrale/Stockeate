package com.stockeate.stockeate.ui.categorizacion_precios;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.stockeate.stockeate.Adapter.Adapter_categorizacion_precios;
import com.stockeate.stockeate.Adapter.Adapter_productos;
import com.stockeate.stockeate.R;
import com.stockeate.stockeate.clases.class_categorizacion_precios;
import com.stockeate.stockeate.clases.class_producto;
import com.stockeate.stockeate.ui.reportes.Fragment_reportes;
import com.stockeate.stockeate.utiles.utiles;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

public class Fragment_categorizacion_precios extends Fragment {

    private CategorizacionPreciosViewModel categorizacionPreciosViewModel;
    private Button btn_buscar, btn_volver;
    private EditText categoria;
    private ArrayList<class_producto> mProductosList = null;
    private ArrayList<class_categorizacion_precios> mComparacion = null;
    private RecyclerView RecycleProductos, RecyclePreciosCategoria;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        categorizacionPreciosViewModel = new ViewModelProvider(this).get(CategorizacionPreciosViewModel.class);
        View root = inflater.inflate(R.layout.fragment_categorizacion_precios, container, false);
        categorizacionPreciosViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        this.btn_buscar = root.findViewById(R.id.btn_Buscar);
        this.btn_volver = root.findViewById(R.id.btn_Volver);
        this.categoria = root.findViewById(R.id.etxtCodigoProducto);
        this.RecycleProductos = root.findViewById(R.id.RecycleProductos);
        this.RecyclePreciosCategoria = root.findViewById(R.id.RecyclePreciosCategoria);

        RecycleProductos.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclePreciosCategoria.setLayoutManager(new LinearLayoutManager(getContext()));

        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mProductosList = new ArrayList<class_producto>();
                    listarproductos();
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
                Fragment_reportes fragment_reportes = new Fragment_reportes();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_categorizacion_precios, fragment_reportes);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return root;

    }

    private void listarproductos() throws IOException, JSONException {
        mProductosList.clear();

        Boolean guardar = true;

        //Asi lee los datos del json estatico
        String jsonFileContent = utiles.leerJson(getContext(), "productos.json");
        JSONArray jsonArray = new JSONArray(jsonFileContent);
        for (int i = 0; i < jsonArray.length(); i++) {
            class_producto productos = new class_producto();
            JSONObject jsonObj = jsonArray.getJSONObject(i);
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
        Adapter_productos adapter_productos = new Adapter_productos(mProductosList);
        RecycleProductos.setAdapter(adapter_productos);

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
        mComparacion = new ArrayList<class_categorizacion_precios>();
        mComparacion.clear();

        String jsonFileContent = utiles.leerJson(getContext(), "productos.json");
        JSONArray jsonArray = new JSONArray(jsonFileContent);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObj = null;
            jsonObj = jsonArray.getJSONObject(i);
            if (jsonObj.getString("categoria").equals(mProductosList.get(posicion).getCategoria())
                    && jsonObj.getString("marca").equals(mProductosList.get(posicion).getMarca())
                    && jsonObj.getString("presentacion").equals(mProductosList.get(posicion).getPresentacion())
                    && jsonObj.getString("unidad").equals(mProductosList.get(posicion).getUnidad())) {

                class_categorizacion_precios precios_categoria = new class_categorizacion_precios();
                precios_categoria.setCategoria(mProductosList.get(posicion).getCategoria());
                precios_categoria.setMarca(mProductosList.get(posicion).getMarca());
                precios_categoria.setPresentacion(mProductosList.get(posicion).getPresentacion());
                precios_categoria.setUnidad(mProductosList.get(posicion).getUnidad());
                precios_categoria.setLocal(jsonObj.getString("comercio"));
                precios_categoria.setPrecio_total(Float.parseFloat(jsonObj.getString("precio")));
                mComparacion.add(precios_categoria);
                Log.d("Precio", "Precio lista" + mComparacion.toString());
            }
        }
        Adapter_categorizacion_precios adapter_categorizacion_precios = new Adapter_categorizacion_precios(mComparacion);
        RecyclePreciosCategoria.setAdapter(adapter_categorizacion_precios);
    }
}
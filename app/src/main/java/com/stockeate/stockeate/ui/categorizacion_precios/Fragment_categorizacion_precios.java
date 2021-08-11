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

import com.stockeate.stockeate.R;
import com.stockeate.stockeate.clases.class_categorizacion_precios;
import com.stockeate.stockeate.clases.class_producto;
import com.stockeate.stockeate.ui.reportes.Fragment_reportes;
import com.stockeate.stockeate.utiles.utiles;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Fragment_categorizacion_precios extends Fragment {

    private CategorizacionPreciosViewModel categorizacionPreciosViewModel;
    private Button btn_buscar, btn_volver;
    private EditText categoria;
    private ListView listaResultado, listaPreciosCategoria;
    private ArrayAdapter<class_producto> mArrayAdapterProducto;
    private ArrayList<class_producto> mProductosList = null;
    private ArrayAdapter<class_categorizacion_precios> mArrayAdapterComparacion;
    private ArrayList<class_categorizacion_precios> mComparacion = null;

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
        this.listaResultado = root.findViewById(R.id.listaCategorias);
        this.listaPreciosCategoria = root.findViewById(R.id.listaPreciosCategoria);

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

        listaResultado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Boolean guardar = true;
                mComparacion = new ArrayList<class_categorizacion_precios>();

                //Asi lee los datos del json estatico
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
                                && jsonObj.getString("unidad").equals(mProductosList.get(position).getUnidad())) {

                            class_categorizacion_precios precios_categoria = new class_categorizacion_precios();
                            precios_categoria.setCategoria(mProductosList.get(position).getCategoria());
                            precios_categoria.setMarca(mProductosList.get(position).getMarca());
                            precios_categoria.setPresentacion(mProductosList.get(position).getPresentacion());
                            precios_categoria.setUnidad(mProductosList.get(position).getUnidad());
                            precios_categoria.setLocal(jsonObj.getString("comercio"));
                            precios_categoria.setPrecio_total(Float.parseFloat(jsonObj.getString("precio")));
                            mComparacion.add(precios_categoria);
                            Log.d("Precio", "Precio lista" + mComparacion.toString());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                mArrayAdapterComparacion = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mComparacion);
                listaPreciosCategoria.setAdapter(mArrayAdapterComparacion);
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
        mProductosList.removeAll(Collections.singleton(null));
        mArrayAdapterProducto = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mProductosList);
        listaResultado.setAdapter(mArrayAdapterProducto);
    }
}
package com.stockeate.stockeate.ui.categoria;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.stockeate.stockeate.R;
import com.stockeate.stockeate.clases.class_comparar_precios;
import com.stockeate.stockeate.clases.class_top_10;
import com.stockeate.stockeate.ui.comparar_precios.Fragment_Comparar_Precios;
import com.stockeate.stockeate.ui.reportes.Fragment_reportes;
import com.stockeate.stockeate.ui.reportes.ReportesViewModel;
import com.stockeate.stockeate.utiles.utiles;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Fragment_categorias extends Fragment {

    private CategoriasViewModel categoriasViewModel;
    private Button btn_volver;
    private ListView listaCategorias;
    private ArrayList<class_top_10> mTop10List = null;
    private ArrayAdapter<class_top_10> mArrayAdapterComparar;
    private String usuario;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        categoriasViewModel = new ViewModelProvider(this).get(CategoriasViewModel.class);
        View root = inflater.inflate(R.layout.fragment_categorias, container, false);
        categoriasViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

    btn_volver = root.findViewById(R.id.btn_Volver);
    listaCategorias = root.findViewById(R.id.listaCategorias);

        try {
            cargarDatosTabla();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        btn_volver.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Fragment_reportes fragment_reportes = new Fragment_reportes();
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_categorias, fragment_reportes);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    });

    return root;

    }

    public void cargarDatosTabla() throws IOException, JSONException {

        String jsonFileContent = utiles.leerJson(getContext(), "Top10Categorias.json");
        JSONArray jsonArray = new JSONArray(jsonFileContent);

        mTop10List = new ArrayList<class_top_10>();

        for (int i = 0; i < jsonArray.length(); i++) {
            class_top_10 class_top_10 = new class_top_10();
            JSONObject jsonObj = jsonArray.getJSONObject(i);
            Log.d("Esta leyendo", "entro");
            if (jsonObj.getString("id_usuario").equals("5")){
                Log.d("Esta leyendo", "entro al if");
                class_top_10.setCategoria(jsonObj.getString("categoria"));
                class_top_10.setTop(jsonObj.getString("top"));
                mTop10List.add(class_top_10);
                }
        }
        mArrayAdapterComparar = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mTop10List);
        listaCategorias.setAdapter(mArrayAdapterComparar);
    }
}
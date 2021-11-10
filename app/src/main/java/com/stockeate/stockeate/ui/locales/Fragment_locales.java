package com.stockeate.stockeate.ui.locales;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.stockeate.stockeate.Adapter.Adapter_Top10Locales;
import com.stockeate.stockeate.Adapter.Adapter_Top10Marcas;
import com.stockeate.stockeate.R;
import com.stockeate.stockeate.clases.class_top_10;
import com.stockeate.stockeate.ui.reportes.Fragment_reportes;
import com.stockeate.stockeate.utiles.utiles;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class Fragment_locales extends Fragment {

    private LocalesViewModel localesViewModel;
    private Button btn_volver;
    RecyclerView recycleLocales;
    private ArrayList<class_top_10> mTop10List = null;
    private ArrayAdapter<class_top_10> mArrayAdapterLocales;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        localesViewModel = new ViewModelProvider(this).get(LocalesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_locales, container, false);
        localesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        btn_volver = root.findViewById(R.id.btn_Volver);
        recycleLocales = root.findViewById(R.id.RecycleLocales);

        recycleLocales.setLayoutManager(new LinearLayoutManager(getContext()));

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
                transaction.replace(R.id.fragment_locales, fragment_reportes);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

    return root;

    }

    public void cargarDatosTabla() throws IOException, JSONException {

        String jsonFileContent = utiles.leerJson(getContext(), "Top10Locales.json");
        JSONArray jsonArray = new JSONArray(jsonFileContent);

        mTop10List = new ArrayList<class_top_10>();

        for (int i = 0; i < jsonArray.length(); i++) {
            class_top_10 class_top_10 = new class_top_10();
            JSONObject jsonObj = jsonArray.getJSONObject(i);
            if (jsonObj.getString("id_usuario").equals("5")){
                class_top_10.setTop(jsonObj.getString("top"));
                class_top_10.setLocal(jsonObj.getString("local"));
                mTop10List.add(class_top_10);
            }
        }
        Adapter_Top10Locales adapter_top10Locales = new Adapter_Top10Locales(mTop10List);
        recycleLocales.setAdapter(adapter_top10Locales);
    }
}
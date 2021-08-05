package com.stockeate.stockeate.ui.Ranking;

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

import com.stockeate.stockeate.R;
import com.stockeate.stockeate.clases.class_top_10;
import com.stockeate.stockeate.ui.reportes.Fragment_reportes;
import com.stockeate.stockeate.utiles.utiles;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class Fragment_ranking extends Fragment {

    private RankingViewModel rankingViewModel;
    private Button btn_volver;
    private ListView lista_usuarios;
    private ArrayList<class_top_10> mTop10List = null;
    private ArrayAdapter<class_top_10> mArrayAdapterUsuarios;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rankingViewModel = new ViewModelProvider(this).get(RankingViewModel.class);
        View root = inflater.inflate(R.layout.fragment_ranking, container, false);
        rankingViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        btn_volver = root.findViewById(R.id.btn_Volver);
        lista_usuarios = root.findViewById(R.id.listaUsuarios);

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
                transaction.replace(R.id.fragment_ranking, fragment_reportes);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

    return root;

    }

    public void cargarDatosTabla() throws IOException, JSONException {

        String jsonFileContent = utiles.leerJson(getContext(), "Top10Usuarios.json");
        JSONArray jsonArray = new JSONArray(jsonFileContent);

        mTop10List = new ArrayList<class_top_10>();

        for (int i = 0; i < jsonArray.length(); i++) {
            class_top_10 class_top_10 = new class_top_10();
            JSONObject jsonObj = jsonArray.getJSONObject(i);
            class_top_10.setCategoria(jsonObj.getString("usuario"));
            class_top_10.setTop(jsonObj.getString("top"));
            mTop10List.add(class_top_10);
        }
        mArrayAdapterUsuarios = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mTop10List);
        lista_usuarios.setAdapter(mArrayAdapterUsuarios);
    }
}
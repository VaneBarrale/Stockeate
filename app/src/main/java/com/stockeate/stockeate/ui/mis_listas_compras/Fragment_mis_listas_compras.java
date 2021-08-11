package com.stockeate.stockeate.ui.mis_listas_compras;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.stockeate.stockeate.R;
import com.stockeate.stockeate.clases.class_lista_compras;
import com.stockeate.stockeate.ui.detalle_mis_listas_compras.Fragment_Detalle_Mis_Listas_Compras;
import com.stockeate.stockeate.ui.lista_compras.Fragment_lista_compras;
import com.stockeate.stockeate.utiles.utiles;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class Fragment_mis_listas_compras extends Fragment {

    private ViewModel_mis_listas_compras viewModel_mis_listas_compras;
    private Button btn_volver, btn_detalle;
    private ArrayList<class_lista_compras> mMisListas = null;
    private ListView listaDetalle;
    private ArrayAdapter<class_lista_compras> mArrayAdapterMisListas;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel_mis_listas_compras =
                new ViewModelProvider(this).get(ViewModel_mis_listas_compras.class);
        View root = inflater.inflate(R.layout.fragment_mis_listas_compras, container, false);
        viewModel_mis_listas_compras.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        this.btn_volver = root.findViewById(R.id.btn_Volver);
        this.btn_detalle = root.findViewById(R.id.btn_Detalle);
        this.listaDetalle = root.findViewById(R.id.lw_detalle_lista_compras);

        btn_detalle.setEnabled(false);

        try {
            mostrarLista();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_lista_compras lista_compras = new Fragment_lista_compras();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_mis_listas_compras, lista_compras);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        listaDetalle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int i = 0, j = 0;

                btn_detalle.setEnabled(true);

                String id_lista = mMisListas.get(position).getId_lista_compras();
                Log.d("Lista de compras", "La lista de compras es " + id_lista);
                btn_detalle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle datosAEnviar = new Bundle();
                        datosAEnviar.putString("id_lista_compras", id_lista);
                        Fragment_Detalle_Mis_Listas_Compras detalle_mis_listas_compras = new Fragment_Detalle_Mis_Listas_Compras();
                        detalle_mis_listas_compras.setArguments(datosAEnviar);
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                        try {
                            detalle_mis_listas_compras.leerDatos(getContext());
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        transaction.replace(R.id.fragment_mis_listas_compras, detalle_mis_listas_compras);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                });
            }
        });

        return root;
    }

    private void mostrarLista() throws IOException, JSONException {

        mMisListas = new ArrayList<class_lista_compras>();

        String jsonFileContent = utiles.leerJson(getContext(), "mis_listas_compras.json");
        JSONArray jsonArray = new JSONArray(jsonFileContent);
        Log.d("Longitud json detalle", String.valueOf(jsonArray.length()));
        Log.d("json ", jsonArray.toString());
        for (int i = 0; i < jsonArray.length(); i++) {

            class_lista_compras class_lista_compras = new class_lista_compras();

            JSONObject jsonObj = jsonArray.getJSONObject(i);
            if(jsonObj.getString("id_usuario").equals("1")){
                class_lista_compras.setId(jsonObj.getString("id"));
                class_lista_compras.setId_lista_compras(jsonObj.getString("id_lista_compras"));
                class_lista_compras.setFecha(jsonObj.getString("fecha"));
                mMisListas.add(class_lista_compras);
            }
        }
        mArrayAdapterMisListas = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mMisListas);
        listaDetalle.setAdapter(mArrayAdapterMisListas);

    }
}
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.stockeate.stockeate.Adapter.Adapter_detalle_MIS_lista_compras;
import com.stockeate.stockeate.Adapter.Adapter_detalle_lista_compras;
import com.stockeate.stockeate.Adapter.Adapter_mis_listas;
import com.stockeate.stockeate.R;
import com.stockeate.stockeate.clases.class_detalle_lista_compras;
import com.stockeate.stockeate.clases.class_lista_compras;
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
    private TextView txt_lista;
    private ArrayList<class_lista_compras> mMisListas = null;
    private ArrayList<class_detalle_lista_compras> mDetalleLista = null;
    private RecyclerView RecycleMisListas, RecycleDetalleMisListas;

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

        this.txt_lista = root.findViewById(R.id.txt_lista);
        this.btn_volver = root.findViewById(R.id.btn_Volver);
        this.btn_detalle = root.findViewById(R.id.btn_Detalle);
        this.RecycleDetalleMisListas = root.findViewById(R.id.RecycleDetalleMisListas);
        this.RecycleMisListas = root.findViewById(R.id.RecycleMisListas);

        RecycleMisListas.setLayoutManager(new LinearLayoutManager(getContext()));
        RecycleDetalleMisListas.setLayoutManager(new LinearLayoutManager(getContext()));

        mDetalleLista = new ArrayList<class_detalle_lista_compras>();

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

        /*listaDetalle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int i = 0, j = 0;

                btn_detalle.setEnabled(true);

                String id_lista = mMisListas.get(position).getId_lista_compras();
                Log.d("Lista de compras", "La lista de compras es " + id_lista);
                btn_detalle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        txt_lista.setText("Lista de compras " + id_lista);

                        mDetalleLista = new ArrayList<class_detalle_lista_compras>();

                        String jsonFileContent = null;
                        try {
                            jsonFileContent = utiles.leerJson(getContext(), "detalle_lista_compras.json");
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

                            class_detalle_lista_compras detalle_lista_compras = new class_detalle_lista_compras();
                            JSONObject jsonObj = null;
                            try {
                                jsonObj = jsonArray.getJSONObject(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //hasta aca pasa 11/08
                            try {
                                if(jsonObj.getString("id_lista_compras").equals(id_lista)){
                                    Log.i("Paso por el if", "if");
                                    detalle_lista_compras.setId(jsonObj.getString("id"));
                                    detalle_lista_compras.setId_lista_compras(jsonObj.getString("id_lista_compras"));
                                    detalle_lista_compras.setCategoria(jsonObj.getString("categoria"));
                                    detalle_lista_compras.setMarca(jsonObj.getString("marca"));
                                    detalle_lista_compras.setPresentacion(jsonObj.getString("presentacion"));
                                    detalle_lista_compras.setUnidad(jsonObj.getString("unidad"));
                                    detalle_lista_compras.setCantidad(jsonObj.getString("cantidad"));
                                    mDetalleLista.add(detalle_lista_compras);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        mArrayAdapterDetalle = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mDetalleLista);
                        listaDetalleResultado.setAdapter(mArrayAdapterDetalle);
                    }
                });
            }
        });*/

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
        Adapter_mis_listas adapter_mis_listas = new Adapter_mis_listas(mMisListas);
        RecycleMisListas.setAdapter(adapter_mis_listas);
        btn_detalle.setEnabled(true);

        adapter_mis_listas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int posicion = RecycleMisListas.getChildAdapterPosition(v);
                class_detalle_lista_compras detalle_lista_compras = new class_detalle_lista_compras();

                btn_detalle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*String id_lista = mMisListas.get(RecycleMisListas.getChildAdapterPosition(v)).getId_lista_compras();
                        txt_lista.setText("Lista de compras " + id_lista);*/

                        Log.d("Detalle", "Detalle Seleccionado" + posicion);
                        String jsonFileContent = null;
                            try {
                                jsonFileContent = utiles.leerJson(getContext(), "detalle_lista_compras.json");
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
                                    for (int f = 0; f < mMisListas.size(); f++) {
                                        if (f == posicion) {
                                            if (jsonObj.getString("id_lista_compras").equals(mMisListas.get(posicion).getId_lista_compras())) {
                                                detalle_lista_compras.setId(jsonObj.getString("id"));
                                                detalle_lista_compras.setId_lista_compras(jsonObj.getString("id_lista_compras"));
                                                detalle_lista_compras.setCategoria(jsonObj.getString("categoria"));
                                                detalle_lista_compras.setMarca(jsonObj.getString("marca"));
                                                detalle_lista_compras.setPresentacion(jsonObj.getString("presentacion"));
                                                detalle_lista_compras.setUnidad(jsonObj.getString("unidad"));
                                                detalle_lista_compras.setCantidad(jsonObj.getString("cantidad"));
                                                Log.i("Paso por el IF", "productos " + mDetalleLista.toString());
                                                mDetalleLista.add(detalle_lista_compras);
                                            }
                                        }
                                    }
                                }catch (JSONException e) {
                                        e.printStackTrace();
                                }
                            }
                        Adapter_detalle_MIS_lista_compras adapter_detalle_mis_lista_compras = new Adapter_detalle_MIS_lista_compras(mDetalleLista);
                        RecycleDetalleMisListas.setAdapter(adapter_detalle_mis_lista_compras);
                        }
                });
            }
        });
    }
}
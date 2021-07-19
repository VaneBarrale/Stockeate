package com.stockeate.stockeate.ui.comparar_precios;

import android.location.Location;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.stockeate.stockeate.R;
import com.stockeate.stockeate.clases.class_comparar_precios;
import com.stockeate.stockeate.clases.class_detalle_lista_compras;
import com.stockeate.stockeate.clases.class_producto;
import com.stockeate.stockeate.ui.detalle_lista_precios.Fragment_Detalle_Lista_Precios;
import com.stockeate.stockeate.ui.lista_compras.Fragment_lista_compras;
import com.stockeate.stockeate.ui.ubicacion.ubicacion;
import com.stockeate.stockeate.utiles.utiles;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Fragment_Comparar_Precios extends Fragment {

    private ViewModel_comparar_precios CompararViewModel;
    private Button btn_detalle, btn_volver, btn_como_llegar;
    private ArrayList<class_comparar_precios> mComparacionList = null;
    private ArrayAdapter<class_comparar_precios> mArrayAdapterComparar;
    private ListView listaComparacion;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        CompararViewModel = new ViewModelProvider(this).get(ViewModel_comparar_precios.class);
        View root = inflater.inflate(R.layout.fragment_comparar_precios, container, false);
        CompararViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        this.btn_detalle = root.findViewById(R.id.btn_Detalle);
        this.btn_volver = root.findViewById(R.id.btn_Volver);
        this.btn_como_llegar = root.findViewById(R.id.btn_como_llegar);
        this.listaComparacion = root.findViewById(R.id.ListViewComparacion);

        mComparacionList = new ArrayList<class_comparar_precios>();

        btn_detalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_Detalle_Lista_Precios detalle_lista_precios = new Fragment_Detalle_Lista_Precios();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_comparar_precios, detalle_lista_precios);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_lista_compras lista_compras = new Fragment_lista_compras();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_comparar_precios, lista_compras);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        try {
            cargarDatosTabla();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        listaComparacion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int i = 0, j = 0;

                class_comparar_precios comparar_precios = new class_comparar_precios();

                comparar_precios.setId(String.valueOf(mComparacionList.get(position).getId()));
                comparar_precios.setId_local(String.valueOf(mComparacionList.get(position).getId_local()));
                comparar_precios.setLocal(String.valueOf(mComparacionList.get(position).getLocal()));
                comparar_precios.setPrecio_total(Float.parseFloat(String.valueOf(mComparacionList.get(position).getPrecio_total())));

                if(!comparar_precios.getId().isEmpty()){
                    btn_como_llegar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ubicacion ubicacion = new ubicacion();
                            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

                            LatLng origen= new LatLng(-31.407994999999996,-62.08017333333334);

                            String id_local = String.valueOf(mComparacionList.get(position).getId_local());

                            Log.d("El local ", "Este es el local " + id_local);


                            //VER PORQUE EL ID DEL LOCAL ESTA NULL
                            if(id_local=="5"){
                                LatLng destino = new LatLng(-31.42773493119209, -62.11414910012128);
                                ubicacion.comoLlegar(origen, destino);
                            }

                            transaction.replace(R.id.fragment_comparar_precios, ubicacion);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }
                    });
                }
                //este mensaje no sale.
                else{
                    Toast.makeText(getContext(), "Seleccione un local de la lista", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }

    private void cargarDatosTabla() throws IOException, JSONException {

        String jsonFileContent = utiles.leerJson(getContext(), "comparaciones.json");
        JSONArray jsonArray = new JSONArray(jsonFileContent);
        Log.d("Longitud json ", String.valueOf(jsonArray.length()));
        Log.d("json ", jsonArray.toString());

        for (int i = 0; i < jsonArray.length(); i++) {
            class_comparar_precios comparar_precios = new class_comparar_precios();
            Log.d("dentro del for ", String.valueOf(i));
            JSONObject jsonObj = jsonArray.getJSONObject(i);
            comparar_precios.setLocal(jsonObj.getString("local"));
            comparar_precios.setPrecio_total(Float.parseFloat(jsonObj.getString("precio_total")));
            comparar_precios.setId_local(jsonObj.getString("id_local"));
            mComparacionList.add(comparar_precios);
        }

        mComparacionList.removeAll(Collections.singleton(null));
        mArrayAdapterComparar = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mComparacionList);
        listaComparacion.setAdapter(mArrayAdapterComparar);
    }

}
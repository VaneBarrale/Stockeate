package com.stockeate.stockeate.ui.mis_listas_compras;

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
import com.stockeate.stockeate.clases.class_detalle_lista_compras;
import com.stockeate.stockeate.clases.class_producto;
import com.stockeate.stockeate.ui.home.HomeFragment;
import com.stockeate.stockeate.ui.lista_compras.Fragment_lista_compras;
import com.stockeate.stockeate.ui.lista_compras.ViewModel_lista_compras;
import com.stockeate.stockeate.utiles.utiles;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Fragment_mis_listas_compras extends Fragment {

    private ViewModel_mis_listas_compras viewModel_mis_listas_compras;
    private Button btn_volver;
    private ArrayList<class_detalle_lista_compras> mDetalleLista = null;
    private ListView listaDetalle;
    private ArrayAdapter<class_detalle_lista_compras> mArrayAdapterDetalle;

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
        this.listaDetalle = root.findViewById(R.id.lw_detalle_lista_compras);

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

        return root;
    }

    private void mostrarLista() throws IOException, JSONException {

        mDetalleLista = new ArrayList<class_detalle_lista_compras>();

        String jsonFileContent = utiles.leerJson(getContext(), "detalle_lista_compras.json");
        JSONArray jsonArray = new JSONArray(jsonFileContent);
        Log.d("Longitud json detalle", String.valueOf(jsonArray.length()));
        Log.d("json ", jsonArray.toString());
        for (int i = 0; i < jsonArray.length(); i++) {

            class_detalle_lista_compras detalle_lista_compras = new class_detalle_lista_compras();

            Log.d("dentro del 2 for ", String.valueOf(i));
            JSONObject jsonObj = jsonArray.getJSONObject(i);
            if(jsonObj.getString("id_usuario").equals("1")){
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
        }
        mArrayAdapterDetalle = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mDetalleLista);
        listaDetalle.setAdapter(mArrayAdapterDetalle);

    }
}
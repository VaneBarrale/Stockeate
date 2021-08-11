package com.stockeate.stockeate.ui.detalle_mis_listas_compras;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.stockeate.stockeate.R;
import com.stockeate.stockeate.clases.class_detalle_lista_compras;
import com.stockeate.stockeate.ui.mis_listas_compras.Fragment_mis_listas_compras;
import com.stockeate.stockeate.utiles.utiles;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class Fragment_Detalle_Mis_Listas_Compras extends Fragment {

    private ViewModel_detalle_mis_listas_compras detalle_mis_listas_compras;
    private Button btn_volver;
    private ArrayList<class_detalle_lista_compras> mDetalleLista = null;
    private ListView listaDetalle;
    private ArrayAdapter<class_detalle_lista_compras> mArrayAdapterDetalle;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        detalle_mis_listas_compras = new ViewModelProvider(this).get(ViewModel_detalle_mis_listas_compras.class);
        View root = inflater.inflate(R.layout.fragment_detalle_mis_listas_compras, container, false);
        detalle_mis_listas_compras.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        this.btn_volver = root.findViewById(R.id.btn_Volver);
        this.listaDetalle = root.findViewById(R.id.ListaMisListasCompras);

        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_mis_listas_compras mis_listas_compras = new Fragment_mis_listas_compras();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_detalle_mis_listas_compras, mis_listas_compras);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return root;
    }

    public void leerDatos(Context context) throws IOException, JSONException {

        Bundle datosRecuperados = getArguments();
        String id_lista = datosRecuperados.getString("id_lista");

        mDetalleLista = new ArrayList<class_detalle_lista_compras>();

        String jsonFileContent = utiles.leerJson(context, "detalle_lista_compras.json");
        JSONArray jsonArray = new JSONArray(jsonFileContent);
        Log.d("Longitud json detalle", String.valueOf(jsonArray.length()));
        Log.d("json ", jsonArray.toString());
        for (int i = 0; i < jsonArray.length(); i++) {

            class_detalle_lista_compras detalle_lista_compras = new class_detalle_lista_compras();

            Log.d("dentro del 2 for ", String.valueOf(i));
            JSONObject jsonObj = jsonArray.getJSONObject(i);
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
        }
        mArrayAdapterDetalle = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, mDetalleLista);
        listaDetalle.setAdapter(mArrayAdapterDetalle);
    }
}
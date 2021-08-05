package com.stockeate.stockeate.ui.promocion;

import android.os.Bundle;
import android.util.Log;
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
import com.stockeate.stockeate.clases.class_detalle_lista_compras;
import com.stockeate.stockeate.clases.class_producto;
import com.stockeate.stockeate.clases.class_promociones;
import com.stockeate.stockeate.ui.agregar_promocion.Fragment_Agregar_Promocion;
import com.stockeate.stockeate.ui.home.HomeFragment;
import com.stockeate.stockeate.utiles.utiles;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Fragment_Promocion extends Fragment {

    private PromocionViewModel viewModelPromocion;
    private Button btn_volver, btn_agregar;
    private ArrayAdapter<class_promociones> mArrayAdapterPromociones;
    private ArrayList<class_promociones> mPromocionesList = null;
    private ListView listPromocionesExistentes;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModelPromocion = new ViewModelProvider(this).get(PromocionViewModel.class);
        View root = inflater.inflate(R.layout.fragment_promocion, container, false);
        viewModelPromocion.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        this.btn_volver = root.findViewById(R.id.btn_Volver);
        this.btn_agregar = root.findViewById(R.id.btn_Agregar);
        this.listPromocionesExistentes = root.findViewById(R.id.listPromocionesExistentes);

        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment home = new HomeFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_promocion, home);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btn_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_Agregar_Promocion agregar_promocion = new Fragment_Agregar_Promocion();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_promocion, agregar_promocion);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        try {
            listarproductos();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return root;
    }

    private void listarproductos() throws IOException, JSONException {

        mPromocionesList = new ArrayList<class_promociones>();

        String jsonFileContent = utiles.leerJson(getContext(), "promociones.json");
        JSONArray jsonArray = new JSONArray(jsonFileContent);
        for (int i = 0; i < jsonArray.length(); i++) {
            class_promociones promociones = new class_promociones();
            JSONObject jsonObj = jsonArray.getJSONObject(i);
            promociones.setId(jsonObj.getString("id"));
            promociones.setTipo_promocion(jsonObj.getString("tipo_promocion"));
            promociones.setDesc_tipo_producto(jsonObj.getString("desc_tipo_producto"));
            promociones.setDesc_producto(jsonObj.getString("desc_producto"));
            promociones.setLocal(jsonObj.getString("local"));
            mPromocionesList.add(promociones);
        }

        mPromocionesList.removeAll(Collections.singleton(null));
        mArrayAdapterPromociones = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mPromocionesList);
        listPromocionesExistentes.setAdapter(mArrayAdapterPromociones);
    }
}
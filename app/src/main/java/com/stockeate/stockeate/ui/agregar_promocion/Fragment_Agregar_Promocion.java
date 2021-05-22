package com.stockeate.stockeate.ui.agregar_promocion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.stockeate.stockeate.R;
import com.stockeate.stockeate.ui.detalle_lista_precios.Fragment_Detalle_Lista_Precios;
import com.stockeate.stockeate.ui.lista_compras.Fragment_lista_compras;
import com.stockeate.stockeate.ui.promocion.Fragment_Promocion;
import com.stockeate.stockeate.ui.ubicacion.ubicacion;

public class Fragment_Agregar_Promocion extends Fragment {

    private ViewModel_agregar_promocion AgregarPromocionViewModel;
    private Button btn_volver, btn_como_llegar;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        AgregarPromocionViewModel = new ViewModelProvider(this).get(ViewModel_agregar_promocion.class);
        View root = inflater.inflate(R.layout.fragment_agregar_promocion, container, false);
        AgregarPromocionViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        this.btn_volver = root.findViewById(R.id.btn_Volver);
        this.btn_como_llegar = root.findViewById(R.id.btn_como_llegar);

        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_Promocion promocion = new Fragment_Promocion();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_agregar_promocion, promocion);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btn_como_llegar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ubicacion ubicacion = new ubicacion();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_agregar_promocion, ubicacion);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return root;
    }
}
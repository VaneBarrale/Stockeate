package com.stockeate.stockeate.ui.comparar_precios;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.stockeate.stockeate.R;
import com.stockeate.stockeate.ui.detalle_lista_precios.Fragment_Detalle_Lista_Precios;
import com.stockeate.stockeate.ui.lista_compras.Fragment_lista_compras;
import com.stockeate.stockeate.ui.ubicacion.ubicacion;

public class Fragment_Comparar_Precios extends Fragment {

    private ViewModel_comparar_precios CompararViewModel;
    private Button btn_detalle, btn_volver, btn_como_llegar;

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

        btn_como_llegar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ubicacion ubicacion = new ubicacion();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_comparar_precios, ubicacion);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return root;
    }
}
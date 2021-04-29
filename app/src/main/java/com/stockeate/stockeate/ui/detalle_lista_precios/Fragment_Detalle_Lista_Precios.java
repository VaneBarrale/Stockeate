package com.stockeate.stockeate.ui.detalle_lista_precios;

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
import com.stockeate.stockeate.ui.comparar_precios.Fragment_Comparar_Precios;
import com.stockeate.stockeate.ui.lista_compras.Fragment_lista_compras;
import com.stockeate.stockeate.ui.ubicacion.ubicacion;

public class Fragment_Detalle_Lista_Precios extends Fragment {

    private ViewModel_Detalle_Lista_Precios DetalleViewModel;
    private Button btn_detalle, btn_volver, btn_como_llegar;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DetalleViewModel = new ViewModelProvider(this).get(ViewModel_Detalle_Lista_Precios.class);
        View root = inflater.inflate(R.layout.fragment_detalle_lista_precios, container, false);
        DetalleViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        this.btn_volver = root.findViewById(R.id.btn_Volver);
        this.btn_como_llegar = root.findViewById(R.id.btn_como_llegar);

        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_Comparar_Precios comparar_precios = new Fragment_Comparar_Precios();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_detalle_lista_precios, comparar_precios);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btn_como_llegar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ubicacion ubicacion = new ubicacion();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_detalle_lista_precios, ubicacion);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return root;
    }
}
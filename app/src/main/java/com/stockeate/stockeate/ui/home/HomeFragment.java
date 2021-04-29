package com.stockeate.stockeate.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.stockeate.stockeate.R;
import com.stockeate.stockeate.ui.escanear_codigos_barra.Fragment_escanear_codigos_barra;
import com.stockeate.stockeate.ui.lista_compras.Fragment_lista_compras;
import com.stockeate.stockeate.ui.nuevo_comercio.nuevo_comercio;
import com.stockeate.stockeate.ui.precios.precios;
import com.stockeate.stockeate.ui.promocion.Fragment_Promocion;
import com.stockeate.stockeate.ui.ubicacion.ubicacion;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private ImageButton btn_compras, btn_escanear, btn_ubicacion, btn_precio, btn_promocion, btn_nuevo_comercio;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        btn_compras = root.findViewById(R.id.btn_compras);
        btn_escanear = root.findViewById(R.id.btn_escanear);
        btn_ubicacion = root.findViewById(R.id.btn_ubicacion);
        btn_precio = root.findViewById(R.id.btn_precio);
        btn_promocion = root.findViewById(R.id.btn_promocion);
        btn_nuevo_comercio = root.findViewById(R.id.btn_nuevo_comercio);

        btn_compras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_lista_compras fragment_lista_compras = new Fragment_lista_compras();

                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_home, fragment_lista_compras);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btn_escanear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_escanear_codigos_barra fragment_escanear_codigos_barra = new Fragment_escanear_codigos_barra();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_home, fragment_escanear_codigos_barra);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btn_ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ubicacion ubicacion = new ubicacion();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_home, ubicacion);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btn_precio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                precios precios = new precios();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_home, precios);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btn_promocion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_Promocion promocion = new Fragment_Promocion();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_home, promocion);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btn_nuevo_comercio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nuevo_comercio nuevo_comercio = new nuevo_comercio();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_home, nuevo_comercio);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return root;
    }
}

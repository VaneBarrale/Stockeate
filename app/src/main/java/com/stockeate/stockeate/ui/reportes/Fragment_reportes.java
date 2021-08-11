package com.stockeate.stockeate.ui.reportes;

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
import android.widget.ImageButton;

import com.stockeate.stockeate.R;
import com.stockeate.stockeate.ui.Ranking.Fragment_ranking;
import com.stockeate.stockeate.ui.categoria.Fragment_categorias;
import com.stockeate.stockeate.ui.categorizacion_precios.Fragment_categorizacion_precios;
import com.stockeate.stockeate.ui.historial_precios.Fragment_historial_precios;
import com.stockeate.stockeate.ui.home.HomeFragment;
import com.stockeate.stockeate.ui.locales.Fragment_locales;
import com.stockeate.stockeate.ui.marcas.Fragment_marcas;

import org.json.JSONException;

import java.io.IOException;

public class Fragment_reportes extends Fragment {

    private ReportesViewModel reportesViewModel;
    private ImageButton btn_categorias, btn_marcas, btn_locales, btn_usuarios, btn_precios, btn_precios_categoria;
    private Button btn_volver;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        reportesViewModel = new ViewModelProvider(this).get(ReportesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_reportes, container, false);
        reportesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        btn_categorias = root.findViewById(R.id.btn_categorias);
        btn_marcas = root.findViewById(R.id.btn_marcas);
        btn_locales = root.findViewById(R.id.btn_locales);
        btn_usuarios = root.findViewById(R.id.btn_Ranking);
        btn_precios = root.findViewById(R.id.btn_precios);
        btn_precios_categoria = root.findViewById(R.id.btn_precios_categoria);
        btn_volver  = root.findViewById(R.id.btn_Volver);

        btn_categorias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_categorias fragment_categorias= new Fragment_categorias();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

                //fragment_categorias.cargarDatosTabla();

                transaction.replace(R.id.fragment_reportes, fragment_categorias);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btn_marcas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_marcas fragment_marcas = new Fragment_marcas();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_reportes, fragment_marcas);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btn_locales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_locales fragment_locales = new Fragment_locales();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_reportes, fragment_locales);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btn_usuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_ranking fragment_ranking = new Fragment_ranking();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_reportes, fragment_ranking);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btn_precios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_historial_precios historial_precios = new Fragment_historial_precios();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_reportes, historial_precios);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btn_precios_categoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_categorizacion_precios categorizacion_precios = new Fragment_categorizacion_precios();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_reportes, categorizacion_precios);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment homeFragment = new HomeFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_reportes, homeFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return root;
    }
}
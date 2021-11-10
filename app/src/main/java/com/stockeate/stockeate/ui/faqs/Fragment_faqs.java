package com.stockeate.stockeate.ui.faqs;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.stockeate.stockeate.R;
import com.stockeate.stockeate.ui.home.HomeFragment;
import com.stockeate.stockeate.ui.marcas.MarcasViewModel;

public class Fragment_faqs extends Fragment {

    private FaqsViewModel faqsViewModel;
    private Button btn_pagos, btn_mis_listas, btn_lista_compras, btn_volver;
    private TextView txt_resultado;
    private String pagos, mis_listas, lista_compra;
    private MaterialAlertDialogBuilder ratingDialog;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        faqsViewModel = new ViewModelProvider(this).get(FaqsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_faqs, container, false);
        faqsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        btn_lista_compras = root.findViewById(R.id.btn_listaCompras);
        btn_mis_listas = root.findViewById(R.id.btn_MisListas);
        btn_pagos = root.findViewById(R.id.btn_Pagos);
        txt_resultado = root.findViewById(R.id.txt_Resultado);
        btn_volver = root.findViewById(R.id.btn_Volver);

        btn_lista_compras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lista_compra = "Para poder cargar una lista de compras podes dirigirte al icono del carrito, buscar los productos que desees agregar por categoria, codigo de barra, marca, pesentación o unidad, buscarlos, seleccionarlo y agregar la cantidad que queres agregar. Cuando termines de cargar tu lista, podes comparar los precios para saber donde te conviene comprar";
                txt_resultado.setText(lista_compra);
            }
        });

        btn_mis_listas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mis_listas = "Para poder ver las listas que cargaste con anterioridad, debes ingresar al icono del carrito, dirigirte al margen superior derecho y luego hacer click en el botón mis listas";
                txt_resultado.setText(mis_listas);
            }
        });

        btn_pagos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pagos = "Actualmente la plataforma no cuenta con la opción de abonar desde la misma. Para hacerlo podes dirigirte al local deseado o ponerte en contacto con ellos para mas información sobre formas de pago";
                txt_resultado.setText(pagos);
            }
        });

        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ratingDialog.show();
            }
        });


        final LinearLayout ratingLayout = new LinearLayout(this.getContext());
        final RatingBar starsBar = new RatingBar(this.getContext());
        starsBar.setRating(0);
        starsBar.setMax(5);
        starsBar.setStepSize(0.5F);
        starsBar.setNumStars(5);
        starsBar.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));
        ratingLayout.addView(starsBar);
        ratingLayout.setGravity(Gravity.CENTER);
        this.ratingDialog = new MaterialAlertDialogBuilder(this.getContext());
        this.ratingDialog.setTitle("Puntuanos");
        this.ratingDialog.setMessage("¿Encontraste la respuesta que buscabas?");
        this.ratingDialog.setCancelable(false);
        this.ratingDialog.setView(ratingLayout);
        this.ratingDialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface ratingBar, int id) {
                ratingBar.cancel(); //ACA FALTARIA AGREGAR DONDE SE GUARDARIA
                HomeFragment homeFragment = new HomeFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_faqs, homeFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        this.ratingDialog.setNegativeButton("Salir", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface ratingBar, int id) {
                ratingBar.cancel();
                HomeFragment homeFragment = new HomeFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_faqs, homeFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        return root;

    }
}
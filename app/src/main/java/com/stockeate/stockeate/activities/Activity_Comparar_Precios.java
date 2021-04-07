package com.stockeate.stockeate.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.stockeate.stockeate.R;
import com.stockeate.stockeate.ui.lista_compras.Fragment_lista_compras;
import com.stockeate.stockeate.ui.ubicacion.ubicacion;

public class Activity_Comparar_Precios extends AppCompatActivity {

    private Button btn_detalle, btn_volver, btn_como_llegar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparar_precios);

        this.btn_detalle = findViewById(R.id.btn_Detalle);
        this.btn_volver = findViewById(R.id.btn_Volver);
        this.btn_como_llegar = findViewById(R.id.btn_como_llegar);

        btn_detalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Activity_Comparar_Precios.this, Activity_Detalle_Lista_Precios.class);
                startActivity(i);
            }
        });

        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent volver = new Intent(Activity_Comparar_Precios.this, Fragment_lista_compras.class);
                startActivity(volver);
            }
        });

        btn_como_llegar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Activity_Comparar_Precios.this, ubicacion.class);
                startActivity(i);
            }
        });
    }
}
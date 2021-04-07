package com.stockeate.stockeate.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.stockeate.stockeate.R;
import com.stockeate.stockeate.ui.lista_compras.Fragment_lista_compras;
import com.stockeate.stockeate.ui.promocion.promocion;
import com.stockeate.stockeate.ui.ubicacion.ubicacion;

public class Activity_Agregar_Promocion extends AppCompatActivity {

    private Button btn_agregar, btn_volver, btn_como_llegar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_promocion);

        this.btn_agregar = findViewById(R.id.btn_agregar);
        this.btn_volver = findViewById(R.id.btn_Volver);
        this.btn_como_llegar = findViewById(R.id.btn_como_llegar);

        btn_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent agregar = new Intent(Activity_Agregar_Promocion.this, promocion.class);
                startActivity(agregar);
            }
        });

        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent volver = new Intent(Activity_Agregar_Promocion.this, promocion.class);
                startActivity(volver);
            }
        });

        btn_como_llegar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Activity_Agregar_Promocion.this, ubicacion.class);
                startActivity(i);
            }
        });
    }
}
package com.stockeate.stockeate.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.stockeate.stockeate.R;
import com.stockeate.stockeate.ui.ubicacion.UbicacionViewModel;
import com.stockeate.stockeate.ui.ubicacion.ubicacion;

public class Activity_Detalle_Lista_Precios extends AppCompatActivity {

    private Button btn_como_llegar, btn_volver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_lista_precios);

        this.btn_como_llegar = findViewById(R.id.btn_como_llegar);
        this.btn_volver = findViewById(R.id.btn_Volver);

        btn_como_llegar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Activity_Detalle_Lista_Precios.this, ubicacion.class);
                startActivity(i);
            }
        });

        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent volver = new Intent(Activity_Detalle_Lista_Precios.this, Activity_Comparar_Precios.class);
                startActivity(volver);
            }
        });
    }
}
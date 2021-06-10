package com.stockeate.stockeate.ui.escanear_codigos_barra;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.stockeate.stockeate.R;

import static com.stockeate.stockeate.R.layout.fragment_escanear_codigos_barra;

public class Fragment_escanear_codigos_barra extends Fragment {

    private ViewModel_escanear_codigos_barra viewModelEscanear;
    private Button escanear;
    private TextView resultadoEscaneo;

    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @NonNull Bundle savedInstanceState) {
        viewModelEscanear = new ViewModelProvider(this).get(ViewModel_escanear_codigos_barra.class);
        View root = inflater.inflate(fragment_escanear_codigos_barra, container, false);

        this.escanear = root.findViewById(R.id.btn_Escanear);
        this.resultadoEscaneo = root.findViewById(R.id.txtResultadoEscaneo);

        escanear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                escanear();
            }
        });

        return root;
    }

    public void escanear(){
        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(Fragment_escanear_codigos_barra.this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("ESCANEAR CODIGO");
        integrator.setCameraId(0); //0 es la camara trasera
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(true); //habilito para que pueda leer correctamente.
        integrator.initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result!=null){
            if(result.getContents() == null)
            {
                Toast.makeText(getContext(), "Escaneo cancelado", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getContext(), result.getContents(), Toast.LENGTH_SHORT).show();
                resultadoEscaneo.setText(result.getContents());
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
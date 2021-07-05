package com.stockeate.stockeate.ui.escanear_codigos_barra;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.stockeate.stockeate.R;
import com.stockeate.stockeate.clases.class_producto;
import com.stockeate.stockeate.utiles.utiles;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import static com.stockeate.stockeate.R.layout.fragment_escanear_codigos_barra;

public class Fragment_escanear_codigos_barra extends Fragment {

    private ViewModel_escanear_codigos_barra viewModelEscanear;
    private Button escanear;
    private Boolean guardar;
    private TextView resultadoEscaneo;
    private ArrayList<class_producto> mProductosList = null;
    private ArrayAdapter<class_producto> mArrayAdapterProducto;
    private ListView listaResultado;

    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @NonNull Bundle savedInstanceState) {
        viewModelEscanear = new ViewModelProvider(this).get(ViewModel_escanear_codigos_barra.class);
        View root = inflater.inflate(fragment_escanear_codigos_barra, container, false);

        this.escanear = root.findViewById(R.id.btn_Escanear);
        this.resultadoEscaneo = root.findViewById(R.id.txtResultadoEscaneo);
        this.listaResultado = root.findViewById(R.id.ListViewResultado);

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

                //desde aca empece a tocar 05/07

                String jsonFileContent = null;
                try {
                    jsonFileContent = utiles.leerJson(getContext(), "productos.json");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(jsonFileContent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("Longitud json ", String.valueOf(jsonArray.length()));
                Log.d("json ", jsonArray.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    class_producto productos = new class_producto();
                    Log.d("dentro del for ", String.valueOf(i));
                    try {
                        JSONObject jsonObj = jsonArray.getJSONObject(i);
                        guardar = true;
                        if (jsonObj.getString("codigo_barra").equals(resultadoEscaneo.getText().toString())) {
                            if (guardar) {
                                guardar = true;
                            } else {
                                guardar = false;
                            };
                        }
                        else {
                            guardar = false;
                        }

                        if (guardar) {
                            mProductosList = new ArrayList<class_producto>();
                            productos.setId(jsonObj.getString("id"));
                            productos.setCategoria(jsonObj.getString("categoria"));
                            productos.setMarca(jsonObj.getString("marca"));
                            productos.setPresentacion(jsonObj.getString("presentacion"));
                            productos.setUnidad(jsonObj.getString("unidad"));
                            productos.setId(jsonObj.getString("codigo_barra"));
                            mProductosList.add(productos);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                mProductosList.removeAll(Collections.singleton(null));
                mArrayAdapterProducto = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mProductosList);
                listaResultado.setAdapter(mArrayAdapterProducto);
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
package com.stockeate.stockeate.ui.nuevo_comercio;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.stockeate.stockeate.R;
import com.stockeate.stockeate.clases.class_locales;
import com.stockeate.stockeate.clases.class_producto;
import com.stockeate.stockeate.ui.home.HomeFragment;
import com.stockeate.stockeate.utiles.utiles;

import static com.stockeate.stockeate.R.layout.fragment_nuevo_comercio;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class nuevo_comercio extends Fragment {

    private NuevoComercioViewModel viewModelNuevoComercio;
    private Button btn_volver, btn_validar, btn_agregar;
    private EditText txt_cuit, txt_Descripcion, txt_Latitud, txt_Longitud;
    RequestQueue requestQueue;
    String URL_SERVIDOR = "https://stockeateapp.com.ar/api/markets";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModelNuevoComercio = new ViewModelProvider(this).get(NuevoComercioViewModel.class);
        View root = inflater.inflate(fragment_nuevo_comercio, container, false);
        viewModelNuevoComercio.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        this.btn_volver = root.findViewById(R.id.btn_Volver);
        this.btn_validar = root.findViewById(R.id.btn_validar);
        this.btn_agregar = root.findViewById(R.id.btn_agregar);
        this.txt_cuit = root.findViewById(R.id.etxtCuit);
        this.txt_Descripcion = root.findViewById(R.id.etxtDescripcion);
        this.txt_Latitud = root.findViewById(R.id.etxtLatitud);
        this.txt_Longitud = root.findViewById(R.id.etxtLongitud);

        requestQueue = Volley.newRequestQueue(getContext());
        btn_agregar.setEnabled(false);

        btn_validar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!txt_cuit.getText().toString().isEmpty()){
                    if(isValidCUITCUIL(txt_cuit.getText().toString())){
                        Toast.makeText(getContext(), "CUIT válido, no existe comercio", Toast.LENGTH_SHORT).show();
                        validarCuitExistente(txt_cuit.getText().toString());
                    }else{
                        Toast.makeText(getContext(), "Ingrese nro CUIT válido", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getContext(), "Ingrese nro CUIT", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment homeFragment = new HomeFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_nuevo_comercio, homeFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btn_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((!txt_Descripcion.getText().toString().isEmpty()) && (!txt_Latitud.getText().toString().isEmpty()) && (!txt_Longitud.getText().toString().isEmpty())) {
                    guardarNuevoComercio();
                } else {
                    Toast.makeText(getContext(), "Complete todos los datos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }

    private static boolean isValidCUITCUIL(String cuit) {

        if (cuit.length() != 11) return false;

        boolean isValid = false;
        int result = 0;
        String cuit_nro = cuit.replace("-", "");
        String codes = "6789456789";
        int digitVerif = Character.getNumericValue(cuit_nro.charAt(cuit_nro.length() - 1));
        int position = 0;

        while (position < 10) {
            int digitoValidador = Integer.parseInt(codes.substring(position, position + 1));
            int digito = Integer.parseInt(cuit_nro.substring(position, position + 1));
            int digitoValidacion = digitoValidador * digito;
            result += digitoValidacion;
            position++;
        }
        result = result % 11;
        isValid = (result == digitVerif);
        return isValid;
    }

    private void validarCuitExistente(String cuit) {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL_SERVIDOR,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int size = response.length();
                        for(int i=0; i<size; i++){
                            try {
                                JSONObject jsonObject = new JSONObject(response.get(i).toString());
                                class_locales locales = new class_locales();
                                if (!jsonObject.getString("cuit").equals(cuit)) {
                                    btn_agregar.setEnabled(true);
                                }else {
                                    btn_agregar.setEnabled(false);
                                    Toast.makeText(getContext(), "Ya existe un local con ese CUIT", Toast.LENGTH_SHORT).show();
                                }
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "ERROR AL CARGAR PRODUCTOS", Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + "11|QMuCyTS9qdS2SgEc3IlGpEQDeTzbgPVkk5E82WBZ");
                return params;
            }
        };
        requestQueue.add(jsonArrayRequest);
    }

    private void guardarNuevoComercio(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL_SERVIDOR,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Toast.makeText(getContext(), "Registro exitoso.", Toast.LENGTH_LONG).show();
                        limpiarDatos();
                        }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "ERROR AL GUARDAR", Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params1 = new HashMap<String, String>();
                params1.put("name", txt_Descripcion.getText().toString().trim());
                params1.put("latitude", txt_Latitud.getText().toString().trim());
                params1.put("longitude", txt_Longitud.getText().toString().trim());
                params1.put("cuit", txt_cuit.getText().toString().trim());
                params1.put("Authorization", "Bearer " + "11|QMuCyTS9qdS2SgEc3IlGpEQDeTzbgPVkk5E82WBZ");
                return params1;
            }
        };
        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);

    }

    private void limpiarDatos() {
        txt_cuit.setText("");
        txt_Descripcion.setText("");
        txt_Latitud.setText("");
        txt_Longitud.setText("");
        btn_agregar.setEnabled(false);
    }
}
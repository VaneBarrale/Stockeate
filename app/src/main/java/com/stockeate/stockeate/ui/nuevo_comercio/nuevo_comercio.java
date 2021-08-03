package com.stockeate.stockeate.ui.nuevo_comercio;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.stockeate.stockeate.R;
import com.stockeate.stockeate.clases.class_locales;
import com.stockeate.stockeate.ui.home.HomeFragment;
import com.stockeate.stockeate.utiles.utiles;

import static com.stockeate.stockeate.R.layout.fragment_nuevo_comercio;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class nuevo_comercio extends Fragment {

    private NuevoComercioViewModel viewModelNuevoComercio;
    private Button btn_volver, btn_agregar;
    private EditText txt_cuit, txt_Descripcion, txt_Domicilio;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModelNuevoComercio = new ViewModelProvider(this).get(NuevoComercioViewModel.class);
        View root = inflater.inflate(fragment_nuevo_comercio, container, false);
        viewModelNuevoComercio.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        btn_volver = root.findViewById(R.id.btn_Volver);
        btn_agregar = root.findViewById(R.id.btn_agregar);
        txt_cuit = root.findViewById(R.id.etxtCuit);
        txt_Descripcion = root.findViewById(R.id.etxtDescripcion);
        txt_Domicilio = root.findViewById(R.id.etxtDomicilio);

        btn_agregar.setEnabled(false);

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

        String cuit = txt_cuit.getText().toString();

        try {
            listarlocales(cuit);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return root;
    }

    private void limpiarDatos() {
        txt_cuit.setText("");
        txt_Descripcion.setText("");
        txt_Domicilio.setText("");
    }

    private void listarlocales(String cuit) throws IOException, JSONException {

        String jsonFileContent = utiles.leerJson(getContext(), "locales.json");
        JSONArray jsonArray = new JSONArray(jsonFileContent);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObj = jsonArray.getJSONObject(i);
            if (!jsonObj.getString("cuit").equals(cuit)){
                Log.d("Adentro del primer if", "cuit " + cuit);
                btn_agregar.setEnabled(true);
                if((!txt_Descripcion.getText().toString().isEmpty()) && (!txt_Domicilio.getText().toString().isEmpty())){
                    btn_agregar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getContext(), "Guardado con exito", Toast.LENGTH_SHORT).show();
                            limpiarDatos();
                        }
                    });
                }
                else{
                    Toast.makeText(getContext(), "Complete todos los datos", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Ya existe un local con ese CUIT", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
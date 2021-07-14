package com.stockeate.stockeate.ui.precios;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.stockeate.stockeate.R;
import com.stockeate.stockeate.clases.class_producto;
import com.stockeate.stockeate.ui.home.HomeFragment;
import com.stockeate.stockeate.utiles.utiles;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import static com.stockeate.stockeate.R.layout.fragment_precios;
import static com.stockeate.stockeate.R.layout.lista_productos;
import static com.stockeate.stockeate.R.layout.text_view_with_line_height_from_appearance;

public class precios extends Fragment {

    private EditText categoria, marca, presentacion, comercio;
    private Button btn_actualizar, btn_buscar, btn_volver;
    private PreciosViewModel viewModelPrecios;

    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @NonNull Bundle savedInstanceState) {
        viewModelPrecios = new ViewModelProvider(this).get(PreciosViewModel.class);
        View root = inflater.inflate(fragment_precios, container, false);
        viewModelPrecios.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        this.categoria = root.findViewById(R.id.etxtCodigoProducto);
        this.marca = root.findViewById(R.id.etxtMarca);
        this.presentacion = root.findViewById(R.id.etxtPresentacion);
        this.comercio = root.findViewById(R.id.etxtComercio);
        this.btn_actualizar = root.findViewById(R.id.btn_actualizar);
        this.btn_buscar = root.findViewById(R.id.btn_Buscar);
        this.btn_volver = root.findViewById(R.id.btn_Volver);

        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    listar_productos();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment homeFragment = new HomeFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_precios, homeFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return root;

    }

    private void listar_productos() throws IOException, JSONException {

        double precio_actual = 0;
        Boolean guardar;

        //Asi lee los datos del json estatico
        String jsonFileContent = utiles.leerJson(getContext(), "productos.json");
        JSONArray jsonArray = new JSONArray(jsonFileContent);
        Log.d("Longitud json ", String.valueOf(jsonArray.length()));
        Log.d("json ", jsonArray.toString());

        class_producto productos = new class_producto();
        for (int i = 0; i < jsonArray.length(); i++) {

            Log.d("dentro del for ", String.valueOf(i));
            JSONObject jsonObj = jsonArray.getJSONObject(i);

            guardar = true;

            if (!categoria.getText().toString().isEmpty() ||
                    !marca.getText().toString().isEmpty() ||
                    !presentacion.getText().toString().isEmpty() ||
                    !presentacion.getText().toString().isEmpty() ||
                    !comercio.getText().toString().isEmpty()) {
                if (!categoria.getText().toString().isEmpty()) {
                    if (jsonObj.getString("categoria").equals(categoria.getText().toString())) {
                        if (guardar) {
                            guardar = true;
                        } else {
                            guardar = false;
                        }
                        ;
                    } else {
                        guardar = false;
                    }
                }
                if (!marca.getText().toString().isEmpty()) {
                    if (jsonObj.getString("marca").equals(marca.getText().toString())) {
                        if (guardar) {
                            guardar = true;
                        } else {
                            guardar = false;
                        }
                        ;
                    } else {
                        guardar = false;
                    }
                }
                if (!presentacion.getText().toString().isEmpty()) {
                    if (jsonObj.getString("presentacion").equals(presentacion.getText().toString())) {
                        if (guardar) {
                            guardar = true;
                        } else {
                            guardar = false;
                        }
                        ;
                    } else {
                        guardar = false;
                    }
                }
                if (!comercio.getText().toString().isEmpty()) {
                    if (jsonObj.getString("comercio").equals(comercio.getText().toString())) {
                        if (guardar) {
                            guardar = true;
                        } else {
                            guardar = false;
                        }
                        ;
                    } else {
                        guardar = false;
                    }
                }
                if (guardar) {
                    productos.setId(jsonObj.getString("id"));
                    productos.setCategoria(jsonObj.getString("categoria"));
                    productos.setMarca(jsonObj.getString("marca"));
                    productos.setPresentacion(jsonObj.getString("presentacion"));
                    productos.setUnidad(jsonObj.getString("unidad"));
                    productos.setCodigo_barra(jsonObj.getString("codigo_barra"));
                    productos.setPrecio(jsonObj.getDouble("precio"));
                }
            }
        }

        precio_actual = productos.getPrecio();
        Log.d("Producto", "Producto " + productos.toString());
        Log.d("Precio", "Precio " + precio_actual);
        //txv_precio_actual = productos.getPrecio();
    }
}
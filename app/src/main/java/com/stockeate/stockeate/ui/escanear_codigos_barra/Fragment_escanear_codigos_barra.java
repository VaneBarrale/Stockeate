package com.stockeate.stockeate.ui.escanear_codigos_barra;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.stockeate.stockeate.Adapter.Adapter_detalle_lista_compras;
import com.stockeate.stockeate.Adapter.Adapter_productos;
import com.stockeate.stockeate.R;
import com.stockeate.stockeate.clases.class_producto;
import com.stockeate.stockeate.ui.comparar_precios.Fragment_Comparar_Precios;
import com.stockeate.stockeate.ui.lista_compras.Fragment_lista_compras;
import com.stockeate.stockeate.ui.precios.precios;
import com.stockeate.stockeate.utiles.utiles;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.stockeate.stockeate.R.layout.fragment_comparar_precios;
import static com.stockeate.stockeate.R.layout.fragment_escanear_codigos_barra;

public class Fragment_escanear_codigos_barra extends Fragment {

    private ViewModel_escanear_codigos_barra viewModelEscanear;
    private Button escanear, comprar_precios;
    private RecyclerView RecycleProductos;
    private ArrayList<class_producto> mProductosList = null;
    private Adapter_productos mArrayAdapterProducto;
    RequestQueue requestQueue;
    String URL_SERVIDOR = "https://stockeateapp.com.ar/api/products";

    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @NonNull Bundle savedInstanceState) {
        viewModelEscanear = new ViewModelProvider(this).get(ViewModel_escanear_codigos_barra.class);
        View root = inflater.inflate(fragment_escanear_codigos_barra, container, false);

        this.escanear = root.findViewById(R.id.btn_Escanear);
        this.RecycleProductos = root.findViewById(R.id.RecycleProductos);
        this.comprar_precios = root.findViewById(R.id.btnCompararPreciosLista);
        this.RecycleProductos = root.findViewById(R.id.RecycleProductos);

        this.mArrayAdapterProducto = new Adapter_productos(mProductosList);
        mProductosList = new ArrayList<class_producto>();
        RecycleProductos.setLayoutManager(new LinearLayoutManager(getContext()));

        requestQueue = Volley.newRequestQueue(getContext());
        comprar_precios.setEnabled(false);


        escanear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                escanear();
            }
        });

        comprar_precios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //aca tendría que llamar al servicio para guardar la lista de compras y luego recuperar ese id para mostrar en la comparación
                Fragment_Comparar_Precios fragment_comparar_precios = new Fragment_Comparar_Precios();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_escanear_codigos_barra, fragment_comparar_precios);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

/*        mArrayAdapterProducto.setOnClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mProductosList.remove(RecycleProductos.getChildAdapterPosition(view));
                RecycleProductos.setAdapter(mArrayAdapterProducto);
                Toast.makeText(getContext(), "Producto eliminado", Toast.LENGTH_SHORT).show();
                return true;
            }
        });*/

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
        Toast.makeText(getContext(), result.getContents(), Toast.LENGTH_SHORT).show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
            Request.Method.GET,
            URL_SERVIDOR,
            null,
            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    class_producto productos = new class_producto();
                    int size = response.length();
                    for(int i=0; i<size; i++){
                        try {
                            JSONObject jsonObject = new JSONObject(response.get(i).toString());
                            String code = jsonObject.getString("code");
                            if (code.equals((result.getContents()))) {
                                productos.setCategoria(jsonObject.getString("name"));
                                productos.setMarca(jsonObject.getString("brand"));
                                productos.setPresentacion(jsonObject.getString("presentation"));
                                productos.setCodigo_barra(code);
                                mProductosList.add(productos);
                            }
                            else {
                                Toast.makeText(getContext(), "No existen productos con ese codigo", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Log.d("Mensaje de error", "Mensaje de error " + e);
                            e.printStackTrace();
                        }
                    }
                    RecycleProductos.setVisibility(View.VISIBLE);
                    Adapter_productos mArrayAdapterProducto = new Adapter_productos(mProductosList);
                    RecycleProductos.setAdapter(mArrayAdapterProducto);
                    comprar_precios.setEnabled(true);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "ERROR AL CARGAR PRODUCTOS", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + "10|QzbPyRDIRDNT5dQkI5rqDV7p7WYRdOL0M8if8jcu");
                return params;
            }
        };
        requestQueue.add(jsonArrayRequest);
    }
}
package com.stockeate.stockeate.ui.lista_compras;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.stockeate.stockeate.R;
import com.stockeate.stockeate.clases.class_detalle_lista_compras;
import com.stockeate.stockeate.clases.class_lista_compras;
import com.stockeate.stockeate.clases.class_producto;
import com.stockeate.stockeate.ui.mis_listas_compras.Fragment_mis_listas_compras;
import com.stockeate.stockeate.ui.comparar_precios.Fragment_Comparar_Precios;
import com.stockeate.stockeate.ui.home.HomeFragment;
import com.stockeate.stockeate.utiles.utiles;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Fragment_lista_compras extends Fragment {

    private ViewModel_lista_compras viewModelListacompras;
    private Button btn_comparar, btn_volver, btn_agregar, btn_buscar, btn_guardar, btn_listas;
    private EditText categoria, marca, presentacion, cantidad, unidad;
    private ListView productos_agregados;
    private ArrayAdapter<class_producto> mArrayAdapterProducto;
    private ArrayList<class_producto> mProductosList = null;
    private ArrayList<class_detalle_lista_compras> mDetalleLista = null;
    private ArrayAdapter<class_detalle_lista_compras> mAdapterDetalleLista;
    private ListView listaResultado;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModelListacompras =
                new ViewModelProvider(this).get(ViewModel_lista_compras.class);
        View root = inflater.inflate(R.layout.fragment_lista_compras, container, false);
        viewModelListacompras.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        this.btn_comparar = root.findViewById(R.id.btn_Comparar);
        this.btn_volver = root.findViewById(R.id.btn_Volver);
        this.btn_agregar = root.findViewById(R.id.btn_Agregar);
        this.btn_buscar = root.findViewById(R.id.btn_Buscar);
        this.btn_guardar = root.findViewById(R.id.btn_Guardar);
        this.btn_listas = root.findViewById(R.id.btn_MisListas);
        this.categoria = root.findViewById(R.id.etxCategoria);
        this.marca = root.findViewById(R.id.etxtMarca);
        this.presentacion = root.findViewById(R.id.etxtPresentacion);
        this.cantidad = root.findViewById(R.id.etxtCantidad);
        this.unidad = root.findViewById(R.id.etxtUnidad);
        this.productos_agregados = root.findViewById(R.id.lista_productos_agregados);
        this.listaResultado = root.findViewById(R.id.ListViewResultado);

        //todos los metodos que usen firebase deben ir abajo del inicializador

        mDetalleLista = new ArrayList<class_detalle_lista_compras>();

        btn_listas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_mis_listas_compras fragment_mis_listas_compras = new Fragment_mis_listas_compras();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_lista_compras, fragment_mis_listas_compras);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btn_comparar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ASI SI PASARA DE UN FRAGMENT A UNA ACT.
                // Intent i = new Intent(getActivity(), Activity_Comparar_Precios.class);
                // startActivity(i);
                Fragment_Comparar_Precios fragment_comparar_precios = new Fragment_Comparar_Precios();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_lista_compras, fragment_comparar_precios);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment homeFragment = new HomeFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_lista_compras, homeFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        //arreglar la busqueda cuando los filtros son muchos.
        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProductosList = new ArrayList<class_producto>();
                try {
                    listarproductos();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        listaResultado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int i = 0, j = 0;

                class_lista_compras lista_compras = new class_lista_compras();
                class_detalle_lista_compras detalle_lista_compras = new class_detalle_lista_compras();

                lista_compras.setId(String.valueOf(i++));
                lista_compras.setId_usuario("1");

                detalle_lista_compras.setId(String.valueOf(j++));
                detalle_lista_compras.setId_lista_compras(String.valueOf(i));
                detalle_lista_compras.setId_producto(mProductosList.get(position).getId());
                detalle_lista_compras.setCategoria(mProductosList.get(position).getCategoria());
                detalle_lista_compras.setMarca(mProductosList.get(position).getMarca());
                detalle_lista_compras.setPresentacion(mProductosList.get(position).getPresentacion());
                detalle_lista_compras.setUnidad(mProductosList.get(position).getUnidad());
                detalle_lista_compras.setCantidad(cantidad.getText().toString());

                Log.i("aca", "hasta aca pasa");

                btn_agregar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (!detalle_lista_compras.getId_producto().isEmpty())
                        {
                            String _cantidad = cantidad.getText().toString();
                            if (_cantidad.equals("")){
                                cantidad.setError("Cantidad requerida");
                            } else {
                                mDetalleLista.add(detalle_lista_compras);
                                mAdapterDetalleLista = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mDetalleLista);
                                productos_agregados.setAdapter(mAdapterDetalleLista);
                                Log.i("Detalle Lista", mDetalleLista.toString());
                            }
                        }
                        //ESTE MENSAJE NO SALE!
                        else {
                            Toast.makeText(getContext(), "Seleccione un producto de la lista", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mDetalleLista.isEmpty()){
                    Toast.makeText(getContext(), "Guardado con exito", Toast.LENGTH_SHORT).show();
                    limpiarDatos();
                    mArrayAdapterProducto.clear();
                }
                else {
                    Toast.makeText(getContext(), "Agregue productos a la lista", Toast.LENGTH_SHORT).show();
                }
            }
        });

        productos_agregados.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mDetalleLista.remove(position);
                mAdapterDetalleLista = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mDetalleLista);
                productos_agregados.setAdapter(mAdapterDetalleLista);
                Toast.makeText(getContext(), "Producto eliminado", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        return root;

    }

    private void listarproductos() throws IOException, JSONException {
        mProductosList.clear();

        Boolean guardar;

        //Asi lee los datos del json estatico
        String jsonFileContent = utiles.leerJson(getContext(), "productos.json");
        JSONArray jsonArray = new JSONArray(jsonFileContent);
        Log.d("Longitud json ", String.valueOf(jsonArray.length()));
        Log.d("json ", jsonArray.toString());
        for (int i = 0; i < jsonArray.length(); i++) {
            class_producto productos = new class_producto();
            Log.d("dentro del for ", String.valueOf(i));
            JSONObject jsonObj = jsonArray.getJSONObject(i);

            guardar = true;

            if (!categoria.getText().toString().isEmpty() ||
                    !marca.getText().toString().isEmpty() ||
                    !presentacion.getText().toString().isEmpty() ||
                    !presentacion.getText().toString().isEmpty() ||
                    !unidad.getText().toString().isEmpty()) {
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
                if (!unidad.getText().toString().isEmpty()) {
                    if (jsonObj.getString("unidad").equals(unidad.getText().toString())) {
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
                    mProductosList.add(productos);
                }
            }
        }

        mProductosList.removeAll(Collections.singleton(null));
        mArrayAdapterProducto = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mProductosList);
        listaResultado.setAdapter(mArrayAdapterProducto);
    }

    private void limpiarDatos(){
        categoria.setText("");
        marca.setText("");
        presentacion.setText("");
        cantidad.setText("");
        unidad.setText("");
    }

}
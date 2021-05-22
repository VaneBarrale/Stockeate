package com.stockeate.stockeate.ui.lista_compras;

import android.icu.text.SymbolTable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stockeate.stockeate.R;
import com.stockeate.stockeate.adapter.Adapter_productos;
import com.stockeate.stockeate.clases.class_lista_compras;
import com.stockeate.stockeate.clases.class_producto;
import com.stockeate.stockeate.ui.comparar_precios.Fragment_Comparar_Precios;
import com.stockeate.stockeate.ui.home.HomeFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class Fragment_lista_compras extends Fragment {

    private ViewModel_lista_compras viewModelListacompras;
    private Button btn_comparar, btn_volver, btn_agregar, btn_buscar;
    private EditText codigo_producto, marca, presentacion, cantidad;
    private ListView productos_agregados;
    private ArrayAdapter<class_producto> mArrayAdapterProducto;
    private ArrayList<class_producto> mProductosList = null;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    //RecyclerView recyclerviewresultado; //me permite administrar las vistas

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
        this.codigo_producto = root.findViewById(R.id.etxtCodigoProducto);
        this.marca = root.findViewById(R.id.etxtMarca);
        this.presentacion = root.findViewById(R.id.etxtPresentacion);
        this.cantidad = root.findViewById(R.id.etxtCantidad);
        this.productos_agregados = root.findViewById(R.id.lista_productos_agregados);
        //this.recyclerviewresultado = root.findViewById(R.id.recyclerViewResultado);

        inicializarFirebase();
        //todos los metodos que usen firebase deben ir abajo del inicializador

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
                listarproductos();
//
            }
        });

        btn_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _codigo_producto = codigo_producto.getText().toString();
                String _marca = marca.getText().toString();
                String _presentacion = presentacion.getText().toString();
                String _cantidad = cantidad.getText().toString();
                if(_codigo_producto.equals("") || _marca.equals("") || _presentacion.equals("") || _cantidad.equals(""))  {
                    validar();
                } else {
                    class_lista_compras lista_compras = new class_lista_compras();
                    lista_compras.setId(UUID.randomUUID().toString());
                    //corregir y pasar el id del usuario.
                    lista_compras.setId_usuario("1");
                    databaseReference.child("lista_compras").child(lista_compras.getId()).setValue(lista_compras);
                    Toast.makeText(getContext(), "Agregar", Toast.LENGTH_LONG).show();
                    limpiarDatos();
                }

                /*//Instanciar también base de datos para lista de compras y guardar ids.

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference firebaseReference = database.getReference("stockeate-3d9e6-default-rtdb/detalle_lista_compras");
                class_detalle_lista_compras detalle_lista = new class_detalle_lista_compras(cantidad.getText());
                firebaseReference.push().setValue(detalle_lista);

                //https://www.youtube.com/watch?v=cZlYW91hEVk&t=636s*/
            }
        });

        return root;
    }

    private void listarproductos() {
        databaseReference.child("Productos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                mProductosList.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        class_producto productos = new class_producto();
                        productos.setId(dataSnapshot.getKey());
                        productos.setId_categoria(dataSnapshot.child("categoria").getValue().toString());
                        productos.setMarca(dataSnapshot.child("marca").getValue().toString());
                        productos.setPresentacion(dataSnapshot.child("presentacion").getValue().toString());

                        mProductosList.add(productos);

                        mArrayAdapterProducto = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mProductosList);
                        productos_agregados.setAdapter(mArrayAdapterProducto);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(getContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void validar() {
        String _codigo_producto = codigo_producto.getText().toString();
        String _marca = marca.getText().toString();
        String _presentacion = presentacion.getText().toString();
        String _cantidad = cantidad.getText().toString();

        if(_codigo_producto.equals("")) {codigo_producto.setError("Codigo producto requerido");}

        else if (_marca.equals("")){marca.setError("Marca requerida");}

        else if(_presentacion.equals("")){presentacion.setError("Presentacion requerida");}

        else if(_cantidad.equals("")){cantidad.setError("Cantidad requerida");}
    }

    private void limpiarDatos(){
        codigo_producto.setText("");
        marca.setText("");
        presentacion.setText("");
        cantidad.setText("");
    }
}
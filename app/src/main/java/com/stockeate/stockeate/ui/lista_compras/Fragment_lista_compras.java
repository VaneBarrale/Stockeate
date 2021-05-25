package com.stockeate.stockeate.ui.lista_compras;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.contentcapture.DataShareWriteAdapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stockeate.stockeate.R;
import com.stockeate.stockeate.clases.class_lista_compras;
import com.stockeate.stockeate.clases.class_producto;
import com.stockeate.stockeate.ui.comparar_precios.Fragment_Comparar_Precios;
import com.stockeate.stockeate.ui.home.HomeFragment;

import org.jetbrains.annotations.NotNull;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class Fragment_lista_compras extends Fragment {

    private ViewModel_lista_compras viewModelListacompras;
    private Button btn_comparar, btn_volver, btn_agregar, btn_buscar;
    private EditText filtro_busqueda, cantidad;
    private ListView productos_agregados;
    private ArrayList<String> busqueda_spinner;
    private ArrayAdapter<class_producto> mArrayAdapterProducto;
    private ArrayList<class_producto> mProductosList = null;
    private Spinner storeSpinner;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
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
        this.filtro_busqueda = root.findViewById(R.id.etxtFiltro);
        this.cantidad = root.findViewById(R.id.etxtCantidad);
        this.productos_agregados = root.findViewById(R.id.lista_productos_agregados);
        this.listaResultado = root.findViewById(R.id.ListViewResultado);
        this.storeSpinner = root.findViewById(R.id.filtroTipo);

        inicializarFirebase();
        //todos los metodos que usen firebase deben ir abajo del inicializador

        busqueda_spinner = new ArrayList<>(Arrays.asList("Marca", "Categoria", "Presentacion"));
        //Como no puedo pasar los valores de una, tengo que adaptarlo a un formato. Esto que sigue se lo da.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, busqueda_spinner);
        storeSpinner.setAdapter(adapter);
        storeSpinner.setSelection(0);

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
                mProductosList = new ArrayList<>();
                Log.d("Buscar", "paso por buscar");
                listarproductos();
            }
        });

        /*btn_agregar.setOnClickListener(new View.OnClickListener() {
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
            }
        });*/

        return root;
    }

    private void listarproductos() {
        //Ordeno por marca, comienzo la busqueda con StartAt -> Lo que escriba y endAt como termine, seria un like.
        String opcion_Spinner = null;

        switch (storeSpinner.getSelectedItem().toString()){
            case "Marca": //Aca lo macheo con los nombres de la base
                opcion_Spinner = "marca";
                break;
            case "Categoria": //Aca lo macheo con los nombres de la base
                opcion_Spinner = "id_categoria";
                break;
            case "Presentacion": //Aca lo macheo con los nombres de la base
                opcion_Spinner = "id_presentacion";
                break;
            default: break;
        }

        databaseReference.child("Productos").orderByChild(opcion_Spinner).startAt(filtro_busqueda.getText().toString()).endAt(filtro_busqueda.getText().toString() + "\uf8ff")
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                mProductosList.clear();
                Log.i("Info ", snapshot.getValue().toString());
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        class_producto productos = new class_producto();
                        productos.setId(snapshot.getKey());
                        productos.setIdCategoria(dataSnapshot.child("id_categoria").getValue().toString());
                        productos.setMarca(dataSnapshot.child("marca").getValue().toString());
                        productos.setIdPresentacion(dataSnapshot.child("id_presentacion").getValue().toString());

                        mProductosList.add(productos);

                        Log.d("Listarproductos", "paso por adentro del for listar productos " + productos);
                        //Adapter los prepara, setAdapter los llena
                        mArrayAdapterProducto = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mProductosList);
                        listaResultado.setAdapter(mArrayAdapterProducto);
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
        String _filtro_busqueda = filtro_busqueda.getText().toString();
        String _cantidad = cantidad.getText().toString();

        if(_filtro_busqueda.equals("")) {filtro_busqueda.setError("Ingrese un filtro");}

        else if(_cantidad.equals("")){cantidad.setError("Cantidad requerida");}
    }

    private void limpiarDatos(){
        filtro_busqueda.setText("");
        cantidad.setText("");
    }
}
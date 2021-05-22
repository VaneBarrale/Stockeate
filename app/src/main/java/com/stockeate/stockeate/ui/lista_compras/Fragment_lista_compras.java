package com.stockeate.stockeate.ui.lista_compras;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stockeate.stockeate.R;
import com.stockeate.stockeate.adapter.Adapter_productos;
import com.stockeate.stockeate.clases.class_producto;
import com.stockeate.stockeate.ui.comparar_precios.Fragment_Comparar_Precios;
import com.stockeate.stockeate.ui.home.HomeFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class Fragment_lista_compras extends Fragment {

    private ViewModel_lista_compras viewModelListacompras;
    private Button btn_comparar, btn_volver, btn_agregar, btn_buscar;
    private EditText codigo_producto, marca, presentacion, cantidad;
    private DatabaseReference bbdd;
    private Adapter_productos mAdapter;
    private ArrayList<class_producto> mProductosList;
    RecyclerView recyclerviewresultado; //me permite administrar las vistas

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

        bbdd = FirebaseDatabase.getInstance().getReference();

        this.btn_comparar = root.findViewById(R.id.btn_Comparar);
        this.btn_volver = root.findViewById(R.id.btn_Volver);
        this.btn_agregar = root.findViewById(R.id.btn_Agregar);
        this.btn_buscar = root.findViewById(R.id.btn_Buscar);
        this.codigo_producto = root.findViewById(R.id.etxtCodigoProducto);
        this.marca = root.findViewById(R.id.etxtMarca);
        this.presentacion = root.findViewById(R.id.etxtPresentacion);
        this.cantidad = root.findViewById(R.id.etxtCantidad);
        this.recyclerviewresultado = root.findViewById(R.id.recyclerViewResultado);

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
                recyclerviewresultado.setLayoutManager(new LinearLayoutManager(getContext()));

                getProductosFirebase();

                /*https://www.youtube.com/watch?v=em2CWW5-9Rc 24.25
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference firebaseReference = database.getReference("stockeate-3d9e6-default-rtdb/Productos");

                //
                String codigo_producto_buscar = codigo_producto.getText().toString();

                //BUSCO POR CODIGO DE PRODUCTO (ID DE BASE DE DATOS)
                Query q = firebaseReference.orderByChild("id").equalTo(codigo_producto_buscar);
                q.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                        Log.d("Buscar", "Paso por la busqueda" + resultado);
                        //VER PORQUE NO ESTA ENTRANDO AL FOR 15/05
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            class_producto clase_producto = new class_producto();
                            clase_producto.setId(data.child("id").getValue().toString());
                            resultado.setText(codigo_producto_buscar);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {}
                });
            }*/
            }
        });

        btn_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

    private void getProductosFirebase(){
        bbdd.child("Productos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                mProductosList.clear();
                if (snapshot.exists()){
                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                        String producto = dataSnapshot.child("marca").getValue().toString();
                        mProductosList.add(new class_producto(marca));
                    }

                    mAdapter = new Adapter_productos(mProductosList, R.layout.lista_productos);
                    recyclerviewresultado.setAdapter(mAdapter);
                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });
    }

}
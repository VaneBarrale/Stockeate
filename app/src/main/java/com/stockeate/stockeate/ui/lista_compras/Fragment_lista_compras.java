package com.stockeate.stockeate.ui.lista_compras;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.stockeate.stockeate.R;
import com.stockeate.stockeate.ui.comparar_precios.Fragment_Comparar_Precios;
import com.stockeate.stockeate.ui.home.HomeFragment;

public class Fragment_lista_compras extends Fragment {

    private ViewModel_lista_compras viewModelListacompras;
    private Button btn_comparar, btn_volver;

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

        return root;
    }
}
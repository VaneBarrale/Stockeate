package com.stockeate.stockeate.ui.promocion;

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
import com.stockeate.stockeate.ui.agregar_promocion.Fragment_Agregar_Promocion;
import com.stockeate.stockeate.ui.agregar_promocion.ViewModel_agregar_promocion;
import com.stockeate.stockeate.ui.home.HomeFragment;
import com.stockeate.stockeate.ui.ubicacion.ubicacion;

import static com.stockeate.stockeate.R.layout.fragment_promocion;

public class Fragment_Promocion extends Fragment {

    private PromocionViewModel viewModelPromocion;
    private Button btn_volver, btn_como_llegar, btn_agregar;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModelPromocion = new ViewModelProvider(this).get(PromocionViewModel.class);
        View root = inflater.inflate(R.layout.fragment_promocion, container, false);
        viewModelPromocion.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        this.btn_volver = root.findViewById(R.id.btn_Volver);
        this.btn_como_llegar = root.findViewById(R.id.btn_como_llegar);
        this.btn_agregar = root.findViewById(R.id.btn_Agregar);

        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment home = new HomeFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_promocion, home);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btn_como_llegar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ubicacion ubicacion = new ubicacion();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_promocion, ubicacion);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btn_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_Agregar_Promocion agregar_promocion = new Fragment_Agregar_Promocion();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_promocion, agregar_promocion);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return root;
    }
}
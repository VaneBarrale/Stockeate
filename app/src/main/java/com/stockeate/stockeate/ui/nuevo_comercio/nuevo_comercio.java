package com.stockeate.stockeate.ui.nuevo_comercio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.stockeate.stockeate.R;
import com.stockeate.stockeate.ui.home.HomeFragment;
import com.stockeate.stockeate.ui.reportes.Fragment_reportes;
import com.stockeate.stockeate.ui.reportes.ReportesViewModel;

import static com.stockeate.stockeate.R.layout.fragment_nuevo_comercio;
import static com.stockeate.stockeate.R.layout.fragment_ubicacion;

public class nuevo_comercio extends Fragment {

    private NuevoComercioViewModel viewModelNuevoComercio;
    private Button btn_volver, btn_agregar;

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

        return root;
    }
}
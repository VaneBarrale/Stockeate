package com.stockeate.stockeate.ui.nuevo_comercio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.stockeate.stockeate.R;

import static com.stockeate.stockeate.R.layout.fragment_nuevo_comercio;
import static com.stockeate.stockeate.R.layout.fragment_ubicacion;

public class nuevo_comercio extends Fragment {

    private NuevoComercioViewModel viewModelNuevoComercio;

    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @NonNull Bundle savedInstanceState) {
        viewModelNuevoComercio = new ViewModelProvider(this).get(NuevoComercioViewModel.class);
        View root = inflater.inflate(fragment_nuevo_comercio, container, false);
        final TextView textuevocomercio = root.findViewById(R.id.text_nuevo_comercio);
        return root;
    }
}
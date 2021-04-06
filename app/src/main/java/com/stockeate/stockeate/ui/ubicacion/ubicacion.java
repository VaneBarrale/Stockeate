package com.stockeate.stockeate.ui.ubicacion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.stockeate.stockeate.R;

import static com.stockeate.stockeate.R.layout.fragment_ubicacion;

public class ubicacion extends Fragment {

    private UbicacionViewModel viewModelUbicacion;

    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @NonNull Bundle savedInstanceState) {
        viewModelUbicacion = new ViewModelProvider(this).get(UbicacionViewModel.class);
        View root = inflater.inflate(fragment_ubicacion, container, false);
        return root;
    }
}
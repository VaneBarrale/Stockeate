package com.stockeate.stockeate.ui.precios;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.stockeate.stockeate.R;

import static com.stockeate.stockeate.R.layout.fragment_precios;

public class precios extends Fragment {

    private PreciosViewModel viewModelPrecios;

    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @NonNull Bundle savedInstanceState) {
        viewModelPrecios = new ViewModelProvider(this).get(PreciosViewModel.class);
        View root = inflater.inflate(fragment_precios, container, false);
        return root;


    }
}
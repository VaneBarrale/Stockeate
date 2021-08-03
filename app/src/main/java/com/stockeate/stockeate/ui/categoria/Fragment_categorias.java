package com.stockeate.stockeate.ui.categoria;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stockeate.stockeate.R;
import com.stockeate.stockeate.ui.reportes.ReportesViewModel;

public class Fragment_categorias extends Fragment {

    private CategoriasViewModel categoriasViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        categoriasViewModel = new ViewModelProvider(this).get(CategoriasViewModel.class);
        View root = inflater.inflate(R.layout.fragment_categorias, container, false);
        categoriasViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

    return root;

    }
}
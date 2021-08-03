package com.stockeate.stockeate.ui.marcas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.stockeate.stockeate.R;

public class Fragment_marcas extends Fragment {

    private MarcasViewModel marcasViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        marcasViewModel = new ViewModelProvider(this).get(MarcasViewModel.class);
        View root = inflater.inflate(R.layout.fragment_marcas, container, false);
        marcasViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

    return root;

    }
}
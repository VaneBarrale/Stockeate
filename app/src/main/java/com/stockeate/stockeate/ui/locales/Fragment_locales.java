package com.stockeate.stockeate.ui.locales;

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

public class Fragment_locales extends Fragment {

    private LocalesViewModel localesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        localesViewModel = new ViewModelProvider(this).get(LocalesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_locales, container, false);
        localesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

    return root;

    }
}
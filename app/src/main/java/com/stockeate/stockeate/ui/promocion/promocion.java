package com.stockeate.stockeate.ui.promocion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.stockeate.stockeate.R;

import static com.stockeate.stockeate.R.layout.fragment_promocion;

public class promocion extends Fragment {

    private PromocionViewModel viewModelPromocion;

    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @NonNull Bundle savedInstanceState) {
        viewModelPromocion = new ViewModelProvider(this).get(PromocionViewModel.class);
        View root = inflater.inflate(fragment_promocion, container, false);
        return root;
    }
}
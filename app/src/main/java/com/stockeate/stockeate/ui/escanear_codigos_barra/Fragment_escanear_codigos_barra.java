package com.stockeate.stockeate.ui.escanear_codigos_barra;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.stockeate.stockeate.R;

import static com.stockeate.stockeate.R.layout.fragment_escanear_codigos_barra;

public class Fragment_escanear_codigos_barra extends Fragment {

    private ViewModel_escanear_codigos_barra viewModelEscanear;

    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @NonNull Bundle savedInstanceState) {
        viewModelEscanear = new ViewModelProvider(this).get(ViewModel_escanear_codigos_barra.class);
        View root = inflater.inflate(fragment_escanear_codigos_barra, container, false);
        final TextView text_escanear = root.findViewById(R.id.text_escanear);
        return root;
    }
}
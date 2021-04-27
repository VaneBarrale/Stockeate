package com.stockeate.stockeate.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.stockeate.stockeate.R;
import com.stockeate.stockeate.ui.lista_compras.Fragment_lista_compras;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private ImageButton btn_compras;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        /*this.btn_compras = root.findViewById(R.id.btn_compras);

        btn_compras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent().setClass (getActivity(), Fragment_lista_compras.class);
                startActivity(i);
            }
        });*/

        btn_compras = root.findViewById(R.id.btn_compras);

        btn_compras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_lista_compras fragment_lista_compras = new Fragment_lista_compras();

                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_home, fragment_lista_compras);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return root;
    }
}

package com.stockeate.stockeate.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.stockeate.stockeate.R;
import com.stockeate.stockeate.activities.Activity_Menu;
import com.stockeate.stockeate.activities.MainActivity;
import com.stockeate.stockeate.ui.escanear_codigos_barra.Fragment_escanear_codigos_barra;
import com.stockeate.stockeate.ui.faqs.Fragment_faqs;
import com.stockeate.stockeate.ui.lista_compras.Fragment_lista_compras;
import com.stockeate.stockeate.ui.nuevo_comercio.nuevo_comercio;
import com.stockeate.stockeate.ui.precios.precios;
import com.stockeate.stockeate.ui.promocion.Fragment_Promocion;
import com.stockeate.stockeate.ui.reportes.Fragment_reportes;
import com.stockeate.stockeate.ui.ubicacion.ubicacion;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private ImageButton btn_faqs, btn_salir, btn_compras, btn_reportes, btn_escanear, btn_precio, btn_promocion, btn_nuevo_comercio;
    private GoogleSignInClient mGoogleSignInClient;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        btn_compras = root.findViewById(R.id.btn_compras);
        btn_escanear = root.findViewById(R.id.btn_escanear);
        btn_reportes = root.findViewById(R.id.btn_reportes);
        btn_precio = root.findViewById(R.id.btn_precio);
        btn_promocion = root.findViewById(R.id.btn_promocion);
        btn_nuevo_comercio = root.findViewById(R.id.btn_nuevo_comercio);
        btn_faqs = root.findViewById(R.id.btn_FAQS);
        btn_salir = root.findViewById(R.id.btn_Salir);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(HomeFragment.super.getContext(), gso);

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

        btn_escanear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_escanear_codigos_barra fragment_escanear_codigos_barra = new Fragment_escanear_codigos_barra();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_home, fragment_escanear_codigos_barra);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btn_reportes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_reportes fragment_reportes = new Fragment_reportes();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_home, fragment_reportes);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btn_precio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                precios precios = new precios();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_home, precios);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btn_promocion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_Promocion promocion = new Fragment_Promocion();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_home, promocion);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btn_nuevo_comercio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nuevo_comercio nuevo_comercio = new nuevo_comercio();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_home, nuevo_comercio);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btn_faqs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_faqs fragment_faqs = new Fragment_faqs();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_home, fragment_faqs);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btn_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Salir");
                builder.setMessage("¿Estás seguro que querés salir?");
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mGoogleSignInClient.signOut();
                        Toast.makeText(HomeFragment.super.getContext(), "El usuario no se encuentra logueado", Toast.LENGTH_SHORT).show();
                        getActivity().finishAffinity();
                        Intent i = new Intent(getActivity(), MainActivity.class);
                        startActivity(i);
                        System.exit(0);
                    }
                });

                builder.show();
            }
        });

        return root;
    }
}

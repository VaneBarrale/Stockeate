package com.stockeate.stockeate.ui.comparar_precios;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TableLayout;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.stockeate.stockeate.R;
import com.stockeate.stockeate.clases.class_comparar_precios;
import com.stockeate.stockeate.clases.class_detalle_lista_compras;
import com.stockeate.stockeate.clases.class_producto;
import com.stockeate.stockeate.ui.detalle_lista_precios.Fragment_Detalle_Lista_Precios;
import com.stockeate.stockeate.ui.lista_compras.Fragment_lista_compras;
import com.stockeate.stockeate.ui.ubicacion.ubicacion;
import com.stockeate.stockeate.utiles.utiles;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

public class Fragment_Comparar_Precios extends Fragment {

    private ViewModel_comparar_precios CompararViewModel;
    private Button btn_detalle, btn_volver, btn_como_llegar;
    private ArrayList<class_comparar_precios> mComparacionList = null;
    private ArrayAdapter<class_comparar_precios> mArrayAdapterComparar;
    private ListView listaComparacion;
    private MaterialAlertDialogBuilder ratingDialog;

    @SuppressLint("ResourceType")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        CompararViewModel = new ViewModelProvider(this).get(ViewModel_comparar_precios.class);
        View root = inflater.inflate(R.layout.fragment_comparar_precios, container, false);
        CompararViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        this.btn_detalle = root.findViewById(R.id.btn_Detalle);
        this.btn_volver = root.findViewById(R.id.btn_Volver);
        this.btn_como_llegar = root.findViewById(R.id.btn_como_llegar);
        this.listaComparacion = root.findViewById(R.id.ListViewComparacion);

        btn_como_llegar.setEnabled(false);

        mComparacionList = new ArrayList<class_comparar_precios>();

        btn_detalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_Detalle_Lista_Precios detalle_lista_precios = new Fragment_Detalle_Lista_Precios();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_comparar_precios, detalle_lista_precios);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ratingDialog.show();
            }
        });

        try {
            cargarDatosTabla();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        listaComparacion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int i = 0, j = 0;

                class_comparar_precios comparar_precios = new class_comparar_precios();

                comparar_precios.setId(String.valueOf(mComparacionList.get(position).getId()));
                comparar_precios.setId_local(String.valueOf(mComparacionList.get(position).getId_local()));
                comparar_precios.setLocal(String.valueOf(mComparacionList.get(position).getLocal()));
                comparar_precios.setPrecio_total(Float.parseFloat(String.valueOf(mComparacionList.get(position).getPrecio_total())));

                if(!comparar_precios.getId().isEmpty()){
                    btn_como_llegar.setEnabled(true);
                    btn_como_llegar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ubicacion ubicacion = new ubicacion();
                            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

                            LatLng origen= new LatLng(-31.407994999999996,-62.08017333333334);

                            String id_local = String.valueOf(mComparacionList.get(position).getId_local());


                            switch (id_local) {
                                case "1":
                                    //Castelli
                                    LatLng destino1 = new LatLng(-31.4222692, -62.0885534);
                                    ubicacion.comoLlegar(origen, destino1);
                                    break;
                                case "2":
                                    //Norte
                                    LatLng destino2 = new LatLng(-31.417809772868658, -62.08937372955097);
                                    ubicacion.comoLlegar(origen, destino2);
                                    break;
                                case "3":
                                    //Chapu 9 de julio
                                    LatLng destino3 = new LatLng(-31.4315541,-62.0822393);
                                    ubicacion.comoLlegar(origen, destino3);
                                    break;
                                case "4":
                                    //Pigüino
                                    LatLng destino4 = new LatLng(-31.4305975, -62.0794611);
                                    ubicacion.comoLlegar(origen, destino4);
                                    break;
                                case "5":
                                    //Hiper
                                    LatLng destino5 = new LatLng(-31.42773493119209, -62.11414910012128);
                                    ubicacion.comoLlegar(origen, destino5);
                                    break;
                                case "6":
                                    //Vea
                                    LatLng destino6 = new LatLng(-31.428872563164568, -62.08971817117201);
                                    ubicacion.comoLlegar(origen, destino6);
                                    break;
                                default:
                                    break;
                            }

                            //VER PORQUE EL ID DEL LOCAL ESTA NULL
                            if(id_local=="5"){
                                LatLng destino = new LatLng(-31.42773493119209, -62.11414910012128);
                                ubicacion.comoLlegar(origen, destino);
                            }

                            transaction.replace(R.id.fragment_comparar_precios, ubicacion);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }
                    });
                }
                //este mensaje no sale.
                else{
                    Toast.makeText(getContext(), "Seleccione un local de la lista", Toast.LENGTH_SHORT).show();
                }
            }
        });

        final LinearLayout ratingLayout = new LinearLayout(this.getContext());
        final RatingBar starsBar = new RatingBar(this.getContext());
        starsBar.setRating(0);
        starsBar.setMax(5);
        starsBar.setStepSize(0.5F);
        starsBar.setNumStars(5);
        starsBar.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));
        ratingLayout.addView(starsBar);
        ratingLayout.setGravity(Gravity.CENTER);
        this.ratingDialog = new MaterialAlertDialogBuilder(this.getContext());
        this.ratingDialog.setTitle("Puntuanos");
        this.ratingDialog.setMessage("¿Te resultaron útiles las recomendaciones?");
        this.ratingDialog.setCancelable(false);
        this.ratingDialog.setView(ratingLayout);
        this.ratingDialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface ratingBar, int id) {
                ratingBar.cancel(); //ACA FALTARIA AGREGAR DONDE SE GUARDARIA

                Fragment_lista_compras lista_compras = new Fragment_lista_compras();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_comparar_precios, lista_compras);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        this.ratingDialog.setNegativeButton("Salir", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface ratingBar, int id) {
                ratingBar.cancel();

                Fragment_lista_compras lista_compras = new Fragment_lista_compras();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_comparar_precios, lista_compras);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return root;
    }

    private void cargarDatosTabla() throws IOException, JSONException {

        String jsonFileContent = utiles.leerJson(getContext(), "comparaciones.json");
        JSONArray jsonArray = new JSONArray(jsonFileContent);

        for (int i = 0; i < jsonArray.length(); i++) {
            class_comparar_precios comparar_precios = new class_comparar_precios();
            JSONObject jsonObj = jsonArray.getJSONObject(i);
            comparar_precios.setLocal(jsonObj.getString("local"));
            comparar_precios.setPrecio_total(Float.parseFloat(jsonObj.getString("precio_total")));
            comparar_precios.setId_local(jsonObj.getString("id_local"));
            mComparacionList.add(comparar_precios);
        }

        mComparacionList.removeAll(Collections.singleton(null));
        mArrayAdapterComparar = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mComparacionList);
        listaComparacion.setAdapter(mArrayAdapterComparar);
    }

}
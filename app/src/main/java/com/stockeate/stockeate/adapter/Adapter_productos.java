package com.stockeate.stockeate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.stockeate.stockeate.R;
import com.stockeate.stockeate.clases.class_detalle_lista_compras;
import com.stockeate.stockeate.clases.class_producto;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

//Aca se establecen los valores que obtenemos de la bd en las vistas
public class Adapter_productos extends RecyclerView.Adapter<Adapter_productos.ViewHolder>{

    private int resource;
    private ArrayList<class_producto> productosList;

    public Adapter_productos(ArrayList<class_producto> productosList, int resource) {
        this.productosList = productosList;
        this.resource = resource;
    }

    //Donde obtengo las referencias a las vistas
    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView resultado;
        public View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            this.resultado = (TextView) view.findViewById(R.id.txvResultado);
        }
    }

    @NonNull
    @NotNull
    @Override
    //metodo donde se creara la vista
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new ViewHolder(view); //le paso view porque asi lo defini en el constructos ViewHolder, linea 31
    }

    //donde accedemos a los datos a mostrar en las vistas
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        class_producto producto = productosList.get(position);//asi obtengo un solo mensaje
        holder.resultado.setText(producto.getMarca());
    }

    //retorna cuantas vistas estamos obteniendo
    @Override
    public int getItemCount() {
        return productosList.size();
    }

}


package com.stockeate.stockeate.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.stockeate.stockeate.R;
import com.stockeate.stockeate.clases.class_historial_precios;

import java.util.ArrayList;

public class Adapter_historial_precios extends RecyclerView.Adapter<Adapter_historial_precios.ViewHolderHistorialPrecios> implements View.OnClickListener {

    ArrayList<class_historial_precios> listaHistorialPrecios;
    private View.OnClickListener listener;

    public Adapter_historial_precios(ArrayList<class_historial_precios> listaHistorialPrecios) {
        this.listaHistorialPrecios = listaHistorialPrecios;
    }

    @Override
    public Adapter_historial_precios.ViewHolderHistorialPrecios onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item_historial_precios, null, false);
        root.setOnClickListener(this);
        return new Adapter_historial_precios.ViewHolderHistorialPrecios(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderHistorialPrecios holder, int position) {
        holder.categoria.setText(listaHistorialPrecios.get(position).getCategoria());
        holder.marca.setText(listaHistorialPrecios.get(position).getMarca());
        holder.presentacion.setText(listaHistorialPrecios.get(position).getPresentacion());
        holder.unidad.setText(listaHistorialPrecios.get(position).getUnidad());
        holder.comercio.setText(listaHistorialPrecios.get(position).getCategoria());
        holder.precio.setText((String.valueOf(listaHistorialPrecios.get(position).getPrecio())));
    }

    @Override
    public int getItemCount() {
        return listaHistorialPrecios.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }

    public class ViewHolderHistorialPrecios extends RecyclerView.ViewHolder {

        TextView categoria, marca, presentacion, unidad, comercio, precio;

        public ViewHolderHistorialPrecios(@NonNull View itemView) {
            super(itemView);
            categoria=itemView.findViewById(R.id.Categoria);
            marca=itemView.findViewById(R.id.Marca);
            presentacion=itemView.findViewById(R.id.Presentacion);
            unidad=itemView.findViewById(R.id.Unidad);
            comercio=itemView.findViewById(R.id.Comercio);
            precio=itemView.findViewById(R.id.Precio);
        }
    }
}

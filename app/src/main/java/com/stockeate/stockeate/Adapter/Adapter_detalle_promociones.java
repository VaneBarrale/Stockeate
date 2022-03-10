package com.stockeate.stockeate.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.stockeate.stockeate.R;
import com.stockeate.stockeate.clases.class_detalle_promocion;

import java.util.ArrayList;


public class Adapter_detalle_promociones extends RecyclerView.Adapter<Adapter_detalle_promociones.ViewHolderDetallePromociones>{

    ArrayList<class_detalle_promocion> listaDetallePromociones;

    public Adapter_detalle_promociones(ArrayList<class_detalle_promocion> listaDetallePromociones) {
        this.listaDetallePromociones = listaDetallePromociones;
    }

    @Override
    public ViewHolderDetallePromociones onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item_detalle_promociones, null, false);
        return new ViewHolderDetallePromociones(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDetallePromociones holder, int position) {
        holder.categoria.setText(listaDetallePromociones.get(position).getCategoria());
        holder.marca.setText(listaDetallePromociones.get(position).getMarca());
        holder.presentacion.setText(listaDetallePromociones.get(position).getPresentacion());
        holder.local.setText(listaDetallePromociones.get(position).getLocal());
    }

    @Override
    public int getItemCount() {
        return listaDetallePromociones.size();
    }

    public class ViewHolderDetallePromociones extends RecyclerView.ViewHolder {

        TextView categoria, marca, presentacion, local;

        public ViewHolderDetallePromociones(@NonNull View itemView) {
            super(itemView);
            categoria=itemView.findViewById(R.id.categoria);
            marca=itemView.findViewById(R.id.marca);
            presentacion=itemView.findViewById(R.id.presentacion);
            local=itemView.findViewById(R.id.Local);
        }
    }
}

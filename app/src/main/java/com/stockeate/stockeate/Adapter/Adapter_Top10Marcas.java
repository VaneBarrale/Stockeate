package com.stockeate.stockeate.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.stockeate.stockeate.R;
import com.stockeate.stockeate.clases.class_top_10;

import java.util.ArrayList;

public class Adapter_Top10Marcas extends RecyclerView.Adapter<Adapter_Top10Marcas.ViewHolderMarcas> {

    ArrayList<class_top_10> listaMarcas;

    public Adapter_Top10Marcas(ArrayList<class_top_10> listaMarcas) {
        this.listaMarcas = listaMarcas;
    }

    @Override
    public ViewHolderMarcas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item_top10marcas, null, false);
        return new ViewHolderMarcas(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderMarcas holder, int position) {
        holder.top.setText(listaMarcas.get(position).getTop());
        holder.marca.setText(listaMarcas.get(position).getMarca());
    }

    @Override
    public int getItemCount() {
        return listaMarcas.size();
    }

    public class ViewHolderMarcas extends RecyclerView.ViewHolder {

        TextView top,marca;

        public ViewHolderMarcas(@NonNull View itemView) {
            super(itemView);
            top=itemView.findViewById(R.id.idTop);
            marca=itemView.findViewById(R.id.Marca);
        }
    }
}

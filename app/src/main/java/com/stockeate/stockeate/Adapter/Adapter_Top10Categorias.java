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

public class Adapter_Top10Categorias extends RecyclerView.Adapter<Adapter_Top10Categorias.ViewHolderCategorias> {

    ArrayList<class_top_10> listaCategorias;

    public Adapter_Top10Categorias(ArrayList<class_top_10> listaCategorias) {
        this.listaCategorias = listaCategorias;
    }

    @Override
    public Adapter_Top10Categorias.ViewHolderCategorias onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item_top10categorias, null, false);
        return new Adapter_Top10Categorias.ViewHolderCategorias(root);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Top10Categorias.ViewHolderCategorias holder, int position) {
        holder.top.setText(listaCategorias.get(position).getTop());
        holder.categoria.setText(listaCategorias.get(position).getCategoria());
    }

    @Override
    public int getItemCount() {
        return listaCategorias.size();
    }

    public class ViewHolderCategorias extends RecyclerView.ViewHolder {

        TextView top,categoria;

        public ViewHolderCategorias(@NonNull View itemView) {
            super(itemView);
            top=itemView.findViewById(R.id.idTop);
            categoria=itemView.findViewById(R.id.Categoria);
        }
    }
}

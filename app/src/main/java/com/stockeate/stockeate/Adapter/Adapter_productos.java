package com.stockeate.stockeate.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.stockeate.stockeate.R;
import com.stockeate.stockeate.clases.class_producto;

import java.util.ArrayList;

public class Adapter_productos extends RecyclerView.Adapter<Adapter_productos.ViewHolderProductos> implements View.OnClickListener {

    ArrayList<class_producto> listaProductos;
    private View.OnClickListener listener;

    public Adapter_productos(ArrayList<class_producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    @Override
    public Adapter_productos.ViewHolderProductos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item_productos, null, false);
        root.setOnClickListener(this);
        return new Adapter_productos.ViewHolderProductos(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderProductos holder, int position) {
        holder.categoria.setText(listaProductos.get(position).getCategoria());
        holder.marca.setText(listaProductos.get(position).getMarca());
        holder.presentacion.setText(listaProductos.get(position).getPresentacion());
        holder.unidad.setText(listaProductos.get(position).getUnidad());
    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
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

    public class ViewHolderProductos extends RecyclerView.ViewHolder {

        TextView categoria, marca, presentacion, unidad;

        public ViewHolderProductos(@NonNull View itemView) {
            super(itemView);
            categoria=itemView.findViewById(R.id.Categoria);
            marca=itemView.findViewById(R.id.Marca);
            presentacion=itemView.findViewById(R.id.Presentacion);
            unidad=itemView.findViewById(R.id.Unidad);
        }
    }
}

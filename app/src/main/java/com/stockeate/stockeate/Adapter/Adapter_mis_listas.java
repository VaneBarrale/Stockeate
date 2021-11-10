package com.stockeate.stockeate.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.stockeate.stockeate.R;
import com.stockeate.stockeate.clases.class_lista_compras;

import java.util.ArrayList;

public class Adapter_mis_listas extends RecyclerView.Adapter<Adapter_mis_listas.ViewHolderMisListas> implements View.OnClickListener {

    ArrayList<class_lista_compras> listaMisListas;
    private View.OnClickListener listener;

    public Adapter_mis_listas(ArrayList<class_lista_compras> listaMisListas) {
        this.listaMisListas = listaMisListas;
    }

    @Override
    public Adapter_mis_listas.ViewHolderMisListas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item_mis_listas, null, false);
        root.setOnClickListener(this);
        return new Adapter_mis_listas.ViewHolderMisListas(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderMisListas holder, int position) {
        holder.idLista.setText(listaMisListas.get(position).getId_lista_compras());
        holder.fecha.setText(listaMisListas.get(position).getFecha());
    }

    @Override
    public int getItemCount() {
        return listaMisListas.size();
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

    public class ViewHolderMisListas extends RecyclerView.ViewHolder {

        TextView idLista, fecha;

        public ViewHolderMisListas(@NonNull View itemView) {
            super(itemView);
            idLista=itemView.findViewById(R.id.idLista);
            fecha=itemView.findViewById(R.id.Fecha);
        }
    }
}

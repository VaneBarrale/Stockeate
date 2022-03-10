package com.stockeate.stockeate.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.stockeate.stockeate.R;
import com.stockeate.stockeate.clases.class_promociones;

import java.util.ArrayList;


public class Adapter_promociones extends RecyclerView.Adapter<Adapter_promociones.ViewHolderPromociones> implements View.OnClickListener {

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    ArrayList<class_promociones> listaPromociones;
    private View.OnClickListener listener;

    public Adapter_promociones(ArrayList<class_promociones> listaPromociones) {
        this.listaPromociones = listaPromociones;
    }

    @Override
    public Adapter_promociones.ViewHolderPromociones onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item_promociones, null, false);
        root.setOnClickListener(this);
        return new Adapter_promociones.ViewHolderPromociones(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPromociones holder, int position) {
        holder.tipo_promocion.setText(listaPromociones.get(position).getTipo_promocion());
    }

    @Override
    public int getItemCount() {
        return listaPromociones.size();
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

    public class ViewHolderPromociones extends RecyclerView.ViewHolder {

        TextView tipo_promocion;

        public ViewHolderPromociones(@NonNull View itemView) {
            super(itemView);
            tipo_promocion=itemView.findViewById(R.id.TipoPromocion);
        }
    }
}

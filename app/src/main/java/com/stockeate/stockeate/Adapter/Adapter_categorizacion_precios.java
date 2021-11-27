package com.stockeate.stockeate.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.stockeate.stockeate.R;
import com.stockeate.stockeate.clases.class_categorizacion_precios;
import java.util.ArrayList;

public class Adapter_categorizacion_precios extends RecyclerView.Adapter<Adapter_categorizacion_precios.ViewHolderCategorizacionPrecios> implements View.OnClickListener {

    ArrayList<class_categorizacion_precios> listaCategorizacionPrecios;
    private View.OnClickListener listener;

    public Adapter_categorizacion_precios(ArrayList<class_categorizacion_precios> listaCategorizacionPrecios) {
        this.listaCategorizacionPrecios = listaCategorizacionPrecios;
    }

    @Override
    public Adapter_categorizacion_precios.ViewHolderCategorizacionPrecios onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item_categorizacion_precios, null, false);
        root.setOnClickListener(this);
        return new Adapter_categorizacion_precios.ViewHolderCategorizacionPrecios(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCategorizacionPrecios holder, int position) {
        holder.categoria.setText(listaCategorizacionPrecios.get(position).getCategoria());
        holder.marca.setText(listaCategorizacionPrecios.get(position).getMarca());
        holder.presentacion.setText(listaCategorizacionPrecios.get(position).getPresentacion());
        holder.codigoBarra.setText(listaCategorizacionPrecios.get(position).getCodigoBarra());
        holder.local.setText(listaCategorizacionPrecios.get(position).getLocal());
        holder.precio.setText((String.valueOf(listaCategorizacionPrecios.get(position).getPrecio_total())));
    }

    @Override
    public int getItemCount() {
        return listaCategorizacionPrecios.size();
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

    public class ViewHolderCategorizacionPrecios extends RecyclerView.ViewHolder {

        TextView categoria, marca, presentacion, codigoBarra, local, precio;

        public ViewHolderCategorizacionPrecios(@NonNull View itemView) {
            super(itemView);
            categoria=itemView.findViewById(R.id.Categoria);
            marca=itemView.findViewById(R.id.Marca);
            presentacion=itemView.findViewById(R.id.Presentacion);
            codigoBarra=itemView.findViewById(R.id.Codigo);
            local=itemView.findViewById(R.id.Local);
            precio=itemView.findViewById(R.id.Precio);
        }
    }
}

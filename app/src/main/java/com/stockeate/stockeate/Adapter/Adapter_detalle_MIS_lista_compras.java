package com.stockeate.stockeate.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.stockeate.stockeate.R;
import com.stockeate.stockeate.clases.class_detalle_lista_compras;

import java.util.ArrayList;

public class Adapter_detalle_MIS_lista_compras extends RecyclerView.Adapter<Adapter_detalle_MIS_lista_compras.ViewHolderDetalleLista> {

    ArrayList<class_detalle_lista_compras> listaDetalleListaCompras;

    public Adapter_detalle_MIS_lista_compras(ArrayList<class_detalle_lista_compras> listaDetalleListaCompras) {
        this.listaDetalleListaCompras = listaDetalleListaCompras;
    }

    @Override
    public Adapter_detalle_MIS_lista_compras.ViewHolderDetalleLista onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item_detalle_mis_lista_compras, null, false);
        return new Adapter_detalle_MIS_lista_compras.ViewHolderDetalleLista(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDetalleLista holder, int position) {
        holder.categoria.setText(listaDetalleListaCompras.get(position).getCategoria());
        holder.marca.setText(listaDetalleListaCompras.get(position).getMarca());
        holder.presentacion.setText(listaDetalleListaCompras.get(position).getPresentacion());
        holder.codigoBarra.setText(listaDetalleListaCompras.get(position).getCodigoBarra());
        holder.cantidad.setText(listaDetalleListaCompras.get(position).getCantidad());
    }

    @Override
    public int getItemCount() {
        return listaDetalleListaCompras.size();
    }

    public class ViewHolderDetalleLista extends RecyclerView.ViewHolder {

        TextView categoria, marca, presentacion, codigoBarra, cantidad;

        public ViewHolderDetalleLista(@NonNull View itemView) {
            super(itemView);
            categoria=itemView.findViewById(R.id.Categoria);
            marca=itemView.findViewById(R.id.Marca);
            presentacion=itemView.findViewById(R.id.Presentacion);
            codigoBarra=itemView.findViewById(R.id.Codigo);
            cantidad=itemView.findViewById(R.id.Cantidad);
        }
    }
}

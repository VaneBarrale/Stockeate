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

public class Adapter_Top10Locales extends RecyclerView.Adapter<Adapter_Top10Locales.ViewHolderLocales> {

    ArrayList<class_top_10> listaLocales;

    public Adapter_Top10Locales(ArrayList<class_top_10> listaLocales) {
        this.listaLocales = listaLocales;
    }

    @Override
    public Adapter_Top10Locales.ViewHolderLocales onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item_top10locales, null, false);
        return new Adapter_Top10Locales.ViewHolderLocales(root);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Top10Locales.ViewHolderLocales holder, int position) {
        holder.top.setText(listaLocales.get(position).getTop());
        holder.local.setText(listaLocales.get(position).getLocal());
    }

    @Override
    public int getItemCount() {
        return listaLocales.size();
    }

    public class ViewHolderLocales extends RecyclerView.ViewHolder {

        TextView top,local;

        public ViewHolderLocales(@NonNull View itemView) {
            super(itemView);
            top=itemView.findViewById(R.id.idTop);
            local=itemView.findViewById(R.id.Local);
        }
    }
}

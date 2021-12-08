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

    ArrayList<class_lista_compras> mMisListas;
    private View.OnClickListener listener;

    public Adapter_mis_listas(ArrayList<class_lista_compras> mMisListas) {
        this.mMisListas = mMisListas;
    }

    @Override
    public Adapter_mis_listas.ViewHolderMisListas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item_mis_listas, null, false);
        root.setOnClickListener(this);
        return new Adapter_mis_listas.ViewHolderMisListas(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderMisListas holder, int position) {
        holder.id.setText(mMisListas.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return mMisListas.size();
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

        TextView id;

        public ViewHolderMisListas(@NonNull View itemView) {
            super(itemView);
            id=itemView.findViewById(R.id.id);
        }
    }
}

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

public class Adapter_Ranking_Usuarios extends RecyclerView.Adapter<Adapter_Ranking_Usuarios.ViewHolderRankingUsuarios> {

    ArrayList<class_top_10> listaUsuarios;

    public Adapter_Ranking_Usuarios(ArrayList<class_top_10> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    @Override
    public Adapter_Ranking_Usuarios.ViewHolderRankingUsuarios onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item_ranking_usuario, null, false);
        return new Adapter_Ranking_Usuarios.ViewHolderRankingUsuarios(root);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Ranking_Usuarios.ViewHolderRankingUsuarios holder, int position) {
        holder.top.setText(listaUsuarios.get(position).getTop());
        holder.usuario.setText(listaUsuarios.get(position).getUsuario());
    }

    @Override
    public int getItemCount() {
        return listaUsuarios.size();
    }

    public class ViewHolderRankingUsuarios extends RecyclerView.ViewHolder {

        TextView top,usuario;

        public ViewHolderRankingUsuarios(@NonNull View itemView) {
            super(itemView);
            top=itemView.findViewById(R.id.idTop);
            usuario=itemView.findViewById(R.id.Usuario);
        }
    }
}

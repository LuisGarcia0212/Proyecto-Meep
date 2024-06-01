package com.example.proyectomeep.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectomeep.R;
import com.example.proyectomeep.clases.Proyectos;

import java.util.List;

public class ProyectoAdapter extends RecyclerView.Adapter<ProyectoAdapter.ViewHolder> {
    private List<Proyectos> listaProyectos;

    public ProyectoAdapter(List<Proyectos> listaProyectos) {
        this.listaProyectos = listaProyectos;
    }

    @NonNull
    @Override
    public ProyectoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_proyectos, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProyectoAdapter.ViewHolder holder, int position) {
        Proyectos proyectos = listaProyectos.get(position);
        holder.lblProyecto.setText(proyectos.getNombreProyecto());
        holder.lblEstado.setText(proyectos.getEstado());

        if(proyectos.getId_rol() == 1){
            holder.imageA.setVisibility(View.VISIBLE);
        }else{
            holder.imageA.setVisibility(View.GONE);
        }
        if(proyectos.getEstado().equals("Activo")){
            holder.estado.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.green));
        } else if (proyectos.getEstado().equals("Finalizado")) {
            holder.estado.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.orange));
        }
    }

    @Override
    public int getItemCount() {
        return listaProyectos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView carItemProyecto;
        TextView lblEstado,lblProyecto;
        ImageView imageA;

        EditText estado;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            carItemProyecto = itemView.findViewById(R.id.itemCardProyecto);
            lblEstado = itemView.findViewById(R.id.lblEstado);
            lblProyecto = itemView.findViewById(R.id.lblNomProyecto);
            imageA = itemView.findViewById(R.id.imgAdmin);
            estado = itemView.findViewById(R.id.cirEstado);
        }
    }
}

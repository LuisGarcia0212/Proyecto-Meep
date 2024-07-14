package com.example.proyectomeep.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectomeep.R;
import com.example.proyectomeep.clases.TareaPendiente;

import java.util.List;

public class TareaPendienteAdpter extends RecyclerView.Adapter<TareaPendienteAdpter.ViewHolder> {
    private List<TareaPendiente> listaTarea;

    public TareaPendienteAdpter(List<TareaPendiente> listaTarea) {
        this.listaTarea = listaTarea;
    }

    @NonNull
    @Override
    public TareaPendienteAdpter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tarea_pendiente, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TareaPendienteAdpter.ViewHolder holder, int position) {
        TareaPendiente tarea = listaTarea.get(position);
        holder.tituloP.setText(tarea.getNombreProyecto());
        holder.subtituloP.setText(tarea.getNombreTarea());
    }

    @Override
    public int getItemCount() { return listaTarea.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout contenedor;
        TextView tituloP, subtituloP;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            contenedor = itemView.findViewById(R.id.contenedorProyecto);
            tituloP = itemView.findViewById(R.id.logTxtTituloProyecto);
            subtituloP = itemView.findViewById(R.id.logTxtDescripcionProyecto);
        }
    }
}

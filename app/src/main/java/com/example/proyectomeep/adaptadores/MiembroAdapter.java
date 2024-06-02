package com.example.proyectomeep.adaptadores;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectomeep.R;
import com.example.proyectomeep.clases.Miembro;

import com.loopj.android.http.Base64;
import java.util.List;

public class MiembroAdapter extends RecyclerView.Adapter<MiembroAdapter.ViewHolder> {
    private List<Miembro> listaMiembros;
    private Context context;
    private int idProyecto = -1;

    public MiembroAdapter(List<Miembro> listaMiembros) {
        this.listaMiembros = listaMiembros;
    }

    @NonNull
    @Override
    public MiembroAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_miembros, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MiembroAdapter.ViewHolder holder, int position) {
        Miembro miembro = listaMiembros.get(position);
        String imagen = miembro.getFotoParticipante();
        byte[] imageByte = Base64.decode(imagen, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
        holder.txtM.setText(miembro.getNombreParticipante());
        holder.imgM.setImageBitmap(bitmap);

        if(miembro.getRol() == 1){
            holder.imgRol.setVisibility(View.VISIBLE);
        }else{
            holder.imgRol.setVisibility(View.GONE);
        }
        System.out.println("participantes: "+miembro.getNombreParticipante());
    }

    @Override
    public int getItemCount() {
        return listaMiembros.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgM, imgRol;
        TextView txtM;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgM = itemView.findViewById(R.id.imgMiembro);
            txtM = itemView.findViewById(R.id.nombreMiembro);
            imgRol = itemView.findViewById(R.id.imgRol);
        }
    }
}

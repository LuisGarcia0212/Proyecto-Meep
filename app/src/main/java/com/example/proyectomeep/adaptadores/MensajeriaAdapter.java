package com.example.proyectomeep.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectomeep.R;
import com.example.proyectomeep.clases.MensajeriaInterna;
import com.example.proyectomeep.clases.Menu;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MensajeriaAdapter extends RecyclerView.Adapter<MensajeriaAdapter.ViewHolder> implements Menu {
    private List<MensajeriaInterna> listaMensajeria;
    private Context context;
    private int idUsuarioClicked = -1;
    private Fragment[] fragments;

    public MensajeriaAdapter(List<MensajeriaInterna> listaMensajeria, Context context, Fragment[] fragments) {
        this.listaMensajeria = listaMensajeria;
        this.context = context;
        this.fragments = fragments;
    }

    @NonNull
    @Override
    public MensajeriaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mensajes, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MensajeriaAdapter.ViewHolder holder, int position) {
        MensajeriaInterna mensajeria = listaMensajeria.get(position);
        String imagen = mensajeria.getFoto();
        byte[] imageByte = Base64.decode(imagen, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
        holder.txtUsuario.setText(mensajeria.getUsername2());
        holder.txtMensaje.setText(mensajeria.getMensaje());
        holder.txtHora.setText(mensajeria.getHora().toString());

        holder.contenedor.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("idMiembroClicked", mensajeria.getIdUsuario2());
            editor.apply();
            idUsuarioClicked = mensajeria.getIdUsuario2();
            int idBotom = 5;
            onClickMenu(idBotom);
        });
    }

    @Override
    public int getItemCount() { return listaMensajeria.size(); }

    @Override
    public void onClickMenu(int idBoton) {
        FragmentTransaction ft = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.menuRelaArea, fragments[idBoton]);
        ft.commit();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imgUsuario;
        TextView txtUsuario, txtMensaje, txtHora;
        LinearLayout contenedor;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgUsuario = itemView.findViewById(R.id.imgUsuario2);
            txtUsuario = itemView.findViewById(R.id.lblUsuario2);
            txtMensaje = itemView.findViewById(R.id.lblMensajeUsuario);
            txtHora = itemView.findViewById(R.id.lblHorario);
            contenedor = itemView.findViewById(R.id.contenedor);
        }
    }
}

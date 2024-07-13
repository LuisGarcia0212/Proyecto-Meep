package com.example.proyectomeep.adaptadores;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectomeep.R;
import com.example.proyectomeep.actividades.BienvenidaActivity;
import com.example.proyectomeep.actividades.MiembrosActivity;
import com.example.proyectomeep.clases.Miembro;

import com.example.proyectomeep.clases.Usuario;
import com.loopj.android.http.Base64;

import org.w3c.dom.Text;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MiembroAdapter extends RecyclerView.Adapter<MiembroAdapter.ViewHolder> implements View.OnClickListener{
    private List<Miembro> listaMiembros;
    private Context context;
    private int idProyecto = -1;
    private int idUsuarioClicked = -1;
    private Usuario usuario;
    private Activity activity;
    AlertDialog a2;

    public MiembroAdapter(List<Miembro> listaMiembros, Context context, Usuario usuario, Activity activity) {
        this.listaMiembros = listaMiembros;
        this.context = context;
        this.usuario = usuario;
        this.activity = activity;
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

        System.out.println(miembro.getIdParticipante());
        holder.contenedor.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("idMiembroClicked", miembro.getIdParticipante());
            editor.putInt("fragementoVolver", 4);
            editor.apply();

            idUsuarioClicked = miembro.getIdParticipante();
            abrirDialog(miembro.getFotoParticipante(), miembro.getNombreParticipante());
            notifyDataSetChanged();
        });

    }

    @Override
    public int getItemCount() {
        return listaMiembros.size();
    }


    private void abrirDialog(String fotoUs, String nombreUs) {
        AlertDialog.Builder d = new AlertDialog.Builder(context, R.style.CustomDialogTheme);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.fragment_dialog_usuario, null);
        d.setView(dialogView);
        a2 = d.create();
        TextView txtUsuario = dialogView.findViewById(R.id.logUsuario);
        CircleImageView fotoMiembro = dialogView.findViewById(R.id.nav_header_imageView4);
        byte[] imageByte = Base64.decode(fotoUs, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
        fotoMiembro.setImageBitmap(bitmap);
        txtUsuario.setText(nombreUs);
        Button btnEnviar = dialogView.findViewById(R.id.btnEnviarMensaje);
        btnEnviar.setOnClickListener(this);
        a2.show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnEnviarMensaje){
            a2.dismiss();
            enviarMensaje();
        }
    }

    private void enviarMensaje() {
        int btnMenu = 5;
        Intent iBienvenida = new Intent(context, BienvenidaActivity.class);
        iBienvenida.putExtra("usuario", usuario);
        iBienvenida.putExtra("idBoton", btnMenu);
        context.startActivity(iBienvenida);
        activity.finish();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgM, imgRol;
        TextView txtM;
        LinearLayout contenedor;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgM = itemView.findViewById(R.id.imgMiembro);
            txtM = itemView.findViewById(R.id.nombreMiembro);
            imgRol = itemView.findViewById(R.id.imgRol);
            contenedor = itemView.findViewById(R.id.contenedor);
        }
    }
}

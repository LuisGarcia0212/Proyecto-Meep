package com.example.proyectomeep.adaptadores;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectomeep.R;
import com.example.proyectomeep.clases.Chat;
import com.example.proyectomeep.clases.Usuario;
import com.loopj.android.http.Base64;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private List<Chat> listaChat;
    private Usuario usuario = null;


    public ChatAdapter(List<Chat> listaChat, Usuario usuario) {
        this.listaChat = listaChat;
        this.usuario = usuario;
    }

    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {
        Chat chats = listaChat.get(position);
        holder.txtMensaje.setText(chats.getMensaje());
        holder.imgChat.setVisibility(View.VISIBLE);
        holder.imgChat1.setVisibility(View.VISIBLE);
        holder.contenedorD.setGravity(0);

        String imagen = chats.getFoto_receptor();
        byte[] imageByte = Base64.decode(imagen, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);

        String imagen2 = chats.getFotoEmisor();
        byte[] imageByte2 = Base64.decode(imagen2, Base64.DEFAULT);
        Bitmap bitmap2 = BitmapFactory.decodeByteArray(imageByte2, 0, imageByte2.length);

        if (chats.getIdUsuario1() == usuario.getIdUsuario()){
            holder.imgChat1.setImageBitmap(bitmap2);
            holder.imgChat.setVisibility(View.INVISIBLE);
            holder.contenedorD.setGravity(Gravity.RIGHT);
        }
        else if (chats.getIdUsuario2() == usuario.getIdUsuario()){
            holder.imgChat.setImageBitmap(bitmap2);
            holder.imgChat1.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return listaChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imgChat, imgChat1;
        TextView txtMensaje;
        LinearLayout contenedorD;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgChat1 = itemView.findViewById(R.id.imgUsuario1);
            imgChat = itemView.findViewById(R.id.imgUsuario);
            txtMensaje = itemView.findViewById(R.id.lblMensaje);
            contenedorD = itemView.findViewById(R.id.contenedorD);
        }
    }
}

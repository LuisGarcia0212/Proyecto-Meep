package com.example.proyectomeep.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectomeep.R;
import com.example.proyectomeep.clases.Menu;
import com.example.proyectomeep.clases.Proyectos;
import com.example.proyectomeep.fragmentos.BienvenidaFragment;
import com.example.proyectomeep.fragmentos.CrearPFragment;
import com.example.proyectomeep.fragmentos.ForoFragment;
import com.example.proyectomeep.fragmentos.ProyectoFragment;
import com.example.proyectomeep.fragmentos.ProyectsFragment;
import com.example.proyectomeep.fragmentos.TareasFragment;

import java.util.List;

public class ProyectoAdapter extends RecyclerView.Adapter<ProyectoAdapter.ViewHolder> implements Menu {
    private List<Proyectos> listaProyectos;
    private Context context;
    private int idRolClicked = -1; // Inicializado como -1 para indicar que no se ha hecho clic en ningún elemento aún
    private Fragment[] fragments;


    public ProyectoAdapter(Context context, List<Proyectos> listaProyectos, Fragment[] fragments) {
        this.listaProyectos = listaProyectos;
        this.context = context;
        this.fragments = fragments;
    }


    @NonNull
    @Override
    public ProyectoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_proyectos, parent, false);
        return new ViewHolder(view);
    }
    ProyectoAdapter.ViewHolder h = null;
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
        // Manejar el clic en el contenedor del elemento del RecyclerView
        holder.contenedor.setOnClickListener(v -> {
            idRolClicked = proyectos.getId_rol();
            System.out.println(idRolClicked);
            notifyDataSetChanged(); // Notificar al RecyclerView de que los datos han cambiado
            ingresarAoP();
        });
    }

    @Override
    public int getItemCount() {
        return listaProyectos.size();
    }

    private void ingresarAoP() {
        int btnMenu;
        if(idRolClicked == 1){
            btnMenu = 4;
            onClickMenu(btnMenu);
        } else {
            btnMenu = 1;
            onClickMenu(btnMenu);
        }
    }

    @Override
    public void onClickMenu(int idBoton) {
        FragmentTransaction ft = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
        if (idRolClicked == 1) {
            ft.replace(R.id.menuRelaArea, fragments[idBoton]); // Fragment correspondiente al rol 1
        } else if (idRolClicked == 2) {
            ft.replace(R.id.menuRelaArea, fragments[idBoton]); // Fragment correspondiente al rol 2
        }
        ft.commit();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView carItemProyecto;
        TextView lblEstado,lblProyecto;
        ImageView imageA, btnFavorite;
        ImageButton menuProyect1;
        EditText estado;
        LinearLayout contenedor;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            carItemProyecto = itemView.findViewById(R.id.itemCardProyecto);
            lblEstado = itemView.findViewById(R.id.lblEstado);
            lblProyecto = itemView.findViewById(R.id.lblNomProyecto);
            imageA = itemView.findViewById(R.id.imgAdmin);
            estado = itemView.findViewById(R.id.cirEstado);
            contenedor = itemView.findViewById(R.id.logPro1);
            menuProyect1 = itemView.findViewById(R.id.menuProyect1);
            btnFavorite = itemView.findViewById(R.id.addfavorites1);
        }
    }
}

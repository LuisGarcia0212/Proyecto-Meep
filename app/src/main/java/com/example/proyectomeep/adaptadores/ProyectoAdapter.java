package com.example.proyectomeep.adaptadores;

import android.content.Context;
import android.database.Cursor;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectomeep.R;
import com.example.proyectomeep.SQLite.DatabaseHelper;
import com.example.proyectomeep.clases.Menu;
import com.example.proyectomeep.clases.Proyectos;


import java.util.ArrayList;
import java.util.List;

public class ProyectoAdapter extends RecyclerView.Adapter<ProyectoAdapter.ViewHolder>  implements Menu {
    private List<Proyectos> listaProyectos;
    private Context context;
    private int idRolClicked = -1; // Inicializado como -1 para indicar que no se ha hecho clic en ningún elemento aún
    private Fragment[] fragments;
    private DatabaseHelper dbHelper;
    private boolean isFavorite;

    public ProyectoAdapter(Context context, List<Proyectos> listaProyectos, Fragment[] fragments) {
        this.listaProyectos = listaProyectos;
        this.context = context;
        this.fragments = fragments;
        this.dbHelper = new DatabaseHelper(context); // Inicializar dbHelper
        loadProjects();
    }
    private void loadProjects() {
        Cursor cursor = dbHelper.getAllProjects();
        if (cursor != null && cursor.moveToFirst()) {
            // Obtener los índices de las columnas
            int idIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_ID);
            int nameIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME);
            int descriptionIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_DESCRIPTION);
            int pinnedIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_PINNED);
            int favoriteIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_FAVORITE);

            // Verificar que los índices de las columnas sean válidos
            if (idIndex >= 0 && nameIndex >= 0 && descriptionIndex >= 0 && pinnedIndex >= 0 && favoriteIndex >= 0) {
                do {
                    // Procesar cada registro aquí
                    int id = cursor.getInt(idIndex);
                    String name = cursor.getString(nameIndex);
                    String description = cursor.getString(descriptionIndex);
                    boolean pinned = cursor.getInt(pinnedIndex) == 1;
                    boolean favorite = cursor.getInt(favoriteIndex) == 1;
                    Proyectos proyecto = new Proyectos(id, " ", name, description, pinned, favorite, 0);
                    listaProyectos.add(proyecto);
                } while (cursor.moveToNext());
            } else {
                throw new IllegalStateException("Una o más columnas no se encontraron en el cursor.");
            }
            cursor.close();
        } else {
            if (cursor != null) {
                cursor.close();
            }
        }
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
            SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("idProyectoClicked", proyectos.getIdProyecto());
            editor.apply();

            idRolClicked = proyectos.getId_rol();
            System.out.println(idRolClicked);
            notifyDataSetChanged(); // Notificar al RecyclerView de que los datos han cambiado
            ingresarAoP();
        });

        // Configurar el botón de favoritos
        holder.btnFavorite.setOnClickListener(v -> toggleFavorite(holder, proyectos.getIdProyecto()));
        // Configurar el menú emergente
        holder.menuProyect1.setOnClickListener(v -> showPopupMenu(v, proyectos.getIdProyecto()));
        // Actualizar el estado del botón de favoritos
        updateFavoriteButtonState(holder, proyectos.getIdProyecto());
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


    public void pinProjectToTop(int projectId) {
        // Encuentra el proyecto en la lista
        for (int i = 0; i < listaProyectos.size(); i++) {
            if (listaProyectos.get(i).getIdProyecto() == projectId) {
                // Remueve el proyecto de su posición actual
                Proyectos proyecto = listaProyectos.remove(i);
                // Agrega el proyecto al inicio de la lista
                listaProyectos.add(0, proyecto);
                // Notifica al adaptador que los datos han cambiado
                notifyDataSetChanged();
                break; // Termina el bucle después de encontrar el proyecto
            }
        }
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
    private void showPopupMenu(View view, int projectId) {
        PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.proyect_popup_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(item -> {
            handleMenuClick(projectId, item.getItemId());
            return true;
        });
        popup.show();
    }

    private void handleMenuClick(int projectId, int itemId) {
        boolean pinned = false;
        boolean favorite = false;
        Cursor cursor = dbHelper.getProject(projectId);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int pinnedColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_PINNED);
                int favoriteColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_FAVORITE);
                if (pinnedColumnIndex != -1 && favoriteColumnIndex != -1) {
                    pinned = cursor.getInt(pinnedColumnIndex) == 1;
                    favorite = cursor.getInt(favoriteColumnIndex) == 1;
                }
            }
            cursor.close();
        }
        if(itemId == R.id.action_pin){
            pinned = !pinned;
            Toast.makeText(context, pinned ? "Pinned" : "Unpinned", Toast.LENGTH_SHORT).show();
            // Fija el proyecto en la parte superior si se marca como fijado
            if (pinned) {
                pinProjectToTop(projectId);
            }
        } else if(itemId == R.id.addfavorites1){
            favorite = !favorite;
            Toast.makeText(context, favorite ? "Added to favorites" : "Removed from favorites", Toast.LENGTH_SHORT).show();
        }
        dbHelper.updateProject(projectId, pinned, favorite);
        loadProjects(); // Recargar los proyectos para reflejar el cambio de fijación
        notifyDataSetChanged();
    }
    private void updateFavoriteButtonState(ViewHolder holder, int projectId) {
        Cursor cursor = dbHelper.getProject(projectId);
        if (cursor != null && cursor.moveToFirst()) {
            int favoriteColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_FAVORITE);
            if (favoriteColumnIndex != -1) {
                isFavorite = cursor.getInt(favoriteColumnIndex) == 1;
            }
            cursor.close();
        }
        holder.btnFavorite.setImageResource(isFavorite ? R.drawable.staroff : R.drawable.staron);
    }

    private void toggleFavorite(ViewHolder holder, int projectId) {
        isFavorite = !isFavorite;
        dbHelper.updateProject(projectId, false, isFavorite); // Update only the favorite status
        holder.btnFavorite.setImageResource(isFavorite ? R.drawable.staroff : R.drawable.staron);
        Toast.makeText(context, isFavorite ? "Added to favorites" : "Removed from favorites", Toast.LENGTH_SHORT).show();
    }
}

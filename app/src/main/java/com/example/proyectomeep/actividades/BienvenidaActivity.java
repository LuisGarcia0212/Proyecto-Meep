package com.example.proyectomeep.actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectomeep.R;
import com.example.proyectomeep.SQLite.MEEP;
import com.example.proyectomeep.adaptadores.ProyectoAdapter;
import com.example.proyectomeep.clases.Menu;
import com.example.proyectomeep.clases.Usuario;
import com.example.proyectomeep.fragmentos.BienvenidaFragment;
import com.example.proyectomeep.fragmentos.ConfigFragment;
import com.example.proyectomeep.fragmentos.MessageListFragment;
import com.example.proyectomeep.fragmentos.ProyectoFragment;
import com.example.proyectomeep.fragmentos.ForoFragment;
import com.google.android.material.navigation.NavigationView;
import com.loopj.android.http.Base64;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class BienvenidaActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, Menu {


    Usuario usuario;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    CircleImageView perfilImage;
    Fragment[] fragments;
    TextView nombreUsuario;

    private int boton = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida);

        usuario = (Usuario) getIntent().getSerializableExtra("usuario");


        fragments = new Fragment[5];

        fragments[0] = new BienvenidaFragment();
        fragments[1] = new MessageListFragment();
        fragments[2] = new ProyectoFragment();
        fragments[3] = new ConfigFragment();
        fragments[4] = new ForoFragment();

        int idBoton = getIntent().getIntExtra("idBoton", -1);
        onClickMenu(idBoton);

        Toolbar toolbar = findViewById(R.id.toolBar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        drawer = findViewById(R.id.drawer_layout);

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);

        perfilImage = headerView.findViewById(R.id.nav_header_perfil);
        nombreUsuario = headerView.findViewById(R.id.nav_header_textView);
        String image = usuario.getFoto();
        Uri imgUri = Uri.parse(image);
        perfilImage.setImageURI(imgUri);
        nombreUsuario.setText(usuario.getUsuario());
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_item_one){
            ingresarCuenta();
        } else if (item.getItemId() == R.id.nav_item_three) {
            onClickMenu(3);
        } else if (item.getItemId() == R.id.nav_item_four) {
            acercaDe();
        } else if (item.getItemId() == R.id.nav_item_five) {
            cerrarSesion();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void cerrarSesion() {
        MEEP mp = new MEEP(this);
        int id = usuario.getIdUsuario();
        mp.eliminarUsuario(id);
        finish();
        Intent iLogin = new Intent(this, InicionSesionMeepActivity.class);
        startActivity(iLogin);
        finish();
    }

    private void ingresarCuenta() {
        Intent iCuenta= new Intent(this, CuentaActivity.class);
        iCuenta.putExtra("usuario", usuario);
        iCuenta.putExtra("idBoton", boton);
        startActivity(iCuenta);
        finish();
    }


    private void acercaDe() {
        Intent iAcerca= new Intent(this, AcercaDeActivity.class);
        iAcerca.putExtra("usuario", usuario);
        iAcerca.putExtra("idBoton", boton);
        startActivity(iAcerca);
        finish();
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClickMenu(int idBoton) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.menuRelaArea, fragments[idBoton]);
        boton = idBoton;
        System.out.println("El proyecto se ubica en: "+boton);
        ft.commit();
    }

}
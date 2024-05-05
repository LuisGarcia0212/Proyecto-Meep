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

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.proyectomeep.R;
import com.example.proyectomeep.clases.Menu;
import com.example.proyectomeep.fragmentos.BienvenidaFragment;
import com.example.proyectomeep.fragmentos.ProyectoFragment;
import com.example.proyectomeep.fragmentos.ForoFragment;
import com.google.android.material.navigation.NavigationView;

public class BienvenidaActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener, Menu {

    ImageView imaMap;

    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;

    Fragment[] fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida);


        fragments = new Fragment[3];
        fragments[0] = new BienvenidaFragment();
        fragments[1] = new ForoFragment();
        fragments[2] = new ProyectoFragment();

        int idBoton = 0;
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
        navigationView.setNavigationItemSelectedListener(this);


        imaMap= findViewById(R.id.lblmapa);

        imaMap.setOnClickListener(this);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.nav_item_one){
            ingresarCuenta();
        } else if (item.getItemId() == R.id.nav_item_two) {
            Toast.makeText(this, "Item 2", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.nav_item_three) {
            Toast.makeText(this, "Item 3", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.nav_item_four) {
            Toast.makeText(this, "Item 4", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.nav_item_five) {
            volverLogin();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void volverLogin() {
        Intent iLogin = new Intent(this, InicionSesionMeepActivity.class);
        startActivity(iLogin);
        finish();
    }

    private void ingresarCuenta() {
        Intent iCuenta= new Intent(this, CuentaActivity.class);
        startActivity(iCuenta);
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
        ft.commit();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.lblmapa){
            volver1();
        }
    }

    private void volver1(){
        Intent iBienvenida1 = new Intent(this, MapaActivity.class);
        startActivity(iBienvenida1);
        finish();
    }
}
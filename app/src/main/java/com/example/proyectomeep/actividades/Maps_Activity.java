package com.example.proyectomeep.actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.proyectomeep.R;
import com.example.proyectomeep.clases.Usuario;
import com.example.proyectomeep.fragmentos.ForoFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Maps_Activity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener, View.OnClickListener {
    TextView logTxtCode;
    GoogleMap mMap;
    Button lblAceptar;

    Usuario usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        logTxtCode=findViewById(R.id.logTxtCode);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        lblAceptar=findViewById(R.id.lblAceptar);
        lblAceptar.setOnClickListener(this);
        Usuario usuario = (Usuario) getIntent().getSerializableExtra("usuario");


    }
    public void onClick(View v) {
        if(v.getId()== R.id.lblAceptar){
            AceptarBtn();
        }
    }

    private void AceptarBtn() {
        Intent iRegreso = new Intent(this, ForoFragment.class);
        iRegreso.putExtra("usuario", usuario);
        startActivity(iRegreso);
        finish();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap=googleMap;
        this.mMap.setOnMapClickListener(this);
        this.mMap.setOnMapLongClickListener(this);
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        LatLng lima = new LatLng(-12.016956514997757, -76.99965308375168);
        LatLng lima2 = new LatLng(-12.017176779713242, -77.00380265329773);
        LatLng lima3 = new LatLng(-12.00909770974361, -76.99657339244794);
        LatLng lima4 = new LatLng(-12.009632200921706, -77.00148387668295);
        mMap.addMarker(new MarkerOptions().position(lima).title("Utd está aquí"));
        mMap.addMarker(new MarkerOptions().position(lima2).title("Luis está Aquí"));
        mMap.addMarker(new MarkerOptions().position(lima3).title("Sebitas está Aquí"));
        mMap.addMarker(new MarkerOptions().position(lima4).title("Alvaro está Aquí"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lima, 15));
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        mMap.clear(); // Limpia cualquier marcador previo
        MarkerOptions markerOptions = new MarkerOptions().position(latLng);
        mMap.addMarker(markerOptions);

        // Aquí deberías llamar a un método para obtener el nombre de la ubicación
        // Puedes usar la API de Geocodificación de Google Maps para convertir latLng en una dirección
        // y luego establecer el texto en logTxtCode
        // Ejemplo simplificado (no se muestra dirección real):
        String locationName = getLocationNameFromLatLng(latLng);
        logTxtCode.setText(locationName);

        // Opcional: Mueve la cámara para enfocar el marcador
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f));
    }

    private String getLocationNameFromLatLng(LatLng latLng) {
        // Aquí deberías usar Geocoder para obtener el nombre real de la ubicación
        // Esta es una implementación simplificada
        return "Ubicación en latitud: " + latLng.latitude + ", longitud: " + latLng.longitude;
    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {
        logTxtCode.setText(""+latLng.latitude);
        logTxtCode.setText(""+latLng.longitude);
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}
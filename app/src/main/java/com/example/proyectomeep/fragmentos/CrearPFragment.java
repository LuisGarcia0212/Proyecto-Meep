package com.example.proyectomeep.fragmentos;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.proyectomeep.R;
import com.example.proyectomeep.SQLite.MEEP;
import com.example.proyectomeep.actividades.BienvenidaActivity;
import com.example.proyectomeep.actividades.CuentaActivity;
import com.example.proyectomeep.clases.Menu;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CrearPFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CrearPFragment extends Fragment implements View.OnClickListener, Menu {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CrearPFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CrearPFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CrearPFragment newInstance(String param1, String param2) {
        CrearPFragment fragment = new CrearPFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    EditText editNombre;
    EditText editFecha;
    EditText editArea;
    EditText editDescripcion;

    Fragment[] fragments;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crear_p, container, false);

        fragments = new Fragment[5];
        fragments[0] = new BienvenidaFragment();
        fragments[1] = new ForoFragment();
        fragments[2] = new ProyectoFragment();
        fragments[3] = new CrearPFragment();
        fragments[4] = new MessageListFragment();

        ImageView imgVolver = view.findViewById(R.id.logVolverMenu);
        Button btnGenerar = view.findViewById(R.id.logbtnCrear);

        //valores del proyecto
        editNombre = view.findViewById(R.id.editName);
        editDescripcion = view.findViewById(R.id.editDescripcion);

        btnGenerar.setOnClickListener(this);
        imgVolver.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.logVolverMenu){
            volver();
        } else if (v.getId() == R.id.logbtnCrear) {
            generarProyecto(editNombre.getText().toString(), editFecha.getText().toString(),
                    editArea.getText().toString(), editDescripcion.getText().toString());
        }

    }

    private void volver() {
        int btnMenu = 2;
        onClickMenu(btnMenu);
    }

    private void generarProyecto(String nombre, String fecha, String area, String descripcion){
        MEEP mp = new MEEP(getContext());
        if(nombre.equals("")  || fecha.equals("") || area.equals("") || descripcion.equals("") )
            Toast.makeText(getContext(), "Completa todos los espacios", Toast.LENGTH_SHORT).show();
        else{
            //guardamos el proyecto
            mp.agregarProyecto(nombre, fecha, area, descripcion);
            Toast.makeText(getContext(), "El proyecto se registro exitosamente", Toast.LENGTH_SHORT).show();
            Intent iBienvenida = new Intent(getActivity(), BienvenidaActivity.class);
            startActivity(iBienvenida);
        }
    }

    @Override
    public void onClickMenu(int idBoton) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.menuRelaArea, fragments [idBoton]);
        ft.commit();
    }
}
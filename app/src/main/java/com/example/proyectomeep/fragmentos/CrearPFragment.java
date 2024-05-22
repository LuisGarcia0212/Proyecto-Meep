package com.example.proyectomeep.fragmentos;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CrearPFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CrearPFragment extends Fragment implements View.OnClickListener{

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crear_p, container, false);

        ImageView imgVolver = view.findViewById(R.id.logVolverMenu);
        Button btnGenerar = view.findViewById(R.id.logBtnGenerar);

        //valores del proyecto
        editNombre = view.findViewById(R.id.editName);
        editFecha = view.findViewById(R.id.editFecha);
        editArea = view.findViewById(R.id.editArea);
        editDescripcion = view.findViewById(R.id.editDescripcion);

        btnGenerar.setOnClickListener(this);
        imgVolver.setOnClickListener(this);
        editFecha.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.logVolverMenu){
            volverBienvenida();
        } else if (v.getId() == R.id.editFecha){
            seleccionarFecha();
        } else if (v.getId() == R.id.logBtnGenerar) {
            generarProyecto(editNombre.getText().toString(), editFecha.getText().toString(),
                    editArea.getText().toString(), editDescripcion.getText().toString());
        }

    }

    private void seleccionarFecha() {
        DatePickerDialog dpd;
        final Calendar fechaActual = Calendar.getInstance();
        int dia = fechaActual.get(Calendar.DAY_OF_MONTH);
        int mes = fechaActual.get(Calendar.MONTH);
        int anio = fechaActual.get(Calendar.YEAR);

        dpd = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int y, int m, int d) {
                editFecha.setText(y + "-" + ((m + 1) < 10 ? "0" + (m + 1) : (m + 1)) + "-" + (d < 10 ? "0" + d : d));
            }
        }, anio, mes, dia);
        dpd.show();
    }

    private void volverBienvenida() {
        Intent iBienvenida = new Intent(getActivity(), BienvenidaActivity.class);
        startActivity(iBienvenida);
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
}
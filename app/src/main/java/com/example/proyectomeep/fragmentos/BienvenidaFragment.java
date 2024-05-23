package com.example.proyectomeep.fragmentos;

import android.app.ActivityOptions;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.ImageView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.content.Intent;
import android.widget.TextView;

import com.example.proyectomeep.R;
import com.example.proyectomeep.actividades.MapaActivity;
import com.example.proyectomeep.actividades.TaskAdapter;
import com.example.proyectomeep.actividades.TaskViewActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BienvenidaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BienvenidaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ImageView imaMap;

    public BienvenidaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BienvenidaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BienvenidaFragment newInstance(String param1, String param2) {
        BienvenidaFragment fragment = new BienvenidaFragment();
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
    private RecyclerView recyclerView;
    //private ArrayList<TaskAdapter.TaskItem> arrayList;
    private ArrayList<String> arrayList;
    private TaskAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view1 = inflater.inflate(R.layout.fragment_bienvenida, container, false);

        ImageView img = view1.findViewById(R.id.lblmapa);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MapaActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = view1.findViewById(R.id.linearNotificaciones);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        arrayList = new ArrayList<>();
        arrayList.add("TAREAS DESARROLLO WEB\n\n"+
                "Actualizar la Base de Datos de Clientes\n\n"+
                "-> Revisar y actualizar la información de contacto.\n-> Verificar la precisión de las direcciones y teléfonos.");
        arrayList.add("TAREAS MARKETING\n\n"+
                "Crear Contenido para Redes Sociales\n\n"+
                "-> Diseñar gráficos y escribir publicaciones.\n-> Programar publicaciones utilizando herramientas como Hootsuite o Buffer.");
        arrayList.add("TAREAS DE RR.HH\n\n"+
                "Capacitación y Desarrollo\n\n"+
                "-> Organizar programas de capacitación para empleados.\n-> Evaluar la efectividad de las capacitaciones y hacer ajustes.");
        /*arrayList.add(new TaskAdapter.TaskItem("TAREAS DESARROLLO WEB\n",
                "Actualizar la Base de Datos de Clientes\n",
                "-> Revisar y actualizar la información de contacto.\n-> Verificar la precisión de las direcciones y teléfonos.",
                18f, 16f, 14f, Color.BLACK, Color.BLUE, Color.RED));
        arrayList.add(new TaskAdapter.TaskItem("TAREAS MARKETING\n",
                "Crear Contenido para Redes Sociales\n",
                "-> Diseñar gráficos y escribir publicaciones.\n-> Programar publicaciones utilizando herramientas como Hootsuite o Buffer.",
                18f, 16f, 14f, Color.BLACK, Color.BLUE, Color.RED));
        arrayList.add(new TaskAdapter.TaskItem("TAREAS DE RR.HH\n",
                "Capacitación y Desarrollo\n\n",
                "-> Organizar programas de capacitación para empleados.\n-> Evaluar la efectividad de las capacitaciones y hacer ajustes.",
                18f, 16f, 14f, Color.BLACK, Color.BLUE, Color.RED));
        */
        adapter = new TaskAdapter(getContext(), arrayList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new TaskAdapter.OnItemClickListener() {
            @Override
            public void onClick(TextView textView, String text) {
                startActivity(new Intent(getActivity(), TaskViewActivity.class)
                                .putExtra("tareas", text),
                        ActivityOptions.makeSceneTransitionAnimation(getActivity(), textView, "tareas").toBundle());
            }
        });

        return view1;
    }
}
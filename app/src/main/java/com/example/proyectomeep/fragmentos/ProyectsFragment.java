package com.example.proyectomeep.fragmentos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.proyectomeep.R;
import com.example.proyectomeep.clases.Menu;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProyectsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProyectsFragment extends Fragment implements View.OnClickListener, Menu {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProyectsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProyectsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProyectsFragment newInstance(String param1, String param2) {
        ProyectsFragment fragment = new ProyectsFragment();
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

    Fragment[] fragments;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_proyects, container, false);

        fragments = new Fragment[6];
        fragments[0] = new BienvenidaFragment();
        fragments[1] = new ForoFragment();
        fragments[2] = new ProyectoFragment();
        fragments[3] = new CrearPFragment();
        fragments[4] = new ProyectsFragment();
        fragments[5] = new TareasFragment();

        Button btnForo = view.findViewById(R.id.logBtnForo);
        Button btnTarea = view.findViewById(R.id.logBtnViTareas);
        btnForo.setOnClickListener(this);
        btnTarea.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.logBtnForo){
            ingresarForo();
        } else if (v.getId() == R.id.logBtnViTareas) {
            ingresarTareas();
        }
    }

    private void ingresarTareas() {
        int btnBoton = 5;
        onClickMenu(btnBoton);
    }

    private void ingresarForo() {
        int btnBoton = 1;
        onClickMenu(btnBoton);
    }

    @Override
    public void onClickMenu(int idBoton) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.menuRelaArea, fragments[idBoton]);
        ft.commit();
    }
}
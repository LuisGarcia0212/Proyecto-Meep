package com.example.proyectomeep.fragmentos;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.proyectomeep.R;
import com.example.proyectomeep.clases.Menu;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenuNavFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuNavFragment extends Fragment {

    private final int[] BOTONES = {R.id.ic_homeNav, R.id.ic_chatNav, R.id.ic_setting_nav};

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MenuNavFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenuNavFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuNavFragment newInstance(String param1, String param2) {
        MenuNavFragment fragment = new MenuNavFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View vista = inflater.inflate(R.layout.fragment_menu_nav, container, false);
        AppCompatImageView imbButton;

        for (int i = 0; i < BOTONES.length; i++){
            imbButton = vista.findViewById(BOTONES[i]);
            final int REQUEST_BUTTON = i;
            imbButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Activity actActual = getActivity();
                    ((Menu)actActual).onClickMenu(REQUEST_BUTTON);
                }
            });
        }

        return  vista;
    }
}
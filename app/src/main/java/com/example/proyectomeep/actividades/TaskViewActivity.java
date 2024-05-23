package com.example.proyectomeep.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.proyectomeep.R;

public class TaskViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_view);

        TextView textView = findViewById(R.id.textView);
        textView.setText(getIntent().getStringExtra("tareas"));

        /*
        ImageView imageView = findViewById(R.id.imageView);
        Glide.with(TaskViewActivity.this).load(getIntent().getStringExtra("tareas")).into(imageView);
        */

    }
}
package com.example.proyectomeep.actividades;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectomeep.R;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    Context context;
    ArrayList<String> arrayList;
    //ArrayList<TaskItem> arrayList;
    OnItemClickListener onItemClickListener;

    public TaskAdapter(Context context, /*ArrayList<TaskItem>*/ ArrayList<String> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.task_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(arrayList.get(position));
        holder.itemView.setOnClickListener(view -> onItemClickListener.onClick(holder.textView, arrayList.get(position)));
        /*TaskItem item = arrayList.get(position);
        holder.textView.setText(item.mainText);
        holder.subTextView1.setText(item.subText1);
        holder.subTextView2.setText(item.subText2);

        holder.textView.setTextSize(item.mainTextSize);
        holder.subTextView1.setTextSize(item.subText1Size);
        holder.subTextView2.setTextSize(item.subText2Size);

        holder.textView.setTextColor(item.mainTextColor);
        holder.subTextView1.setTextColor(item.subText1Color);
        holder.subTextView2.setTextColor(item.subText2Color);

        holder.itemView.setOnClickListener(view -> onItemClickListener.onClick(holder.textView, item.mainText));
        */

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView, subTextView1, subTextView2;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.task_list_item);
            /*subTextView1 = itemView.findViewById(R.id.task_list_item2);
            subTextView2 = itemView.findViewById(R.id.task_list_item3);*/
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(TextView textView, String text);
    }
/*
    public static class TaskItem {
        String mainText;
        String subText1;
        String subText2;
        float mainTextSize;
        float subText1Size;
        float subText2Size;
        int mainTextColor;
        int subText1Color;
        int subText2Color;

        public TaskItem(String mainText, String subText1, String subText2,
                        float mainTextSize, float subText1Size, float subText2Size,
                        int mainTextColor, int subText1Color, int subText2Color) {
            this.mainText = mainText;
            this.subText1 = subText1;
            this.subText2 = subText2;
            this.mainTextSize = mainTextSize;
            this.subText1Size = subText1Size;
            this.subText2Size = subText2Size;
            this.mainTextColor = mainTextColor;
            this.subText1Color = subText1Color;
            this.subText2Color = subText2Color;
        }
    }*/
}

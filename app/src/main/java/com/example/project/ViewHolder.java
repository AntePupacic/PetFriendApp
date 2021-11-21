package com.example.project;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {

    TextView petName, petLocation, petAge;
    View view;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        petName = itemView.findViewById(R.id.petName);
        petLocation = itemView.findViewById(R.id.petLocation);
        petAge = itemView.findViewById(R.id.petAge);
        view = itemView;
    }
}
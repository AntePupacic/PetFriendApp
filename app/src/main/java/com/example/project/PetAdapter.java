package com.example.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public class PetAdapter extends RecyclerView.Adapter<ViewHolder> {

    List<Pet> petList = Collections.emptyList();
    Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pet_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        //final int index = viewHolder.getAdapterPosition();
        viewHolder.petName.setText(petList.get(position).getName());
        viewHolder.petLocation.setText(petList.get(position).getLocation());
        viewHolder.petAge.setText(petList.get(position).getAge());
    }


    @Override
    public int getItemCount() {
        return petList.size();
    }
}
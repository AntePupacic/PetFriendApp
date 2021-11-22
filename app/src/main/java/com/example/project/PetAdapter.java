package com.example.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class PetAdapter extends RecyclerView.Adapter<ViewHolder> {

    private Context myContext;

    public PetAdapter(Context context){
        myContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pet_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        Pet dog = DataStorage.petData.get(position);
        viewHolder.petName.setText(dog.getName());
        viewHolder.petLocation.setText(dog.getLocation());
        //viewHolder.petAge.setText(dog.getAge());
    }


    @Override
    public int getItemCount() {
        return DataStorage.petData.size();
    }
}
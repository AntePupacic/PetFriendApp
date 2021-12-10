package com.example.project;

import android.content.Context;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.ViewHolder> {

    private Context myContext;
    public RecycleViewClickListener myRecycleViewClickListener;

    public PetAdapter(Context context, RecycleViewClickListener recycleViewClickListener) {
        myContext = context;
        myRecycleViewClickListener = recycleViewClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView petName, petLocation, petAge;
        private final ImageView petImage;

        public ViewHolder(View view){
            super(view);
            petImage = (ImageView) itemView.findViewById(R.id.petImage);
            petName = (TextView) itemView.findViewById(R.id.petName);
            petLocation = (TextView) itemView.findViewById(R.id.petLocation);
            petAge = (TextView) itemView.findViewById(R.id.petAge);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            myRecycleViewClickListener.onClick(view, getAdapterPosition());
        }
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
        viewHolder.petAge.setText(String.valueOf(dog.getAge()));
    }

    public interface RecycleViewClickListener{
        void onClick(View v, int position);
    }

    @Override
    public int getItemCount() {
        return DataStorage.petData.size();
    }
}
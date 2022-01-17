package com.example.project;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class PetAdapter extends RecyclerView.Adapter<PetAdapter.ViewHolder> implements Filterable {

    public Context myContext;
    public RecycleViewClickListener myRecycleViewClickListener;
    private   ArrayList<Pet> itemsFiltered = DataStorage.pets;


    public PetAdapter(Context context, RecycleViewClickListener recycleViewClickListener) {
        myContext = context;
        myRecycleViewClickListener = recycleViewClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView petName, petLocation, petAge, petDate;
        private final ImageView petImage;

        public ViewHolder(View view){
            super(view);
            petImage = (ImageView) itemView.findViewById(R.id.petImage);
            petName = (TextView) itemView.findViewById(R.id.petName);
            petLocation = (TextView) itemView.findViewById(R.id.petLocation);
            petAge = (TextView) itemView.findViewById(R.id.petAge);
            petDate = (TextView)itemView.findViewById(R.id.petDate);
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
        viewHolder.petName.setText(itemsFiltered.get(position).getName());
        viewHolder.petLocation.setText(itemsFiltered.get(position).getLocation());
        viewHolder.petAge.setText(String.valueOf(itemsFiltered.get(position).getAge()));
        viewHolder.petDate.setText(itemsFiltered.get(position).getDate());
        viewHolder.petImage.setImageBitmap(DbBitmapUtility.getImage(itemsFiltered.get(position).getImage()));
    }

    public interface RecycleViewClickListener{
        void onClick(View v, int position);
    }

    @Override
    public int getItemCount() {
        return itemsFiltered.size();
    }

    //Function to filter RecyclerView items
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String query = charSequence.toString();

                ArrayList<Pet> filtered = new ArrayList<Pet>();

                if (query.isEmpty()) {
                    filtered = DataStorage.pets;
                } else {
                    for ( int i = 0; i < DataStorage.pets.size(); i++) {
                        if (DataStorage.pets.get(i).getName().toLowerCase().contains(query.toLowerCase())) {
                            Pet pet = new Pet();
                            pet.setID(DataStorage.pets.get(i).getID());
                            pet.setName(DataStorage.pets.get(i).getName());
                            pet.setDescription(DataStorage.pets.get(i).getDescription());
                            pet.setLocation(DataStorage.pets.get(i).getLocation());
                            pet.setAge(DataStorage.pets.get(i).getAge());
                            pet.setPhone(DataStorage.pets.get(i).getPhone());
                            pet.setDate(DataStorage.pets.get(i).getDate());
                            pet.setImage(DataStorage.pets.get(i).getImage());
                            filtered.add(pet);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.count = filtered.size();
                results.values = filtered;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {
                itemsFiltered = (ArrayList<Pet>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
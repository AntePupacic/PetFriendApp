package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class DogList extends AppCompatActivity {

    RecyclerView dogRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_list);

        DataStorage.fillData();

        dogRecycleView = findViewById(R.id.dogRecycleView);
        PetAdapter petAdapter = new PetAdapter(getApplicationContext());
        dogRecycleView.setAdapter(petAdapter);
    }
}
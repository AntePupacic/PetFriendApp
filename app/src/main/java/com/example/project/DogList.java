package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class DogList extends AppCompatActivity {

    private RecyclerView dogRecycleView;
    private PetAdapter.RecycleViewClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_list);

        DataStorage.fillData();


        listener = new PetAdapter.RecycleViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(DogList.this, PetInfo.class);
                intent.putExtra("Position", position);
                Toast.makeText(getApplicationContext(), "Position: " +
                        position, Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        };
        dogRecycleView = findViewById(R.id.dogRecycleView);
        PetAdapter petAdapter = new PetAdapter(getApplicationContext(), listener);
        dogRecycleView.setAdapter(petAdapter);


    }
}
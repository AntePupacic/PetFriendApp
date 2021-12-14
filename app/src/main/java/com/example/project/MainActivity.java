package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button dogBtn, addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dogBtn = (Button) findViewById(R.id.dogBtn);
        addBtn = (Button) findViewById(R.id.addBtn);
        addPet();
        dogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dogIntent = new Intent(MainActivity.this, DogList.class);
                startActivity(dogIntent);
            }
        });
    }

    private void addPet(){
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddPet.class);
                startActivity(intent);
            }
        });
    }
}
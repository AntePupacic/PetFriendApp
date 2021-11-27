package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class PetInfo extends AppCompatActivity {

    TextView position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_info);

        position = (TextView) findViewById(R.id.txtViewPos);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            String value = extras.getString("Position");
            position.setText(value);
            Toast.makeText(getApplicationContext(), "Data: " +
                    value, Toast.LENGTH_SHORT).show();
        }



    }
}
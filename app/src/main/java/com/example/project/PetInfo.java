package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class PetInfo extends AppCompatActivity {

    TextView txtViewPos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_info);

        txtViewPos = (TextView) findViewById(R.id.txtViewPos);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            Integer position = extras.getInt("Position");
            txtViewPos.setText(String.valueOf(position));
            Toast.makeText(getApplicationContext(), "Position: " +
                    position, Toast.LENGTH_SHORT).show();
        }



    }
}
package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddPet extends AppCompatActivity {

    EditText editTxtPetName, editTxtPetDescription, editTxtLocation, editTxtAge;
    Button btnAddPet;
    String petName, petDescription, petLocation, petAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);

        Log.i("SIMO", "Prije");
        findViews();
        final PetDataSource petDs = new PetDataSource(getApplicationContext());
        petDs.open();
        getPetDetails();

        btnAddPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                petDs.addPetDetailsToDB(petName,petDescription, petLocation, Integer.valueOf(petAge));
                Intent intent = new Intent(AddPet.this, DogList.class);
                startActivity(intent);
            }
        });
    }

    private void findViews() {
        editTxtPetName = (EditText) findViewById(R.id.editTxtPetName);
        editTxtPetDescription = (EditText) findViewById(R.id.editTxtPetDescription);
        editTxtLocation = (EditText) findViewById(R.id.editTxtLocation);
        editTxtAge = (EditText) findViewById(R.id.editTxtAge);
        btnAddPet = (Button) findViewById(R.id.btnAddPet);
    }


    private void getPetDetails(){
        petName = editTxtPetName.getText().toString();
        petDescription = editTxtPetDescription.getText().toString();
        petLocation = editTxtLocation.getText().toString();
        petAge = editTxtAge.getText().toString();
    }
}
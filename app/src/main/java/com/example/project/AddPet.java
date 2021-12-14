package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddPet extends AppCompatActivity {

    EditText editTxtPetName, editTxtPetDescription, editTxtLocation, editTxtAge;
    Button btnAddPet;
    String petName, petDescription, petLocation, petAge;
    PetDataSource petDs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);
        
        findViews();
        getPetDetails();
        addDataToDB();

    }

    private void addDataToDB(){
        btnAddPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(petDs.addPetDetailsToDB(petName, petDescription, petLocation, petAge)){
                    makeToast("Uspješno upisan podatak u DB");
                }else{
                    makeToast("Greška pri upisu podatka u DB");
                }
                petDs.close();
                Intent intent = new Intent(AddPet.this, MainActivity.class);
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
        petDs = new PetDataSource(getApplicationContext());
        petDs.open();
    }


    private void getPetDetails(){
        petName = editTxtPetName.getText().toString();
        petDescription = editTxtPetDescription.getText().toString();
        petLocation = editTxtLocation.getText().toString();
        petAge = editTxtAge.getText().toString();
    }

    private void makeToast(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
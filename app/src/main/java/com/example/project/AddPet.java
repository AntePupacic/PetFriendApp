package com.example.project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddPet extends AppCompatActivity {


    //Bakota
    EditText editTxtPetName, editTxtPetDescription, editTxtLocation, editTxtAge;
    Button btnAddPet, btnAddImg;
    String petName, petDescription, petLocation, petAge;
    PetDataSource petDs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);
        
        findViews();
        addDataToDB();
    }

    private void addImg(){
        btnAddImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data != null){
            Uri selectedImage = data.getData();
        }
    }

    private void addDataToDB(){
        btnAddPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPetDetails();
                if(petDs.addPetDetailsToDB(petName, petDescription, petLocation, petAge)){
                    makeToast("Uspjesno upisan podatak u DB");
                }else{
                    makeToast("Greska pri upisu podatka u DB");
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
        btnAddImg = (Button) findViewById(R.id.btnAddImg);
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
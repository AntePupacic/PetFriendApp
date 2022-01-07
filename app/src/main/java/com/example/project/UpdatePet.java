package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class UpdatePet extends AppCompatActivity {
    EditText editTxtPetName, editTxtPetDescription, editTxtLocation, editTxtAge, editTxtPhone;
    String petName, petDescription, petLocation, petAge, petPhone;
    Button btnUpdatePet, btnAddImg;
    ImageView petImage;
    PetDataSource petDs;
    Integer position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pet);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            position = extras.getInt("Position");
        }
        findViews();
    }


    private void findViews() {
        editTxtPetName = (EditText) findViewById(R.id.editTxtPetName);
        editTxtPetDescription = (EditText) findViewById(R.id.editTxtPetDescription);
        editTxtLocation = (EditText) findViewById(R.id.editTxtLocation);
        editTxtAge = (EditText) findViewById(R.id.editTxtAge);
        editTxtPhone = (EditText) findViewById(R.id.editTxtPhone);
        btnUpdatePet = (Button) findViewById(R.id.btnUpdatePet);
        btnAddImg = (Button) findViewById(R.id.btnAddImg);
        petImage = (ImageView) findViewById(R.id.petImage);
        petDs = new PetDataSource(getApplicationContext());
        petDs.open();
    }

    //Setting data from ArrayList into editText 
    private void setValues(){

    }

    private void getPetDetails(){
        petName = editTxtPetName.getText().toString();
        petDescription = editTxtPetDescription.getText().toString();
        petLocation = editTxtLocation.getText().toString();
        petAge = editTxtAge.getText().toString();
        petPhone = editTxtPhone.getText().toString();
    }
}
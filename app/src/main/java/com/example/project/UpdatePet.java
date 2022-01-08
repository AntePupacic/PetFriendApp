package com.example.project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdatePet extends AppCompatActivity {
    EditText editTxtPetName, editTxtPetDescription, editTxtLocation, editTxtAge, editTxtPhone;
    String petName, petDescription, petLocation, petAge, petPhone;
    Button btnUpdatePet, btnAddImg;
    ImageView petImage;
    PetDataSource petDs;
    Integer position;
    Bitmap bitmapImg;
    SimpleDateFormat sdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pet);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            position = extras.getInt("Position");
        }


        findViews();
        addImg();
        setValues();
        updateDataIntoDB();
    }

    private void updateDataIntoDB(){
        btnUpdatePet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getPetDetails();
                if(TextUtils.isEmpty(petName) || TextUtils.isEmpty(petDescription) || TextUtils.isEmpty(petAge) || TextUtils.isEmpty(petLocation) || TextUtils.isEmpty(petPhone)){
                    makeToast("Nisi popunio sva polja");
                }else{
                    if(petDs.updatePet(DataStorage.pets.get(position).getID(), petName, petDescription, petLocation, petAge, petPhone,sdf.format(new Date()), DbBitmapUtility.getBytes(bitmapImg))){
                        makeToast("Uspjesno promjenjen podatak u DB");
                    }else{
                        makeToast("Greska pri promjeni podatka u DB");
                    }
                    petDs.close();
                    Intent intent = new Intent(UpdatePet.this, DogList.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void addImg(){
        btnAddImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 3);
            }
        });
    }

    //Getting image from External storage
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data != null){
            Uri imageUri = data.getData();
            try {
                bitmapImg = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            petImage.setImageBitmap(bitmapImg);
        }
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
        sdf = new SimpleDateFormat("dd.MM.yyyy. 'at' HH:mm:ss");
        petDs = new PetDataSource(getApplicationContext());
    }

    //Inserting data from ArrayList into editText
    private void setValues(){
        editTxtPetName.setText(DataStorage.pets.get(position).getName());
        editTxtPetDescription.setText(DataStorage.pets.get(position).getDescription());
        editTxtLocation.setText(DataStorage.pets.get(position).getLocation());
        editTxtAge.setText(DataStorage.pets.get(position).getAge());
        editTxtPhone.setText(DataStorage.pets.get(position).getPhone());
        petImage.setImageBitmap(DbBitmapUtility.getImage(DataStorage.pets.get(position).getImage()));
        bitmapImg = DbBitmapUtility.getImage(DataStorage.pets.get(position).getImage());

    }

    //Get all pet data from user
    private void getPetDetails(){
        petName = editTxtPetName.getText().toString();
        petDescription = editTxtPetDescription.getText().toString();
        petLocation = editTxtLocation.getText().toString();
        petAge = editTxtAge.getText().toString();
        petPhone = editTxtPhone.getText().toString();
    }

    private void makeToast(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
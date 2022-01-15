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

public class AddPetActivity extends AppCompatActivity {


    private EditText editTxtPetName, editTxtPetDescription, editTxtLocation, editTxtAge, editTxtPhone;
    private Button btnAddPet;
    private String petName, petDescription, petLocation, petAge, petPhone;
    private ImageView petImage, btnAddImg;
    private PetDataSource petDs;
    private Bitmap bitmapImg;
    private SimpleDateFormat sdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);

        findViews();
        addImg();
        addDataToDB();

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data != null){
            Uri imageUri = data.getData();
            try {
                bitmapImg = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                petImage.setImageBitmap(bitmapImg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void addDataToDB(){
        btnAddPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getPetDetails();

                if(TextUtils.isEmpty(petName) || TextUtils.isEmpty(petDescription) || TextUtils.isEmpty(petAge) || TextUtils.isEmpty(petLocation) || TextUtils.isEmpty(petPhone) || (bitmapImg == null)){
                    makeToast("Nisi popunio sva polja");
                }else{
                    if(petDs.addPetDetailsToDB(petName, petDescription, petLocation, petAge, petPhone, sdf.format(new Date()), DbBitmapUtility.getBytes(bitmapImg))){
                        makeToast("Uspjesno upisan podatak u DB");
                    }else{
                        makeToast("Greska pri upisu podatka u DB");
                    }
                    petDs.close();
                    Intent intent = new Intent(AddPetActivity.this, PetListActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void findViews() {
        editTxtPetName = (EditText) findViewById(R.id.editTxtPetName);
        editTxtPetDescription = (EditText) findViewById(R.id.editTxtPetDescription);
        editTxtLocation = (EditText) findViewById(R.id.editTxtLocation);
        editTxtAge = (EditText) findViewById(R.id.editTxtAge);
        editTxtPhone = (EditText) findViewById(R.id.editTxtPhone);
        btnAddPet = (Button) findViewById(R.id.btnAddPet);
        btnAddImg = (ImageView) findViewById(R.id.btnAddImg);
        petImage = (ImageView) findViewById(R.id.petImage);
        sdf = new SimpleDateFormat("MM/dd/yyyy 'at' HH:mm:ss");
        petDs = new PetDataSource(getApplicationContext());
        petDs.open();
    }


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
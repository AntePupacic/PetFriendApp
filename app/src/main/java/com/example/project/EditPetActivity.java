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

public class EditPetActivity extends AppCompatActivity {
    private EditText editTxtPetName, editTxtPetDescription, editTxtLocation, editTxtAge, editTxtPhone;
    private String petName, petDescription, petLocation, petAge, petPhone;
    private Button btnUpdatePet;
    private ImageView petImage;
    private PetDataSource petDs;
    private Integer id;
    private Bitmap bitmapImg;
    private SimpleDateFormat sdf;
    private Pet pet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pet);

        pet = null;

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            id = extras.getInt("id");
            pet = DataStorage.getPetById(id);
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
                if(TextUtils.isEmpty(petName) || TextUtils.isEmpty(petDescription) || TextUtils.isEmpty(petAge) || TextUtils.isEmpty(petLocation) || TextUtils.isEmpty(petPhone) || (bitmapImg == null)){
                    makeToast("Nisi popunio sva polja");
                }else{
                    if(petDs.updatePet(id, petName, petDescription, petLocation, petAge, petPhone, sdf.format(new Date()), DbBitmapUtility.getBytes(bitmapImg))){
                        makeToast("Uspjesno promjenjen podatak u DB");
                    }else{
                        makeToast("Greska pri promjeni podatka u DB");
                    }
                    petDs.close();
                    Intent intent = new Intent(EditPetActivity.this, PetListActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void addImg(){
        petImage.setOnClickListener(new View.OnClickListener() {
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
        petImage = (ImageView) findViewById(R.id.petImage);
        sdf = new SimpleDateFormat("MM/dd/yyyy 'at' HH:mm:ss");
        petDs = new PetDataSource(getApplicationContext());
    }

    //Inserting data from ArrayList into editText
    private void setValues(){
        editTxtPetName.setText(pet.getName());
        editTxtPetDescription.setText(pet.getDescription());
        editTxtLocation.setText(pet.getLocation());
        editTxtAge.setText(String.valueOf(pet.getAge()));
        editTxtPhone.setText(String.valueOf(pet.getPhone()));
        petImage.setImageBitmap(DbBitmapUtility.getImage(pet.getImage()));
        bitmapImg = DbBitmapUtility.getImage(pet.getImage());
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
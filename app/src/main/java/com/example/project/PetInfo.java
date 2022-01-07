package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PetInfo extends AppCompatActivity {

    TextView txtViewName, txtViewDes, txtViewLoc, txtViewAge;
    ImageView petImage;
    Button btnPhoneCall, btnDeletePet, btnUpdatePet;
    Integer position;
    PetDataSource petDs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_info);

        findViews();

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            position = extras.getInt("Position");
        }

        setDataIntoViews();
        makePhoneCall();
        deletePet();
        updatePet();
    }

    private void findViews(){
        txtViewName = (TextView) findViewById(R.id.txtViewName);
        txtViewDes = (TextView) findViewById(R.id.txtViewDes);
        txtViewLoc = (TextView) findViewById(R.id.txtViewLoc);
        txtViewAge = (TextView) findViewById(R.id.txtViewAge);
        btnPhoneCall = (Button) findViewById(R.id.btnCallPhone);
        btnDeletePet = (Button) findViewById(R.id.btnDeletePet);
        btnUpdatePet = (Button) findViewById(R.id.btnUpdatePet);
        petImage = (ImageView) findViewById(R.id.petImage);
        petDs = new PetDataSource(getApplicationContext());
    }

    private void setDataIntoViews(){
        txtViewName.setText(DataStorage.pets.get(position).getName());
        txtViewDes.setText(DataStorage.pets.get(position).getDescription());
        txtViewLoc.setText(DataStorage.pets.get(position).getLocation());
        txtViewAge.setText(DataStorage.pets.get(position).getAge());
        petImage.setImageBitmap(DbBitmapUtility.getImage(DataStorage.pets.get(position).getImage()));
        btnPhoneCall.setText("CALL " + DataStorage.pets.get(position).getPhone());
    }

    private void makePhoneCall(){
        btnPhoneCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                callPhoneNumber();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callPhoneNumber();
            }
        }
    }

    public void callPhoneNumber()
    {
        try
        {
            if(Build.VERSION.SDK_INT > 22)
            {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(PetInfo.this, new String[]{Manifest.permission.CALL_PHONE}, 101);
                    return;
                }

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + DataStorage.pets.get(position).getPhone()));
                startActivity(callIntent);

            }
            else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + DataStorage.pets.get(position).getPhone()));
                startActivity(callIntent);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private void deletePet(){
        btnDeletePet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(petDs.deletePet(DataStorage.pets.get(position).getID())){
                    petDs.close();
                    Toast.makeText(getApplicationContext(), "Uspješno izbrisan podatak iz DB", Toast.LENGTH_SHORT).show();
                    Intent dogIntent = new Intent(PetInfo.this, DogList.class);
                    startActivity(dogIntent);
                }else{
                    Toast.makeText(getApplicationContext(), "Greška pri brisanju podatka iz DB", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updatePet(){
        btnUpdatePet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PetInfo.this, UpdatePet.class);
                intent.putExtra("Position", position);
                startActivity(intent);
            }
        });
    }


}
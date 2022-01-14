package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PetInfoActivity extends AppCompatActivity {

    TextView txtViewName, txtViewDes, txtViewLoc, txtViewAge, txtPhoneCall;
    ImageView petImage, btnPhoneCall, btnDeletePet, btnUpdatePet, btnGoogleMap;
    Integer position;
    String uri= "geo:0,0?q=";
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
        showGoogleMaps();
    }

    private void findViews(){
        txtViewName = (TextView) findViewById(R.id.txtViewName);
        txtViewDes = (TextView) findViewById(R.id.txtViewDes);
        txtViewLoc = (TextView) findViewById(R.id.txtViewLoc);
        txtViewAge = (TextView) findViewById(R.id.txtViewAge);
        txtPhoneCall = (TextView) findViewById(R.id.txtCallPhone);
        btnPhoneCall = (ImageView) findViewById(R.id.btnCallPhone);
        btnDeletePet = (ImageView) findViewById(R.id.btnDeletePet);
        btnUpdatePet = (ImageView) findViewById(R.id.btnUpdatePet);
        btnGoogleMap = (ImageView) findViewById(R.id.btnGoogleMap);
        petImage = (ImageView) findViewById(R.id.petImage);
        petDs = new PetDataSource(getApplicationContext());
    }

    private void setDataIntoViews(){
        txtViewName.setText(DataStorage.pets.get(position).getName());
        txtViewDes.setText(DataStorage.pets.get(position).getDescription());
        txtViewLoc.setText(DataStorage.pets.get(position).getLocation());
        txtViewAge.setText(String.valueOf(DataStorage.pets.get(position).getAge()));
        petImage.setImageBitmap(DbBitmapUtility.getImage(DataStorage.pets.get(position).getImage()));
        txtPhoneCall.setText("CALL: " + DataStorage.pets.get(position).getPhone());
    }

    private void showGoogleMaps(){
        btnGoogleMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse(uri + DataStorage.pets.get(position).getLocation());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if(mapIntent.resolveActivity(getPackageManager()) != null){
                    startActivity(mapIntent);
                }
            }
        });
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
                    ActivityCompat.requestPermissions(PetInfoActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 101);
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
                    Intent dogIntent = new Intent(PetInfoActivity.this, DogListActivity.class);
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
                Intent intent = new Intent(PetInfoActivity.this, EditPetActivity.class);
                intent.putExtra("Position", position);
                startActivity(intent);
            }
        });
    }


}
package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.MenuCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PetInfoActivity extends AppCompatActivity {

    private TextView txtViewName, txtViewDes, txtViewLoc, txtViewAge, txtPhoneCall;
    private ImageView petImage, btnPhoneCall, btnGoogleMap;
    private Integer id;
    private String uri= "geo:0,0?q=";
    private PetDataSource petDs;
    private Pet openedPet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_info);

        openedPet = null;
        findViews();

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            id = extras.getInt("id");
            openedPet = DataStorage.getPetById(id);
        }

        setDataIntoViews();
        makePhoneCall();
        showGoogleMaps();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuCompat.setGroupDividerEnabled(menu, true);
        getMenuInflater().inflate(R.menu.petinfo_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.btnEdit:
                Intent intent = new Intent(PetInfoActivity.this, EditPetActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
                return true;
            case R.id.btnDelete:
                deletePet();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }


    private void findViews(){
        txtViewName = (TextView) findViewById(R.id.txtViewName);
        txtViewDes = (TextView) findViewById(R.id.txtViewDes);
        txtViewLoc = (TextView) findViewById(R.id.txtViewLoc);
        txtViewAge = (TextView) findViewById(R.id.txtViewAge);
        txtPhoneCall = (TextView) findViewById(R.id.txtCallPhone);
        btnPhoneCall = (ImageView) findViewById(R.id.btnCallPhone);
        btnGoogleMap = (ImageView) findViewById(R.id.btnGoogleMap);
        petImage = (ImageView) findViewById(R.id.petImage);
        petDs = new PetDataSource(getApplicationContext());
    }

    private void setDataIntoViews(){
        txtViewName.setText(openedPet.getName());
        txtViewDes.setText(openedPet.getDescription());
        txtViewLoc.setText(openedPet.getLocation());
        txtViewAge.setText(String.valueOf(openedPet.getAge()));
        petImage.setImageBitmap(DbBitmapUtility.getImage(openedPet.getImage()));
        txtPhoneCall.setText("CALL: " + openedPet.getPhone());
    }

    private void showGoogleMaps(){
        btnGoogleMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse(uri + openedPet.getLocation());
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
                callIntent.setData(Uri.parse("tel:" + openedPet.getPhone()));
                startActivity(callIntent);

            }
            else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + openedPet.getPhone()));
                startActivity(callIntent);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private void deletePet(){
        if(petDs.deletePet(openedPet.getID())){
            petDs.close();
            Toast.makeText(getApplicationContext(), "Uspješno izbrisan podatak iz DB", Toast.LENGTH_SHORT).show();
            Intent dogIntent = new Intent(PetInfoActivity.this, PetListActivity.class);
            startActivity(dogIntent);
        }else{
            Toast.makeText(getApplicationContext(), "Greška pri brisanju podatka iz DB", Toast.LENGTH_SHORT).show();
        }

    }



}
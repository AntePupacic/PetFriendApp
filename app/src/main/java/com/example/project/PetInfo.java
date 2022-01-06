package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PetInfo extends AppCompatActivity {

    TextView txtViewName, txtViewDes, txtViewLoc, txtViewAge;
    ImageView petImage;
    Button phoneCall;
    Integer position;

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
        clickPhoneCall();
    }

    private void findViews(){
        txtViewName = (TextView) findViewById(R.id.txtViewName);
        txtViewDes = (TextView) findViewById(R.id.txtViewDes);
        txtViewLoc = (TextView) findViewById(R.id.txtViewLoc);
        txtViewAge = (TextView) findViewById(R.id.txtViewAge);
        phoneCall = (Button) findViewById(R.id.btnCallPhone);
        petImage = (ImageView) findViewById(R.id.petImage);
    }
    private void setDataIntoViews(){
        txtViewName.setText(DataStorage.pets.get(position).getName());
        txtViewDes.setText(DataStorage.pets.get(position).getDescription());
        txtViewLoc.setText(DataStorage.pets.get(position).getLocation());
        txtViewAge.setText(DataStorage.pets.get(position).getAge());
        petImage.setImageBitmap(DbBitmapUtility.getImage(DataStorage.pets.get(position).getImage()));
        phoneCall.setText("CALL " + DataStorage.pets.get(position).getPhone());
    }

    private void clickPhoneCall(){
        phoneCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + DataStorage.pets.get(position).getPhone()));

                if (ActivityCompat.checkSelfPermission(PetInfo.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);
            }
        });
    }

}
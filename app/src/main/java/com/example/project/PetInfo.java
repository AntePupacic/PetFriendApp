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
        makePhoneCall();
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
    
    private void makePhoneCall(){
        phoneCall.setOnClickListener(new View.OnClickListener() {
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

}
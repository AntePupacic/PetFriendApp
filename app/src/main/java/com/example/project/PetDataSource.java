package com.example.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class PetDataSource {
    private SQLiteDatabase db;
    private PetDataDBHelper dbHelper;

    public PetDataSource(Context context){
        dbHelper = new PetDataDBHelper(context);
    }

    public void open() throws SQLException{
        db = dbHelper.getWritableDatabase();
    }

    public void close(){
        db.close();
    }

    public boolean addPetDetailsToDB(String name, String description, String location, String age){

        ContentValues values = new ContentValues();
        values.put(PetEntry.COLUMN_NAME, name);
        values.put(PetEntry.COLUMN_LOCATION, description);
        values.put(PetEntry.COLUMN_DESCRIPTION, location);
        values.put(PetEntry.COLUMN_AGE, age);

        long result = db.insert(PetEntry.TABLE_NAME, null, values);

        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public ArrayList<Pet> getAllPetData(){
        ArrayList<Pet> pets = new ArrayList<Pet>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + PetEntry.TABLE_NAME, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Pet pet = new Pet();
            pet.setID(cursor.getInt(0));
            pet.setName(cursor.getString(1));
            pet.setDescription(cursor.getString(2));
            pet.setLocation(cursor.getString(3));
            pet.setAge(cursor.getString(4));
            pets.add(pet);
            cursor.moveToNext();
        }

        cursor.close();
        return pets;
    }
}

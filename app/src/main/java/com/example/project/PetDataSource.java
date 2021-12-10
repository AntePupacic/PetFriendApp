package com.example.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

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

    void addPetDetailsToDB(String name, String description, String location, Integer age){

        ContentValues values = new ContentValues();
        values.put(PetColumnsDB.PetEntry.COLUMN_NAME, name);
        values.put(PetColumnsDB.PetEntry.COLUMN_DESCRIPTION, description);
        values.put(PetColumnsDB.PetEntry.COLUMN_LOCATION, location);
        values.put(PetColumnsDB.PetEntry.COLUMN_AGE, age);

        db.insert(PetColumnsDB.PetEntry.TABLE_NAME, null, values);
    }

    public ArrayList<Pet> getAllPetData(){
        ArrayList<Pet> pets = new ArrayList<Pet>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + PetColumnsDB.PetEntry.TABLE_NAME, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Pet pet = new Pet();
            pet.setID(cursor.getInt(0));
            pet.setName(cursor.getString(1));
            pet.setDescription(cursor.getString(2));
            pet.setLocation(cursor.getString(3));
            pet.setAge(cursor.getInt(4));
            pets.add(pet);
        }
        cursor.close();
        return pets;
    }
}

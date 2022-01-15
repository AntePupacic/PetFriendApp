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
        try {
            this.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void open() throws SQLException{
        db = dbHelper.getWritableDatabase();
    }

    public void close(){
        db.close();
    }

    public boolean addPetDetailsToDB(String name, String description, String location, String age, String phone, String date, byte[] image){

        ContentValues values = new ContentValues();
        values.put(PetEntry.COLUMN_NAME, name);
        values.put(PetEntry.COLUMN_LOCATION, description);
        values.put(PetEntry.COLUMN_DESCRIPTION, location);
        values.put(PetEntry.COLUMN_AGE, age);
        values.put(PetEntry.COLUMN_PHONE, phone);
        values.put(PetEntry.COLUMN_DATE, date);
        values.put(PetEntry.COLUMN_IMAGE, image);

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
            pet.setPhone(cursor.getString(5));
            pet.setDate(cursor.getString(6));
            pet.setImage(cursor.getBlob(7));
            pets.add(pet);
            cursor.moveToNext();
        }
        cursor.close();
        return pets;
    }

    public boolean deletePet(int id){
        return db.delete(PetEntry.TABLE_NAME, PetEntry._ID + "=" + id, null ) > 0;
    }


    public boolean updatePet(int id, String name, String description, String location, String age, String phone, String date, byte[] image){

        ContentValues values = new ContentValues();

        values.put(PetEntry.COLUMN_NAME, name);
        values.put(PetEntry.COLUMN_LOCATION, description);
        values.put(PetEntry.COLUMN_DESCRIPTION, location);
        values.put(PetEntry.COLUMN_AGE, age);
        values.put(PetEntry.COLUMN_PHONE, phone);
        values.put(PetEntry.COLUMN_DATE, date);
        values.put(PetEntry.COLUMN_IMAGE, image);

        return db.update(PetEntry.TABLE_NAME, values, PetEntry._ID + "=" + id, null) > 0;
    }

}

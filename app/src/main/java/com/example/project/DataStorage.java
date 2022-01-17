package com.example.project;

import android.content.Context;

import java.util.ArrayList;

public class DataStorage {
    public static ArrayList<Pet> pets = new ArrayList<Pet>();
    private static PetDataSource petDs;

    //Fill data from DB to ArrayList
    public static void fillData(Context context){
        petDs = new PetDataSource(context);
        pets = petDs.getAllPetData();
        petDs.close();
    }


    public static Pet getPetById(Integer id){
        for (int i = 0; i < pets.size(); i++) {
            if (pets.get(i).getID() == id) {
                return pets.get(i);
            }
        }
        return null;
    }

}

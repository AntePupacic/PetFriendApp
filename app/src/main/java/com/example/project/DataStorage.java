package com.example.project;

import android.content.Context;

import java.util.ArrayList;

public class DataStorage {
    public static ArrayList<Pet> pets = new ArrayList<Pet>();
    private static PetDataSource petDs;

    public static void fillData(Context context){
        petDs = new PetDataSource(context);
        petDs.open();
        pets = petDs.getAllPetData();
        petDs.close();
    }

}

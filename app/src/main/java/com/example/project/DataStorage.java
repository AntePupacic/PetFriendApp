package com.example.project;

import java.util.HashMap;
import java.util.Map;

public class DataStorage {
    public static HashMap<Integer, Pet> petData = new HashMap<Integer, Pet>();

    public static void fillData(){
        for(int i = 0; i < 10; i++){
            Pet dog = new Pet(i+1, "Ari", "Split", "Ari is black and brown dog", 10);
            petData.put(i, dog);
        }
    }


}

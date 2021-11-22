package com.example.project;

import java.util.HashMap;
import java.util.Map;

public class DataStorage {
    public static HashMap<Integer, Pet> petData = new HashMap<Integer, Pet>();

    public static void fillData(){
        Pet dog = new Pet(1, "Pasko", "Split", "Pasko is black and smart dog", 10);
        petData.put(0, dog);
    }


}

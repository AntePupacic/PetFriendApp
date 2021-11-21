package com.example.project;

public class Pet {
    private int ID;
    private String name;
    private String location;
    private String description;
    private int age;

    public Pet(int ID, String name, String location, String description, int age) {
        this.ID = ID;
        this.name = name;
        this.location = location;
        this.description = description;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

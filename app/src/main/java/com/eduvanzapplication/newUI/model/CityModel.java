package com.eduvanzapplication.newUI.model;

public class CityModel {
    String id, name;

    public CityModel() {
    }

    public CityModel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

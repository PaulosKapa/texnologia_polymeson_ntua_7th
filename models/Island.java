package models;

import java.util.ArrayList;

public class Island {
    public String name;
    public int width;
    public int height;
    public double budget;
    public ArrayList<String> map;
    public ArrayList<Unit> units;
    public ArrayList<Site> sites;
    
    public Island(String name,
                  int width,
                  int height,
                  double budget,
                  ArrayList<String> map,
                  ArrayList<Unit> units,
                  ArrayList<Site> sites){

        this.name = name;
        this.width = width;
        this.height = height;
        this.budget = budget;
        this.map = map;
        this.units = units;
        this.sites =sites;

    }
}

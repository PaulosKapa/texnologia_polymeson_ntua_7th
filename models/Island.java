package models;

import java.util.ArrayList;

public class Island {
    String name;
    int width;
    int height;
    double budget;
    String[] map;
    ArrayList<Unit> units;
    ArrayList<Site> sites;
    
    public Island(String name,
                  int width,
                  int height,
                  double budget,
                  String[] map,
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

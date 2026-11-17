package models;

import java.util.ArrayList;

public class Island {
    String name;
    int width;
    int height;
    double budget;
    ArrayList<String> map;
    ArrayList<Unit> units;
    ArrayList<Site> sites;
    
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
    public String getName(){
        return name;
    }
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
    public double getBudget(){
        return budget;
    }
    public ArrayList<String> getMap(){
        return map;
    }
    public ArrayList<Unit> getUnits(){
        return units;
    }
    public ArrayList<Site> getSites(){
        return sites;
    }
}

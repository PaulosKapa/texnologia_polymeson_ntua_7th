package models;

public class Item {
    String id;
    String type;
    double capex;
    double size;
    int life;

    public Item(String id,
                String type,
                double capex,
                double size,
                int life){
        this.id = id;
        this.type = type;
        this.capex = capex;
        this.size = size;
        this.life = life;
    }

    public String getId(){
        return id;
    }
    public String getType(){
        return type;
    }
    public double getCapex(){
        return capex;
    }
    public double getSize(){
        return size;
    }
    public int getLife(){
        return life;
    }
}

package models;

public class Item {
    String id;
    String type;
    double cost;
    double size;
    int life;

    public Item(String id,
                String type,
                double cost,
                double size,
                int life){
        this.id = id;
        this.type = type;
        this.cost = cost;
        this.size = size;
        this.life = life;
    }
}

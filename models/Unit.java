package models;

public class Unit {
    String id;
    String type;
    int x;
    int y;

    public Unit(String id,
                String type,
                int x,
                int y){
        this.id = id;
        this.type = type;
        this.x = x;
        this.y = y;
                }
        public int getX(){
                return x;
        }
        public int getY(){
                return y;
        }
        public String getType(){
                return type;
        }
}

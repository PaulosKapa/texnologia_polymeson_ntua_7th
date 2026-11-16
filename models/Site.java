package models;

import java.util.ArrayList;

public class Site {
    String id;
    int x;
    int y;
    ArrayList<String> allowed;

    public Site(String id,
                int x,
                int y,
                ArrayList<String> allowed){
        this.id = id;
        this.x = x;
        this.y = y;
        this.allowed = allowed;
    }

    public int getX(){
                return x;
        }
        public int getY(){
                return y;
        }
}

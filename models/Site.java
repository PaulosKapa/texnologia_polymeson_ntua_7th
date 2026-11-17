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
                        ArrayList<String> allowed) {
                this.id = id;
                this.x = x;
                this.y = y;
                this.allowed = allowed;
        }

        public String getId() {
                return id;
        }

        public int getX() {
                return x;
        }

        public int getY() {
                return y;
        }

        public ArrayList<String> getAllowed() {
                return allowed;
        }

        public String hints(){
        return "Allowed: " + this.allowed;
    }
}

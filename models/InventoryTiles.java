package models;


import javafx.scene.text.Text;
import actions.Placements;

public class InventoryTiles extends Tiles {
    
    public InventoryTiles(String type, String color){
        super(type, color);
    }

    public void information(double capex, double size, int life){
        this.tooltip.setText(this.tooltip.getText() + " " + capex + " "+ size + " " + life);
    }
    public void onInventoryTilePressed(Item item, Text text, Placements place){
        
            
            place.pickItem(item, text);
            place.place();
            
        
    }

}

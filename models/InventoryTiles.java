package models;


import javafx.scene.text.Text;
import actions.Placements;

public class InventoryTiles extends Tiles {
    boolean canPress = true;
    public InventoryTiles(String type, String color){
        super(type, color);
    }

    public void information(double capex, double size, int life){
        this.tooltip.setText(this.tooltip.getText() + " " + capex + " "+ size + " " + life);
    }
    public void onInventoryTilePressed(Item item, Text text, Placements place){
        
            if(canPress){
                place.pickItem(item, text);
                if(place.place()){
                
                this.tileButton.setStyle("-fx-background-color: gray; -fx-border-color: black; -fx-border-width: 0.5;");
                canPress = false;
            }
            System.out.println(canPress);
            
        
    }

}
}

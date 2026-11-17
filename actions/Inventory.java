package actions;
import java.util.ArrayList;
import models.Item;
import javafx.scene.control.Button;

public class Inventory {
    ArrayList<Item> availableUnits;
    Button itemButton;

    public Inventory(ArrayList<Item> availableUnits){
        this.availableUnits = availableUnits;
    }

    public ArrayList<Item> getInventory(){
        return availableUnits;
    }

}

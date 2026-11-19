package models;
import java.util.ArrayList;

import actions.Placements;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Button;

import javafx.scene.text.Text;
public class Tiles {
    String type;
    String color;
    Button tileButton;
    Tooltip tooltip;

    public Tiles(String type, String color){
        this.type = type;
        this.color = color;
    }

    public Button createTile(){
        this.tileButton = new Button();
        this.tileButton.setPrefSize(40, 40);
        this.tileButton.setMinSize(40, 40);
        this.tileButton.setMaxSize(40, 40);
        this.tileButton.setStyle("-fx-background-color:" + color + "; -fx-border-color: black; -fx-border-width: 0.5;");
        this.tooltip = new Tooltip(this.type);
        // 2. Το βάζουμε πάνω στο κουμπί
        this.tileButton.setTooltip(this.tooltip);
        return this.tileButton;
    }

    public void onButtonPressed(){
        System.out.println(type);
    }



    public void alreadyInstalled(String unitType){
        switch (unitType) {
            case "DIESEL":
                this.tileButton.setText("D");
                break;
            case "LOAD":
                this.tileButton.setText("L");
                break;
            default:
                break;
        }
        this.tooltip.setText(this.tooltip.getText() + " " + unitType);
    }
   

    public void canInstall(ArrayList<String> allowed){
        String newColor = "White";
        
        if (this.color.equals("green")) {
            newColor = "lightgreen";
        }
        else if (this.color.equals("brown")) {
            newColor = "tan"; // Το tan είναι ένα ωραίο ανοιχτό καφέ
        }
        else if(this.color.equals("gray")){
            newColor = "white";
        }
        else if(this.color.equals("blue")){
            newColor = "cyan";
        }
        this.tileButton.setStyle("-fx-background-color:" + newColor + "; -fx-border-color: black; -fx-border-width: 0.5;");
        this.tooltip.setText(this.tooltip.getText() + " " + allowed.toString());
    }

 
    public void onTilePressed(ArrayList<String> allowed, Site site, Text text, Placements place){
        if(allowed!=null){
            
            place.pickSite(site, text);
            if(place.place()){
                String generatorType = place.getHashMap().get(site).getType();
                switch (generatorType) {
                       case "PV":
                            this.tileButton.setText("P");
                            
                            break;
                        case "WIND":
                            this.tileButton.setText("W");
                            break;
                        case "BATTERY":
                            this.tileButton.setText("B");
                            break;
                        default:
                            break;
                }

            }
            
        }
    }

    public Button getButton(){
        return tileButton;
    }


}

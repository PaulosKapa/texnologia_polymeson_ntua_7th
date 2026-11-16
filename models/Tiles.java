package models;
import javafx.scene.control.Button;
public class Tiles {
    public int type;
    public String color;
    public Button tileButton;

    public Tiles(int type, String color){
        this.type = type;
        this.color = color;
    }

    public Button createTile(){
        this.tileButton = new Button();
        this.tileButton.setPrefSize(25, 25);
        this.tileButton.setMinSize(25, 25);
        this.tileButton.setMaxSize(25, 25);
        this.tileButton.setStyle("-fx-background-color:" + color + "; -fx-border-color: black; -fx-border-width: 0.5;");
        return  this.tileButton;
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
    }


    public void canInstall(){
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
    }





}

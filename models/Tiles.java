package models;
import java.util.ArrayList;

import actions.Placements;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Button;

import javafx.scene.text.Text;
//κλάση που διαχειρίζεται των χώρων του νησιού
public class Tiles {
    String type;
    String color;
    Button tileButton;
    Tooltip tooltip;

    public Tiles(String type, String color){
        this.type = type;
        this.color = color;
    }
    //μέθοδος για τη δημιουργία κάθε χώρου
    public Button createTile(){
        this.tileButton = new Button();
        this.tileButton.setPrefSize(30, 30);
        this.tileButton.setMinSize(20, 20);
        this.tileButton.setMaxSize(40, 40);
        applyStyle(color);
        this.tooltip = new Tooltip(this.type);
        // 2. Το βάζουμε πάνω στο κουμπί
        this.tileButton.setTooltip(this.tooltip);
        return this.tileButton;
    }
    //styl κουμπιού
    protected void applyStyle(String bgColor) {
    this.tileButton.setStyle(
        "-fx-background-color: " + bgColor + "; " +
        "-fx-border-color: black; " +
        "-fx-border-width: 0.5; " +
        "-fx-padding: 0; " +
        "-fx-font-size: 10px;"
    );
}

    public void onButtonPressed(){
        System.out.println(type);
    }


    //μέθοδοσ για τους χώρους που είχαν πηγές ενέργειας ή κατανάλωση ενέργειας
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
   
    //μέθοδοσ για το χρωματισμό των χώρων που μπορούν να εγκαταστήσουν μονάδες
    public void canInstall(ArrayList<String> allowed){
        String newColor = "White";
        
        if (this.color.equals("green")) {
            newColor = "lightgreen";
        }
        else if (this.color.equals("brown")) {
            newColor = "tan"; 
        }
        else if(this.color.equals("gray")){
            newColor = "white";
        }
        else if(this.color.equals("blue")){
            newColor = "cyan";
        }
        applyStyle(newColor);
        this.tooltip.setText(this.tooltip.getText() + " " + allowed.toString());
    }

    //όταν πατηθεί ένας χώρος
    public void onTilePressed(ArrayList<String> allowed, Site site, Text text, Placements place){
        //μόνο για αυτούς που επιτρέπεται χτίσημο
        if(allowed!=null){
            
            //επιλογή χώρου και έλεγχος ικανότητας εγκατάστασης
            place.pickSite(site, this, text);
            place.place();
                

            }
            
        }
        
        //αλλαγή κειμένου αν γίνει εγκατάσταση
        public void setInstalledText(String generatorType) {
       
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
    //καθαρισμός κειμένου
    public void clearInstalledText() {
        this.tileButton.setText("");
    }
    public Button getButton(){
        return tileButton;
    }


}

package models;


import javafx.scene.text.Text;
import actions.Placements;
//κλάση που χειρίζεται τα κουμπιά για τις πηγές ενέργειας
public class InventoryTiles extends Tiles {

    boolean canPress = true;
    Item item;
    //κληρωνομεί την κλάση Tiles
    public InventoryTiles(Item item, String color){
        super(item.getType(), color);
        this.item = item;
    }
    public Item getItem(){
        return this.item;
    }
    //πληροφορίες για τη πηγή ενέργειας μόλις αιωρηθεί το ποντίκι πάνω από το κουμπί
    public void information() {
        this.tooltip.setText(this.tooltip.getText() + "\n" + this.item.getDetails());
    }
    //μόλις πατηθεί το κουμπί
    public void onInventoryTilePressed(Text text, Placements place){
            //αν είναι επιλέξιμο
           if(this.canPress){
                //επέλεξε αυτή την πηγή ενέργειας και δές αν μπορεί να γίνει εγκατάσταση
                place.pickItem(this.item, this, text);
                place.place();
           }
           //αν δεν είναι επιλέξιμο αφεραισέ το
           else{
            place.removeItem(this.item);
           }
    }
    //αλαγή στυλ ανάλογα τη κατάσταση
    public void setUse(boolean used) {
        if (used) {
            this.tileButton.setStyle("-fx-background-color: gray; -fx-border-color: black; -fx-border-width: 0.5; -fx-padding: 0;");
            this.canPress = false;
        } else {
            
            this.tileButton.setStyle("-fx-background-color:" + this.color + "; -fx-border-color: black; -fx-border-width: 0.5; -fx-padding: 0;");
            this.canPress = true;
        }
    }

}


package actions;

import java.util.ArrayList;
import java.util.HashMap;

import models.InventoryTiles;
import models.Item;
import models.Site;
import models.Tiles;
import javafx.scene.text.Text;
//κλάση που διαχειρίζεται την εγκατάσταση των πηγών ενέργειας
public class Placements {
    Item item;
    Site site;
    //κρατάει χάρτη με εδάφη και εγκαταστημένες πηγές ενέργειας
    HashMap<Site, Item> placements = new HashMap<Site, Item>();
    //διαχείριση του κειμένου επιλογής
    Text currentItemText;
    Text currentSiteText;
    //ποιά πηγή ενέργειας έχει επιλεχθεί
    InventoryTiles selectedInventoryTile;
    //ποιό κομμάτι γης έχει επιλεχθεί
    Tiles selectedMapTile;
    //λίστα με όλες τις διαθέσιμες πηγές ενέργειας
    ArrayList<InventoryTiles> allInventoryTiles = new ArrayList<>();
    //ποιό κουμπί πηγής ενέργειας αντιστοιχεί σε ποιό κομμάτι γης
    HashMap<Site, Tiles> siteToTile = new HashMap<>();

    public HashMap<Site, Item> getHashMap(){
        return this.placements;
    }

    public void registerInventoryTile(InventoryTiles tile) {
        allInventoryTiles.add(tile);
    }
    //μέθοδος για όταν διαλέγει ο χρήστης μία πηγή ενέργειας 
    public Item pickItem(Item i,InventoryTiles invTile, Text itemText) {
        //αποθηκεύει το αντικείμενο για επόμενη χρήση
        this.item = i;
        //αλλάζει το κείμενο
        this.currentItemText = itemText;
        this.selectedInventoryTile = invTile;
        itemText.setText("Item: " + item.getId());
        return item;
    }
    //μέθοδος για επιλογή του χώρου
    public Site pickSite(Site s, Tiles mapTile, Text siteText) {
        //αποθήκευση του χώρου για επόμενη χρήση
        this.site = s;
        //αλλαγή του κειμένου
        this.currentSiteText = siteText;
        this.selectedMapTile = mapTile;
        siteText.setText("Site: " + site.getId());
        return site;
    }
    //μέθοδος για να αλλάξει τις πηγές ενέργειας από σε χρήση σε όχι χρήση
    private void refreshInventory() {
        for(InventoryTiles invTile : allInventoryTiles){
            boolean isPlaced = this.placements.containsValue(invTile.getItem());
            invTile.setUse(isPlaced);
        }
    }
    //μέθοδος για όταν τοποθετούμε μία πηγή ενέργειας
    public boolean place() {
        //σε περίπτωση που κάποιοα από τα δύο δεν έχει επιλεχθεί
        if(this.site == null || this.item == null){
            return false;
        }
        //σε περίπτωση που ο χώρος που έχει επιλεχθέι επιτρέπει την επιλογή του αντικειμένου που έχει επιλεχθει
        if(this.site.getAllowed().contains(this.item.getType())){
            // //βγάζει τα διπλότυπα από το χάρτη
            // if(this.placements.get(this.site)!=null){
            //     this.placements.remove(this.site);

            // }
            //βάζει τα επιλεγμένα στη λίστα
            this.placements.put(this.site, this.item);
            //ενώνει το επιλεγμένο χώρο με το επιλεγμένο κουμπί
            this.siteToTile.put(this.site, this.selectedMapTile);
            //βάζει το γράμμα στο χώρο
            this.selectedMapTile.setInstalledText(this.item.getType());
            //αλλάζει σε χρήση τη πηγή ενέργειας
            refreshInventory();
            //καθαρίζει επιλογές και κείμενο
            resetSelection();
          

            return true;
        } else {
            //σε διαφορετική περίπτωση απλά καθαρίζει επιλογές και κείμενο
            resetSelection();
            return false;
        }
    }
    //μέθοδος για να διαράψουμε αντικείμενο αποό τις εγκατεστημένες
    public void removeItem(Item targetItem) {
        Site siteToRemove = null;
        //κοιτάει στη λίστα ποιό κουμπί ταιριάζει με αυτό που πατήσαμε        
        for (var entry : this.placements.entrySet()) {
            if (entry.getValue().equals(targetItem)) {
                siteToRemove = entry.getKey();
                break;
            }
        }


        if (siteToRemove != null) {
            //αφαιρεί το χώρο
            this.placements.remove(siteToRemove);
            //καθαρίζει το κείμενο
            this.siteToTile.get(siteToRemove).clearInstalledText();
            //επαναφορά πηγής ενέργειας σε επιλέξιμη
            refreshInventory();
        }
        resetSelection();
    }
    //μέθοδοσ για καθαρισμός επιλογών και κειμένου
    public void resetSelection(){
         this.site = null;
            this.item = null;

            if(currentSiteText != null) currentSiteText.setText("Site: ");
            if(currentItemText != null) currentItemText.setText("Item: ");
    }
}

package actions;

import java.util.HashMap;

import models.Item;
import models.Site;
import javafx.scene.text.Text;
public class Placements {
    Item item;
    Site site;
    HashMap<Site, Item> placements = new HashMap<Site, Item>();
    private Text currentItemText;
    private Text currentSiteText;

    public HashMap<Site, Item> getHashMap(){
        return this.placements;
    }

    public Item pickItem(Item i, Text itemText) {

        this.item = i;
        this.currentItemText = itemText;
        itemText.setText("Item: " + item.getId());
        return item;
    }

    public Site pickSite(Site s, Text siteText) {
        this.site = s;
        this.currentSiteText = siteText;
        siteText.setText("Site: " + site.getId());
        return site;
    }

    public boolean place() {
        
        if (this.site == null || this.item == null) {
            return false;
        }
        if (this.site.getAllowed().contains(this.item.getType())) {

            this.placements.put(this.site, this.item);
            this.site = null;
            this.item = null;

            if(currentSiteText != null) currentSiteText.setText("Site: ");
            if(currentItemText != null) currentItemText.setText("Item: ");
            return true;
        } else {
            if(currentSiteText != null) currentSiteText.setText("Site: ");
            if(currentItemText != null) currentItemText.setText("Item: ");
            this.site = null;
            this.item = null;
            return false;
        }
    }
}

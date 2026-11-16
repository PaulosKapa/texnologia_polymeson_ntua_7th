package models;

import java.util.HashMap;

public class PlacedBlocks {
    HashMap<Site, Item> placements;

    public PlacedBlocks(HashMap<Site, Item> placements){
        this.placements = placements;
    }
}

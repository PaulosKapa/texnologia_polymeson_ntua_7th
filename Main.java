
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import models.InventoryTiles;
import models.Island;
import models.Tiles;
import javafx.scene.text.Text;
import java.io.File;
import java.util.ArrayList;

import actions.Inventory;
import actions.Loader;
import actions.Placements;
import actions.UnitsLoader;
import javafx.stage.FileChooser;

import models.Item;
//κύρια κλάση του προγράματος
public class Main extends Application {

    @Override
    public void start(Stage stage) {
        //απαιτούμενο ui
        Text info = new Text("Click load to start. Select a resource button and then an appropriate tile to build. Hover over tiles to inspect them. Press that resource button again to remove the building");
        Button loadButton = new Button("Load Island.JSON");
        Text budget = new Text("Budget: ");
        Text name = new Text("Name: ");
        ArrayList<Text> labels = new ArrayList<Text>();
        Text siteChosen = new Text("Site: ");
        Text itemChosen = new Text("Item: ");
        labels.add(budget);
        labels.add(siteChosen);
        labels.add(itemChosen);
        labels.add(name);

        //δημιουργία κουτιού για τις πηγές ενέργειας
        HBox inventoryBox = new HBox(10); 
        inventoryBox.setAlignment(Pos.CENTER);


        //δημιουργία πλέγματος για το χάρτη
        GridPane mapGrid = new GridPane();
        mapGrid.setAlignment(Pos.CENTER);
        mapGrid.setVgap(1);
        mapGrid.setHgap(1);

        //μόλις πατηθεί το κουμπί που φορτώνει το αρχείο
        loadButton.setOnAction(event -> loadFile(stage, mapGrid, inventoryBox, labels));

        //δημιουργία χώρου που βρίσκεται το ui
        VBox root = new VBox(15); 
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        
        
        root.getChildren().addAll(info, loadButton, mapGrid, inventoryBox);
        root.getChildren().addAll(labels);

        //δημιουργία σκηνής
        Scene scene = new Scene(root, 1280, 720);

        stage.setTitle("Medialab Simulation");
        stage.setScene(scene);
        stage.show();
    }
    //μέθοδοσ για φόρτωση του αρχείου
    private void loadFile(Stage stage, GridPane mapGrid, HBox inventoryBox, ArrayList<Text> labels) {
        FileChooser file = new FileChooser();
        File selectFile = file.showOpenDialog(stage);
        Placements placementManager = new Placements();
       
        //αν υπάρχει αρχείο
        if (selectFile != null) {
            try {
                //αυτόματη επιλογή του catalog.json
                File inventoryFile = new File(selectFile.getParent(), "catalog.json");
                //δημιουργία κλάσεων island και inventory
                Island island = Loader.loadMap(selectFile.getAbsolutePath());
                Inventory inventory = UnitsLoader.loadUnits(inventoryFile.getAbsolutePath());
                //καθαρισμός εικόνας αν γίνει και δε΄λυτερο νησί
                inventoryBox.getChildren().clear();
                //δημιουργία κουμπιών για τις πηγές ενέργειας
                for(int i =0; i<inventory.getInventory().size();i++){
                    Item item = inventory.getInventory().get(i);
                    String color = "black";
                    
                    switch (item.getType()) {
                        
                        case "PV":
                            color = "teal";
                            
                            break;
                        case "WIND":
                            color = "yellow";
                            break;
                        case "BATTERY":
                            color = "red";
                            break;
                        default:
                            break;
                    }
                    //δημιουργία των αντικειμένων
                        InventoryTiles inventoryTile = new InventoryTiles(item, color);
                        //δημιουργία κουτιών από αντικείμενα
                        Button tileButton = inventoryTile.createTile();
                        inventoryTile.information();
                        //προσθήκη στις διαθέσιμες πηγές ενέργειας
                        placementManager.registerInventoryTile(inventoryTile);
                        tileButton.setOnAction(event->inventoryTile.onInventoryTilePressed(labels.get(2), placementManager));

                        inventoryBox.getChildren().add(tileButton);
                    }
                
                labels.get(0).setText("Budget: " + String.valueOf(island.getBudget()));
                labels.get(3).setText("Name:" + String.valueOf(island.getBudget()));
                int index = 0;
                Tiles[][] tileGrid = new Tiles [island.getHeight()][island.getWidth()];
                //αλλαγή χρώματος για τους χώρους
                for (int i = 0; i < island.getHeight(); i++) {
                    for (int j = 0; j < island.getWidth(); j++) {
                        String type = "null";
                        String color = "black";
                        
                        switch (island.getMap().get(index)) {
                            case "~":
                                type = "water";
                                color = "blue";
                                break;
                            case ".":
                                type = "plane";
                                color = "green";
                                break;
                            case "^":
                                type = "mountain";
                                color = "brown";
                                break;
                            case "H":
                                type = "city";
                                color = "gray";
                                break;
                            default:
                                break;
                        }
                        //δημιουργία αντικειμένων χώρων
                        Tiles tile = new Tiles(type, color);
                        //δημιουργία κουμπιών
                        Button tileButton = tile.createTile();
                        tileButton.setOnAction(event->tile.onButtonPressed());
                        //προσθήκη κουμπιού στο χάρτη
                        mapGrid.add(tileButton, j, i);
                        tileGrid[i][j] = tile;
                        
                        index++;
                    }
                }
                if(island.getUnits()!=null){
                    //προσθήση των χώρων που είναι διαθέσιμοι για εγκατάσταση
                    for(int k = 0; k<island.getUnits().size(); k++){
                        var unit = island.getUnits().get(k);
                        Tiles unitTile = tileGrid[unit.getY()][unit.getX()];
                        unitTile.alreadyInstalled(unit.getType());


                    }
                
                }
                if(island.getSites()!=null){
                    //προσθήκη ήδη υπάρχοντων πηγών/φορτίων
                    for(int k = 0; k<island.getSites().size(); k++){
                        var site = island.getSites().get(k);
                        Tiles unitTile = tileGrid[site.getY()][site.getX()];
                        unitTile.canInstall(island.getSites().get(k).getAllowed());
                        unitTile.getButton().setOnAction(event -> unitTile.onTilePressed(site.getAllowed(), site, labels.get(1), placementManager));


                    }
            }
        } 
            catch (Exception e) {
                showError(e.getMessage());
            }

        }

    }
    //για να εμφανιστούν τα error σαν popup
    public static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Σφάλμα");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

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

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        
        Button loadButton = new Button("load");
        Text budget = new Text("0");
        ArrayList<Text> labels = new ArrayList<Text>();
        Text siteChosen = new Text("Site: ");
        Text itemChosen = new Text("Item: ");
        labels.add(budget);
        labels.add(siteChosen);
        labels.add(itemChosen);

        // ΝΕΟ: Ένα οριζόντιο κουτί για τα αντικείμενα του καταλόγου
        HBox inventoryBox = new HBox(10); // 10 pixels κενό ανάμεσα στα κουμπιά
        inventoryBox.setAlignment(Pos.CENTER);


      // Φτιάχνουμε ένα ξεχωριστό πλέγμα ΜΟΝΟ για τον χάρτη
        GridPane mapGrid = new GridPane();
        mapGrid.setAlignment(Pos.CENTER);
        mapGrid.setVgap(1); // Μικρό κενό μεταξύ των κελιών για να ξεχωρίζουν
        mapGrid.setHgap(1);

        // Περνάμε το mapGrid στη μέθοδο loadFile
        loadButton.setOnAction(event -> loadFile(stage, mapGrid, inventoryBox, labels));

        // Χρησιμοποιούμε ένα VBox για να βάλουμε τα κουμπιά πάνω και τον χάρτη από κάτω
        VBox root = new VBox(15); 
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        
        // Προσθέτουμε τα στοιχεία στο κεντρικό VBox
        root.getChildren().addAll(loadButton, mapGrid, inventoryBox);
        root.getChildren().addAll(labels);

        // Φτιάχνουμε τη Scene ΜΙΑ ΦΟΡΑ εδώ
        Scene scene = new Scene(root, 800, 600);

        stage.setTitle("MediaLab Energy Island");
        stage.setScene(scene);
        stage.show();
    }

    private void loadFile(Stage stage, GridPane mapGrid, HBox inventoryBox, ArrayList<Text> labels) {
        FileChooser file = new FileChooser();
        File selectFile = file.showOpenDialog(stage);
        Placements placementManager = new Placements();
       

        if (selectFile != null) {
            try {
                File inventoryFile = new File(selectFile.getParent(), "catalog.json");
                Island island = Loader.loadMap(selectFile.getAbsolutePath());
                Inventory inventory = UnitsLoader.loadUnits(inventoryFile.getAbsolutePath());
                // Καθαρίζουμε τον κατάλογο σε περίπτωση που φορτώσουμε 2ο αρχείο
                inventoryBox.getChildren().clear();
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
                        InventoryTiles inventoryTile = new InventoryTiles(item.getType(), color);
                        
                        Button tileButton = inventoryTile.createTile();
                        tileButton.setOnAction(event->inventoryTile.onInventoryTilePressed(item, labels.get(2), placementManager));

                        inventoryBox.getChildren().add(tileButton);
                    }
                
                labels.get(0).setText(String.valueOf(island.getBudget()));
                int index = 0;
                Tiles[][] tileGrid = new Tiles [island.getHeight()][island.getWidth()];
                
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
                   
                        Tiles tile = new Tiles(type, color);
                        
                        Button tileButton = tile.createTile();
                        tileButton.setOnAction(event->tile.onButtonPressed());

                        mapGrid.add(tileButton, j, i);
                        tileGrid[i][j] = tile;
                        
                        index++;
                    }
                }
                if(island.getUnits()!=null){
                    for(int k = 0; k<island.getUnits().size(); k++){
                        var unit = island.getUnits().get(k);
                        Tiles unitTile = tileGrid[unit.getY()][unit.getX()];
                        unitTile.alreadyInstalled(unit.getType());


                    }
                
                }
                if(island.getSites()!=null){
                    
                    for(int k = 0; k<island.getSites().size(); k++){
                        var site = island.getSites().get(k);
                        Tiles unitTile = tileGrid[site.getY()][site.getX()];
                        unitTile.canInstall(island.getSites().get(k).getAllowed());
                        unitTile.getButton().setOnAction(event -> unitTile.onTilePressed(site.getAllowed(), site, labels.get(1), placementManager));


                    }
            }
        } 
            catch (Exception e) {
                System.err.println("error loading file");
            }

        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
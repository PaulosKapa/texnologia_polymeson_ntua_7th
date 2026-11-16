
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import models.Island;
import models.Tiles;
import javafx.scene.text.Text;
import java.io.File;
import javafx.stage.FileChooser;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        Button loadButton = new Button("load");
        Text budget = new Text("0");


      // Φτιάχνουμε ένα ξεχωριστό πλέγμα ΜΟΝΟ για τον χάρτη
        GridPane mapGrid = new GridPane();
        mapGrid.setAlignment(Pos.CENTER);
        mapGrid.setVgap(1); // Μικρό κενό μεταξύ των κελιών για να ξεχωρίζουν
        mapGrid.setHgap(1);

        // Περνάμε το mapGrid στη μέθοδο loadFile
        loadButton.setOnAction(event -> loadFile(stage, budget, mapGrid));

        // Χρησιμοποιούμε ένα VBox για να βάλουμε τα κουμπιά πάνω και τον χάρτη από κάτω
        VBox root = new VBox(15); 
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        
        // Προσθέτουμε τα στοιχεία στο κεντρικό VBox
        root.getChildren().addAll(loadButton, budget, mapGrid);

        // Φτιάχνουμε τη Scene ΜΙΑ ΦΟΡΑ εδώ
        Scene scene = new Scene(root, 800, 600);

        stage.setTitle("MediaLab Energy Island");
        stage.setScene(scene);
        stage.show();
    }

    private void loadFile(Stage stage, Text budget, GridPane mapGrid) {
        FileChooser file = new FileChooser();
        File selectFile = file.showOpenDialog(stage);

        if (selectFile != null) {
            try {
                Island island = Loader.loadMap(selectFile.getAbsolutePath());
                budget.setText(String.valueOf(island.budget));
                int index = 0;
                Tiles[][] tileGrid = new Tiles [island.height][island.width];
                for (int i = 0; i < island.height; i++) {
                    for (int j = 0; j < island.width; j++) {
                        int id = -1;
                        String color = "black";
                        
                        switch (island.map.get(index)) {
                            case "~":
                                id = 0;
                                color = "blue";
                                break;
                            case ".":
                                id = 1;
                                color = "green";
                                break;
                            case "^":
                                id = 2;
                                color = "brown";
                                break;
                            case "H":
                                id = 3;
                                color = "gray";
                                break;
                            default:
                                break;
                        }
                   
                        Tiles tile = new Tiles(id, color);
                        
                        Button tileButton = tile.createTile();
                        tileButton.setOnAction(event->tile.onButtonPressed());

                        mapGrid.add(tileButton, j, i);
                        tileGrid[i][j] = tile;
                        
                        index++;
                    }
                }
                if(island.units!=null){
                    for(int k = 0; k<island.units.size(); k++){
                        var unit = island.units.get(k);
                        Tiles unitTile = tileGrid[unit.getY()][unit.getX()];
                        unitTile.alreadyInstalled(unit.getType());


                    }
                
                }
                if(island.sites!=null){
                    for(int k = 0; k<island.sites.size(); k++){
                        var site = island.sites.get(k);
                        Tiles unitTile = tileGrid[site.getY()][site.getX()];
                        unitTile.canInstall();


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
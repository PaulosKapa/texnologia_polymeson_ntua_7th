package actions;
import java.io.EOFException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import models.Battery;
import models.Item;
import models.Wind;
//κλάση που φορτώνει τις διαθέσιμες πηγές ενέργειας από το catalog.json
public class UnitsLoader {
    public static Inventory loadUnits(String filePath) throws Exception{
        String content;
        try{
            //διαβάζει το περιεχόμενο του αρχείου και το κάνει string
            content = new String(Files.readAllBytes((Paths.get(filePath))));

        }
        catch(Exception e){
            throw new Exception("Units file not found");
        }
        JSONObject json;
        try{
            //κάνει το περιεχόμενο του αρχείου αντικείμενο json
            json = new JSONObject(content);
        }
        catch(JSONException e){
            throw new EOFException("Invalid units file");
        }

        try{
                    
            JSONArray itemsArray = json.getJSONArray("items");
            //φτιάχνει λίστα με τα αντικείμενα και τις ιδιοτητές τους
            ArrayList<Item>items = new ArrayList<Item>();
            
            for(int i = 0; i<itemsArray.length(); i++){
                JSONObject item = itemsArray.getJSONObject(i);
                String id = item.getString("id");
                String type = item.getString("type");
                double size = item.getDouble("size");
                double capex = item.getDouble("capex");
                int life = item.getInt("life");
                
                switch (type) {
                    case "WIND":
                        double cutin = item.getDouble("cutin");
                        double rated = item.getDouble("rated");
                        double cutout = item.getDouble("cutout");
                        items.add(new Wind(id, type, capex, size, life, cutin, rated, cutout));
                        break;

                    case "BATTERY":
                        double pmax = item.getDouble("pmax");
                        double eff = item.getDouble("eff");
                        items.add(new Battery(id, type, capex, size, life, pmax, eff));
                        break;

                    case "PV":
                        // Αν το PV δεν έχει έξτρα πεδία, ή αν έχει δική του υποκλάση PV:
                        items.add(new Item(id, type, capex, size, life));
                        break;

                    default:
                        items.add(new Item(id, type, capex, size, life));
                        break;
                }
            }
            
            
            return(new Inventory(items));
        }
        catch(JSONException e){
                throw new Exception("JSOn file error");
            }
    }
}

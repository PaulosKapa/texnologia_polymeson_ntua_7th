package actions;
import java.io.EOFException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import models.Island;
import models.Site;
import models.Unit;

//κλάση για να διαχειριστεί την φόρτωση του αρχείου island.json
public class Loader {
    public static Island loadMap(String filePath) throws Exception{
        String content;
        try{
            //βρίσκει το περιεχόμενο του αρχείου και το μετατρέπει σε string
            content = new String(Files.readAllBytes((Paths.get(filePath))));

        }
        catch(Exception e){
            throw new Exception("File not found");
        }
        JSONObject json;
        try{
            //φτιάχνει καιούριο json αντικείμενο από το περιεχόμενο του αρχείου
            json = new JSONObject(content);
        }
        catch(JSONException e){
            throw new EOFException("Invalid file");
        }

        try{
            //οι μεταβλητές για το νησί που περιέχει το αρχείο island.json
            String name = json.getString("name");
            int width = json.getInt("width");
            int height = json.getInt("height");
            double budget = json.getDouble("budget");

            //δημιουργούμε μια λίστα να περιέχει όλα τα σημεία του χάρτη, με την πληροφορία του εδάφους τους.
            JSONArray mapArray = json.getJSONArray("map");
            if(mapArray.length() != height){
                throw new EOFException("Number of line error");
            }

            ArrayList<String>mapLetters = new ArrayList<String>();
            for(int i = 0; i < mapArray.length(); i++){
                String l = mapArray.getString(i);
                if(l.length() != width ){
                    throw new Exception("Map error");
                }

                for(int j = 0; j< l.length(); j++){
                    mapLetters.add(String.valueOf(l.charAt(j)));
                }
            }
            
            //μία λίστα που περιέχει όλες τις ήδη εκατεστημένες περιοχές και τις πληροφοριές τους
            JSONArray units = json.getJSONArray("units");
            ArrayList<Unit> unitObjects = new ArrayList<Unit>();
            for(int i = 0; i<units.length(); i++){
                JSONObject unit = units.getJSONObject(i);
                String id = unit.getString("id");
                String type = unit.getString("type");
                int x = unit.getInt("x");
                int y = unit.getInt("y");
                unitObjects.add(new Unit(id, type, x, y));
            }
            //λίστα που περιέχει τα κομμάτια του νησιού που μπορούν να εγκαταστηθούν οι διαθέσιμες πηγές ενέργειας.
            JSONArray sites = json.getJSONArray("sites");
            ArrayList<Site> siteObjects = new ArrayList<Site>();
            for(int i = 0; i<sites.length(); i++){
                JSONObject site = sites.getJSONObject(i);
                String id = site.getString("id");
                int x = site.getInt("x");
                int y = site.getInt("y");
                //μια λίστα με το τι πηγή ενέργειας επιτρέπεται σε κάθε περιοχή
                JSONArray allowedArray = site.getJSONArray("allowed");
                ArrayList<String> allowedItems = new ArrayList<>();
                for(int j = 0; j<allowedArray.length(); j++){
                    allowedItems.add(allowedArray.getString(j));
                }
                siteObjects.add(new Site(id, x, y, allowedItems));
            }
            
            return(new Island(name, width, height, budget, mapLetters, unitObjects, siteObjects));
        }
        catch(JSONException e){
                throw new Exception("JSOn file error");
                
            }
    }
}

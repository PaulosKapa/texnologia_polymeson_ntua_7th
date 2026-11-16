import java.io.EOFException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import models.Island;

public class Loader {
    public static Island loadMap(String filePath) throws Exception{
        String content;
        try{
            content = new String(Files.readAllBytes((Paths.get(filePath))));

        }
        catch(Exception e){
            throw new Exception("File not found");
        }
        JSONObject json;
        try{
            json = new JSONObject(content);
        }
        catch(JSONException e){
            throw new EOFException("Invalid file");
        }

        try{
            String name = json.getString("name");
            int width = json.getInt("width");
            int height = json.getInt("height");
            double budget = json.getDouble("budget");

            Island island = new Island(name, width, height, budget, null, null, null);
            JSONArray mapArray = json.getJSONArray("map");
            if(mapArray.length() != height){
                throw new EOFException("Number of line error");
            }

            ArrayList<String>lines = new ArrayList();
            for(int i = 0; i < mapArray.length(); i++){
                String l = mapArray.getString(i);
                if(l.length() != width ){
                    throw new Exception("Map error");
                }
                lines.add(l);
            }
            //island.setMap(lines);
            JSONArray units = json.getJSONArray("units");
            for(int i = 0; i<units.length(); i++){
                JSONObject unit = units.getJSONObject(i);
                String id = unit.getString("id");
                String type = unit.getString("type");
                int x = unit.getInt("x");
                int y = unit.getInt("y");
            }

            JSONArray sites = json.getJSONArray("sites");
            for(int i = 0; i<sites.length(); i++){
                JSONObject site = sites.getJSONObject(i);
                String id = site.getString("id");
                int x = site.getInt("x");
                int y = site.getInt("y");
            }
            return(island);
        }
        catch(JSONException e){
                throw new Exception("JSOn file error");
            }
    }
}

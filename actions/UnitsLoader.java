package actions;
import java.io.EOFException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import models.Item;

public class UnitsLoader {
    public static Inventory loadUnits(String filePath) throws Exception{
        String content;
        try{
            content = new String(Files.readAllBytes((Paths.get(filePath))));

        }
        catch(Exception e){
            throw new Exception("Units file not found");
        }
        JSONObject json;
        try{
            json = new JSONObject(content);
        }
        catch(JSONException e){
            throw new EOFException("Invalid units file");
        }

        try{
                    
            JSONArray itemsArray = json.getJSONArray("items");
          
            ArrayList<Item>items = new ArrayList<Item>();
            
            for(int i = 0; i<itemsArray.length(); i++){
                JSONObject item = itemsArray.getJSONObject(i);
                String id = item.getString("id");
                String type = item.getString("type");
                double size = item.getInt("size");
                double capex = item.getInt("capex");
                int life = item.getInt("life");
                items.add(new Item(id, type, capex, size, life));
            }
            
            return(new Inventory(items));
        }
        catch(JSONException e){
                throw new Exception("JSOn file error");
            }
    }
}

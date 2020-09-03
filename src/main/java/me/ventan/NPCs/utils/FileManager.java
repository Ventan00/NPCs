package me.ventan.NPCs.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.ventan.NPCs.MainNPCs;

import java.io.*;

public class FileManager {
    private static FileManager instance = new FileManager();
    private static int ID=0;
    private FileManager(){ }
    public static void readDatabase(){
        File file = new File("plugins/NPCs/database.json");
        if(!file.exists())
        {
            MainNPCs.getInstance().getLogger().severe("brak database.json");
            MainNPCs.getInstance().getPluginLoader().disablePlugin(MainNPCs.getInstance());
            return;
        }
        if(file.length()==0) {
            return;
        }
        JsonParser parser = new JsonParser();
        JsonElement temp = null;
        JsonArray npcs = null;
        try {
            FileReader reader = new FileReader(new File("plugins/NPCs/database.json"));
            StringBuilder sb = new StringBuilder();
            int b;
            while((b=reader.read())!=-1)
            {
                sb.append((char)b);
            }
            reader.close();
            temp = parser.parse(sb.toString());
        }  catch (IOException e) {
            e.printStackTrace();
        }
        npcs= temp.getAsJsonArray();
        npcs.forEach(jsonElement -> {
            JsonObject me = (JsonObject) jsonElement;
            int id = me.get("id").getAsInt();
            String nazwa = me.get("nazwa").getAsString();
            String Skin=null;
            if(!me.get("skin").isJsonNull())
                Skin = me.get("skin").getAsString();
            String Color = null;
            if(!me.get("color").isJsonNull())
                Color = me.get("color").getAsString();
            String item =null;
            if(!me.get("item").isJsonNull())
                item = me.get("item").getAsString();
            String Type = me.get("typ").getAsString();
            JsonArray array = me.get("location").getAsJsonArray();
            String World = array.get(0).getAsString();
            double x = array.get(1).getAsDouble();
            double y = array.get(2).getAsDouble();
            double z = array.get(3).getAsDouble();
            float yaw = array.get(4).getAsFloat();
            float pitch = array.get(5).getAsFloat();

            new NPCProfile(id,nazwa,Skin,Color,item,Type,x,y,z,yaw,pitch,World);
        });
    }
    public static void saveProfile(NPCProfile profile){
        File file = new File("plugins/NPCs/database.json");
        JsonParser parser = new JsonParser();
        JsonElement temp = null;
        JsonArray npcs;
        if(file.length()!=0){
            try {
                temp = parser.parse(new FileReader(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            npcs= temp.getAsJsonArray();
        }
        else
            npcs= new JsonArray();

        npcs.add(profile.toJsonElement());
        file.delete();
        try {
            file.createNewFile();
            BufferedWriter bw =  new BufferedWriter(new FileWriter("plugins/NPCs/database.json"));
            bw.write(npcs.toString());
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        saveID();

    }
    public static void refreshProfile(NPCProfile profile){
        File file = new File("plugins/NPCs/database.json");
        if(!file.exists())
        {
            MainNPCs.getInstance().getLogger().severe("brak database.json");
            MainNPCs.getInstance().getPluginLoader().disablePlugin(MainNPCs.getInstance());
            return;
        }
        if(file.length()==0) {
            return;
        }
        JsonParser parser = new JsonParser();
        JsonElement temp = null;
        JsonArray npcs = null;
        try {
            FileReader reader = new FileReader(new File("plugins/NPCs/database.json"));
            StringBuilder sb = new StringBuilder();
            int b;
            while((b=reader.read())!=-1)
            {
                sb.append((char)b);
            }
            reader.close();
            temp = parser.parse(sb.toString());
        }  catch (IOException e) {
            e.printStackTrace();
        }
        npcs= temp.getAsJsonArray();
        JsonArray output =  new JsonArray();
        npcs.forEach(jsonElement -> {
            JsonObject me = (JsonObject) jsonElement;
            if(me.get("id").getAsInt()==profile.getID()){
                me=profile.toJsonElement().getAsJsonObject();
                output.add(me);
            }
            else
                output.add(me);
        });

        file.delete();
        try {
            file.createNewFile();
            BufferedWriter bw =  new BufferedWriter(new FileWriter("plugins/NPCs/database.json"));
            bw.write(output.toString());
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void readLastID(){
        File file = new File("plugins/NPCs/id.yml");
        if(!file.exists()) {
            MainNPCs.getInstance().getLogger().severe("brak pliku id.yml!");
            MainNPCs.getInstance().getPluginLoader().disablePlugin(MainNPCs.getInstance());
            return;
        }
        try {
            ID=Integer.parseInt(new BufferedReader(new FileReader(file)).readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void saveID(){
        File file = new File("plugins/NPCs/id.yml");
        if(file.exists())
            file.delete();
        try {
            file.createNewFile();
            BufferedWriter bw =  new BufferedWriter(new FileWriter("plugins/NPCs/id.yml"));
            bw.write(String.valueOf(ID));
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static int getID(){
        return ID;
    }
    public static void incrementID(){
        ID++;
    }
    public static FileManager getInstance(){
        return instance;
    }

    public static void removeProfile(NPCProfile profile) {
        File file = new File("plugins/NPCs/database.json");
        if(!file.exists())
        {
            MainNPCs.getInstance().getLogger().severe("brak database.json");
            MainNPCs.getInstance().getPluginLoader().disablePlugin(MainNPCs.getInstance());
            return;
        }
        if(file.length()==0) {
            return;
        }
        JsonParser parser = new JsonParser();
        JsonElement temp = null;
        JsonArray npcs = null;
        try {
            FileReader reader = new FileReader(new File("plugins/NPCs/database.json"));
            StringBuilder sb = new StringBuilder();
            int b;
            while((b=reader.read())!=-1)
            {
                sb.append((char)b);
            }
            reader.close();
            temp = parser.parse(sb.toString());
        }  catch (IOException e) {
            e.printStackTrace();
        }
        npcs= temp.getAsJsonArray();
        JsonArray output =  new JsonArray();
        npcs.forEach(jsonElement -> {
            JsonObject me = (JsonObject) jsonElement;
            if(me.get("id").getAsInt()!=profile.getID()){
                output.add(me);
            }
        });

        file.delete();
        try {
            file.createNewFile();
            BufferedWriter bw =  new BufferedWriter(new FileWriter("plugins/NPCs/database.json"));
            bw.write(output.toString());
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

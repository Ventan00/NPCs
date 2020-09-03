package me.ventan.NPCs.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.ventan.NPCs.MainNPCs;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.UUID;

public class MineToolsAPIWrapper {
    private static MineToolsAPIWrapper instance = new MineToolsAPIWrapper();
    private MineToolsAPIWrapper(){}
    public static String getSkin(UUID UUID) throws Exception{
        URL url = new URL("https://api.minetools.eu/profile/"+UUID.toString());
        BufferedReader read = new BufferedReader(new InputStreamReader(url.openStream()));
        JsonParser jsonParser = new JsonParser();
        JsonObject Profile = (JsonObject) jsonParser.parse(read);
        return Profile.get("raw").getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject().get("value").getAsString();
    }
    public static String getSignature(UUID UUID) throws Exception {
        URL url = new URL("https://api.minetools.eu/profile/"+UUID.toString());
        BufferedReader read = new BufferedReader(new InputStreamReader(url.openStream()));
        JsonParser jsonParser = new JsonParser();
        JsonObject Profile = (JsonObject) jsonParser.parse(read);
        return Profile.get("raw").getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject().get("signature").getAsString();
    }

    public static UUID getUUID(String nick) throws Exception {
        URL url = new URL("https://api.minetools.eu/uuid/"+nick);
        BufferedReader read = new BufferedReader(new InputStreamReader(url.openStream()));
        JsonParser jsonParser = new JsonParser();
        JsonObject parsed = (JsonObject) jsonParser.parse(read);
        return UUID.fromString(parsed.get("id").getAsString().replaceAll("(.{8})(.{4})(.{4})(.{4})(.+)", "$1-$2-$3-$4-$5"));
    }


    public static MineToolsAPIWrapper getInstance(){
        return instance;
    }
}

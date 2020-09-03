package me.ventan.NPCs;

import me.ventan.NPCs.commands.CommandNPC;
import me.ventan.NPCs.commands.TabCompleterSpawnNPC;
import me.ventan.NPCs.events.BurnEvent;
import me.ventan.NPCs.utils.FileManager;
import me.ventan.NPCs.utils.NPCProfile;
import me.ventan.NPCs.utils.NPCs.*;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class MainNPCs extends JavaPlugin {
    private static MainNPCs instance;
    private HashMap<Integer, NPCProfile> npcs;

    @Override
    public void onEnable(){
        /*custom entities*/
        EntityTypes.b.a(54,new MinecraftKey("MyNpcZombie"), NPCZombie.class);
        EntityTypes.b.a(61,new MinecraftKey("MyNpcBlaze"), NPCBlaze.class);
        EntityTypes.b.a(120,new MinecraftKey("MyNpcVillager"), NPCVillager.class);
        EntityTypes.b.a(98,new MinecraftKey("MyNpcKot"), NPCKot.class);

        File myLocation = new File("plugins/NPCs");
        File database = new File("plugins/NPCs/database.json");
        File saveID = new File("plugins/NPCs/id.yml");
        if(!myLocation.exists()){
            myLocation.mkdir();
        }
        if(!database.exists()){
            try {
                database.createNewFile();
            } catch (IOException e) {
                getLogger().severe(ChatColor.RED+"Nie da się stworzyć pliku database.json! Upewnij się, że plugin ma prawa do zapisu/odczytu");
                getServer().getPluginManager().disablePlugin(this);
            }
        }
        if(!saveID.exists()){
            try {
                saveID.createNewFile();
                FileManager.saveID();
            } catch (IOException e) {
                getLogger().severe(ChatColor.RED+"Nie da się stworzyć pliku id.yml! Upewnij się, że plugin ma prawa do zapisu/odczytu");
                getServer().getPluginManager().disablePlugin(this);
            }
        }
        instance=this;
        npcs = new HashMap<>();


        /*commands*/
        getCommand("npc").setExecutor(new CommandNPC());
        getCommand("npc").setTabCompleter(new TabCompleterSpawnNPC());

        /*events*/
        getServer().getPluginManager().registerEvents(new BurnEvent(), this);

        FileManager.readDatabase();
        FileManager.readLastID();
    }

    @Override
    public void onDisable(){
        getLogger().info("Rozpoczynam wyłączanie pluginu");
        cleanMap();
    }

    public static MainNPCs getInstance(){
        return instance;
    }
    public void addProfile(int Key,NPCProfile profile){
        npcs.put(Key,profile);
    }
    private void cleanMap(){
        if(npcs==null)
            return;
        npcs.values().forEach(npcProfile -> npcProfile.destroy());
        npcs.clear();
    }
    public Set<Integer> getNPCs(){
        return npcs.keySet();
    }
    public NPCProfile getProfile(int ID){
        if(npcs.containsKey(ID))
            return npcs.get(ID);
        else
            return null;
    }
    public void removeNPC(int ID){
        npcs.remove(ID);
    }
    public Collection<NPCProfile> getProfiles(){
        return npcs.values();
    }

}

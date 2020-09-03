package me.ventan.NPCs.API;

import me.ventan.NPCs.MainNPCs;
import me.ventan.NPCs.utils.FileManager;
import me.ventan.NPCs.utils.NPCProfile;
import org.bukkit.Location;

public class NPCsAPI {
    //return npc profile (zombie)
    public static NPCProfile getNPC(String name, String Skin, String color , String item, Location loc){
        return new NPCProfile(name,Skin,color,item,loc);
    }

    //return npc profile (other)
    public static NPCProfile getNPC(String Type, String name, Location loc){
        return new NPCProfile(Type, name, loc);
    }

    //return NPC of ID
    public static NPCProfile getFromID(int ID){
        return MainNPCs.getInstance().getProfile(ID);
    }

    public static void removeNPC(int ID){
        MainNPCs.getInstance().removeNPC(ID);
    }

    //teleport npc to new location
    public static void teleport(NPCProfile profile, Location loc){
        profile.getEntity().getBukkitEntity().teleport(loc);
        FileManager.refreshProfile(profile);
    }
}

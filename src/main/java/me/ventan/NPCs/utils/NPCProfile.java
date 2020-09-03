package me.ventan.NPCs.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.ventan.NPCs.MainNPCs;
import me.ventan.NPCs.utils.NPCs.*;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.entity.Zombie;

import static me.ventan.NPCs.utils.NPCType.*;

public class NPCProfile {
    final  int ID;
    private String name;
    private String Skin;
    private String Color;
    private Entity npc;
    private String item;
    private NPCType type;

    public NPCProfile(String name,String Skin, String color , String item, Location loc){
        this.ID= FileManager.getID();
        FileManager.incrementID();
        this.name = name;
        try {
            this.Skin=MineToolsAPIWrapper.getSkin(MineToolsAPIWrapper.getUUID(Skin));
        } catch (Exception e) {
            e.printStackTrace();
            this.Skin = null;
        }
        npc = new NPCZombie(((CraftWorld) loc.getWorld()).getHandle(),ID);
        type=ZOMBIE;
        npc.setCustomName(parseSpace(parseSpace(parseColors(name))));
        npc.setCustomNameVisible(true);
        npc.setInvulnerable(true);
        npc.setSilent(true);
        npc.setPosition(loc.getX(), loc.getY(), loc.getZ());

        Zombie zombie = (Zombie) npc.getBukkitEntity();
        zombie.getEquipment().setHelmet(CustomEqCreator.getInstance().getSkull(Skin));
        if(color!=null){
            this.Color=color;
            zombie.getEquipment().setChestplate(CustomEqCreator.getInstance().getChest(color));
            zombie.getEquipment().setLeggings(CustomEqCreator.getInstance().getLeggins(color));
            zombie.getEquipment().setBoots(CustomEqCreator.getInstance().getBoots(color));

        }
        if(item!=null){
                zombie.getEquipment().setItemInMainHand(CustomEqCreator.getInstance().getItem(item));
            this.item=item;
        }

        ((CraftWorld) loc.getWorld()).getHandle().addEntity(npc);

        MainNPCs.getInstance().addProfile(ID,this);
        FileManager.saveProfile(this);
    }

    public NPCProfile(String Type, String name, Location loc){
        this.ID= FileManager.getID();
        FileManager.incrementID();
        this.name = name;
        Skin=null;
        Color=null;

        //add switch on type
        switch(Type.toLowerCase()){
            case "villager":
                npc = new NPCVillager(((CraftWorld) loc.getWorld()).getHandle(),ID);
                type=VILLAGER;
                break;
            case "kot":
                npc = new NPCKot(((CraftWorld) loc.getWorld()).getHandle(),ID);
                type=KOT;
                break;
            case "blaze":
                npc = new NPCBlaze(((CraftWorld) loc.getWorld()).getHandle(),ID);
                type=BLAZE;
                break;
        }

        npc.setCustomName(parseSpace(parseColors(name)));
        npc.setCustomNameVisible(true);
        npc.setInvulnerable(true);
        npc.setSilent(true);
        npc.setPosition(loc.getX(), loc.getY(), loc.getZ());
        ((CraftWorld) loc.getWorld()).getHandle().addEntity(npc);


        MainNPCs.getInstance().addProfile(ID,this);
        FileManager.saveProfile(this);

    }

    //loading const
    public NPCProfile(int ID, String name, String skin, String Color, String item, String Type, double x, double y, double z, float yaw, float pitch, String WorldName){
        Location loc = new Location(MainNPCs.getInstance().getServer().getWorld(WorldName),x,y,z,yaw,pitch);
        this.ID = ID;
        this.name=name;
        this.Skin=skin;
        this.Color=Color;
        this.item=item;
        switch (Type){
            case "ZOMBIE":
                npc = new NPCZombie(((CraftWorld) loc.getWorld()).getHandle(),ID);
                type=ZOMBIE;
                Zombie zombie = (Zombie) npc.getBukkitEntity();
                zombie.getEquipment().setHelmet(skullCreator.getSkull(Skin));
                if(Color!=null){
                    this.Color=Color;
                    zombie.getEquipment().setChestplate(CustomEqCreator.getInstance().getChest(Color));
                    zombie.getEquipment().setLeggings(CustomEqCreator.getInstance().getLeggins(Color));
                    zombie.getEquipment().setBoots(CustomEqCreator.getInstance().getBoots(Color));
                }
                if(item!=null){
                    zombie.getEquipment().setItemInMainHand(CustomEqCreator.getInstance().getItem(item));
                    this.item=item;
                }
                break;
            case "VILLAGER":
                npc = new NPCVillager(((CraftWorld) loc.getWorld()).getHandle(),ID);
                type=VILLAGER;
                break;
            case "KOT":
                npc = new NPCKot(((CraftWorld) loc.getWorld()).getHandle(),ID);
                type=KOT;
                break;
            case "BLAZE":
                npc = new NPCBlaze(((CraftWorld) loc.getWorld()).getHandle(),ID);
                type=BLAZE;
                break;
        }
        npc.setCustomName(parseSpace(parseColors(name)));
        npc.setCustomNameVisible(true);
        npc.setInvulnerable(true);
        npc.setSilent(true);
        npc.setPosition(loc.getX(), loc.getY(), loc.getZ());
        ((CraftWorld) loc.getWorld()).getHandle().addEntity(npc);
        MainNPCs.getInstance().addProfile(ID,this);
    }

    public JsonElement toJsonElement(){
        JsonObject Object = new JsonObject();
        Object.addProperty("id",ID);
        Object.addProperty("nazwa",name);
        Object.addProperty("skin",Skin);
        Object.addProperty("item",item);
        Object.addProperty("color",Color);
        Object.addProperty("typ",type.toString());

        JsonArray loc = new JsonArray();
        Location params = npc.getBukkitEntity().getLocation();
        loc.add(params.getWorld().getName());
        loc.add(params.getX());
        loc.add(params.getY());
        loc.add(params.getZ());
        loc.add(params.getYaw());
        loc.add(params.getPitch());
        Object.add("location", loc );
        return Object;

    }
    public String toColoredString(){
        StringBuilder sb = new StringBuilder();
        sb.append(ChatColor.AQUA+"===============================\n");
        sb.append(ChatColor.AQUA+"ID: " +ChatColor.YELLOW+ ID +"\n");
        sb.append(ChatColor.AQUA+"Nazwa: "+parseColors(name) +"\n");
        sb.append(ChatColor.AQUA+"Typ: "+ChatColor.YELLOW + type.toString() +"\n");
        sb.append(ChatColor.AQUA+"Lokalizacja: \n");
        sb.append(ChatColor.AQUA+"world: "+ChatColor.YELLOW+npc.getWorld().getWorldData().getName()+ ChatColor.AQUA+" x: "+ChatColor.YELLOW+npc.locX + ChatColor.AQUA+" y: "+ChatColor.YELLOW+npc.locY+ ChatColor.AQUA+" z: "+ChatColor.YELLOW+npc.locZ);
        sb.append(ChatColor.AQUA+"===============================\n");
        return sb.toString();
    }
    public int getID(){
        return ID;
    }
    public Entity getEntity(){return npc; }


    private String parseColors(String input){
        StringBuilder output = new StringBuilder();
        for(int i=0; i<input.length();i++){
            char c = input.charAt(i);
            if (c == '&') {
                output.append('§');
            } else {
                output.append(c);
            }
        }
        return output.toString();
    }
    private String parseSpace(String input) {
        StringBuilder output = new StringBuilder();
        for(int i=0; i<input.length();i++){
            char c = input.charAt(i);
            if (c == '_') {
                output.append(' ');
            } else {
                output.append(c);
            }
        }
        return output.toString();
    }
    public void destroy(){
        npc.getBukkitEntity().remove();
    }
    public NPCType getType(){
        return this.type;
    }
}

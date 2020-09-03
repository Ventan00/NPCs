package me.ventan.NPCs.utils;

import me.ventan.NPCs.MainNPCs;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class CustomEqCreator {
    private static CustomEqCreator instance = new CustomEqCreator();
    public static CustomEqCreator getInstance(){
        return instance;
    }
    private CustomEqCreator(){}

    public ItemStack getHead(String color){
        org.bukkit.inventory.ItemStack item =  new org.bukkit.inventory.ItemStack(Material.LEATHER_HELMET,1);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        meta.setColor(Color.fromRGB(getRed(color),getGreen(color),getBule(color)));
        item.setItemMeta(meta);
        return item;
    }
    public ItemStack getSkull(String nick){
        try {
            return skullCreator.getSkull(MineToolsAPIWrapper.getSkin(MineToolsAPIWrapper.getUUID(nick)));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public ItemStack getChest(String color){
        org.bukkit.inventory.ItemStack item =  new org.bukkit.inventory.ItemStack(Material.LEATHER_CHESTPLATE,1);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        meta.setColor(Color.fromRGB(getRed(color),getGreen(color),getBule(color)));
        item.setItemMeta(meta);
        return item;
    }
    public ItemStack getLeggins(String color){
        org.bukkit.inventory.ItemStack item =  new org.bukkit.inventory.ItemStack(Material.LEATHER_LEGGINGS,1);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        meta.setColor(Color.fromRGB(getRed(color),getGreen(color),getBule(color)));
        item.setItemMeta(meta);
        return item;
    }
    public ItemStack getBoots(String color){
        org.bukkit.inventory.ItemStack item =  new org.bukkit.inventory.ItemStack(Material.LEATHER_BOOTS,1);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        meta.setColor(Color.fromRGB(getRed(color),getGreen(color),getBule(color)));
        item.setItemMeta(meta);
        return item;
    }
    public ItemStack getItem(String id){
        return new ItemStack(Integer.valueOf(id),1);
    }
    private int getRed(String Hex){
        return Integer.parseInt(Hex.substring(0,2),16);
    }
    private int getGreen(String Hex){
        return Integer.parseInt(Hex.substring(2,4),16);
    }
    private int getBule(String Hex){
        return Integer.parseInt(Hex.substring(4,6),16);
    }


}

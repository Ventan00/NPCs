package me.ventan.NPCs.commands;

import me.ventan.NPCs.MainNPCs;
import me.ventan.NPCs.utils.FileManager;
import me.ventan.NPCs.utils.NPCProfile;
import me.ventan.NPCs.utils.NPCs.NPCsNPC;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static me.ventan.NPCs.commands.CommandNPC.ValidationType.*;

public class CommandNPC implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(commandSender instanceof Player){
            Player player = (Player) commandSender;
            if(args.length>0){
                switch (args[0].toLowerCase()){
                    case "spawn":{
                        if(args.length>2) {
                            if(args.length>3 && args[1].equalsIgnoreCase("zombie")) {
                                if(args.length>4) {
                                    if (args.length > 5 && validateData(Arrays.copyOfRange(args, 4,6),IS_HEX, IS_ID_ITEMU)) {
                                        new NPCProfile(args[2], args[3], args[4], args[5], player.getLocation());
                                        return true;
                                    }
                                    else if(validateData(Arrays.copyOfRange(args, 4,5),IS_HEX))
                                        new NPCProfile( args[2], args[3], args[4], null, player.getLocation());
                                    else
                                        player.sendMessage(ChatColor.RED+"Poprawne przykłady kolorów: FF00FF, 123456, 000000 (bez znaku '#')");
                                }
                                else {
                                    new NPCProfile(args[2],args[3],null,null,player.getLocation());
                                }
                            }
                            else if(args[1].equalsIgnoreCase("blaze")||args[1].equalsIgnoreCase("villager")||args[1].equalsIgnoreCase("kot"))
                                new NPCProfile(args[1],args[2],player.getLocation());
                            else if(args[1].equalsIgnoreCase("zombie"))
                                commandSender.sendMessage(ChatColor.RED+"Poprawne użycie /npc spawn zombie <nazwa> <skin> [kolor hex bez #] [ID itemu]");
                            else
                                commandSender.sendMessage(ChatColor.RED+"Typy mobów to: Zombie, Blaze, Villager, Kot");
                        }
                        else {
                            commandSender.sendMessage(ChatColor.RED+"Poprawne użycie: /npc spawn <typ> <nazwa>");
                            return false;
                        }
                        break;
                    }
                    case "remove":{
                        if(args.length>1 && validateData(Arrays.copyOfRange(args, 1,2),IS_ID_NPC)){
                            manageRemove(Integer.parseInt(args[1]));
                        }
                        else {
                            player.sendMessage(ChatColor.RED+"poprane użycie: /npc remove <id>");
                        }
                        break;
                    }
                    case "info":{
                        if(args.length>1 && validateData(Arrays.copyOfRange(args, 1,2),IS_ID_NPC)){
                            manageInfo(player,args[1]);
                        }
                        else{
                            manageInfo(player,null);
                        }
                        break;
                    }
                    case "tp":{
                        if(args.length>2 && validateData(Arrays.copyOfRange(args, 1,3),IS_ID_NPC,IS_ONLINE))
                            manageTP(Integer.valueOf(args[1]),MainNPCs.getInstance().getServer().getPlayer(args[2]), true);
                        else if(args.length>1 && validateData(Arrays.copyOfRange(args, 1,2),IS_ID_NPC)){
                            manageTP(Integer.valueOf(args[1]),player,false);
                        }
                        else {
                            player.sendMessage(ChatColor.RED+"poprawne użycie: /npc tp <ID> [gracz]");
                        }

                        break;
                    }
                    case "help":{
                        commandSender.sendMessage(getSomeHelp());
                        break;
                    }
                    default:{
                        commandSender.sendMessage(ChatColor.RED+"Poprawne użycie:");
                        commandSender.sendMessage(ChatColor.RED+"/npc spawn:");
                        commandSender.sendMessage(ChatColor.RED+"/npc info:");
                        commandSender.sendMessage(ChatColor.RED+"/npc tp:");
                        commandSender.sendMessage(ChatColor.RED+"/npc remove:");
                        commandSender.sendMessage(ChatColor.RED+"/npc help:");
                        return false;
                    }
                }
            }else{
                commandSender.sendMessage(ChatColor.RED+"Poprawne użycie:");
                commandSender.sendMessage(ChatColor.RED+"/npc spawn:");
                commandSender.sendMessage(ChatColor.RED+"/npc info:");
                commandSender.sendMessage(ChatColor.RED+"/npc tp:");
                commandSender.sendMessage(ChatColor.RED+"/npc remove:");
                commandSender.sendMessage(ChatColor.RED+"/npc help:");
            }
        }
        else{
            commandSender.sendMessage(ChatColor.RED+"Konsola nie może zespawnować NPC!");
        }
        return false;
    }

    private void manageRemove(int ID) {
        MainNPCs.getInstance().getProfile(ID).destroy();
        FileManager.removeProfile(MainNPCs.getInstance().getProfile(ID));
        MainNPCs.getInstance().removeNPC(ID);
    }

    private void manageTP(int ID, Player player, boolean side) {
        if(side){
            MainNPCs.getInstance().getProfile(ID).getEntity().getBukkitEntity().teleport(player);
            FileManager.refreshProfile(MainNPCs.getInstance().getProfile(ID));
        }else {
            player.teleport(MainNPCs.getInstance().getProfile(ID).getEntity().getBukkitEntity());
        }
    }

    private void manageInfo(Player player, String arg) {
        if(arg!=null){
            String string = MainNPCs.getInstance().getProfile(Integer.parseInt(arg)).toColoredString();
            player.sendMessage(string);
        }
        else{
            List<Entity> entities = player.getNearbyEntities(10,10,10);
            for(Entity entity: entities){
                if(((CraftEntity)entity).getHandle() instanceof NPCsNPC){
                    int x = entity.getLocation().getBlockX();
                    int z = entity.getLocation().getBlockZ();
                    List<Block> blocks = player.getLineOfSight(null, 10);
                    for(Block block: blocks){
                        if(block.getX()==x && block.getZ()==z){
                            player.sendMessage(MainNPCs.getInstance().getProfile(((NPCsNPC) ((CraftEntity) entity).getHandle()).getNPCID()).toColoredString());
                            return;
                        }
                    }
                }
            }
        }
    }

    private boolean validateData(String[] args , ValidationType... valType){
        int i=0;
        for(ValidationType type: valType){
            switch (type){
                case IS_HEX:{
                    Pattern pattern = Pattern.compile("[a-fA-F0-9]{6}");
                    Matcher matcher = pattern.matcher(args[i]);
                    if(!matcher.matches()){
                        return false;
                    }
                    break;
                }
                case IS_ID_NPC:{
                    if(MainNPCs.getInstance().getProfile(Integer.parseInt(args[i]))==null)
                        return false;
                    break;
                }
                case IS_ONLINE:{
                    if (MainNPCs.getInstance().getServer().getOfflinePlayer(args[i])==null)
                        return false;
                    break;
                }
                case IS_ID_ITEMU:{
                    if(new ItemStack(Integer.valueOf(args[i]),1)==null)
                        return false;
                    break;
                }
            }
            i++;

        }
        return true;
    }

    private String getSomeHelp(){
        StringBuilder output= new StringBuilder();
        output.append(ChatColor.AQUA+"NPCs help Strona 1/1\n");
        output.append(ChatColor.AQUA+"/npc spawn - spawnuje NPCTa\n");
        output.append(ChatColor.AQUA+"/npc info - pokazuje info na temat NPCta w zasięgu wzroku lub o podanym ID\n");
        output.append(ChatColor.AQUA+"/npc remove - usuwa NPCta o danym ID\n");
        output.append(ChatColor.AQUA+"/npc spawn - teleportuje gracza do NPC o danym ID (lub w drugą stronę jeśli podany jest nick)\n");
        output.append(ChatColor.AQUA+"/npc help - pokazuje poradnik do NPC\n");
        return output.toString();
    }

    public enum ValidationType{
        IS_HEX, IS_ONLINE, IS_ID_NPC, IS_ID_ITEMU
    }
}
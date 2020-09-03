package me.ventan.NPCs.commands;

import me.ventan.NPCs.MainNPCs;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

import static me.ventan.NPCs.utils.NPCType.*;

public class TabCompleterSpawnNPC implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        List<String> podpowiedzi = null;
        if(args.length==1){
            podpowiedzi = new ArrayList<>();
            podpowiedzi.add("spawn");
            podpowiedzi.add("usun");
            podpowiedzi.add("info");
            podpowiedzi.add("tp");
            podpowiedzi.add("help");
        }
        if(args.length==2 && args[0].equalsIgnoreCase("spawn")){
            podpowiedzi = new ArrayList<>();
            podpowiedzi.add(ZOMBIE.toString());
            podpowiedzi.add(VILLAGER.toString());
            podpowiedzi.add(BLAZE.toString());
            podpowiedzi.add(KOT.toString());
        }
        if(args.length==2 && args[0].equalsIgnoreCase("usun")){
            podpowiedzi = new ArrayList<>();
            List<String> finalPodpowiedzi = podpowiedzi;
            MainNPCs.getInstance().getNPCs().forEach(integer -> finalPodpowiedzi.add(String.valueOf(integer)));
            podpowiedzi=finalPodpowiedzi;
        }
        return podpowiedzi;
    }
}

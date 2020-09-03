package me.ventan.NPCs.events;

import me.ventan.NPCs.utils.NPCs.NPCsNPC;
import net.minecraft.server.v1_12_R1.EntityHuman;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.EntityTypes;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;

public class BurnEvent implements Listener {
    @EventHandler
    public void onDamage(EntityCombustEvent event){
        if(((CraftEntity)event.getEntity()).getHandle() instanceof NPCsNPC){
            /*String entityType = EntityTypes.a(((CraftEntity)event.getEntity()).getHandle()).toString();
            if(entityType.equalsIgnoreCase("minecraft:MyNpcZombie") ||
                    entityType.equalsIgnoreCase("minecraft:MyNpcBlaze") ||
                    entityType.equalsIgnoreCase("minecraft:MyNpcVillager") ||
                    entityType.equalsIgnoreCase("minecraft:MyNpcKot")
                    )*/
            {
                event.setCancelled(true);
            }
        }
    }
}

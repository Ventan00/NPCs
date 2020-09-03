package me.ventan.NPCs.utils.NPCs;

import net.minecraft.server.v1_12_R1.EntityHuman;
import net.minecraft.server.v1_12_R1.EntityOcelot;
import net.minecraft.server.v1_12_R1.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_12_R1.World;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Zombie;

public class NPCKot extends EntityOcelot implements NPCsNPC {
    public int ID;
    public NPCKot(World world, int ID) {
        super(world);
        this.ID=ID;
        Ocelot ocelot = (Ocelot) this.getBukkitEntity();
        ocelot.setRemoveWhenFarAway(false);
    }
    @Override
    protected void r(){
        this.goalSelector.a(0, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 10.0f));
    }
    @Override
    public void a(float f, float f1, float f2) {
        return;
    }

    @Override
    public int getNPCID() {
        return ID;
    }
}

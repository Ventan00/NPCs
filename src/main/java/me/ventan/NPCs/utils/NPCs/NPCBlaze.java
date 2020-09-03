package me.ventan.NPCs.utils.NPCs;

import net.minecraft.server.v1_12_R1.EntityBlaze;
import net.minecraft.server.v1_12_R1.EntityHuman;
import net.minecraft.server.v1_12_R1.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_12_R1.World;
import org.bukkit.entity.Blaze;

public class NPCBlaze extends EntityBlaze implements NPCsNPC {
    public int ID;
    public NPCBlaze(World world, int ID) {
        super(world);
        this.ID=ID;
        Blaze blaze = (Blaze) this.getBukkitEntity();
        blaze.setRemoveWhenFarAway(false);
        blaze.setSilent(true);
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

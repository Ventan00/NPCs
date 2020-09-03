package me.ventan.NPCs.utils.NPCs;

import me.ventan.NPCs.MainNPCs;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.entity.Zombie;

public class NPCZombie extends EntityZombie implements NPCsNPC {
    public int ID;
    public NPCZombie(World world, int ID) {
        super(world);
        this.ID = ID;
        Zombie zombie = (Zombie) this.getBukkitEntity();
        zombie.setRemoveWhenFarAway(false);
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

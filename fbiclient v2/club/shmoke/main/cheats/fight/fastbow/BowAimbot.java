package club.shmoke.main.cheats.fight.fastbow;

import club.shmoke.api.utility.Utility;
import net.minecraft.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class BowAimbot extends Utility {

    private Entity current;

    public void aim() {
        List<Object> objects = new ArrayList<>(mc.theWorld.loadedEntityList);

        if (objects.size() == 0) return;

        current = (Entity) objects.get(0);

        if (mc.thePlayer.getDistanceToEntity(current) > 10 || current == mc.thePlayer) {
            int next = objects.indexOf(current) + 1;
            current = objects.size() > next ? (Entity) objects.get(next) : (Entity) objects.get(0);
        }

        float[] rots = rotationUtility.getRotations(current);

        double x = current.posX - current.prevPosX, z = current.posZ - current.prevPosZ;

        boolean isXPos = x >= 0;

        mc.thePlayer.rotationYaw = rots[0] + (float) ((isXPos ? 2.5 : -2.5) * mc.thePlayer.getDistanceToEntity(current) * 0.04);
        mc.thePlayer.rotationPitch = rots[1] - (mc.thePlayer.getDistanceToEntity(current) / 5);
    }

}

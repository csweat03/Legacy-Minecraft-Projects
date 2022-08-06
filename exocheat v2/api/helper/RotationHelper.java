package club.shmoke.main.api.helper;

import org.bukkit.entity.Player;

public class RotationHelper {

    private Player me;

    public RotationHelper(Player player) {
        me = player;
    }

    public float getYaw(Player player) {
        double x = player.getLocation().getX() - me.getLocation().getX();
        double z = player.getLocation().getZ() - me.getLocation().getZ();
        double deg;

        if (z < 0.0D && x < 0.0D) {
            deg = 90.0D + java.lang.Math.toDegrees(java.lang.Math.atan(z / x));
        } else if (z < 0.0D && x > 0.0D) {
            deg = -90.0D + java.lang.Math.toDegrees(java.lang.Math.atan(z / x));
        } else {
            deg = java.lang.Math.toDegrees(-java.lang.Math.atan(x / z));
        }

        return (float) Math.wrapAngle(-(me.getLocation().getYaw() - (float) deg));
    }

    public float getPitch(Player player) {
        double var1 = player.getLocation().getX() - me.getLocation().getX();
        double var3 = player.getLocation().getZ() - me.getLocation().getZ();
        double var5 = player.getLocation().getY() - 1.6D + player.getEyeHeight() - me.getLocation().getY();
        double var7 = Math.sqrt(var1 * var1 + var3 * var3);
        double var9 = -java.lang.Math.toDegrees(java.lang.Math.atan(var5 / var7));
        return (float) -Math.wrapAngle(me.getLocation().getPitch() - (float) var9);
    }

}

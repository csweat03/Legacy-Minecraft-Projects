package club.shmoke.main.cheats.user.scaffold;

import club.shmoke.api.utility.Utility;
import club.shmoke.api.utility.utilities.Timer;
import club.shmoke.main.cheats.user.Scaffold;
import club.shmoke.main.events.UpdateEvent;
import me.tojatta.api.utilities.angle.Angle;
import me.tojatta.api.utilities.angle.AngleUtility;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;

import java.util.Random;

/**
 * @author Christian
 */
public class AAC {

    private Minecraft mc = Scaffold.mc;
    private Utility utility = new Utility();

    private Timer timer = new Timer();

    public void onEnable() {
        timer.reset();
    }

    public void onDisable() {
        mc.rightClickDelayTimer = 6;
    }

    public void onUpdate(UpdateEvent event) {
        mc.gameSettings.keyBindSneak.pressed = mc.gameSettings.keyBindSprint.pressed = false;
        changeRotations(event);
        mc.rightClickDelayTimer = 4;
        if (mc.thePlayer.isMoving())
            mc.thePlayer.setSpeed(mc.thePlayer.getSpeed() + (mc.thePlayer.ticksExisted % 2 == 0 ? 0.0101 : 0.000525));
        if (Scaffold.isAirBorne() && Scaffold.canPlace())
            placeBlock();
    }

    public void placeBlock() {
        BlockPos pos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ);
        EnumFacing face = EnumFacing.getHorizontal(MathHelper.floor_double(mc.thePlayer.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3);

        if (face.equals(EnumFacing.EAST))
            pos = pos.add(-1, 0, 0);
        else if (face.equals(EnumFacing.SOUTH))
            pos = pos.add(0, 0, -1);
        else if (face.equals(EnumFacing.WEST))
            pos = pos.add(1, 0, 0);
        else if (face.equals(EnumFacing.NORTH))
            pos = pos.add(0, 0, 1);

        if (utility.blockUtility.canBeClicked(pos))
            Scaffold.place(pos, face);

    }

    public void changeRotations(UpdateEvent event) {
        float[] rot = getAngles();
        event.setYaw(rot[0]);
        event.setPitch(rot[1]);
    }

    public float[] getAngles() {
        EnumFacing f = mc.thePlayer.getHorizontalFacing();
        float y = mc.gameSettings.keyBindJump.pressed ? 0 : f == EnumFacing.EAST ? 90 : f == EnumFacing.SOUTH ? 180 : f == EnumFacing.WEST ? -90 : f == EnumFacing.NORTH ? 0 : 0;
        float p = 83;
        y -= 2;
        y += new Random().nextInt(4);
        return new float[]{y, p};
    }

}

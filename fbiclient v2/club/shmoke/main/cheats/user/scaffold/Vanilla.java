package club.shmoke.main.cheats.user.scaffold;

import club.shmoke.main.cheats.user.Scaffold;
import club.shmoke.main.events.UpdateEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;

import java.util.Random;

/**
 * @author Christian
 */
public class Vanilla {

    private Minecraft mc = Scaffold.mc;

    public void onEnable() {
    }

    public void onDisable() {
        mc.rightClickDelayTimer = 6;
    }

    public void onUpdate(UpdateEvent event) {
        if (mc.gameSettings.keyBindSneak.pressed) return;

        mc.rightClickDelayTimer = 0;

        changeRotations(event);

        if (Scaffold.canPlace())
            placeBlock();
    }

    public void placeBlock() {
        BlockPos pos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ);
        EnumFacing face = EnumFacing.getHorizontal(MathHelper.floor_double(mc.thePlayer.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3);

        for (int i = 0; i < 3; i++) {
            if (face.equals(EnumFacing.EAST))
                pos = pos.add(-i, 0, 0);
            else if (face.equals(EnumFacing.SOUTH))
                pos = pos.add(0, 0, -i);
            else if (face.equals(EnumFacing.WEST))
                pos = pos.add(i, 0, 0);
            else if (face.equals(EnumFacing.NORTH))
                pos = pos.add(0, 0, i);
            Scaffold.place(pos, face);
        }
    }

    public void changeRotations(UpdateEvent event) {
        if (event.getType() != UpdateEvent.Type.PRE) return;

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

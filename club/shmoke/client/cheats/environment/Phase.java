package club.shmoke.client.cheats.environment;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockHopper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovementInput;
import club.shmoke.client.Client;
import club.shmoke.api.anticheat.Anticheat;
import club.shmoke.api.cheat.Cheat;
import club.shmoke.api.cheat.helpers.interfaces.IHelper;
import club.shmoke.api.event.Event;
import club.shmoke.client.events.block.BoundingBoxEvent;
import club.shmoke.client.events.block.PushOutOfBlocksEvent;
import club.shmoke.client.events.update.UpdatePlayerEvent;
import club.shmoke.api.event.interfaces.EventListener;
import club.shmoke.client.util.math.DelayHelper;
import club.shmoke.client.util.math.MathHelper;

public class Phase extends Cheat implements IHelper {

    private DelayHelper timer = new DelayHelper();
    private int resetNext;

    public Phase() {
        super("Phase", Type.ENVIRONMENT);
        description = "Allows you to 'glitch' through blocks.";
    }

    @EventListener
    public void onUpdate(UpdatePlayerEvent e) {
        Anticheat a = Client.INSTANCE.getAnticheatManager().findAnticheat();

        switch (a) {
            case WATCHDOG:
                if (e.type == Event.Type.POST) {
                    double multiplier = 0.263;
                    double mx = Math.cos(Math.toRadians(mc.thePlayer.rotationYaw + 90.0F)), mz = Math.sin(Math.toRadians(mc.thePlayer.rotationYaw + 90.0F));
                    double x = (mc.thePlayer.movementInput.moveForward * multiplier * mx
                            + mc.thePlayer.movementInput.moveStrafe * multiplier * mz);
                    double z = (mc.thePlayer.movementInput.moveForward * multiplier * mz
                            - mc.thePlayer.movementInput.moveStrafe * multiplier * mx);
                    if (mc.thePlayer.isCollidedHorizontally && !mc.thePlayer.isOnLadder()
                            && !BLOCK_HELPER.isInsideBlock()) {
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX + x, mc.thePlayer.posY, mc.thePlayer.posZ + z, false));
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 0.5, mc.thePlayer.posZ,false));
                    }
                }
                break;
            case ANTIKIDZ:
                if (true) {
                    --resetNext;
                    double xOff;
                    double zOff;
                    double multiplier = 2.6;
                    double mx = Math.cos(Math.toRadians(mc.thePlayer.rotationYaw + 90.0f));
                    double mz = Math.sin(Math.toRadians(mc.thePlayer.rotationYaw + 90.0f));
                    xOff = MovementInput.moveForward * multiplier * mx + MovementInput.moveStrafe * multiplier * mz;
                    zOff = MovementInput.moveForward * multiplier * mz - MovementInput.moveStrafe * multiplier * mx;
                    if (isInsideBlock() && mc.thePlayer.isSneaking()) {
                        resetNext = 1;
                    }
                    if (resetNext > 0) {
                        mc.thePlayer.boundingBox.offsetAndUpdate(xOff, 0.0, zOff);
                    }
                } else if (timer.hasReached(150) && mc.thePlayer.isCollidedHorizontally) {
                    float yaw = mc.thePlayer.rotationYaw;
                    if (mc.thePlayer.moveForward < 0.0f)
                        yaw += 180.0f;
                    if (mc.thePlayer.moveStrafing > 0.0f)
                        yaw -= 90.0f * (mc.thePlayer.moveForward < 0.0f ? -0.5f : ((mc.thePlayer.moveForward > 0.0f) ? 0.5f : 1.0f));
                    if (mc.thePlayer.moveStrafing < 0.0f)
                        yaw += 90.0f * ((mc.thePlayer.moveForward < 0.0f) ? -0.5f : ((mc.thePlayer.moveForward > 0.0f) ? 0.5f : 1.0f));
                    double horizontalMultiplier = 0.3;
                    double xOffset = Math.cos((yaw + 90.0f) * 3.141592653589793 / 180.0) * horizontalMultiplier;
                    double zOffset = Math.sin((yaw + 90.0f) * 3.141592653589793 / 180.0) * horizontalMultiplier;
                    double yOffset = 0.0;
                    for (int i = 0; i < 3; ++i) {
                        yOffset += 0.01;
                        mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - yOffset, mc.thePlayer.posZ, mc.thePlayer.onGround));
                        mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX + xOffset * i, mc.thePlayer.posY, mc.thePlayer.posZ + zOffset * i, mc.thePlayer.onGround));
                    }
                } else if (!mc.thePlayer.isCollidedHorizontally)
                    timer.reset();
                break;
            default:
                break;
        }

        if (mc.thePlayer.onGround && mc.gameSettings.keyBindSneak.pressed) {
            for (double i = 0; i <= 3.0; i += 0.1)
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - i, mc.thePlayer.posZ, true));
            return;
        }
    }

    private boolean isInsideBlock() {
        AxisAlignedBB b = mc.thePlayer.boundingBox;
        for (int x = MathHelper.floor(b.minX); x < MathHelper.floor(b.maxX) + 1; ++x) {
            for (int y = MathHelper.floor(b.minY); y < MathHelper.floor(b.maxY) + 1; ++y) {
                for (int z = MathHelper.floor(b.minZ); z < MathHelper.floor(b.maxZ) + 1; ++z) {
                    Block block = Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                    if (block != null && !(block instanceof BlockAir)) {
                        BlockPos bp = new BlockPos(x, y, z);
                        AxisAlignedBB boundingBox = block.getCollisionBoundingBox(mc.theWorld, bp, mc.theWorld.getBlockState(bp));
                        if (block instanceof BlockHopper)
                            boundingBox = new AxisAlignedBB(bp.getX(), bp.getY(), bp.getZ(), bp.getX() + 1, bp.getY() + 1, bp.getZ() + 1);
                        if (boundingBox != null && b.intersectsWith(boundingBox))
                            return true;
                    }
                }
            }
        }
        return false;
    }

    @EventListener
    public void onPush(PushOutOfBlocksEvent e) {
        Anticheat a = Client.INSTANCE.getAnticheatManager().findAnticheat();
        if (mc.thePlayer.isCollidedHorizontally && BLOCK_HELPER.isInsideBlock() && a == Anticheat.WATCHDOG)
            e.cancel();
    }

    @EventListener
    public void onBB(BoundingBoxEvent e) {
        if (!isInsideBlock() && e.getBoundingBox() != null && e.getBoundingBox().maxY > mc.thePlayer.boundingBox.minY && mc.thePlayer.isSneaking())
            e.setBoundingBox(null);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.timer.timerSpeed = 1;
        mc.thePlayer.noClip = false;
    }
}

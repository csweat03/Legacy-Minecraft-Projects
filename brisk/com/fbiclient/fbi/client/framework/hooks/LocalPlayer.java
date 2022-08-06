package com.fbiclient.fbi.client.framework.hooks;

import com.fbiclient.fbi.client.events.blocks.PushOutOfBlockEvent;
import com.fbiclient.fbi.client.events.player.MoveEvent;
import com.fbiclient.fbi.client.events.render.RenderInsideBlockEvent;
import me.xx.utility.MathUtility;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.optifine.Config;

/**
 * @author Kyle
 * @since 3/12/2018
 **/
public class LocalPlayer extends EntityPlayerSP {

    public LocalPlayer(Minecraft mcIn, World worldIn, NetHandlerPlayClient netHandler, StatFileWriter statFile) {
        super(mcIn, worldIn, netHandler, statFile);
    }

    float i;

    public void damage(double damage) {
        if (damage < 1)
            damage = 1;
        if (damage > MathHelper.floor_double(mc.thePlayer.getMaxHealth()))
            damage = MathHelper.floor_double(mc.thePlayer.getMaxHealth());

        double offset = 0.0625;
        if (mc.thePlayer != null && mc.getNetHandler() != null && mc.thePlayer.onGround) {
            for (int i = 0; i <= ((3 + damage) / offset); i++) {
                mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
                        mc.thePlayer.posY + offset, mc.thePlayer.posZ, false));
                mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
                        mc.thePlayer.posY, mc.thePlayer.posZ, (i == ((3 + damage) / offset))));
            }
        }
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
    }

    @Override
    public void setVelocity(double x, double y, double z) {
        super.setVelocity(x, y, z);
    }

    @Override
    public void moveEntity(double x, double y, double z) {
        MoveEvent e = new MoveEvent(x, y, z);
        e.fire();
        super.moveEntity(e.getX(), e.getY(), e.getZ());
    }

    @Override
    public boolean isEntityInsideOpaqueBlock() {
        RenderInsideBlockEvent event = new RenderInsideBlockEvent();
        event.fire();
        return !event.isCancelled() && super.isEntityInsideOpaqueBlock();
    }

    @Override
    protected boolean pushOutOfBlocks(double x, double y, double z) {
        PushOutOfBlockEvent event = new PushOutOfBlockEvent();
        event.fire();
        return !event.isCancelled() && super.pushOutOfBlocks(x, y, z);
    }

    public boolean isMoving() {
        return this.moveForward != 0 || this.moveStrafing != 0;
    }

    public boolean hasMovementInput() {
        return mc.getMinecraft().gameSettings.keyBindForward.isKeyDown()
                || mc.getMinecraft().gameSettings.keyBindBack.isKeyDown()
                || mc.getMinecraft().gameSettings.keyBindLeft.isKeyDown()
                || mc.getMinecraft().gameSettings.keyBindRight.isKeyDown()
                || ((mc.getMinecraft().gameSettings.keyBindSneak.isKeyDown()
                && !mc.getMinecraft().thePlayer.isCollidedVertically)
                || mc.getMinecraft().gameSettings.keyBindJump.isKeyDown());
    }

    public boolean hasMoved() {
        return this.posX != this.prevPosX || this.posY != this.prevPosY || this.posZ != this.prevPosZ;
    }

    public boolean hasRotatedHead() {
        return this.rotationYaw != this.prevRotationYaw || this.rotationPitch != this.prevRotationPitch
                || this.rotationYawHead != this.prevRotationYawHead;
    }

    public float getDirection() {
        float yaw = this.rotationYawHead, forward = this.moveForward, strafe = this.moveStrafing;
        yaw += (forward < 0 ? 180 : 0);
        if (strafe < 0) {
            yaw += forward == 0 ? 90 : forward < 0 ? -45 : 45;
        }
        if (strafe > 0) {
            yaw -= forward == 0 ? 90 : forward < 0 ? -45 : 45;
        }
        return yaw * MathHelper.deg2Rad;
    }

    public double getSpeed() {
        return Math.sqrt(MathUtility.square(this.motionX) + MathUtility.square(this.motionZ));
    }

    public void setSpeed(double speed) {
        this.motionX = -MathHelper.sin(this.getDirection()) * speed;
        this.motionZ = MathHelper.cos(this.getDirection()) * speed;
    }

    public boolean isOnSameTeam(Entity entity) {
        boolean team = false;

        if (entity instanceof EntityPlayer) {
            String n = entity.getDisplayName().getFormattedText();
            if (n.startsWith('\u00a7' + "f") && !n.equalsIgnoreCase(entity.getName()))
                team = (n.substring(0, 6).equalsIgnoreCase(
                        mc.getMinecraft().thePlayer.getDisplayName().getFormattedText().substring(0, 6)));
            else
                team = (n.substring(0, 2).equalsIgnoreCase(
                        mc.getMinecraft().thePlayer.getDisplayName().getFormattedText().substring(0, 2)));
        }

        return team;
    }

    public boolean isInsideBlock() {
        for (int x = MathHelper.floor_double(this.boundingBox.minX); x < MathHelper
                .floor_double(this.boundingBox.maxX) + 1; ++x) {
            for (int y = MathHelper.floor_double(this.boundingBox.minY); y < MathHelper
                    .floor_double(this.boundingBox.maxY) + 1; ++y) {
                for (int z = MathHelper.floor_double(this.boundingBox.minZ); z < MathHelper
                        .floor_double(this.boundingBox.maxZ) + 1; ++z) {
                    AxisAlignedBB boundingBox;
                    Block block = mc.getMinecraft().theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                    if (block == null || block instanceof BlockAir
                            || (boundingBox = block.getCollisionBoundingBox(mc.getMinecraft().theWorld,
                            new BlockPos(x, y, z),
                            mc.getMinecraft().theWorld.getBlockState(new BlockPos(x, y, z)))) == null
                            || !this.boundingBox.intersectsWith(boundingBox))
                        continue;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean onLiquid() {
        double y = this.posY - 0.03;
        for (int x = MathHelper.floor_double(this.posX); x < MathHelper.ceiling_double_int(this.posX); ++x) {
            for (int z = MathHelper.floor_double(this.posZ); z < MathHelper.ceiling_double_int(this.posZ); ++z) {
                BlockPos pos = new BlockPos(x, MathHelper.floor_double(y), z);
                if (mc.getMinecraft().theWorld.getBlockState(pos).getBlock() instanceof BlockLiquid) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean onLiquid(double offset) {
        double y = this.posY + offset;
        for (int x = MathHelper.floor_double(this.posX); x < MathHelper.ceiling_double_int(this.posX); ++x) {
            for (int z = MathHelper.floor_double(this.posZ); z < MathHelper.ceiling_double_int(this.posZ); ++z) {
                BlockPos pos = new BlockPos(x, MathHelper.floor_double(y), z);
                if (mc.getMinecraft().theWorld.getBlockState(pos).getBlock() instanceof BlockLiquid) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean inLiquid(double offset) {
        double y = this.posY + offset;
        for (int x = MathHelper.floor_double(this.posX); x < MathHelper.ceiling_double_int(this.posX); ++x) {
            for (int z = MathHelper.floor_double(this.posZ); z < MathHelper.ceiling_double_int(this.posZ); ++z) {
                BlockPos pos = new BlockPos(x, (int) y, z);
                if (mc.getMinecraft().theWorld.getBlockState(pos).getBlock() instanceof BlockLiquid) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean onLand() {
        double y = this.posY - 0.01;
        for (int x = MathHelper.floor_double(this.posX); x < MathHelper.ceiling_double_int(this.posX); ++x) {
            for (int z = MathHelper.floor_double(this.posZ); z < MathHelper.ceiling_double_int(this.posZ); ++z) {
                BlockPos pos = new BlockPos(x, MathHelper.floor_double(y), z);
                if (mc.getMinecraft().theWorld.getBlockState(pos).getBlock().isCollidable()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean inLiquid() {
        double y = this.posY + 0.01;
        for (int x = MathHelper.floor_double(this.posX); x < MathHelper.ceiling_double_int(this.posX); ++x) {
            for (int z = MathHelper.floor_double(this.posZ); z < MathHelper.ceiling_double_int(this.posZ); ++z) {
                BlockPos pos = new BlockPos(x, (int) y, z);
                if (mc.getMinecraft().theWorld.getBlockState(pos).getBlock() instanceof BlockLiquid) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setInWater(boolean b) {
        inWater = b;
    }

    public void sendUseItem(EntityPlayer playerIn) {
        mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(playerIn.inventory.getCurrentItem()));
        mc.thePlayer.setItemInUse(mc.thePlayer.getHeldItem(), mc.thePlayer.getHeldItem().getMaxItemUseDuration());
    }

    @Override
    public float getFovModifier() {
        float gay;
        if (Config.zoomMode) {
            if (i < 5)
                i += 0.02F;
            gay = super.getFovModifier() / 2;
        } else {
            i = 0;
            gay = super.getFovModifier();
        }
        return gay;
    }

}

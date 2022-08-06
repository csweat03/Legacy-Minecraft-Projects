package com.fbiclient.fbi.impl.cheats.motion;

import com.fbiclient.fbi.client.events.render.RenderGuiEvent;
import com.fbiclient.fbi.client.framework.helper.IHelper;
import com.fbiclient.fbi.client.framework.helper.game.RenderHelper;
import com.fbiclient.fbi.impl.cheats.motion.scaffold.AAC;
import com.fbiclient.fbi.impl.cheats.motion.scaffold.Hypixel;
import com.fbiclient.fbi.impl.cheats.motion.scaffold.Normal;
import com.fbiclient.fbi.impl.gui.hud.tabbed.TabbedGui;
import me.valkyrie.api.value.Val;
import me.xx.api.cheat.Category;
import me.xx.api.cheat.Cheat;
import me.xx.api.cheat.CheatManifest;
import me.xx.api.event.Register;
import com.fbiclient.fbi.client.events.blocks.SafewalkEvent;
import com.fbiclient.fbi.client.events.player.UpdateMotionEvent;
import me.xx.utility.Stopwatch;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.*;

import java.util.List;
import java.util.Random;

/**
 * @author Kyle
 * @since 3/16/2018
 **/
@CheatManifest(label = "Scaffold", description = "Automatically places blocks below player", category = Category.MOTION)
public class Scaffold extends Cheat {

    private static double ext = 0;

    private AAC aac = new AAC();
    private Hypixel hypixel = new Hypixel();
    private Normal normal = new Normal();

    private static Stopwatch stopwatch = new Stopwatch();

    @Val(label = "Mode", description = "The mode of scaffold")
    public Mode mode = Mode.NORMAL;

    @Val(label = "Tower")
    public static boolean tower;

    public static boolean isAirBorne() {
        BlockPos pos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ);
        Block block = mc.theWorld.getBlockState(pos).getBlock();
        return (block.getMaterial().equals(Material.air));
    }

    public static boolean canPlace() {
        List<? extends Object> list = mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(mc.thePlayer.motionX * ext, -1, mc.thePlayer.motionZ * ext));
        return list.isEmpty();
    }

    public static void place(BlockPos pos, EnumFacing face) {
        mc.thePlayer.inventory.currentItem = getSlot();
        if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventoryContainer.getSlot(36 + mc.thePlayer.inventory.currentItem).getStack(), pos, face, new Vec3(pos.getX(), pos.getY(), pos.getZ())))
            tower();
        mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
        mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), pos, face, new Vec3(0.5, 0, 0.5));
    }

    public static int getSlot() {
        int i = 36;
        while (i < 45) {
            ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (itemStack != null && itemStack.getItem() instanceof ItemBlock && itemStack.stackSize >= 1 && Block.getBlockFromItem(itemStack.getItem()).getDefaultState().getBlock().isFullBlock()) {
                return i - 36;
            }
            ++i;
        }
        return 0;
    }

    @Register
    public void onUpdate(UpdateMotionEvent event) {
        setSuffix(mode.name());

        if (mc.thePlayer.fallDistance > 0) return;

        switch (mode) {
            case NORMAL:
                ext = 0.9;
                normal.onUpdate(event);
                break;
            case AAC:
                ext = 0.1;
                aac.onUpdate(event);
                break;
            case HYPIXEL:
                ext = 0.4;
                hypixel.onUpdate(event);
                break;
        }
    }

    public void onDisable() {
        super.onDisable();
        switch (mode) {
            case NORMAL:
                normal.onDisable();
                break;
            case AAC:
                aac.onDisable();
                break;
            case HYPIXEL:
                hypixel.onDisable();
                break;
        }
    }

    public void onEnable() {
        super.onEnable();
        switch (mode) {
            case NORMAL:
                normal.onEnable();
                break;
            case AAC:
                aac.onEnable();
                break;
            case HYPIXEL:
                hypixel.onEnable();
                break;
        }
    }

    @Register
    public void onSafe(SafewalkEvent event) {
        BlockPos pos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ);
        Block block = mc.theWorld.getBlockState(pos).getBlock();
        if (block.getMaterial().equals(Material.air))
            event.setCancelled(true);
    }


    public static float[] getGenericAngles() {
        EnumFacing f = mc.thePlayer.getHorizontalFacing();
        float y = mc.gameSettings.keyBindJump.isKeyDown() ? 0 : f == EnumFacing.EAST ? 90 : f == EnumFacing.SOUTH ? 180 : f == EnumFacing.WEST ? -90 : f == EnumFacing.NORTH ? 0 : 0;
        float p = 83;
        y -= 2;
        y += new Random().nextInt(4);
        return new float[]{y, p};
    }

    public static void placeGenericBlock() {
        BlockPos pos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ);
        EnumFacing face = EnumFacing.getHorizontal(MathHelper.floor_double(mc.thePlayer.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3);

        if (mc.theWorld.getBlockState(pos.add(0, -1, 0)).getBlock() != Blocks.air) {
            pos = pos.add(0, -1, 0);
            face = EnumFacing.UP;
        }
        if (face.equals(EnumFacing.EAST))
            pos = pos.add(-1, 0, 0);
        if (face.equals(EnumFacing.SOUTH))
            pos = pos.add(0, 0, -1);
        if (face.equals(EnumFacing.WEST))
            pos = pos.add(1, 0, 0);
        if (face.equals(EnumFacing.NORTH))
            pos = pos.add(0, 0, 1);

        if (BLOCK_HELPER.canBeClicked(pos))
            place(pos, face);

    }

    private static void tower() {
        if (tower) {

            if (!BLOCK_HELPER.getBlock(mc.thePlayer.posX, mc.thePlayer.posY - 1.5, mc.thePlayer.posZ).isFullCube())
                return;

            if (mc.gameSettings.keyBindJump.isKeyDown()) {
                mc.thePlayer.jump();
                mc.thePlayer.setSpeed(0.0f);
            }
            if (stopwatch.hasReached(1500L) && mc.gameSettings.keyBindJump.isKeyDown()) {
                mc.thePlayer.motionY = -0.28D;
                stopwatch.reset();
            }
            if (!mc.gameSettings.keyBindJump.isKeyDown())
                stopwatch.reset();
        }
    }

    private int getTotalBlocks() {
        int total = 0;
        for (int i = 0; i < 8; i++) {
            ItemStack cur = mc.thePlayer.inventory.getStackInSlot(i);
            if (cur != null && cur.getItem() != null && cur.getItem() instanceof ItemBlock) {
                total += cur.stackSize;
            }
        }
        return total;
    }

    @Register
    public void onRender(RenderGuiEvent event) {
        if (mc.gameSettings.showDebugInfo) {
            return;
        }

        int digits = getTotalBlocks() <= 9 ? 1 : getTotalBlocks() <= 99 ? 2 : 3;

        Gui.drawRect(event.getScreenWidth() / 2 - 2 - (digits * 3), event.getScreenHeight() / 2 - 16, event.getScreenWidth() / 2 + 1 + (digits * 3), event.getScreenHeight() / 2 - 5.5, 0xaa000000);
        IHelper.FR.drawCenteredString(getTotalBlocks() + "", (float) (event.getScreenWidth() / 2), (float) (event.getScreenHeight() / 2) - 15, getTotalBlocks() >= 64 ? 0xff00ee00 : getTotalBlocks() >= 32 ? 0xffeeee00 : 0xffee0000);
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
    }

    public enum Mode {
        NORMAL, AAC, HYPIXEL
    }

}

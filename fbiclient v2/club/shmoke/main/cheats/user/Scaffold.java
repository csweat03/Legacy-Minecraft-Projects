package club.shmoke.main.cheats.user;

import club.shmoke.api.cheat.Category;
import club.shmoke.api.cheat.Cheat;
import club.shmoke.api.cheat.property.Property;
import club.shmoke.api.event.EventHandler;
import club.shmoke.main.cheats.user.scaffold.AAC;
import club.shmoke.main.cheats.user.scaffold.Hypixel;
import club.shmoke.main.cheats.user.scaffold.Vanilla;
import club.shmoke.main.events.SafewalkEvent;
import club.shmoke.main.events.UpdateEvent;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;

import java.util.ArrayList;
import java.util.List;

public class Scaffold extends Cheat {

    public static Minecraft mc = Minecraft.getMinecraft();
    public final Property<Mode> mode = new Property<>(this, "Mode", Mode.VANILLA);

    public Property<Boolean> tower = new Property<>(this, "Tower", true);
    public Property<Integer> delay = new Property<>(this, "Delay", 0, 0, 1000, 1);

    private AAC aac = new AAC();
    private Hypixel hypixel = new Hypixel();
    private Vanilla vanilla = new Vanilla();

    private static double ext = 0;

    public Scaffold() {
        super("Scaffold", 0, Category.USER, "Places blocks under the customizable_gui.");
    }

    public static boolean isAirBorne() {
        BlockPos pos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ);
        Block block = mc.theWorld.getBlockState(pos).getBlock();
        return (block.getMaterial().equals(Material.air));
    }

    public static boolean canPlace() {
        double val = ext;
        List<? extends Object> list = mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(mc.thePlayer.motionX * val, -1, mc.thePlayer.motionZ * val));
        return list.isEmpty();
    }

    public static void place(BlockPos pos, EnumFacing face) {

        mc.thePlayer.inventory.currentItem = getNormalSlot();
        mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
        mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), pos, face, new Vec3(0.5, 0, 0.5));
    }

    public static int getNormalSlot() {
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

    @EventHandler
    public void onUpdate(UpdateEvent event) {
        setSuffix(mode.getValue() + "");

        if (mc.thePlayer.fallDistance > 0) return;

        switch (mode.getValue()) {
            case VANILLA:
                ext = 5;
                vanilla.onUpdate(event);
                break;
            case AAC:
                ext = 0.33;
                aac.onUpdate(event);
                break;
            case HYPIXEL:
                ext = 0.9;
                hypixel.onUpdate(event);
                break;
        }
    }

    public void onDisable() {
        super.onDisable();
        switch (mode.getValue()) {
            case VANILLA:
                vanilla.onDisable();
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
        switch (mode.getValue()) {
            case VANILLA:
                vanilla.onEnable();
                break;
            case AAC:
                aac.onEnable();
                break;
            case HYPIXEL:
                hypixel.onEnable();
                break;
        }
    }

    @EventHandler
    public void onSafe(SafewalkEvent event) {
        BlockPos pos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ);
        Block block = mc.theWorld.getBlockState(pos).getBlock();
        if (block.getMaterial().equals(Material.air))
            event.setCancelled(true);
    }

    public enum Mode {
        VANILLA, HYPIXEL, AAC
    }
}

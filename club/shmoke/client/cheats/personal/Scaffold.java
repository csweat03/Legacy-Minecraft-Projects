package club.shmoke.client.cheats.personal;

import club.shmoke.api.cheat.Cheat;
import club.shmoke.api.cheat.data.Property;
import club.shmoke.api.cheat.helpers.interfaces.IHelper;
import club.shmoke.api.event.Event;
import club.shmoke.api.event.interfaces.EventListener;
import club.shmoke.client.events.entity.JumpEvent;
import club.shmoke.client.events.entity.SafeWalkEvent;
import club.shmoke.client.events.update.UpdatePlayerEvent;
import club.shmoke.client.util.math.DelayHelper;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;

public class Scaffold extends Cheat implements IHelper {

    BlockPos currentPos;
    EnumFacing currentFacing;
    private int slot = 0;
    private DelayHelper timer = new DelayHelper(), stopwatch = new DelayHelper();
    private Property<Boolean> eagle = new Property<>(this, "Eagle", false);
    private Property<Boolean> sprint = new Property<>(this, "Sprint", true);
    private Property<Boolean> tower = new Property<>(this, "Tower", false);
    private Property<Integer> delay = new Property<>(this, "Delay", 0, 0, 1000, 1);

    public Scaffold() {
        super("Scaffold", Type.PERSONAL);
        this.description = "Places blocks under you.";
    }

    @EventListener
    public void onUpdate(UpdatePlayerEvent event) {
        int tempSlot = getBlockSlot();
        this.slot = -1;
        if (tempSlot == -1)
            return;
        BlockPos belowPlayer = new BlockPos(mc.thePlayer).offsetDown();
        if (event.type == Event.Type.PRE) {
            if (!Minecraft.getMinecraft().gameSettings.keyBindJump.pressed) {
                stopwatch.reset();
            }
        } else {
            if (BLOCK_HELPER.getMaterial(belowPlayer).isReplaceable()) {
                setBlockAndFacing(belowPlayer);
                this.slot = tempSlot;
                if (currentPos != null) {
                    if (!sprint.getValue()) mc.thePlayer.setSprinting(false);
                    boolean dohax = mc.thePlayer.inventory.currentItem != slot;
                    if (eagle.getValue()) mc.thePlayer.setSneaking(true);
                    if (dohax)
                        mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(slot));
                    placeDelayed(belowPlayer);
                    if (dohax)
                        mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
                    if (eagle.getValue()) mc.thePlayer.setSneaking(false);
                }
            }
        }
    }

    public boolean placeDelayed(BlockPos pos) {
        Vec3 eyesPos = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);
        EnumFacing[] values;
        for (int length = (values = EnumFacing.values()).length, i = 0; i < length; ++i) {
            EnumFacing side = values[i];
            EnumFacing oppositeSide = side.getOpposite();
            BlockPos neighbor = pos.offset(side);
            if (eyesPos.squareDistanceTo(new Vec3(pos).addVector(0.5, 0.5, 0.5)) < eyesPos.squareDistanceTo(new Vec3(neighbor).addVector(0.5, 0.5, 0.5))) {
                if (BLOCK_HELPER.canBeClicked(neighbor) && timer.hasReached(delay.getValue())) {
                    Vec3 hitVec = new Vec3(neighbor).addVector(0.5, 0.5, 0.5).add(new Vec3(oppositeSide.getDirectionVec()).scale(0.5));
                    if (eyesPos.squareDistanceTo(hitVec) <= 18.0625) {
                        BLOCK_HELPER.faceVectorPacketInstant(hitVec);
                        mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventoryContainer.getSlot(36 + this.slot).getStack(), neighbor, oppositeSide, hitVec);
                        if (tower.getValue()) {
                            if (mc.gameSettings.keyBindJump.pressed) {
                                mc.thePlayer.jump();
                                mc.thePlayer.setSpeed(0.0f);
                            }
                            if (stopwatch.hasReached(1500L) && mc.gameSettings.keyBindJump.pressed) {
                                mc.thePlayer.motionY = -0.28D;
                                stopwatch.reset();
                            }
                            if (!mc.gameSettings.keyBindJump.pressed)
                                stopwatch.reset();
                        }
                        mc.getNetHandler().getNetworkManager().sendPacket(new C0APacketAnimation());
                        mc.rightClickDelayTimer = 4;
                        timer.reset();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private int getBlockSlot() {
        int i = 36;
        while (i < 45) {
            ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (itemStack != null && itemStack.getItem() instanceof ItemBlock && itemStack.stackSize >= 1 && Block.getBlockFromItem(itemStack.getItem()).getDefaultState().getBlock().isFullBlock()) {
                return i - 36;
            }
            ++i;
        }
        return -1;
    }

    public void onEnable() {
        super.onEnable();
        timer.reset();
    }

    @EventListener
    public void onSafe(SafeWalkEvent event) {
        event.cancel();
    }

    @EventListener
    public void onJump(JumpEvent event) {
        if (!tower.getValue())
            event.cancel();
    }

    private void setBlockAndFacing(BlockPos pos) {
        BlockPos pos1 = pos.add(1, 0, 0);
        BlockPos pos2 = pos.add(-1, 0, 0);
        BlockPos pos3 = pos.add(0, 0, 1);
        BlockPos pos4 = pos.add(0, 0, -1);
        if (mc.theWorld.getBlockState(pos.add(0, -1, 0)).getBlock() != Blocks.air) {
            currentPos = pos.add(0, -1, 0);
            currentFacing = EnumFacing.UP;
        } else if (mc.theWorld.getBlockState(pos.add(-1, 0, 0)).getBlock() != Blocks.air) {
            currentPos = pos.add(-1, 0, 0);
            currentFacing = EnumFacing.EAST;
        } else if (mc.theWorld.getBlockState(pos.add(1, 0, 0)).getBlock() != Blocks.air) {
            currentPos = pos.add(1, 0, 0);
            currentFacing = EnumFacing.WEST;
        } else if (mc.theWorld.getBlockState(pos.add(0, 0, -1)).getBlock() != Blocks.air) {
            currentPos = pos.add(0, 0, -1);
            currentFacing = EnumFacing.SOUTH;
        } else if (mc.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock() != Blocks.air) {
            currentPos = pos.add(0, 0, 1);
            currentFacing = EnumFacing.NORTH;
        } else if (mc.theWorld.getBlockState(pos1.add(-1, 0, 0)).getBlock() != Blocks.air) {
            currentPos = pos1.add(-1, 0, 0);
            currentFacing = EnumFacing.EAST;
        } else if (mc.theWorld.getBlockState(pos1.add(1, 0, 0)).getBlock() != Blocks.air) {
            currentPos = pos1.add(1, 0, 0);
            currentFacing = EnumFacing.WEST;
        } else if (mc.theWorld.getBlockState(pos1.add(0, 0, -1)).getBlock() != Blocks.air) {
            currentPos = pos1.add(0, 0, -1);
            currentFacing = EnumFacing.SOUTH;
        } else if (mc.theWorld.getBlockState(pos1.add(0, 0, 1)).getBlock() != Blocks.air) {
            currentPos = pos1.add(0, 0, 1);
            currentFacing = EnumFacing.NORTH;
        } else if (mc.theWorld.getBlockState(pos2.add(-1, 0, 0)).getBlock() != Blocks.air) {
            currentPos = pos2.add(-1, 0, 0);
            currentFacing = EnumFacing.EAST;
        } else if (mc.theWorld.getBlockState(pos2.add(1, 0, 0)).getBlock() != Blocks.air) {
            currentPos = pos2.add(1, 0, 0);
            currentFacing = EnumFacing.WEST;
        } else if (mc.theWorld.getBlockState(pos2.add(0, 0, -1)).getBlock() != Blocks.air) {
            currentPos = pos2.add(0, 0, -1);
            currentFacing = EnumFacing.SOUTH;
        } else if (mc.theWorld.getBlockState(pos2.add(0, 0, 1)).getBlock() != Blocks.air) {
            currentPos = pos2.add(0, 0, 1);
            currentFacing = EnumFacing.NORTH;
        } else if (mc.theWorld.getBlockState(pos3.add(-1, 0, 0)).getBlock() != Blocks.air) {
            currentPos = pos3.add(-1, 0, 0);
            currentFacing = EnumFacing.EAST;
        } else if (mc.theWorld.getBlockState(pos3.add(1, 0, 0)).getBlock() != Blocks.air) {
            currentPos = pos3.add(1, 0, 0);
            currentFacing = EnumFacing.WEST;
        } else if (mc.theWorld.getBlockState(pos3.add(0, 0, -1)).getBlock() != Blocks.air) {
            currentPos = pos3.add(0, 0, -1);
            currentFacing = EnumFacing.SOUTH;
        } else if (mc.theWorld.getBlockState(pos3.add(0, 0, 1)).getBlock() != Blocks.air) {
            currentPos = pos3.add(0, 0, 1);
            currentFacing = EnumFacing.NORTH;
        } else if (mc.theWorld.getBlockState(pos4.add(-1, 0, 0)).getBlock() != Blocks.air) {
            currentPos = pos4.add(-1, 0, 0);
            currentFacing = EnumFacing.EAST;
        } else if (mc.theWorld.getBlockState(pos4.add(1, 0, 0)).getBlock() != Blocks.air) {
            currentPos = pos4.add(1, 0, 0);
            currentFacing = EnumFacing.WEST;
        } else if (mc.theWorld.getBlockState(pos4.add(0, 0, -1)).getBlock() != Blocks.air) {
            currentPos = pos4.add(0, 0, -1);
            currentFacing = EnumFacing.SOUTH;
        } else if (mc.theWorld.getBlockState(pos4.add(0, 0, 1)).getBlock() != Blocks.air) {
            currentPos = pos4.add(0, 0, 1);
            currentFacing = EnumFacing.NORTH;
        } else {
            currentPos = null;
            currentFacing = null;
        }
    }
}

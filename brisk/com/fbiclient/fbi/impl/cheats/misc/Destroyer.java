package com.fbiclient.fbi.impl.cheats.misc;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import me.valkyrie.api.value.Val;
import me.xx.api.cheat.Category;
import me.xx.api.cheat.Cheat;
import me.xx.api.cheat.CheatManifest;
import me.xx.api.event.Register;
import com.fbiclient.fbi.client.events.player.UpdateMotionEvent;

/**
 * @author Kyle
 * @since 5/3/2018
 **/
@CheatManifest(label = "F*cker", description = "F*cks spawn generators", category = Category.MISC)
public class F*cker extends Cheat {

    @Val(label = "Cake")
    public boolean cake = true;

    @Val(label = "Egg")
    public boolean egg = false;

    @Val(label = "Bed")
    public boolean bed = true;

    @Register
    public void onUpdate(UpdateMotionEvent event) {
        breaker();
    }

    private void breaker() {
        int radius = 6;
        for (int x = -radius; x < radius; ++x)
            for (int y = radius; y > -radius; --y)
                for (int z = -radius; z < radius; ++z) {
                    int xPos = (int) mc.thePlayer.posX + x;
                    int yPos = (int) mc.thePlayer.posY + y;
                    int zPos = (int) mc.thePlayer.posZ + z;
                    BlockPos blockPos = new BlockPos(xPos, yPos, zPos);
                    Block block = mc.theWorld.getBlockState(blockPos).getBlock();
                    if (qualifies(block)) {
                        for (int i = 0; i < 10; i++) {
                            mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(blockPos, 0, mc.thePlayer.getHeldItem(), 0, 0, 0));
                            mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, EnumFacing.UP));
                            mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos, EnumFacing.UP));
                        }
                    }
                }
    }

    private boolean qualifies(Block block) {
        if ((block.getBlockState().getBlock() == Block.getBlockById(92) && cake)
                || (block.getBlockState().getBlock() == Block.getBlockById(122) && egg)
                || (block.getBlockState().getBlock() == Block.getBlockById(26) && bed))
            return true;
        return false;
    }

}

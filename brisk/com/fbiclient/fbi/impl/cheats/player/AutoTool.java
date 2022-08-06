package com.fbiclient.fbi.impl.cheats.player;

import me.xx.utility.Stopwatch;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import me.xx.api.cheat.Category;
import me.xx.api.cheat.Cheat;
import me.xx.api.cheat.CheatManifest;
import me.xx.api.event.Register;
import com.fbiclient.fbi.client.events.player.UpdateMotionEvent;

/**
 * @author Kyle
 * @since 5/7/2018
 **/
@CheatManifest(label = "Auto Tool", category = Category.PLAYER, description = "Auto select best tool from inventory")
public class AutoTool extends Cheat {

    private int old = 0;

    private Stopwatch watch = new Stopwatch();

    @Register
    public void handleMotion(UpdateMotionEvent event) {

        if (mc.gameSettings.keyBindAttack.isKeyDown()) {
            if (mc.objectMouseOver != null) {
                BlockPos pos = mc.objectMouseOver.getBlockPos();
                if (pos != null) {
                    updateTools(pos);
                }
            }
        }
    }

    void updateTools(BlockPos pos) {
        Block block = mc.theWorld.getBlockState(pos).getBlock();
        float strength = 1.0F;
        int bestIndex = -1;

        for (int i = 0; i < 9; ++i) {
            ItemStack itemStack = mc.thePlayer.inventory.mainInventory[i];
            if (itemStack != null && itemStack.getStrVsBlock(block) > strength) {
                strength = itemStack.getStrVsBlock(block);
                bestIndex = i;
            }
        }

        if (bestIndex != -1)
            mc.thePlayer.inventory.currentItem = bestIndex;
    }
}

package club.shmoke.main.cheats.user;

import club.shmoke.api.cheat.Category;
import club.shmoke.api.cheat.Cheat;
import club.shmoke.api.event.EventHandler;
import club.shmoke.main.events.UpdateEvent;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;

public class AutoTool extends Cheat {

    public AutoTool() {
        super("AutoTool", 0, Category.USER, "Switchs to the best tool automatically.");
    }

    @EventHandler
    public void onUpdate(UpdateEvent event) {
        if (mc.gameSettings.keyBindAttack.isKeyDown()) {
            if (mc.objectMouseOver != null) {
                BlockPos pos = mc.objectMouseOver.getBlockPos();
                if (pos != null)
                    updateTools(pos);
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

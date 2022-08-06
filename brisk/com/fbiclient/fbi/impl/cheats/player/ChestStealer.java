package com.fbiclient.fbi.impl.cheats.player;

import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import me.valkyrie.api.value.Val;
import me.valkyrie.api.value.types.constrain.Clamp;
import me.valkyrie.api.value.types.constrain.Increment;
import me.xx.api.cheat.Category;
import me.xx.api.cheat.Cheat;
import me.xx.api.cheat.CheatManifest;
import me.xx.api.event.Register;
import com.fbiclient.fbi.client.events.player.UpdateMotionEvent;
import me.xx.utility.MathUtility;
import me.xx.utility.Stopwatch;

import java.util.ArrayList;

/**
 * @author Kyle
 * @since 3/12/2018
 **/
@CheatManifest(label = "Chest Stealer", description = "Steals loot from chests", category = Category.PLAYER)
public class ChestStealer extends Cheat {

    private ArrayList<Item> items = new ArrayList();
    private Stopwatch timer = new Stopwatch();

    @Val(label = "Check Lobby")
    private boolean lobby;

    @Increment("10")
    @Clamp(min = "50")
    @Val(label = "Delay")
    public int delay = 170;

    @Register
    public void handleUpdates(UpdateMotionEvent e) {
        switch (e.getType()) {
            case PRE:
                if (mc.currentScreen instanceof GuiChest) {
                    GuiChest guiChest = (GuiChest) mc.currentScreen;

                    if (CONTAINER_HELPER.isChestEmpty(guiChest) || CONTAINER_HELPER.isInventoryFull())
                        mc.thePlayer.closeScreen();

                    for (int index = 0; index < guiChest.getLowerChestInventory().getSizeInventory(); index++) {
                        ItemStack stack = guiChest.getLowerChestInventory().getStackInSlot(index);

                        if (stack == null)
                            continue;
                        if(!qualifies(guiChest))
                            return;
                        if (timer.hasReached((MathUtility.getRandom(delay - 50, delay)), false)) {
                            mc.playerController.windowClick(guiChest.inventorySlots.windowId, index, 0, 1, mc.thePlayer);
                            timer.reset();
                        }
                    }
                }
                break;
        }
    }

    boolean qualifies(GuiChest gui) {
        return lobby ? (gui.getLowerChestInventory().getDisplayName().toString().contains("Chest") || gui.getUpperChestInventory().getDisplayName().toString().contains("Chest")) : true;
    }

}

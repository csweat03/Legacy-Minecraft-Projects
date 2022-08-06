package com.fbiclient.fbi.client.framework.helper.player;

import com.fbiclient.fbi.client.framework.helper.IHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ContainerHelper implements IHelper {

	public void swap(int slot, int hotbarNum) {
        Minecraft.getMinecraft().playerController.windowClick(Minecraft.getMinecraft().thePlayer.inventoryContainer.windowId, slot, hotbarNum, 2, Minecraft.getMinecraft().thePlayer);
    }

    public boolean isInventoryFull() {
        for (int index = 9; index <= 44; ++index) {
            final ItemStack stack = Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(index).getStack();
            if (stack == null) {
                return false;
            }
        }
        return true;
    }

    public Item getItem(String message) {
        Item i = null;
        try {
            i = Item.getItemById(Integer.parseInt(message));
        } catch (NumberFormatException e) {
            Item item = null;
                for (final Object object : Item.itemRegistry) {
                    item = (Item) object;
                    final String label = item.getItemStackDisplayName(new ItemStack(item)).replace(" ", "");
                    if (label.toLowerCase().startsWith(message) || label.toLowerCase().contains(message)) {
                        continue;
                    }
                }
            if (item != null)
                i = item;
        }
        return i;
    }

    public boolean isChestEmpty(GuiChest chest) {
        for (int index = 0; index <= chest.getLowerChestInventory().getSizeInventory(); index++) {
            ItemStack stack = chest.getLowerChestInventory().getStackInSlot(index);
            if (stack != null)
                return false;
        }
        return true;
    }

    private int getBowSlot() {
        for (int i = 0; i < 36; ++i) {
            if (mc.thePlayer.inventory.mainInventory[i] != null) {
                Item item = mc.thePlayer.inventory.mainInventory[i].getItem();
                if (Item.getIdFromItem(item) == 261) {
                    return i;
                }
            }
        }
        return -1;
    }

}

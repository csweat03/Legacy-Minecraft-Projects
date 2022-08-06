package com.fbiclient.fbi.impl.cheats.player;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0DPacketCloseWindow;

import java.util.Random;

import me.xx.api.cheat.Category;
import me.xx.api.cheat.Cheat;
import me.xx.api.cheat.CheatManifest;
import me.xx.api.event.Register;
import com.fbiclient.fbi.client.events.player.UpdateMotionEvent;

/**
 * @author Kyle
 * @since 3/12/2018
 **/
@CheatManifest(label = "Auto Armor", description = "Automatically place on best armor", category = Category.PLAYER)
public class AutoArmor extends Cheat {

    private int delay, num = 5, item = -1;
    private double maxValue = -1, mv;

    private final int[] boots = {313, 309, 317, 305, 301};
    private final int[] chestplate = {311, 307, 315, 303, 299};
    private final int[] helmet = {310, 306, 314, 302, 298};
    private final int[] leggings = {312, 308, 316, 304, 300};

    @Register
    public void handleUpdates(UpdateMotionEvent e) {
        switch (e.getType()) {
            case PRE:
                if ((mc.thePlayer.capabilities.isCreativeMode)
                        || (mc.thePlayer.openContainer != null) && (mc.thePlayer.openContainer.windowId != 0)) {
                    return;
                }
                if (mc.currentScreen == null)
                    mc.thePlayer.sendQueue
                            .addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.OPEN_INVENTORY));
                if (this.delay >= 6 + new Random().nextInt(4)) {
                    delay = 0;
                    maxValue = -1;
                    item = -1;
                    for (int i = 9; i < 45; i++) {
                        if ((mc.thePlayer.inventoryContainer.getSlot(i).getStack() != null)) {
                            if (canEquip(mc.thePlayer.inventoryContainer.getSlot(i).getStack()) != -1
                                    && (canEquip(mc.thePlayer.inventoryContainer.getSlot(i).getStack()) == num))
                                change(num, i);
                        }
                    }
                    if (item != -1) {
                        if (mc.thePlayer.inventoryContainer.getSlot(item).getStack() != null)
                            mc.playerController.windowClick(0, num, 0, 1, mc.thePlayer);
                        mc.playerController.windowClick(0, item, 0, 1, mc.thePlayer);
                        delay = 0;
                        if (mc.currentScreen == null)
                            mc.thePlayer.sendQueue
                                    .addToSendQueue(new C0DPacketCloseWindow());
                    }
                    if (num == 8) {
                        num = 5;
                    } else {
                        num++;
                    }

                } else
                    delay++;
                break;
        }
    }

    private int canEquip(ItemStack stack) {
        for (int id : this.boots)
            if (stack.getItem().getIdFromItem(stack.getItem()) == id) {
                return 8;
            }
        for (int id : this.leggings)
            if (stack.getItem().getIdFromItem(stack.getItem()) == id) {
                return 7;
            }
        for (int id : this.chestplate)
            if (stack.getItem().getIdFromItem(stack.getItem()) == id) {
                return 6;
            }
        for (int id : this.helmet)
            if (stack.getItem().getIdFromItem(stack.getItem()) == id) {
                return 5;
            }

        return -1;
    }

    private void change(int numy, int i) {
        if (maxValue == -1) {
            if (mc.thePlayer.inventoryContainer.getSlot(numy).getStack() != null) {
                mv = protectionValue(mc.thePlayer.inventoryContainer.getSlot(numy).getStack());
            } else
                mv = maxValue;
        } else {
            mv = maxValue;
        }
        if (mv <= protectionValue(mc.thePlayer.inventoryContainer.getSlot(i).getStack())) {
            if (mv == protectionValue(mc.thePlayer.inventoryContainer.getSlot(i).getStack())) {
                int currentD = (mc.thePlayer.inventoryContainer.getSlot(numy).getStack() != null
                        ? mc.thePlayer.inventoryContainer.getSlot(numy).getStack().getItemDamage()
                        : 999);
                int newD = (mc.thePlayer.inventoryContainer.getSlot(i).getStack() != null
                        ? mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItemDamage()
                        : 500);
                if (newD <= currentD) {
                    if (newD == currentD) {
                    } else {
                        item = i;
                        maxValue = protectionValue(mc.thePlayer.inventoryContainer.getSlot(i).getStack());
                    }
                }
            } else {
                item = i;
                maxValue = protectionValue(mc.thePlayer.inventoryContainer.getSlot(i).getStack());
            }
        }
    }

    private double protectionValue(ItemStack stack) {
        if (stack != null)
            return ((ItemArmor) stack.getItem()).damageReduceAmount
                    + (100 - ((ItemArmor) stack.getItem()).damageReduceAmount * 4)
                    * EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack) * 4
                    * 0.0075D;
        else
            return 0;
    }

}

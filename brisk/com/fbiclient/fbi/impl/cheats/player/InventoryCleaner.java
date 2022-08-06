package com.fbiclient.fbi.impl.cheats.player;

import com.google.common.collect.Multimap;

import me.valkyrie.api.value.Val;
import me.xx.api.cheat.Category;
import me.xx.api.cheat.Cheat;
import me.xx.api.cheat.CheatManifest;
import me.xx.api.event.Event;
import me.xx.api.event.Register;
import com.fbiclient.fbi.client.events.entity.LivingUpdateEvent;
import me.xx.utility.Stopwatch;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.*;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0DPacketCloseWindow;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Kyle
 * @since 3/16/2018
 **/
@CheatManifest(label = "Inventory Cleaner", description = "Cleans out useless items from inventory", category = Category.PLAYER, handles = {"inventorycleaner", "cleaner", "invcleaner"})
public class InventoryCleaner extends Cheat {

    @Val(label = "Best Sword")
    public boolean sword;

    public boolean openinv = true;
    Stopwatch stopwatch = new Stopwatch();
    private final Random RANDOM = new Random();

    public static List<Integer> blacklistedItems = new ArrayList<>();
    public static Stopwatch timer = new Stopwatch();

    @Register
    public void onUpdate(LivingUpdateEvent e) {
        if (sword)
            autoSword(e);
        if (mc.thePlayer.isUsingItem())
            return;
        if (mc.thePlayer.ticksExisted % 2 == 0 && RANDOM.nextInt(2) == 0) {
            if (!openinv || mc.currentScreen instanceof GuiInventory) {
                if (timer.hasReached(59L)) {
                    CopyOnWriteArrayList<Integer> uselessItems = new CopyOnWriteArrayList<>();
                    for (int o = 0; o < 45; ++o) {
                        if (mc.thePlayer.inventoryContainer.getSlot(o).getHasStack()) {
                            ItemStack item = mc.thePlayer.inventoryContainer.getSlot(o).getStack();
                            InventoryPlayer inventory = mc.thePlayer.inventory;
                            if (inventory.armorItemInSlot(0) == item
                                    || inventory.armorItemInSlot(1) == item
                                    || inventory.armorItemInSlot(2) == item
                                    || inventory.armorItemInSlot(3) == item)
                                continue;
                            if (item != null && item.getItem() != null && Item.getIdFromItem(item.getItem()) != 0
                                    && !stackIsUseful(o)) {
                                uselessItems.add(o);
                            }
                        }
                    }
                    if (!uselessItems.isEmpty()) {
                        mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId,
                                uselessItems.get(0), 1, 4, mc.thePlayer);
                        uselessItems.remove(0);

                        timer.reset();
                    }
                }
            }
        }
    }

    private boolean stackIsUseful(int i) {
        ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();

        boolean hasAlreadyOrBetter = false;

        if (itemStack.getItem() instanceof ItemSword || itemStack.getItem() instanceof ItemPickaxe
                || itemStack.getItem() instanceof ItemAxe) {
            for (int o = 0; o < 45; ++o) {
                if (o == i)
                    continue;
                if (mc.thePlayer.inventoryContainer.getSlot(o).getHasStack()) {
                    ItemStack item = mc.thePlayer.inventoryContainer.getSlot(o).getStack();
                    if (item != null && item.getItem() instanceof ItemSword || item.getItem() instanceof ItemAxe
                            || item.getItem() instanceof ItemPickaxe) {
                        float damageFound = getItemDamage(itemStack);
                        damageFound += EnchantmentHelper.getSharpnessLevel(itemStack, EnumCreatureAttribute.UNDEFINED);

                        float damageCurrent = getItemDamage(item);
                        damageCurrent += EnchantmentHelper.getSharpnessLevel(item, EnumCreatureAttribute.UNDEFINED);

                        if (damageCurrent > damageFound) {
                            hasAlreadyOrBetter = true;
                            break;
                        }
                    }
                }
            }
        } else if (itemStack.getItem() instanceof ItemArmor) {
            for (int o = 0; o < 45; ++o) {
                if (i == o)
                    continue;
                if (mc.thePlayer.inventoryContainer.getSlot(o).getHasStack()) {
                    ItemStack item = mc.thePlayer.inventoryContainer.getSlot(o).getStack();
                    if (item != null && item.getItem() instanceof ItemArmor) {

                        List<Integer> helmet = Arrays.asList(298, 314, 302, 306, 310);
                        List<Integer> chestplate = Arrays.asList(299, 315, 303, 307, 311);
                        List<Integer> leggings = Arrays.asList(300, 316, 304, 308, 312);
                        List<Integer> boots = Arrays.asList(301, 317, 305, 309, 313);

                        if (helmet.contains(Item.getIdFromItem(item.getItem()))
                                && helmet.contains(Item.getIdFromItem(itemStack.getItem()))) {
                            if (helmet.indexOf(Item.getIdFromItem(itemStack.getItem())) < helmet
                                    .indexOf(Item.getIdFromItem(item.getItem()))) {
                                hasAlreadyOrBetter = true;
                                break;
                            }
                        } else if (chestplate.contains(Item.getIdFromItem(item.getItem()))
                                && chestplate.contains(Item.getIdFromItem(itemStack.getItem()))) {
                            if (chestplate.indexOf(Item.getIdFromItem(itemStack.getItem())) < chestplate
                                    .indexOf(Item.getIdFromItem(item.getItem()))) {
                                hasAlreadyOrBetter = true;
                                break;
                            }
                        } else if (leggings.contains(Item.getIdFromItem(item.getItem()))
                                && leggings.contains(Item.getIdFromItem(itemStack.getItem()))) {
                            if (leggings.indexOf(Item.getIdFromItem(itemStack.getItem())) < leggings
                                    .indexOf(Item.getIdFromItem(item.getItem()))) {
                                hasAlreadyOrBetter = true;
                                break;
                            }
                        } else if (boots.contains(Item.getIdFromItem(item.getItem()))
                                && boots.contains(Item.getIdFromItem(itemStack.getItem()))) {
                            if (boots.indexOf(Item.getIdFromItem(itemStack.getItem())) < boots
                                    .indexOf(Item.getIdFromItem(item.getItem()))) {
                                hasAlreadyOrBetter = true;
                                break;
                            }
                        }

                    }
                }
            }
        }

        for (int o = 0; o < 45; ++o) {
            if (i == o)
                continue;
            if (mc.thePlayer.inventoryContainer.getSlot(o).getHasStack()) {
                ItemStack item = mc.thePlayer.inventoryContainer.getSlot(o).getStack();
                if (item != null && (item.getItem() instanceof ItemSword || item.getItem() instanceof ItemAxe
                        || item.getItem() instanceof ItemBow || item.getItem() instanceof ItemFishingRod
                        || item.getItem() instanceof ItemArmor || item.getItem() instanceof ItemAxe
                        || item.getItem() instanceof ItemPickaxe || Item.getIdFromItem(item.getItem()) == 346)) {
                    if (Item.getIdFromItem(itemStack.getItem()) == Item.getIdFromItem(item.getItem())) {
                        hasAlreadyOrBetter = true;
                        break;
                    }
                }
            }
        }

        if (Item.getIdFromItem(itemStack.getItem()) == 367)
            return false; // rotten flesh
        if (Item.getIdFromItem(itemStack.getItem()) == 30)
            return true; // cobweb
        if (Item.getIdFromItem(itemStack.getItem()) == 259)
            return true; // flint & steel
        if (Item.getIdFromItem(itemStack.getItem()) == 262)
            return true; // arrow
        if (Item.getIdFromItem(itemStack.getItem()) == 264)
            return true; // diamond
        if (Item.getIdFromItem(itemStack.getItem()) == 265)
            return true; // iron
        if (Item.getIdFromItem(itemStack.getItem()) == 346)
            return true; // fishing rod
        if (Item.getIdFromItem(itemStack.getItem()) == 384)
            return true; // bottle o' enchanting
        if (Item.getIdFromItem(itemStack.getItem()) == 345)
            return true; // compass
        if (Item.getIdFromItem(itemStack.getItem()) == 296)
            return true; // wheat
        if (Item.getIdFromItem(itemStack.getItem()) == 336)
            return true; // brick
        if (Item.getIdFromItem(itemStack.getItem()) == 266)
            return true; // gold ingot
        if (Item.getIdFromItem(itemStack.getItem()) == 280)
            return true; // stick
        if (itemStack.hasDisplayName())
            return true;

        if (hasAlreadyOrBetter) {
            return false;
        }

        if (itemStack.getItem() instanceof ItemArmor)
            return true;
        if (itemStack.getItem() instanceof ItemAxe)
            return true;
        if (itemStack.getItem() instanceof ItemBow)
            return true;
        if (itemStack.getItem() instanceof ItemSword)
            return true;
        if (itemStack.getItem() instanceof ItemPotion)
            return true;
        if (itemStack.getItem() instanceof ItemFlintAndSteel)
            return true;
        if (itemStack.getItem() instanceof ItemEnderPearl) {
            return true;
        }
        if (itemStack.getItem() instanceof ItemBlock) {
            return true;
        }
        if (itemStack.getItem() instanceof ItemFood)
            return true;
        if (itemStack.getItem() instanceof ItemPickaxe)
            return true;
        return false;
    }

    private float getItemDamage(final ItemStack itemStack) {
        final Multimap multimap = itemStack.getAttributeModifiers();
        if (!multimap.isEmpty()) {
            final Iterator iterator = multimap.entries().iterator();
            if (iterator.hasNext()) {
                final Map.Entry entry = (Map.Entry) iterator.next();
                final AttributeModifier attributeModifier = (AttributeModifier) entry.getValue();
                double damage;
                if (attributeModifier.getOperation() != 1 && attributeModifier.getOperation() != 2) {
                    damage = attributeModifier.getAmount();
                } else {
                    damage = attributeModifier.getAmount() * 100.0;
                }
                if (attributeModifier.getAmount() > 1.0) {
                    return 1.0f + (float) damage;
                }
                return 1.0f;
            }
        }
        return 1.0f;
    }

    void autoSword(LivingUpdateEvent event) {
        if (event.getType() == Event.Type.PRE
                && (mc.currentScreen instanceof GuiInventory || mc.currentScreen == null)
                && mc.thePlayer.getCurrentEquippedItem() != null
                && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword
                && this.stopwatch.hasReached(100)) {
            if (mc.currentScreen == null)
                mc.thePlayer.sendQueue
                        .addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.OPEN_INVENTORY));
            for (int i = 0; i < 45; ++i) {
                if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                    Item itevent = mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem();
                    if (itevent instanceof ItemSword) {
                        float iteventDamage = this.getAttackDamage(mc.thePlayer.inventoryContainer.getSlot(i).getStack());
                        float currentDamage = this.getAttackDamage(mc.thePlayer.getCurrentEquippedItem());
                        if (iteventDamage > currentDamage) {
                            CONTAINER_HELPER.swap(i, mc.thePlayer.inventory.currentItem);
                            if (mc.currentScreen == null)
                                mc.thePlayer.sendQueue
                                        .addToSendQueue(new C0DPacketCloseWindow());
                            this.stopwatch.reset();
                            break;
                        }
                    }
                }
            }
        }
    }

    private float getAttackDamage(ItemStack stack) {
        return !(stack.getItem() instanceof ItemSword) ? 0.0F : (float) EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack) * 1.25F + ((ItemSword) stack.getItem()).getDamageGiven();
    }

    public boolean isValid(Item item) {
        if (blacklistedItems.contains(Item.getIdFromItem(item))) {
            return openinv == false || mc.currentScreen instanceof GuiInventory;
        }
        return false;
    }

}

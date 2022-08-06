package club.shmoke.client.cheats.personal;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.item.*;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import org.lwjgl.input.Keyboard;
import club.shmoke.client.Client;
import club.shmoke.api.anticheat.Anticheat;
import club.shmoke.api.cheat.Cheat;
import club.shmoke.api.cheat.data.Property;
import club.shmoke.client.events.update.UpdatePlayerEvent;
import club.shmoke.api.event.interfaces.EventListener;
import club.shmoke.client.ui.click.GuiClick;

import java.util.*;
import java.util.stream.Collectors;

public class Inventory extends Cheat {

    private final Set<Integer> blacklisted = new HashSet<>();
    private ItemStack[] bestArmor;
    private ItemStack bestSword;

    KeyBinding[] moveKeys = {mc.gameSettings.keyBindRight, mc.gameSettings.keyBindLeft,
            mc.gameSettings.keyBindBack, mc.gameSettings.keyBindForward, mc.gameSettings.keyBindJump,
            mc.gameSettings.keyBindSprint
    };
    private int invTicks = 0;
    private Property<Boolean> move = new Property<>(this, "Move", true);
    private Property<Boolean> look = new Property<>(this, "Look", true);
    private Property<Boolean> clean = new Property<>(this, "Clean", false);

    public Inventory() {
        super("Inventory", Type.PERSONAL);
        Arrays.stream(new int[]{344, 384, 37, 38, 280, 287, 318, 288, 75, 76, 116, 54, 332, 145, 50, 32}).forEach(blacklisted::add);
    }

    @EventListener
    public void onUpdate(UpdatePlayerEvent event) {
        if (clean.getValue())
            cleaner();
        if (mc.currentScreen == null || !(mc.currentScreen instanceof GuiContainer || mc.currentScreen instanceof GuiClick)) {
            invTicks = 0;
            return;
        }
        invTicks++;
        if (invTicks > 25 || Client.INSTANCE.getAnticheatManager().findAnticheat() != Anticheat.MMC) {
            if (mc.thePlayer.ticksExisted % 5 == 0 && Client.INSTANCE.getAnticheatManager().findAnticheat() == Anticheat.MMC)
                mc.thePlayer.sendQueue.addToSendQueue(new C0DPacketCloseWindow());
            if (move.getValue())
                for (KeyBinding key : moveKeys)
                    key.pressed = Keyboard.isKeyDown(key.getKeyCode());
            if (look.getValue()) {
                // left
                if (Keyboard.isKeyDown(203))
                    mc.thePlayer.rotationYaw -= 3;
                //right
                if (Keyboard.isKeyDown(205)) {
                    mc.thePlayer.rotationYaw += 3;
                }
                //up
                if (Keyboard.isKeyDown(200))
                    mc.thePlayer.rotationPitch -= 1.5;
                //down
                if (Keyboard.isKeyDown(208))
                    mc.thePlayer.rotationPitch += 1.5;
            }
        }
    }

    private void cleaner() {
        if (mc.thePlayer.ticksExisted % 4 != 0) return;

        bestArmor = isBestArmor();
        bestSword = isBest(ItemSword.class, Comparator.comparingDouble(this::swordDamage));
        Optional<Slot> blacklistedItem = lambda();

        if (blacklistedItem.isPresent())
            dropItemInSlot(blacklistedItem.get().slotNumber);
    }

    private Optional<Slot> lambda() {
        return ((List<Slot>) mc.thePlayer.inventoryContainer.inventorySlots).stream().filter(Slot::getHasStack).filter(slot -> Arrays.stream(mc.thePlayer.inventory.armorInventory)
                .noneMatch(slot.getStack()::equals)).filter(slot -> !slot.getStack().equals(mc.thePlayer.getHeldItem())).filter(slot -> getBlacklisted(slot.getStack())).findFirst();
    }

    private void dropItemInSlot(int slotID) {
        mc.playerController.windowClick(0, slotID, 1, 4, mc.thePlayer);
    }

    private boolean getBlacklisted(ItemStack stack) {
        Item item = stack.getItem();

        return blacklisted.contains(Item.getIdFromItem(item)) || item instanceof ItemBow
                || item instanceof ItemTool || item instanceof ItemFishingRod
                || item instanceof ItemGlassBottle || item instanceof ItemBucket
                || item instanceof ItemFood && !(item instanceof ItemAppleGold)
                || item instanceof ItemSword && !bestSword.equals(stack)
                || item instanceof ItemArmor && !bestArmor[((ItemArmor) item).armorType].equals(stack)
                || item instanceof ItemPotion && negativePotion(stack);
    }

    private ItemStack isBest(Class<? extends Item> itemType, Comparator comparator) {
        Optional<ItemStack> bestItem = ((List<Slot>) mc.thePlayer.inventoryContainer.inventorySlots).stream()
                .map(Slot::getStack).filter(Objects::nonNull)
                .filter(stack -> stack.getItem().getClass().equals(itemType)).max(comparator);

        return bestItem.orElse(null);
    }

    private ItemStack[] isBestArmor() {
        ItemStack[] bestArmorSet = new ItemStack[4];

        List<ItemStack> armor = ((List<Slot>) mc.thePlayer.inventoryContainer.inventorySlots).stream()
                .filter(Slot::getHasStack).map(Slot::getStack).filter(stack -> stack.getItem() instanceof ItemArmor)
                .collect(Collectors.toList());

        for (ItemStack stack : armor) {
            ItemArmor itemArmor = (ItemArmor) stack.getItem();

            ItemStack bestArmor = bestArmorSet[itemArmor.armorType];

            if (bestArmor == null || armorProtection(stack) > armorProtection(bestArmor)) {
                bestArmorSet[itemArmor.armorType] = stack;
            }
        }

        return bestArmorSet;
    }

    private double swordDamage(ItemStack stack) {
        double damage = 0;

        Optional<AttributeModifier> attributeModifier = stack.getAttributeModifiers().values().stream().findFirst();

        if (attributeModifier.isPresent()) {
            damage = attributeModifier.get().getAmount();
        }

        damage += EnchantmentHelper.func_152377_a(stack, EnumCreatureAttribute.UNDEFINED);

        return damage;
    }

    private double armorProtection(ItemStack stack) {
        int damageReductionAmount = ((ItemArmor) stack.getItem()).damageReduceAmount;

        damageReductionAmount += EnchantmentHelper.getEnchantmentModifierDamage(new ItemStack[]{stack},
                DamageSource.causePlayerDamage(mc.thePlayer));

        return damageReductionAmount;
    }

    private boolean negativePotion(ItemStack stack) {
        ItemPotion potion = (ItemPotion) stack.getItem();

        List<PotionEffect> potionEffectList = potion.getEffects(stack);

        return potionEffectList.stream().map(potionEffect -> Potion.potionTypes[potionEffect.getPotionID()])
                .anyMatch(Potion::isBadEffect);
    }
}

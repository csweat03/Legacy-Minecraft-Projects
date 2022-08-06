package club.shmoke.client.cheats.environment;

import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.*;
import net.minecraft.network.play.client.*;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import club.shmoke.client.Client;
import club.shmoke.api.anticheat.Anticheat;
import club.shmoke.api.cheat.Cheat;
import club.shmoke.client.cheats.movement.Speed;
import club.shmoke.api.cheat.data.Property;
import club.shmoke.api.cheat.helpers.interfaces.IHelper;
import club.shmoke.api.event.Event;
import club.shmoke.client.events.update.UpdatePlayerEvent;
import club.shmoke.api.event.interfaces.EventListener;
import club.shmoke.client.util.math.DelayHelper;

import java.util.ArrayList;
import java.util.Random;

public class AutoInteract extends Cheat implements IHelper {

    private static int pots;
    private static boolean souping = false;
    private final int[] boots = {313, 309, 317, 305, 301},
            chestplate = {311, 307, 315, 303, 299},
            helmet = {310, 306, 314, 302, 298},
            leggings = {312, 308, 316, 304, 300};

    private final DelayHelper souptimer = new DelayHelper(), pottimer = new DelayHelper();

    DelayHelper lootTime = new DelayHelper(), equipTime = new DelayHelper();
    Property<Boolean> sprint = new Property<>(this, "Sprint", true);
    Property<Boolean> loot = new Property<>(this, "Looter", true);
    Property<Boolean> equip = new Property<>(this, "Equip", true);
    Property<Boolean> consume = new Property<>(this, "Consume", false);
    Property<Boolean> sword = new Property<>(this, "Sword", false);
    Property<Boolean> breaker = new Property<>(this, "Breaker", false);
    Property<Boolean> healer = new Property<>(this, "Healer", false);
    Property<String> lootOptions = new Property<>(this, "Loot Options");
    Property<Integer> lootDelay = new Property<>(this, "Delay", 100, 0, 1000, 1);
    Property<String> equipOptions = new Property<>(this, "Equip Options");
    Property<Integer> equipDelay = new Property<>(this, "Delay", 100, 0, 1000, 1);
    Property<Boolean> equipInventory = new Property<>(this, "Inventory", false);
    Property<String> healOptions = new Property<>(this, "Heal Options");
    Property<Boolean> soupper = new Property<>(this, "Soup", true);
    Property<Boolean> potter = new Property<>(this, "Pots", true);
    Property<Boolean> regenner = new Property<>(this, "Regen", true);
    Property<Integer> delay = new Property(this, "Delay", 100, 0, 1000, 100);
    Property<Integer> health = new Property(this, "Health", 10, 1, 20, 1);
    Property<String> breakerSub = new Property(this, "Breaker Options");
    Property<Integer> radius = new Property(this, "Radius", 3, 1, 5, 1);
    Property<Boolean> nuke = new Property(this, "Nuke All Blocks", false);
    Property<Boolean> cake = new Property(this, "Cake", true);
    Property<Boolean> egg = new Property(this, "Egg", false);
    Property<Boolean> bed = new Property(this, "Bed", false);
    double maxValue = -1;
    double mv;
    int item = -1;
    int eatingTicks = 0, slot = 0;
    private DelayHelper time = new DelayHelper();
    private int num = 5;
    private ArrayList<BlockPos> blacklistedPos = new ArrayList();
    private int xPos;
    private int yPos;
    private int zPos;
    private boolean potting = false;
    private boolean needsToPot = false;

    public AutoInteract() {
        super("Auto Interact", Type.ENVIRONMENT);
    }

    @EventListener
    public void onUpdate(UpdatePlayerEvent event) {

        if (sword.getValue() && event.type == Event.Type.POST)
            sword();

        if (loot.getValue())
            loot();

        if (equip.getValue()) {
            if (event.type != Event.Type.PRE || (mc.thePlayer.capabilities.isCreativeMode)
                    || (mc.thePlayer.openContainer != null) && (mc.thePlayer.openContainer.windowId != 0)
                    || (equipInventory.getValue() && !(mc.currentScreen instanceof GuiInventory)))
                return;

            if (time.hasReached(equipDelay.getValue() + 7)) {
                if (mc.currentScreen == null)
                    mc.thePlayer.sendQueue.addToSendQueue(
                            new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.OPEN_INVENTORY));
                equip();
            }
        }

        if (consume.getValue()) {
            if (event.type == Event.Type.POST && mc.thePlayer.getFoodStats().needFood() && mc.currentScreen == null) {
                eatingTicks++;
                getFood();
            } else if (!mc.thePlayer.getFoodStats().needFood() && eatingTicks > 0) {
                mc.gameSettings.keyBindUseItem.pressed = false;
                eatingTicks = 0;
                mc.thePlayer.inventory.currentItem = slot;
            }
            if (eatingTicks == 0) slot = mc.thePlayer.inventory.currentItem;
        }

        if (healer.getValue()) {
            if (potter.getValue())
                autoheal(event);
            if (soupper.getValue())
                autosoup(event);
            if (regenner.getValue())
                autoregen();
        }

        if (breaker.getValue())
            breaker();

        if (sprint.getValue() && canSprint() && event.type == (Client.INSTANCE.getAnticheatManager().findAnticheat() == Anticheat.MMC ? Event.Type.POST : Event.Type.PRE))
            mc.thePlayer.setSprinting(true);
    }

    @Override
    public void onDisable() {
        mc.gameSettings.keyBindJump.pressed = false;
        mc.gameSettings.keyBindForward.pressed = false;
        mc.gameSettings.keyBindSprint.pressed = false;
        mc.gameSettings.keyBindSneak.pressed = false;
        super.onDisable();
    }

    private boolean canSprint() {
        if (mc.thePlayer.moveForward > 0
                && !mc.thePlayer.isSneaking()
                && !mc.thePlayer.isCollidedHorizontally
                && mc.thePlayer.onGround)
            return true;
        return false;
    }

    private void sword() {
        int swordSlot = 0;
        for (int i = 0; i < 9; i++) {
            if ((mc.thePlayer.inventoryContainer.inventorySlots.toArray()[i] != null)) {
                ItemStack current = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (current != null && current.getItem() instanceof ItemSword)
                    swordSlot = i;
            }
        }
        mc.thePlayer.inventory.currentItem = swordSlot;
    }

    private void loot() {
        if (mc.currentScreen instanceof GuiChest) {
            ContainerChest chest = (ContainerChest) mc.thePlayer.openContainer;

            for (int i = 0; i < chest.getLowerChestInventory().getSizeInventory(); i++) {
                if (chest.getLowerChestInventory().getStackInSlot(i) != null && lootTime.hasReached(lootDelay.getValue())
                        && (chest.getLowerChestInventory().getName().contains("Chest"))) {
                    mc.playerController.windowClick(chest.windowId, i, 0, 1, mc.thePlayer);
                    lootTime.reset();
                }

                if (isChestEmpty(chest) && lootTime.hasReached(lootDelay.getValue() * 2)) {
                    mc.thePlayer.closeScreen();
                    mc.thePlayer.sendQueue.addToSendQueue(new C0DPacketCloseWindow());
                    mc.currentScreen = null;
                    lootTime.reset();
                }
            }
        }
    }

    private boolean isChestEmpty(ContainerChest chest) {
        for (int index = 0; index <= chest.getLowerChestInventory().getSizeInventory(); index++) {
            ItemStack stack = chest.getLowerChestInventory().getStackInSlot(index);

            if (stack != null) {
                return false;
            }
        }

        return true;
    }

    private void equip() {
        if (equipTime.hasReached(equipDelay.getValue() + new Random().nextInt(6))) {
            equipTime.reset();
            maxValue = -1;
            item = -1;

            for (int i = 9; i < 45; i++) {
                if ((mc.thePlayer.inventoryContainer.getSlot(i).getStack() != null)) {
                    if (canEquip(mc.thePlayer.inventoryContainer.getSlot(i).getStack()) != -1
                            && (canEquip(mc.thePlayer.inventoryContainer.getSlot(i).getStack()) == num)) {
                        change(num, i);
                    }
                }
            }

            if (item != -1) {
                if (mc.thePlayer.inventoryContainer.getSlot(item).getStack() != null) {
                    mc.playerController.windowClick(0, num, 0, 1, mc.thePlayer);
                }

                mc.playerController.windowClick(0, item, 0, 1, mc.thePlayer);

                if (mc.currentScreen == null)
                    mc.thePlayer.sendQueue.addToSendQueue(new C0DPacketCloseWindow());
                equipTime.reset();
            }

            if (num == 8) {
                num = 5;
            } else {
                num++;
            }
        }
    }

    private int canEquip(ItemStack stack) {
        for (int id : this.boots)
            if (stack.getItem().getIdFromItem(stack.getItem()) == id)
                return 8;

        for (int id : this.leggings)
            if (stack.getItem().getIdFromItem(stack.getItem()) == id)
                return 7;

        for (int id : this.chestplate)
            if (stack.getItem().getIdFromItem(stack.getItem()) == id)
                return 6;

        for (int id : this.helmet)
            if (stack.getItem().getIdFromItem(stack.getItem()) == id)
                return 5;

        return -1;
    }

    private void change(int numy, int i) {
        if (maxValue == -1) {
            if (mc.thePlayer.inventoryContainer.getSlot(numy).getStack() != null)
                mv = getProtValue(mc.thePlayer.inventoryContainer.getSlot(numy).getStack());
            else
                mv = maxValue;
        } else
            mv = maxValue;

        if (mv <= getProtValue(mc.thePlayer.inventoryContainer.getSlot(i).getStack())) {
            if (mv == getProtValue(mc.thePlayer.inventoryContainer.getSlot(i).getStack())) {
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
                        maxValue = getProtValue(mc.thePlayer.inventoryContainer.getSlot(i).getStack());
                    }
                }
            } else {
                item = i;
                maxValue = getProtValue(mc.thePlayer.inventoryContainer.getSlot(i).getStack());
            }
        }
    }

    private double getProtValue(ItemStack stack) {
        if (stack != null)
            return ((ItemArmor) stack.getItem()).damageReduceAmount
                    + (100 - ((ItemArmor) stack.getItem()).damageReduceAmount * 4)
                    * EnchantmentHelper.getEnchantmentLevel(Enchantment.field_180310_c.effectId, stack) * 4
                    * 0.0075D;
        else {
            return 0;
        }
    }

    private void getFood() {
        int foodSlot = 0;
        for (int i = 1; i < 10; i++) {
            if ((mc.thePlayer.inventoryContainer.inventorySlots.toArray()[i] != null)) {
                ItemStack current = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (current != null && current.getItem() instanceof ItemFood) {
                    foodSlot = i - 1;
                }
            }
        }
        mc.thePlayer.inventory.currentItem = foodSlot;
        mc.gameSettings.keyBindUseItem.pressed = true;
    }

    private void breaker() {
        if (mc.thePlayer.ticksExisted < 5)
            blacklistedPos.clear();
        int radius = this.radius.getValue();
        double prevX = mc.thePlayer.posX, prevY = mc.thePlayer.posY, prevZ = mc.thePlayer.posZ;
        for (int x = -radius; x < radius; ++x) {
            for (int y = radius; y > -radius; --y) {
                for (int z = -radius; z < radius; ++z) {
                    this.xPos = (int) mc.thePlayer.posX + x;
                    this.yPos = (int) mc.thePlayer.posY + y;
                    this.zPos = (int) mc.thePlayer.posZ + z;
                    final BlockPos blockPos = new BlockPos(this.xPos, this.yPos, this.zPos);
                    final Block block = mc.theWorld.getBlockState(blockPos).getBlock();
                    if (goodBlock(block)) {
                        for (Entity ent : mc.theWorld.loadedEntityList) {
                            if (mc.thePlayer.getDistanceToEntity(ent) >= radius || !(ent instanceof EntityArmorStand))
                                continue;
                            EntityArmorStand entt = (EntityArmorStand) ent;
                            if (entt.getName().contains("costs!")) {
                                blacklistedPos.add(blockPos);
                                return;
                            }
                        }
                        if (!blacklistedPos.contains(blockPos)) {
                            mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(
                                    C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, EnumFacing.NORTH));
                            mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(
                                    C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos, EnumFacing.NORTH));
                        }
                    }
                }
            }
        }
    }

    private boolean goodBlock(Block block) {
        if ((block.getBlockState().getBlock() == Block.getBlockById(92) && cake.getValue())
                || (block.getBlockState().getBlock() == Block.getBlockById(122) && egg.getValue())
                || (block.getBlockState().getBlock() == Block.getBlockById(26) && bed.getValue()) || nuke.getValue())
            return true;
        return false;
    }

    private void autoregen() {
        Anticheat a = Client.INSTANCE.getAnticheatManager().findAnticheat();
        switch (a) {
            case ANTIKIDZ:
                if ((!mc.thePlayer.capabilities.isCreativeMode) && (mc.thePlayer.getFoodStats().getFoodLevel() > 17)
                        && (mc.thePlayer.getHealth() < 20.0F) && (mc.thePlayer.getHealth() != 0.0F)
                        && (mc.thePlayer.onGround)) {
                    for (int i = 0; i < 50; i++)
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer());
                }
                break;
            case GUARDIAN:
                if (mc.thePlayer.getHealth() < 20 &&
                        mc.thePlayer.getFoodStats().getFoodLevel() > 17) {
                    IHelper.ENTITY_HELPER.oldGaurdianBypass();
                    for (int i = 0; i < 75; ++i)
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(
                                mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, mc.thePlayer.onGround));
                }
                break;
            default:
                break;
        }
    }

    private void autoheal(UpdatePlayerEvent event) {
        switch (event.type) {
            case PRE:
                pots = ENTITY_HELPER.countPotion(Potion.heal, true);
                if (!pottimer.hasReached(delay.getValue()) || mc.thePlayer.getHealth() > health.getValue() || pots == 0) {
                    potting = false;
                    return;
                }
                if (pottimer.hasReached(delay.getValue()) && mc.thePlayer.getHealth() <= health.getValue()) {
                    event.pitch = getPitch();
                    potting = true;
                }
            case POST:
                if (!potting || event.pitch != getPitch())
                    return;
                if (potting && pottimer.hasReached(delay.getValue()) && mc.thePlayer.getHealth() <= health.getValue()
                        && event.pitch == getPitch()) {
                    if (ENTITY_HELPER.hotbarHasPotion(Potion.heal, true)) {
                        ENTITY_HELPER.useFirstPotionSilent(Potion.heal, true);
                        pottimer.reset();
                        return;
                    } else {
                        ENTITY_HELPER.getPotion(Potion.heal, true);
                        ENTITY_HELPER.useFirstPotionSilent(Potion.heal, true);
                        pottimer.reset();
                        return;
                    }
                }
                break;
        }
    }

    private void autosoup(UpdatePlayerEvent event) {
        needsToPot = mc.thePlayer.getHealth() <= health.getValue();
        switch (event.type) {
            case PRE:
                if (updateCounter() == 0) {
                    souping = false;
                    return;
                }
                if (souptimer.hasReached(200)) {
                    souping = true;
                    souptimer.reset();
                }
                break;
            case POST:
                needsToPot = mc.thePlayer.getHealth() <= health.getValue();
                if (needsToPot) {
                    if (doesHotbarHaveSoups()) {
                        if (souping) {
                            for (int i = 36; i < 45; i++) {
                                final ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                                if (stack != null) {
                                    if (isSoup(stack)) {
                                        final int oldSlot = mc.thePlayer.inventory.currentItem;
                                        if (souptimer.hasReached(200)) {
                                            mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(i - 36));
                                            mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(stack));
                                            mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(oldSlot));
                                        }

                                        needsToPot = false;
                                        souping = false;
                                        break;
                                    }
                                }
                            }
                        }
                        souptimer.reset();
                    }
                } else
                    getSoupsFromInventory();
                break;
        }
    }

    private boolean doesHotbarHaveSoups() {
        for (int i = 36; i < 45; i++) {
            final ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (stack != null) {
                if (isSoup(stack))
                    return true;
            }
        }
        return false;
    }

    private void getSoupsFromInventory() {
        for (int i = 9; i < 36; i++) {
            final ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (stack != null) {
                if (isSoup(stack)) {
                    mc.playerController.windowClick(mc.thePlayer.openContainer.windowId, i, 1, 2, mc.thePlayer);
                    break;
                }
            }
        }
    }

    private boolean isSoup(final ItemStack stack) {
        if (stack != null) {
            if (stack.getItem() instanceof ItemSoup)
                return true;
        }
        return false;
    }

    private int updateCounter() {
        pots = 0;
        for (int i = 9; i < 45; i++) {
            final ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (stack != null) {
                if (isSoup(stack))
                    pots += stack.stackSize;
            }
        }
        return pots;
    }

    public int getCount() {
        int counter = 0;
        for (int i = 0; i < 36; ++i) {
            if (this.mc.thePlayer.inventory.mainInventory[i] != null) {
                ItemStack is = this.mc.thePlayer.inventory.mainInventory[i];
                Item item = is.getItem();
                if (item instanceof ItemPotion) {
                    ItemPotion potion = (ItemPotion) item;
                    if (potion.getEffects(is) != null) {
                        for (Object o : potion.getEffects(is)) {
                            PotionEffect effect = (PotionEffect) o;
                            if (effect.getPotionID() == Potion.heal.id && ItemPotion.isSplash(is.getItemDamage())) {
                                ++counter;
                            }
                        }
                    }
                }
            }
        }
        return counter;
    }

    public float getPitch() {
        boolean checksprintfwd = mc.gameSettings.keyBindForward.getIsKeyPressed()
                && !mc.gameSettings.keyBindBack.getIsKeyPressed() && mc.thePlayer.isSprinting()
                && mc.thePlayer.isMoving() && mc.thePlayer.moveForward != 0;
        boolean checksprintbwd = mc.gameSettings.keyBindBack.getIsKeyPressed()
                && !mc.gameSettings.keyBindForward.getIsKeyPressed() && mc.thePlayer.isSprinting()
                && mc.thePlayer.isMoving() && mc.thePlayer.moveForward == 0;
        boolean checkfwd = mc.gameSettings.keyBindForward.getIsKeyPressed()
                && !mc.gameSettings.keyBindBack.getIsKeyPressed() && mc.thePlayer.isMoving();
        boolean checkbwd = mc.gameSettings.keyBindBack.getIsKeyPressed()
                && !mc.gameSettings.keyBindForward.getIsKeyPressed() && mc.thePlayer.isMoving();
        Speed s = (Speed) Client.INSTANCE.getCheatManager().get(Speed.class);
        boolean bhop = s.getState() && s.type.getValue() == Speed.Type.SLOWHOP && !mc.thePlayer.isCollidedVertically
                && mc.thePlayer.moveForward != 0;
        boolean air = !mc.thePlayer.isCollidedVertically && mc.thePlayer.moveForward != 0;
        return checksprintfwd ? 50
                : checkfwd ? 65 : checksprintbwd && !bhop ? 175 : checkbwd && !bhop ? 170 : bhop ? 90 : air ? 65 : 105;
    }

}

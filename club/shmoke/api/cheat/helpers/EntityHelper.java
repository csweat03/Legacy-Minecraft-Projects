package club.shmoke.api.cheat.helpers;

import com.google.common.collect.Multimap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.network.play.client.*;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathUtil;
import club.shmoke.client.events.entity.MoveEvent;
import club.shmoke.client.util.math.DelayHelper;

import java.awt.*;
import java.awt.event.InputEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.List;

public class EntityHelper {
    private static final Minecraft MC = Minecraft.getMinecraft();

    private static boolean set = false;
    private static EntityPlayer reference;
    private static DelayHelper timer = new DelayHelper();
    private static Minecraft mc = Minecraft.getMinecraft();

    public static EntityPlayer getReference() {
        return reference == null ? reference = MC.thePlayer : ((reference));
    }

    public static void setReference(EntityPlayer ref) {
        reference = ref;
        set = reference == MC.thePlayer;
    }

    public static boolean isReferenceSet() {
        return !set;
    }

    public static float[] getEntityRotations(Entity target) {
        final double var4 = target.posX - MC.thePlayer.posX;
        final double var6 = target.posZ - MC.thePlayer.posZ;
        final double var8 = target.posY + target.getEyeHeight() / 1.3
                - (MC.thePlayer.posY + MC.thePlayer.getEyeHeight());
        final double var14 = MathUtil.sqrt_double(var4 * var4 + var6 * var6);
        final float yaw = (float) (Math.atan2(var6, var4) * 180.0D / Math.PI) - 90.0F;
        final float pitch = (float) -(Math.atan2(var8, var14) * 180.0D / Math.PI);
        return new float[]{yaw, pitch};
    }

    public static float getAngle(float[] original, float[] rotations) {
        float curYaw = normalizeAngle(original[0]);
        rotations[0] = normalizeAngle(rotations[0]);
        float curPitch = normalizeAngle(original[1]);
        rotations[1] = normalizeAngle(rotations[1]);
        float fixedYaw = normalizeAngle(curYaw - rotations[0]);
        float fixedPitch = normalizeAngle(curPitch - rotations[1]);
        return Math.abs(normalizeAngle(fixedYaw) + Math.abs(fixedPitch));
    }

    public static float getAngle(float[] rotations) {
        return getAngle(new float[]{MC.thePlayer.rotationYaw, MC.thePlayer.rotationPitch}, rotations);
    }

    public static float normalizeAngle(float angle) {
        return MathUtil.wrapAngleTo180_float((angle + 180.0F) % 360.0F - 180.0F);
    }

    public static float getPitchChange(final EntityLivingBase entity) {
        final double deltaX = entity.posX - MC.thePlayer.posX;
        final double deltaZ = entity.posZ - MC.thePlayer.posZ;
        final double deltaY = entity.posY - 2.2D + entity.getEyeHeight() - MC.thePlayer.posY;
        final double distanceXZ = MathUtil.sqrt_double(deltaX * deltaX + deltaZ * deltaZ);
        final double pitchToEntity = -Math.toDegrees(Math.atan(deltaY / distanceXZ));
        return -MathUtil.wrapAngleTo180_float(MC.thePlayer.rotationPitch - (float) pitchToEntity);
    }

    public static float getYawChange(final EntityLivingBase entity) {
        final double deltaX = entity.posX - MC.thePlayer.posX;
        final double deltaZ = entity.posZ - MC.thePlayer.posZ;
        double yawToEntity;

        if ((deltaZ < 0.0D) && (deltaX < 0.0D)) {
            yawToEntity = 90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX));
        } else {
            if ((deltaZ < 0.0D) && (deltaX > 0.0D)) {
                yawToEntity = -90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX));
            } else {
                yawToEntity = Math.toDegrees(-Math.atan(deltaX / deltaZ));
            }
        }

        return MathUtil.wrapAngleTo180_float(-(MC.thePlayer.rotationYaw - (float) yawToEntity));
    }

    public static int getBestWeapon(EntityLivingBase target) {
        final int originalSlot = MC.thePlayer.inventory.currentItem;
        int weaponSlot = -1;
        float weaponDamage = 1.0F;

        for (byte slot = 0; slot < 9; slot = (byte) (slot + 1)) {
            MC.thePlayer.inventory.currentItem = slot;
            final ItemStack itemStack = MC.thePlayer.getHeldItem();

            if (itemStack != null) {
                float damage = getItemDamage(itemStack);
                damage += EnchantmentHelper.func_152377_a(MC.thePlayer.getHeldItem(), target.getCreatureAttribute());

                if (damage > weaponDamage) {
                    weaponDamage = damage;
                    weaponSlot = slot;
                }
            }
        }

        if (weaponSlot != -1) {
            return weaponSlot;
        }

        return originalSlot;
    }

    private static float getItemDamage(ItemStack itemStack) {
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
                    damage = attributeModifier.getAmount() * 100.0D;
                }

                if (attributeModifier.getAmount() > 1.0D) {
                    return 1.0F + (float) damage;
                }

                return 1.0F;
            }
        }

        return 1.0F;
    }

    public static void damagePlayer(int damage) {
        /* capping it just in case anybody has an autism attack */
        if (damage < 1) {
            damage = 1;
        }

        if (damage > MathUtil.floor_double(MC.thePlayer.getMaxHealth())) {
            damage = MathUtil.floor_double(MC.thePlayer.getMaxHealth());
        }

        double offset = 0.0625;

        if (MC.thePlayer != null && MC.getNetHandler() != null && MC.thePlayer.onGround) {
            for (int i = 0; i <= ((3 + damage) / offset); i++) {
                MC.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(MC.thePlayer.posX,
                        MC.thePlayer.posY + offset, MC.thePlayer.posZ, false));
                MC.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(MC.thePlayer.posX,
                        MC.thePlayer.posY, MC.thePlayer.posZ, (i == ((3 + damage) / offset))));
            }
        }
    }

    public static double[] teleportToPosition(double[] startPosition, double[] endPosition, double setOffset,
                                              double slack, boolean extendOffset, boolean onGround) {
        boolean wasSneaking = false;

        if (MC.thePlayer.isSneaking()) {
            wasSneaking = true;
        }

        double startX = startPosition[0];
        double startY = startPosition[1];
        double startZ = startPosition[2];
        double endX = endPosition[0];
        double endY = endPosition[1];
        double endZ = endPosition[2];
        double distance = Math.abs(startX - startY) + Math.abs(startY - endY) + Math.abs(startZ - endZ);
        int count = 0;

        while (distance > slack) {
            distance = Math.abs(startX - endX) + Math.abs(startY - endY) + Math.abs(startZ - endZ);

            if (count > 120) {
                break;
            }

            double offset = extendOffset && (count & 0x1) == 0 ? setOffset + 0.15D : setOffset;
            double diffX = startX - endX;
            double diffY = startY - endY;
            double diffZ = startZ - endZ;

            if (diffX < 0.0D) {
                if (Math.abs(diffX) > offset) {
                    startX += offset;
                } else {
                    startX += Math.abs(diffX);
                }
            }

            if (diffX > 0.0D) {
                if (Math.abs(diffX) > offset) {
                    startX -= offset;
                } else {
                    startX -= Math.abs(diffX);
                }
            }

            if (diffY < 0.0D) {
                if (Math.abs(diffY) > offset) {
                    startY += offset;
                } else {
                    startY += Math.abs(diffY);
                }
            }

            if (diffY > 0.0D) {
                if (Math.abs(diffY) > offset) {
                    startY -= offset;
                } else {
                    startY -= Math.abs(diffY);
                }
            }

            if (diffZ < 0.0D) {
                if (Math.abs(diffZ) > offset) {
                    startZ += offset;
                } else {
                    startZ += Math.abs(diffZ);
                }
            }

            if (diffZ > 0.0D) {
                if (Math.abs(diffZ) > offset) {
                    startZ -= offset;
                } else {
                    startZ -= Math.abs(diffZ);
                }
            }

            if (wasSneaking) {
                MC.getNetHandler().addToSendQueue(
                        new C0BPacketEntityAction(MC.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
            }

            MC.getNetHandler().getNetworkManager()
                    .sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(startX, startY, startZ, onGround));
            count++;
        }

        if (wasSneaking) {
            MC.getNetHandler().addToSendQueue(
                    new C0BPacketEntityAction(MC.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
        }

        return new double[]{startX, startY, startZ};
    }

    public static boolean isBot(EntityLivingBase entity) {
        if (!entity.canBePushed()) {
            return true;
        }

        return false;
    }

    public static void shiftClick(final Item item) {
        for (int index = 9; index <= 36; ++index) {
            final ItemStack stack = MC.thePlayer.inventoryContainer.getSlot(index).getStack();
            if (stack != null && stack.getItem() == item) {
                MC.playerController.windowClick(0, index, 0, 1, MC.thePlayer);
                break;
            }
        }
    }

    public static boolean hotbarHas(final Item item) {
        for (int index = 0; index <= 36; ++index) {
            final ItemStack stack = MC.thePlayer.inventory.getStackInSlot(index);
            if (stack != null && stack.getItem() == item) {
                return true;
            }
        }
        return false;
    }

    public static void useFirst(final Item item) {
        for (int index = 0; index <= 8; ++index) {
            final ItemStack stack = MC.thePlayer.inventory.getStackInSlot(index);
            if (stack != null && stack.getItem() == item) {
                final int oldItem = MC.thePlayer.inventory.currentItem;
                MC.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(index));
                MC.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(MC.thePlayer.inventory.getCurrentItem()));
                MC.thePlayer.stopUsingItem();
                MC.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(oldItem));
                break;
            }
        }
    }

    public static void instantUseFirst(final Item item) {
        for (int index = 0; index <= 8; ++index) {
            final ItemStack stack = MC.thePlayer.inventory.getStackInSlot(index);
            if (stack != null && stack.getItem() == item) {
                final int oldItem = MC.thePlayer.inventory.currentItem;
                MC.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(index));
                MC.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(MC.thePlayer.inventory.getCurrentItem()));
                for (int x = 0; x <= 32; ++x) {
                    MC.getNetHandler().addToSendQueue(new C03PacketPlayer(MC.thePlayer.onGround));
                }
                MC.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                MC.thePlayer.stopUsingItem();
                MC.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(oldItem));
                break;
            }
        }
    }

    public static void UseFirstSoup() {
        for (int index = 0; index <= 8; ++index) {
            final ItemStack stack = MC.thePlayer.inventory.getStackInSlot(index);
            if (stack != null && stack.getItem() instanceof ItemSoup) {
                final int oldItem = MC.thePlayer.inventory.currentItem;
                MC.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(index));
                MC.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(MC.thePlayer.inventory.getCurrentItem()));
                MC.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(oldItem));
                break;
            }
        }
    }

    public static void dropFirst(final Item item) {
        for (int index = 0; index <= 8; ++index) {
            final ItemStack stack = MC.thePlayer.inventory.getStackInSlot(index);
            if (stack != null && stack.getItem() == item) {
                MC.playerController.windowClick(0, 36 + index, 1, 4, MC.thePlayer);
                break;
            }
        }
    }

    public static int getSlotID(final Item item) {
        for (int index = 0; index <= 36; ++index) {
            final ItemStack stack = MC.thePlayer.inventory.getStackInSlot(index);
            if (stack != null && stack.getItem() == item) {
                return index;
            }
        }
        return -1;
    }

    public static int countItem(final Item item) {
        int counter = 0;
        for (int index = 0; index <= 36; ++index) {
            final ItemStack stack = MC.thePlayer.inventory.getStackInSlot(index);
            if (stack != null && stack.getItem() == item) {
                counter += stack.stackSize;
            }
        }
        return counter;
    }

    public static boolean isPotion(final ItemStack stack, final Potion potion, final boolean splash) {
        if (stack == null) {
            return false;
        }
        if (!(stack.getItem() instanceof ItemPotion)) {
            return false;
        }
        final ItemPotion potionItem = (ItemPotion) stack.getItem();
        if (splash && !ItemPotion.isSplash(stack.getItemDamage())) {
            return false;
        }
        if (potionItem.getEffects(stack) == null) {
            return potion == null;
        }
        if (potion == null) {
            return false;
        }
        for (final Object o : potionItem.getEffects(stack)) {
            final PotionEffect effect = (PotionEffect) o;
            if (effect.getPotionID() == potion.getId()) {
                return true;
            }
        }
        return false;
    }

    public static void shiftClickPotion(final Potion effect, final boolean splash) {
        for (int index = 9; index <= 36; ++index) {
            final ItemStack stack = MC.thePlayer.inventoryContainer.getSlot(index).getStack();
            if (stack != null && isPotion(stack, effect, splash)) {
                MC.playerController.windowClick(MC.thePlayer.openContainer.windowId, index, 0, 1, MC.thePlayer);
                break;
            }
        }
    }

    public static void getPotion(final Potion effect, final boolean splash) {
        for (int index = 9; index <= 36; ++index) {
            final ItemStack stack = MC.thePlayer.inventoryContainer.getSlot(index).getStack();
            if (stack != null && isPotion(stack, effect, splash)) {
                MC.playerController.windowClick(MC.thePlayer.openContainer.windowId, index, 1, 2, MC.thePlayer);
                break;
            }
        }
    }

    public static void shiftClickSoup() {
        for (int index = 9; index <= 36; ++index) {
            final ItemStack stack = MC.thePlayer.inventoryContainer.getSlot(index).getStack();
            if (stack != null && stack.getItem() instanceof ItemSoup) {
                MC.playerController.windowClick(0, index, 0, 1, MC.thePlayer);
                break;
            }
        }
    }

    public static boolean hotbarHasPotion(final Potion effect, final boolean splash) {
        for (int index = 0; index <= 8; ++index) {
            final ItemStack stack = MC.thePlayer.inventory.getStackInSlot(index);
            if (stack != null && isPotion(stack, effect, splash)) {
                return true;
            }
        }
        return false;
    }

    public static boolean inventoryHasPotion(final Potion effect, final boolean splash) {
        for (int index = 0; index <= 36; ++index) {
            final ItemStack stack = MC.thePlayer.inventory.getStackInSlot(index);
            if (stack != null && isPotion(stack, effect, splash)) {
                return true;
            }
        }
        return false;
    }

    public static void swap(int slot, int hotbarNum) {
        Minecraft.getMinecraft().playerController.windowClick(Minecraft.getMinecraft().thePlayer.inventoryContainer.windowId, slot, hotbarNum, 2, Minecraft.getMinecraft().thePlayer);
    }

    public static void useFirstPotionSilent(final Potion effect, final boolean splash) {
        for (int index = 0; index <= 8; ++index) {
            final ItemStack stack = MC.thePlayer.inventory.getStackInSlot(index);
            if (stack != null && isPotion(stack, effect, splash)) {
                final int oldItem = MC.thePlayer.inventory.currentItem;
                MC.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(index));
                MC.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(MC.thePlayer.inventory.getCurrentItem()));
                MC.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(oldItem));
                break;
            }
        }
    }

    public static void instantUseFirstPotion(final Potion effect) {
        for (int index = 0; index <= 8; ++index) {
            final ItemStack stack = MC.thePlayer.inventory.getStackInSlot(index);
            if (stack != null && isPotion(stack, effect, false)) {
                final int oldItem = MC.thePlayer.inventory.currentItem;
                MC.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(index));
                MC.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(MC.thePlayer.inventory.getCurrentItem()));
                for (int x = 0; x <= 32; ++x) {
                    MC.getNetHandler().addToSendQueue(new C03PacketPlayer(MC.thePlayer.onGround));
                }
                MC.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                MC.thePlayer.stopUsingItem();
                MC.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(oldItem));
                break;
            }
        }
    }

    public static void dropFirstPotion(final Potion effect, final boolean splash) {
        for (int index = 0; index <= 8; ++index) {
            final ItemStack stack = MC.thePlayer.inventory.getStackInSlot(index);
            if (stack != null && isPotion(stack, effect, splash)) {
                MC.playerController.windowClick(0, 36 + index, 1, 4, MC.thePlayer);
                break;
            }
        }
    }

    public static int getPotionSlotID(final Potion effect, final boolean splash) {
        for (int index = 0; index <= 36; ++index) {
            final ItemStack stack = MC.thePlayer.inventory.getStackInSlot(index);
            if (stack != null && isPotion(stack, effect, splash)) {
                return index;
            }
        }
        return -1;
    }

    public static int countPotion(final Potion effect, final boolean splash) {
        int counter = 0;
        for (int index = 0; index <= 36; ++index) {
            final ItemStack stack = MC.thePlayer.inventory.getStackInSlot(index);
            if (stack != null && isPotion(stack, effect, splash)) {
                counter += stack.stackSize;
            }
        }
        return counter;
    }

    public static int countInInventory(EntityPlayer player, Item item, int md) {
        int count = 0;
        for (int i = 0; i < player.inventory.mainInventory.length; i++)
            if ((player.inventory.mainInventory[i] != null) && item.equals(player.inventory.mainInventory[i].getItem()) && ((md == -1) || (player.inventory.mainInventory[i].getMetadata() == md)))
                count += player.inventory.mainInventory[i].stackSize;
        return count;
    }

    public static boolean hotbarIsFull() {
        for (int index = 0; index <= 8; ++index) {
            final ItemStack stack = MC.thePlayer.inventory.getStackInSlot(index);
            if (stack == null) {
                return false;
            }
        }
        return true;
    }

    public static void oldGaurdianBypass() {
        if (timer.hasReached(1000)) {
            mc.thePlayer.sendQueue.addToSendQueue(
                    new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX + mc.thePlayer.motionX,
                            mc.thePlayer.posY - 110, mc.thePlayer.posZ + mc.thePlayer.motionZ, true));
            timer.reset();
        }
    }

    public void doRealClick() throws AWTException {
        Robot robot = new Robot();

        if (Minecraft.getMinecraft().inGameHasFocus) {
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
        }
    }

    public boolean isOnSameTeam(Entity entity, boolean check) {
        boolean team = false;

        if (check && entity instanceof EntityPlayer) {
            String n = entity.getDisplayName().getFormattedText();
            if (n.startsWith('\u00a7' + "f") && !n.equalsIgnoreCase(entity.getName()))
                team = (n.substring(0, 6)
                        .equalsIgnoreCase(mc.thePlayer.getDisplayName().getFormattedText().substring(0, 6)));
            else
                team = (n.substring(0, 2)
                        .equalsIgnoreCase(mc.thePlayer.getDisplayName().getFormattedText().substring(0, 2)));
        }

        return team;
    }

    public float getDirection() {
        float yaw = this.MC.thePlayer.rotationYaw;

        if (this.MC.thePlayer.moveForward < 0) {
            yaw += 180;
        }

        float forward = 1;

        if (this.MC.thePlayer.moveForward < 0) {
            forward = -0.5F;
        } else if (this.MC.thePlayer.moveForward > 0) {
            forward = 0.5F;
        }

        if (this.MC.thePlayer.moveStrafing > 0) {
            yaw -= 90 * forward;
        }

        if (this.MC.thePlayer.moveStrafing < 0) {
            yaw += 90 * forward;
        }

        yaw *= 0.017453292F;
        return yaw;
    }

    public double getBaseMoveSpeed() {
        double baseSpeed = 0.2873;

        if (this.MC.thePlayer.isPotionActive(Potion.moveSpeed)) {
            int amplifier = this.MC.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1 + 0.2 * (amplifier + 1);
        }

        return baseSpeed;
    }

    public boolean isValidEntityType(Entity e, boolean players, boolean passive, boolean hostile) {
        if (passive && (e instanceof EntityAnimal || e instanceof EntityVillager))
            return true;

        if (players && e instanceof EntityPlayer)
            return true;

        if (hostile && (e instanceof EntityMob || e instanceof EntitySlime || e instanceof EntityWither
                || e instanceof EntityDragon))
            return true;

        return false;
    }

    public float getSpeed() {
        return (float) Math.sqrt((this.MC.thePlayer.motionX * this.MC.thePlayer.motionX)
                + (this.MC.thePlayer.motionZ * this.MC.thePlayer.motionZ));
    }

    public void setSpeed(final double speed) {
        MC.thePlayer.motionX = -(Math.sin(getDirection()) * speed);
        MC.thePlayer.motionZ = Math.cos(getDirection()) * speed;
    }

    public boolean isMoving(Entity ent) {
        return ent == Minecraft.getMinecraft().thePlayer
                ? ((Minecraft.getMinecraft().thePlayer.moveForward != 0
                || Minecraft.getMinecraft().thePlayer.moveStrafing != 0))
                : (ent.motionX != 0 || ent.motionZ != 0);
    }

    public List<EntityPlayer> getPlayerList() {
        NetHandlerPlayClient var4 = MC.thePlayer.sendQueue;
        ArrayList<EntityPlayer> list = new ArrayList<EntityPlayer>();
        Collection players = GuiPlayerTabOverlay.field_175252_a
                .sortedCopy((Iterable) var4.getPlayerInfoMap().keySet().iterator());

        for (Object o : players) {
            NetworkPlayerInfo info = (NetworkPlayerInfo) o;

            if (info == null) {
                continue;
            }

            list.add(MC.theWorld.getPlayerEntityByName(info.getGameProfile().getName()));
        }

        return list;
    }

    public boolean isNameBot(EntityLivingBase entityLivingBase) {
        List<String> playerNames = getPlayerNames();

        if (playerNames.contains(entityLivingBase.getName())) {
            return false;
        }

        return true;
    }

    public List<String> getPlayerNames() {
        List<String> playerNames = new ArrayList<>();
        playerNames.clear();
        Collection<NetworkPlayerInfo> playerInfo = MC.thePlayer.sendQueue.getPlayerInfoMap().values();

        for (NetworkPlayerInfo info : playerInfo) {
            String name = info.getGameProfile().getName();

            if (name.equalsIgnoreCase(MC.thePlayer.getName())) {
                continue;
            }

            playerNames.add(name);
        }

        return playerNames;
    }

    private String getName(final UUID uuid) {
        try {
            final URL url = new URL("https://namemc.com/profile/" + uuid.toString());
            final URLConnection connection = url.openConnection();
            connection.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.7; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            final BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String name = null;
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.contains("<title>")) {
                    name = line.split("&")[0].trim().replaceAll("<title>", "").replaceAll("</title>", "")
                            .replaceAll("\u2013 Minecraft Profile \u2013 NameMC", "")
                            .replaceAll("\u00e2\u20ac\u201c Minecraft Profile \u00e2\u20ac\u201c NameMC", "");
                }
            }

            reader.close();
            return name;
        } catch (Exception e) {
            e.printStackTrace();
            return uuid + "is an unknown player";
        }
    }

    public boolean isSoup(final ItemStack stack) {
        if (stack != null) {
            if (stack.getItem() instanceof ItemSoup)
                return true;
        }
        return false;
    }

    public boolean isBowing() {
        if (mc.thePlayer.isUsingItem() && mc.thePlayer.onGround
                && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBow
                && mc.thePlayer.getItemInUseDuration() > 1)
            return true;
        return false;
    }

}

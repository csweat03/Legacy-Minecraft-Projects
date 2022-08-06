package com.fbiclient.fbi.impl.cheats.combat;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;

import me.tojatta.api.utilities.angle.Angle;
import me.tojatta.api.utilities.angle.AngleUtility;
import me.valkyrie.api.value.Val;
import me.valkyrie.api.value.types.child.Child;
import me.valkyrie.api.value.types.constrain.Clamp;
import me.valkyrie.api.value.types.constrain.Increment;
import me.xx.api.cheat.Category;
import me.xx.api.cheat.Cheat;
import me.xx.api.cheat.CheatManifest;
import me.xx.api.event.Event;
import me.xx.api.event.Register;
import com.fbiclient.fbi.client.anticheat.Anticheats;
import com.fbiclient.fbi.client.events.game.TickEvent;
import com.fbiclient.fbi.client.events.player.UpdateMotionEvent;
import com.fbiclient.fbi.client.framework.helper.IChatBuilder;
import com.fbiclient.fbi.client.framework.helper.ICheats;
import com.fbiclient.fbi.impl.Brisk;
import me.xx.utility.MathUtility;
import me.xx.utility.Stopwatch;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

@CheatManifest(label = "Killaura", description = "Attack valid entities in range of player", category = Category.COMBAT, handles = {"ka", "aura", "forcefield"})
public class Killaura extends Cheat implements ICheats, IChatBuilder {

    public Method event = Method.TICK;

    public Stopwatch attackTimer = new Stopwatch();
    public Stopwatch duraTimer = new Stopwatch();
    public Stopwatch botTimer = new Stopwatch();

    private Optional<Entity> target;

    @Val(label = "APS", aliases = {"Attacks_Per_Second", "CPS",
            "Speed"}, description = "How fast the killaura attacks")
    @Clamp(min = "1", max = "20")
    public int aps = 12;

    @Val(label = "Range", aliases = {"Reach", "Distance"}, description = "Range to combat entities at")
    @Clamp(min = "3", max = "6")
    @Increment("0.25")
    public double reach = 4;

    @Increment("10")
    @Clamp(min = "10", max = "180")
    @Val(label = "FOV")
    public int fov = 180;

    @Increment("10")
    @Clamp(min = "0", max = "200")
    @Val(label = "Ticks_Existed")
    public int ticks = 10;

    @Val(label = "Mode")
    public Mode mode = Mode.SINGULAR;

    @Val(label = "Criticals", aliases = {"Crits"}, description = "Criticals hits everytime")
    public boolean criticals = true;

    @Child("Criticals")
    @Val(label = "Mode", aliases = {"Crit_Mode"}, description = "The critical mode that should be used")
    public Criticals criticalMode = Criticals.PACKET;

    @Val(label = "Block_Hit", aliases = {"Autoblock",
            "Block"}, description = "If the killaura should automatically block")
    public boolean autoblock = true;
    @Child("Block_Hit")
    @Val(label = "Range", aliases = {"Reach", "Distance"}, description = "Range to start blocking")
    @Clamp(min = "3", max = "10")
    @Increment("0.5")
    public double blockRange = 6;

    @Val(label = "Durability")
    public boolean dura = false;

    @Val(label = "Smooth")
    public boolean smooth = true;

    @Val(label = "Silent")
    public boolean silent = true;

    /*Player values*/
    @Val(label = "Players", aliases = {"Humans"}, description = "Decides what entity to hit depending on some trait")
    public boolean players = true;
    /*Child players*/
    @Child("Players")
    @Val(label = "Friends", aliases = {"Friendly", "Friendlies"}, description = "If killaura should combat friends")
    public boolean friends = true;
    @Child("Players")
    @Val(label = "Teams")
    public boolean teams = false;

    /*Mob values*/
    @Val(label = "Mobs", aliases = {"Mob"}, description = "If killaura should combat mobs")
    private boolean mobs = false;
    /*Child mobs*/
    @Child("Mobs")
    @Val(label = "Passive", aliases = {"Animals"}, description = "If killaura should combat passive mobs")
    boolean passives = true;
    @Child("Mobs")
    @Val(label = "Neutral", aliases = {"Harmless"}, description = "If killaura should combat neutral mobs")
    boolean neutrals = true;
    @Child("Mobs")
    @Val(label = "Hostile", aliases = {"Monsters"}, description = "If killaura should combat hostile mobs")
    boolean hostiles = true;

    @Val(label = "Invisibles", description = "Attack Invisibles")
    public boolean invisible = false;

    @Val(label = "Walls", description = "Hit through walls")
    public boolean walls = true;

    private AngleUtility angleUtility = new AngleUtility(10, 50, 10, 50, false);

    @Register
    public void handleUpdates(UpdateMotionEvent event) {
        if (event.getType() == Event.Type.PRE) {
            target = findNextTarget();
            if (!AutoHeal.healing && !SCAFFOLD.getState()) {
                target.ifPresent(entity -> {
                    rotate(target.get(), event);
                });
            }
            if (mode == Mode.SINGULAR && this.event == Method.PRE) {
                target.ifPresent(t -> attack(t));
                block();
            }
        } else {
            if (mode == Mode.SINGULAR && this.event == Method.POST) {
                target.ifPresent(t -> attack(t));
                block();
            }
        }
    }

    @Register
    public void handleTicking(TickEvent event) {
        setSuffix(mode.name());
        if (mode == Mode.SINGULAR && this.event == Method.TICK) {
            target.ifPresent(t -> attack(t));
            block();
        }
        if (mode == Mode.TICK) {
            target.ifPresent(t -> attack(t));
            block();
        }
    }

    public void attack(Entity entity) {
        if (!canAttack())
            return;
        switch (mode) {
            case SINGULAR:
                if (mc.thePlayer.isBlocking())
                    if (Anticheats.findAnticheat() != Anticheats.AGC || Anticheats.findAnticheat() != Anticheats.WATCHDOG)
                        mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(
                                C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.UP));
                if (duraTimer.hasReached(370L, true) && dura) {
                    attack(entity, false);
                    attack(entity, true);
                    return;
                }
                attack(entity, criticals);
                break;
            case TICK:
                if (dura) {
                    CONTAINER_HELPER.swap(9, mc.thePlayer.inventory.currentItem);
                    attack(entity, false);
                    attack(entity, false);
                    attack(entity, true);
                    CONTAINER_HELPER.swap(9, mc.thePlayer.inventory.currentItem);
                    attack(entity, false);
                    attack(entity, true);
                } else {
                    if (mc.thePlayer.isBlocking())
                        mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(
                                C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.UP));
                    attack(entity, criticals);
                }
                break;
        }
        renderParticles();
    }

    private void block() {
        if (shouldBlock() && !mc.thePlayer.isBlocking()) {
            if (Anticheats.findAnticheat() == Anticheats.WATCHDOG) {
                mc.thePlayer.setItemInUse(mc.thePlayer.getHeldItem(), 71999);
            } else {
                mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem());
            }
        }
    }

    public void attack(Entity entity, boolean crit) {
        if (crit)
            critical();

        mc.thePlayer.swingItem();
        mc.getNetHandler().addToSendQueue(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
        this.attackTimer.reset();
    }

    public void critical() {
        if (!this.criticals) {
            return;
        }
        switch (this.criticalMode) {
            case PACKET: {
                if (Anticheats.findAnticheat() == Anticheats.WATCHDOG && !mc.thePlayer.onGround)
                    return;
                for (double offset : new double[]{0.04, 0.0, 0.03, 0.0}) {
                    mc.thePlayer.sendQueue.getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(
                            mc.thePlayer.posX, mc.thePlayer.posY + offset, mc.thePlayer.posZ, false));
                }
                break;
            }
            case JUMP: {
                if (mc.thePlayer.onGround)
                    mc.thePlayer.jump();
                break;
            }
        }
    }

    public Predicate<Entity> qualifies(boolean autoblock) {
        return (entity) -> {
            return entity != null
                    && entity instanceof EntityLivingBase
                    && entity != mc.thePlayer
                    && EntityType.qualifies(entity, this)
                    && (entity.isEntityAlive() || Double.isNaN(((EntityLivingBase) entity).getHealth()))
                    && (entity.isInvisible() ? invisible : true)
                    && entity.ticksExisted > ticks
                    && (double) entity.getDistanceToEntity(mc.thePlayer) < (autoblock ? this.blockRange : this.reach)
                    && (!this.friends || !Brisk.INSTANCE.getFriendManager().isFriend(entity.getUniqueID()))
                    && (mc.thePlayer.isOnSameTeam(entity) ? !teams : true)
                    && (walls ? true : mc.thePlayer.canEntityBeSeen(entity))
                    && ANGLE_HELPER.isWithinFOV(entity, fov);
        };
    }

    public boolean canAttack() {
        long delay = mode == Mode.TICK ? 493 : (1000L / MathUtility.getRandom(aps - 4, aps));
        return !AutoHeal.healing
                && this.attackTimer.hasReached(delay)
                && !SCAFFOLD.getState();
        //&& requireMouse ? mc.gameSettings.keyBindAttack.isKeyDown() : true;
    }

    public boolean shouldBlock() {
        return this.autoblock
                && mc.theWorld.loadedEntityList.stream()
                .filter(this.qualifies(true))
                .anyMatch(entity -> entity instanceof EntityLivingBase)
                && this.mc.thePlayer.getHeldItem() != null
                && this.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword
                && !AutoHeal.healing
                && !SCAFFOLD.getState();
    }

    public void rotate(Entity entity, UpdateMotionEvent e) {
        if (canAttack()) {
            float[] rot = ANGLE_HELPER.getAngles(entity, true);

            if (mc.thePlayer.getDistanceToEntity(entity) <= 0.5) {
                e.setYaw(90F);
            } else {
                Angle angle = new Angle(rot[0], rot[1]);
                Angle smoothedAngle = angleUtility.smoothAngle(angle, new Angle(mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch));

                ANGLE_HELPER.rotate(e, smooth ? smoothedAngle.getYaw() : rot[0], smooth ? smoothedAngle.getPitch() : rot[1], silent);
            }
        }
    }

    public void renderParticles() {
        Optional<Entity> target = findNextTarget();
        target.ifPresent(entity -> {
            float sharpLevel = EnchantmentHelper.getSharpnessLevel(mc.thePlayer.getHeldItem(),
                    ((EntityLivingBase) entity).getCreatureAttribute());
            boolean vanillaCrit = mc.thePlayer.fallDistance > 0.0f && !mc.thePlayer.onGround && !mc.thePlayer.isOnLadder()
                    && !mc.thePlayer.isInWater() && !mc.thePlayer.isPotionActive(Potion.blindness)
                    && mc.thePlayer.ridingEntity == null;
            if ((criticals && criticalMode == Criticals.PACKET) || vanillaCrit) {
                mc.thePlayer.onCriticalHit(entity);
            }
            if (sharpLevel > 0.0f) {
                mc.thePlayer.onEnchantmentCritical(entity);
            }
        });
    }

    public Optional<Entity> findNextTarget() {
        return mc.theWorld.loadedEntityList.stream()
                .filter(this.qualifies(false))
                .sorted(Comparator.comparingDouble(entity -> mc.thePlayer.getDistanceToEntity(entity))).findFirst();
    }

    public int getAps() {
        return MathUtility.getRandom(aps - 4, aps);
    }

    public enum Criticals {
        PACKET, JUMP
    }

    public enum Mode {
        SINGULAR, TICK
    }

    public enum Method {
        PRE, POST, TICK
    }

    public enum TPMode {
        GROUND, AIR
    }

    public enum Sorting {
        ANGLE, DISTANCE, HEALTH, ARMOR
    }

    public enum EntityType {
        PLAYER, PASSIVE, NEUTRAL, HOSTILE;

        public static boolean qualifies(Entity entity, Killaura killaura) {
            EntityType entityType = getType(entity);
            return (entityType == EntityType.PLAYER && killaura.players)
                    || (entityType == EntityType.HOSTILE && killaura.mobs && killaura.hostiles)
                    || (entityType == EntityType.NEUTRAL && killaura.mobs && killaura.neutrals)
                    || (entityType == EntityType.PASSIVE && killaura.mobs && killaura.passives);
        }

        public static EntityType getType(Entity entity) {
            if (entity instanceof EntityPlayer) {
                return EntityType.PLAYER;
            }
            if (entity instanceof EntityWither || entity instanceof EntityDragon) {
                return EntityType.HOSTILE;
            }
            if (entity instanceof EntityWolf) {
                return ((EntityWolf) entity).isAngry() ? EntityType.HOSTILE
                        : (((EntityWolf) entity).isTamed() ? EntityType.PASSIVE : EntityType.NEUTRAL);
            }
            if (entity instanceof EntityPigZombie) {
                return ((EntityPigZombie) entity).isAngry() ? EntityType.HOSTILE : EntityType.NEUTRAL;
            }
            if (entity instanceof EntityGolem) {
                return EntityType.NEUTRAL;
            }
            return (entity instanceof EntityAnimal || entity instanceof EntityAmbientCreature
                    || entity instanceof EntityAgeable) ? EntityType.PASSIVE : EntityType.HOSTILE;
        }
    }
}

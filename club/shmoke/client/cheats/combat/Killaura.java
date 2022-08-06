package club.shmoke.client.cheats.combat;

import club.shmoke.api.cheat.Cheat;
import club.shmoke.api.cheat.data.Property;
import club.shmoke.api.cheat.helpers.AngleHelper;
import club.shmoke.api.event.interfaces.EventListener;
import club.shmoke.client.Client;
import club.shmoke.client.cheats.movement.Flight;
import club.shmoke.client.events.entity.RespawnEvent;
import club.shmoke.client.events.other.SendPacketEvent;
import club.shmoke.client.events.update.UpdatePlayerEvent;
import club.shmoke.client.util.GameLogger;
import club.shmoke.client.util.math.DelayHelper;
import club.shmoke.client.util.math.MathHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;

import java.util.ArrayList;

public class Killaura extends Cheat {

    public static ArrayList<Entity> attackList = new ArrayList<>();
    public Property<Boolean> autoblock = new Property<>(this, "Autoblock", false);
    public Property<Boolean> lockView = new Property<>(this, "LockView", false);
    public Property<Boolean> critical = new Property<>(this, "Criticals", false);
    public Property<Boolean> death = new Property<>(this, "Death", false);
    public Property<Integer> aps = new Property<>(this, "APS", 13, 1, 20, 1);
    public Property<Integer> waitdelay = new Property<>(this, "Wait Delay", 100, 0, 1000, 10);
    public Property<Integer> ticks = new Property<>(this, "Ticks Existed", 50, 0, 1000, 10);
    public Property<Integer> FOV = new Property<>(this, "FOV", 360, 10, 360, 10);
    public Property<Integer> sdelay = new Property<>(this, "Switch Delay", 10, 1, 20, 1);
    public Property<Double> reach = new Property<>(this, "Reach", 4.5, 1.0, 6.0, 0.1);
    public Property<String> targets = new Property<>(this, "Targets");
    public Property<Boolean> players = new Property<>(this, "Players", true);
    public Property<Boolean> animals = new Property<>(this, "Passive", false);
    public Property<Boolean> monsters = new Property<>(this, "Hostile", false);
    public Property<Boolean> teams = new Property<>(this, "Teams", false);
    public Property<Boolean> friends = new Property<>(this, "Friends", false);
    public Property<Boolean> invisibles = new Property<>(this, "Invisibles", false);
    private DelayHelper attackTimer = new DelayHelper(), switchTimer = new DelayHelper(), waitTimer = new DelayHelper();

    public Killaura() {
        super("Killaura", Type.COMBAT);
        this.description = "Attacks the closest entity.";
    }

    @EventListener
    public void onSend(SendPacketEvent e) {
        if (e.getPacket() instanceof C02PacketUseEntity && mc.thePlayer.isCollidedVertically && critical.getValue()) {
            C02PacketUseEntity packet = (C02PacketUseEntity) e.getPacket();
            if (packet.getAction() == C02PacketUseEntity.Action.ATTACK && !flight.getState())
                critical();
        }
    }

    @EventListener
    public void onRespawn(RespawnEvent event) {
        if (death.getValue() && getState()) {
            toggle();
            GameLogger.log("\2476Killaura \2477has been disabled due to death.", false, GameLogger.Type.INFO);
        }
    }

    @EventListener
    public void onUpdate(UpdatePlayerEvent e) {
        Entity ent;
        if (mc.theWorld == null || scaffold.getState()) return;
        switch (e.type) {
            case PRE:
                setupTargets();
                ent = (attackList.size() > 0 ? attackList.get(0) : AngleHelper.getClosestEntity());
                if (isValidTarget(ent)) {
                    if (waitTimer.hasReached(waitdelay.getValue())) {
                        aim(e, ent);
                        aura(ent);
                    }
                } else
                    waitTimer.reset();
                break;
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
        attackTimer.reset();
        switchTimer.reset();
        attackList.clear();
    }

    public void setupTargets() {
        mc.theWorld.loadedEntityList.forEach(o -> {
            Entity e = o;

            if (isValidTarget(e) && !attackList.contains(e))
                attackList.add(e);
            if (!isValidTarget(e) && attackList.contains(e))
                attackList.remove(e);
            if (mc.thePlayer.getDistanceToEntity(e) > 40)
                attackList.remove(e);
        });
    }

    public boolean isValidTarget(Entity e) {
        boolean valid = e != null && e != mc.thePlayer && e instanceof EntityLivingBase && !e.isDead && e.ticksExisted >= ticks.getValue()
                && mc.thePlayer.getDistanceToEntity(e) <= reach.getValue() && ANGLE_HELPER.isWithinFOV(e, FOV.getValue())
                && !Client.INSTANCE.getFriendManager().has(e.getName()) && (invisibles.getValue() || !e.isInvisible()) &&
                ENTITY_HELPER.isValidEntityType(e, players.getValue(), animals.getValue(), monsters.getValue())
                && !ENTITY_HELPER.isOnSameTeam(e, !teams.getValue());
        return valid;
    }

    private void aura(Entity ent) {
        if (switchTimer.hasReached(100 * sdelay.getValue()) && attackList.size() > 1 && attackList.indexOf(ent) < attackList.size()) {
            ent = attackList.get(attackList.indexOf(ent) + 1);
            switchTimer.reset();
        }
        attack(ent);
    }

    private void aim(UpdatePlayerEvent event, Entity ent) {
        float[] rot = ANGLE_HELPER.kyleIsActuallyFuckingAwesomeForGivingTheseRotationsToMe(ent, true);

        if (lockView.getValue()) {
            mc.thePlayer.rotationYaw = rot[0];
            mc.thePlayer.rotationPitch = rot[1];
        }
        event.yaw = rot[0];
        event.pitch = rot[1];
    }

    private void block() {
        if (autoblock.getValue() && mc.thePlayer.getHeldItem() != null
                && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword)
            mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem());
    }

    @EventListener(value = 4)
    public void onPacket(SendPacketEvent e) {
        if (!getState()) return;
        if (mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword && autoblock.getValue()) {
            if (attackTimer.hasReached((long) (1000 / aps.getValue() - 8))) {
                if (e.getPacket() instanceof C08PacketPlayerBlockPlacement) {
                    e.setPacket(null);
                    e.setCancelled(true);
                }
            }
        }
    }

    private void attack(Entity ent) {
        block();
        if (!attackTimer.hasReached((long) (1000 / aps.getValue() - MathHelper.getRandom(1, 5))))
            return;
        if (ent != null && isValidTarget(ent)) {
            attackTimer.reset();
            swing();
            mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(ent, C02PacketUseEntity.Action.ATTACK));
        }
    }

    private void swing() {
        mc.thePlayer.swingItem();
    }

    private void critical() {
        C04(mc.thePlayer.posX, mc.thePlayer.posY + 0.033, mc.thePlayer.posZ, false);
        C04(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false);
        C04(mc.thePlayer.posX, mc.thePlayer.posY + 0.00000022, mc.thePlayer.posZ, false);
        C04(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false);
    }

    private void C04(double x, double y, double z, boolean onGround) {
        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, onGround));
    }
}

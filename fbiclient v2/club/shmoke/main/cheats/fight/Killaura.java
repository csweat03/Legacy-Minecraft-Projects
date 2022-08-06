package club.shmoke.main.cheats.fight;

import club.shmoke.api.cheat.Category;
import club.shmoke.api.cheat.Cheat;
import club.shmoke.api.cheat.property.Property;
import club.shmoke.api.event.EventHandler;
import club.shmoke.api.utility.utilities.Timer;
import club.shmoke.main.events.UpdateEvent;
import me.tojatta.api.utilities.angle.Angle;
import me.tojatta.api.utilities.angle.AngleUtility;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;

import java.util.Comparator;

/**
 * @author Christian
 */
public class Killaura extends Cheat {

    public final Property<AttackMode> mode = new Property<>(this, "Mode", AttackMode.SWITCH);
    public final Property<SortingMode> sortingtype = new Property<>(this, "Sorting Type", SortingMode.DISTANCE);

    private final Property<Boolean>
            block = new Property<>(this, "Block", false),
            silent = new Property<>(this, "Silent", true),
            smooth = new Property<>(this, "Smooth", true),
            raycast = new Property<>(this, "Raycast", false);
    private final Property<Integer>
            aps = new Property<>(this, "APS", 12, 1, 20, 1),
            ticks = new Property<>(this, "Ticks", 50, 0, 1000, 10),
            sdelay = new Property<>(this, "Switch Delay", 10, 0, 50, 1);
    public final Property<Double>
            range = new Property<>(this, "Range", 4.2, 3.0, 6.0, 0.1);
    private final Property<Boolean>
            player = new Property<>(this, "Player", true),
            passive = new Property<>(this, "Passive", false),
            hostile = new Property<>(this, "Hostile", false);

    private EntityLivingBase current;
    private AngleUtility angleUtility = new AngleUtility(10, 50, 10, 50);

    private final Timer switchTimer = new Timer(), attackTimer = new Timer();

    public Killaura() {
        super("Killaura", 0, Category.FIGHT, "Attacks entities in-range.");
    }

    @EventHandler
    public void onUpdate(UpdateEvent event) {
        setSuffix(mode.getValue() + "");

        range.setValue(6.0);
        aps.setValue(20);

        raycast.setValue(true);

        switch (event.getType()) {
            case PRE:
                Object[] objects = mc.theWorld.loadedEntityList.stream().filter(this::isValidTarget)
                        .sorted(Comparator.comparingDouble(entity -> sortingtype.getValue() == SortingMode.DISTANCE ?
                                entity.getDistanceToEntity(mc.thePlayer) :
                                ((EntityLivingBase) entity).getHealth())).toArray();

                if (!isValidTarget(current))
                    current = null;

                if (objects.length > 0 && current == null) {
                    for (Object object : objects) {
                        if (rotationUtility.raycast((EntityLivingBase) object) == null) continue;
                        current = (EntityLivingBase) rotationUtility.raycast((EntityLivingBase) object);
                    }
                    switchTimer.reset();
                }

                if (current == null)
                    return;

                float[] rot;

                if (raycast.getValue()) {
                    rot = rotationUtility.getRotationsNeeded(current);
                    rot[0] += 2 - (Math.random() * 4);
                    rot[1] += 10 - (Math.random() * 20);
                } else {
                    rot = rotationUtility.kyleIsDopeASF(current, true);
                }

                Angle angle = new Angle(rot[0], rot[1]);
                Angle smoothedAngle = angleUtility.smoothAngle(angle, new Angle(mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch));

                playerUtility.look(event, smooth.getValue() ? smoothedAngle.getYaw() : rot[0], smooth.getValue() ? smoothedAngle.getPitch() : rot[1], true);
                break;
            case POST:
                if (current == null)
                    return;

                //block();

                int randomHitDelay = (1000 / aps.getValue()) - mathUtility.getRandom(-25, 25);

                if (attackTimer.hasReached(randomHitDelay)) {
                    attackTimer.reset();

//                    block();

//
//                    E-Z Bureau Autoblock Bypass!!!
//
//                    try {
//                        Thread.sleep(new Random().nextInt(25));
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }

                    mc.thePlayer.swingItem();
                    mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(current, C02PacketUseEntity.Action.ATTACK));

                    mc.thePlayer.onCriticalHit(current);
                    mc.thePlayer.onEnchantmentCritical(current);

                    current = null;

                }


                break;
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
        current = null;
    }

    private boolean isValidTarget(Entity e) {
        return playerUtility.isValidTarget(e) && mc.thePlayer.getDistanceToEntity(e) <= range.getValue() && e.ticksExisted >= ticks.getValue()
                && playerUtility.isValidEntityType(e, player.getValue(), passive.getValue(), hostile.getValue());
    }

    private void block() {
        if (mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword)
            mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem());
        //mc.thePlayer.setItemInUse(mc.thePlayer.getHeldItem(), 10000);
    }

    public enum AttackMode {
        SINGLE, SWITCH, MULTI
    }

    public enum SortingMode {
        HEALTH, DISTANCE
    }
}
package club.shmoke.client.cheats.environment;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathUtil;
import club.shmoke.client.Client;
import club.shmoke.api.cheat.Cheat;
import club.shmoke.api.cheat.data.Property;
import club.shmoke.api.cheat.helpers.interfaces.IHelper;
import club.shmoke.api.event.Event;
import club.shmoke.client.events.update.UpdatePlayerEvent;
import club.shmoke.api.event.interfaces.EventListener;

import java.util.ArrayList;

public class FastInteract extends Cheat {
    public static ArrayList<EntityPlayer> attackList = new ArrayList<>();
    public static ArrayList<EntityPlayer> targets = new ArrayList<>();
    public static int target;
    public static int eat = 0;
    Property<Boolean> place = new Property(this, "Place", true);
    Property<Boolean> breaker = new Property(this, "Break", false);
    Property<Boolean> bow = new Property(this, "Bow", false);
    Property<Boolean> consume = new Property(this, "Consume", false);
    Property<String> bowSub = new Property(this, "FastBow");
    Property<Boolean> aimbot = new Property(this, "Aimbot", false);
    Property<Mode> bowMode = new Property(this, "Mode", Mode.FAST);
    Property<String> consumeSub = new Property(this, "FastConsume");
    Property<Mode2> consumeMode = new Property(this, "Mode", Mode2.FAST);

    public FastInteract() {
        super("Fast Interact", Type.ENVIRONMENT);
        this.description = "Interact with blocks faster.";
    }

    @EventListener
    public void onUpdate(UpdatePlayerEvent e) {
        if (bow.getValue())
            bow(e);
        if (consume.getValue())
            consume(e);
        if (breaker.getValue())
            mc.thePlayer.addPotionEffect(new PotionEffect(3, Integer.MAX_VALUE, 1, false, false));
        if (place.getValue())
            mc.rightClickDelayTimer = 0;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        attackList.clear();
        targets.clear();
        target = 0;
        if (place.getValue())
            mc.rightClickDelayTimer = 6;

        if (breaker.getValue())
            mc.thePlayer.removePotionEffect(3);
    }

    private void consume(UpdatePlayerEvent event) {
        switch (event.type) {
            case PRE:
                switch (consumeMode.getValue()) {
                    case GAURDIAN:
                        if (isChompinAway())
                            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
                        break;

                    case AAC:
                        if (mc.thePlayer.isEating() && mc.thePlayer.getItemInUse().getItem() != null
                                && mc.thePlayer.getItemInUse().getItem() instanceof ItemFood) {
                            ++eat;
                            if (eat < 20 && mc.thePlayer.onGround)
                                mc.timer.timerSpeed = 1.35f;
                            else
                                mc.timer.timerSpeed = 1f;
                            if (eat >= 30)
                                eat = 0;
                        } else
                            mc.timer.timerSpeed = 1F;
                        break;

                    case NCP:
                        if (isChompinAway())
                            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
                        break;

                    case FAST:
                        if (isChompinAway())
                            for (int i = 0; i < 100; ++i)
                                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(
                                        mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, mc.thePlayer.onGround));
                        break;

                    default:
                        break;
                }

                break;

            default:
                break;
        }
    }

    private boolean isChompinAway() {
        return mc.thePlayer.getItemInUseDuration() > 0 && mc.thePlayer.getItemInUse().getItem() != null
                && (mc.thePlayer.getItemInUse().getItem() instanceof ItemFood
                || mc.thePlayer.getItemInUse().getItem() instanceof ItemPotion);
    }

    private void bow(UpdatePlayerEvent event) {
        if (aimbot.getValue()) aimbot(event);
        switch (bowMode.getValue()) {
            case GAURDIAN:
                if (ENTITY_HELPER.isBowing()) {
                    mc.thePlayer.stopUsingItem();
                    mc.thePlayer.setItemInUse(mc.thePlayer.getCurrentEquippedItem(), 1);
                    mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getCurrentEquippedItem());
                    mc.thePlayer.setSprinting(false);
                    mc.thePlayer.isInWeb = true;
                    for (int i = 0; i < 20; ++i)
                        mc.thePlayer.sendQueue.addToSendQueue(
                                new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ,
                                        mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, mc.thePlayer.onGround));

                    mc.thePlayer.sendQueue.addToSendQueue(
                            new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                    IHelper.ENTITY_HELPER.oldGaurdianBypass();
                }
                break;
            case FAST:
                if (mc.thePlayer.onGround) {
                    mc.thePlayer.setItemInUse(mc.thePlayer.getCurrentEquippedItem(), 1);
                    mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getCurrentEquippedItem());
                    for (int i = 0; i < 20; ++i) {
                        mc.thePlayer.sendQueue.addToSendQueue(
                                new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ,
                                        mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, mc.thePlayer.onGround));
                    }

                    mc.thePlayer.sendQueue.addToSendQueue(
                            new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                }
                break;
        }
    }

    private void aimbot(UpdatePlayerEvent event) {
        if (event.type == Event.Type.PRE) {
            for (EntityPlayer e : mc.theWorld.playerEntities) {
                if (!targets.contains(e))
                    targets.add(e);
            }

            if (target >= attackList.size())
                target = 0;

            for (EntityPlayer e : mc.theWorld.playerEntities) {
                if ((isValidTarget(e)) && !attackList.contains(e))
                    attackList.add(e);
                if (!isValidTarget(e) && attackList.contains(e))
                    attackList.remove(e);
            }
            sortTargets();
            if (!attackList.isEmpty() && attackList.get(target) != null && isValidTarget(attackList.get(target)) && mc.thePlayer.isUsingItem()
                    && mc.thePlayer.getCurrentEquippedItem().getItem() != null
                    && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBow) {
                int bowCurrentCharge = mc.thePlayer.getItemInUseDuration();
                float bowVelocity = (bowCurrentCharge / 20.0f);
                bowVelocity = (bowVelocity * bowVelocity + bowVelocity * 2.0f) / 3.0f;
                bowVelocity = MathUtil.clamp_float(bowVelocity, 0.0F, 1.0F);

                double v = bowVelocity * 3.0F;
                double g = 0.05000000074505806D;

                if (bowVelocity < 0.1)
                    return;

                if (bowVelocity > 1.0f)
                    bowVelocity = 1.0f;

                double xDistance = attackList.get(target).posX - mc.thePlayer.posX
                        + (attackList.get(target).posX - attackList.get(target).lastTickPosX)
                        * ((bowVelocity) * 10.0f);
                double zDistance = attackList.get(target).posZ - mc.thePlayer.posZ
                        + (attackList.get(target).posZ - attackList.get(target).lastTickPosZ)
                        * ((bowVelocity) * 10.0f);

                final float trajectoryTheta90 = (float) (Math.atan2(zDistance, xDistance) * 180.0 / 3.141592653589793)
                        - 90.0f;
                final float bowTrajectory = (float) -Math
                        .toDegrees(getLaunchAngle(attackList.get(target), v, g)) - 3;

                mc.thePlayer.rotationYaw = trajectoryTheta90;
                mc.thePlayer.rotationPitch = bowTrajectory;
            }
        }
    }

    public boolean isValidTarget(final EntityPlayer entity) {
        boolean valid = false;
        boolean isFriend = Client.INSTANCE.getFriendManager().getContents().containsKey(entity.getName());
        if (entity.isInvisible())
            valid = true;

        if (isFriend || !mc.thePlayer.canEntityBeSeen(entity))
            return false;

        if (entity instanceof EntityPlayer) {
            valid = (entity != null && mc.thePlayer.getDistanceToEntity(entity) <= 64 && entity != mc.thePlayer
                    && entity.isEntityAlive() && !isFriend);
            if (entity.isInvisible())
                valid = false;
        }
        return valid;
    }

    public void sortTargets() {
        attackList.sort((ent1, ent2) -> {
            double d1 = mc.thePlayer.getDistanceToEntity(ent1);
            double d2 = mc.thePlayer.getDistanceToEntity(ent2);
            return (d1 < d2) ? -1 : (d1 == d2) ? 0 : 1;
        });
    }

    private float getLaunchAngle(EntityLivingBase targetEntity, double v, double g) {
        double yDif = targetEntity.posY + targetEntity.getEyeHeight() / 2.0F
                - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
        double xDif = targetEntity.posX - mc.thePlayer.posX;
        double zDif = targetEntity.posZ - mc.thePlayer.posZ;

        double xCoord = Math.sqrt(xDif * xDif + zDif * zDif);

        return theta(v + 2, g, xCoord, yDif);
    }

    private float theta(double v, double g, double x, double y) {
        double yv = 2.0D * y * (v * v);
        double gx = g * (x * x);
        double g2 = g * (gx + yv);
        double insqrt = v * v * v * v - g2;
        double sqrt = Math.sqrt(insqrt);

        double numerator = v * v + sqrt;
        double numerator2 = v * v - sqrt;

        double atan1 = Math.atan2(numerator, g * x);
        double atan2 = Math.atan2(numerator2, g * x);

        return (float) Math.min(atan1, atan2);
    }

    private enum Mode {
        GAURDIAN, FAST
    }

    private enum Mode2 {
        GAURDIAN, NCP, AAC, FAST
    }
}

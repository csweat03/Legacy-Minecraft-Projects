package club.shmoke.api.utility.utilities;

import club.shmoke.Client;
import club.shmoke.main.events.UpdateEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;

/**
 * @author Christian
 */
public class PlayerUtility {

    private Minecraft mc = Minecraft.getMinecraft();

    public double getBaseMoveSpeed() {
        double base = 0.285;

        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            PotionEffect potion = mc.thePlayer.getActivePotionEffect(Potion.moveSpeed);
            base *= 1 + (0.2 * (potion.getAmplifier() + 1));
        }

        return base;
    }

    public void updatePosition(double horizontal, double vertical) {
        double x = -MathHelper.sin(mc.thePlayer.getDirection()) * horizontal, z = MathHelper.cos(mc.thePlayer.getDirection()) * horizontal;
        mc.thePlayer.setPosition(mc.thePlayer.posX + x, mc.thePlayer.posY + vertical, mc.thePlayer.posZ + z);
    }

    public boolean isBowing(int delay) {
        return mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemBow && mc.thePlayer.getItemInUseDuration() > delay && mc.thePlayer.onGround;
    }


    public void look(UpdateEvent event, float yaw, float pitch, boolean silent) {
        if (event.getType() != UpdateEvent.Type.PRE) return;

        event.setYaw(yaw);
        event.setPitch(pitch);
        if (!silent) {
            mc.thePlayer.rotationYaw = yaw;
            mc.thePlayer.rotationPitch = pitch;
        }
    }

    public void attack(Entity entity) {
        mc.thePlayer.swingItem();
        mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
    }

    public boolean isValidEntityType(Entity e, boolean players, boolean passive, boolean hostile) {
        players = passive = hostile = true;
        if (passive && (e instanceof EntityAnimal || e instanceof EntityVillager))
            return true;

        if (players && e instanceof EntityPlayer)
            return true;

        if (hostile && (e instanceof EntityMob || e instanceof EntitySlime || e instanceof EntityDragon))
            return true;

        return false;
    }

    public boolean isValidTarget(Entity entity) {
        return entity != null && entity != mc.thePlayer && entity instanceof EntityLivingBase && (entity.isEntityAlive() || Double.isNaN(((EntityLivingBase) entity).getHealth())) && (entity instanceof EntityPlayer && !Client.GET.FRIEND_MANAGER.getFriends().containsKey(entity));
    }

    public void dropItem(int slot) {
        mc.playerController.windowClick(0, slot, 1, 4, mc.thePlayer);
    }

}

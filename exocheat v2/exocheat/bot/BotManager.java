package club.shmoke.main.exocheat.bot;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;
import java.util.UUID;

/**
 * @author Christian
 */
public class BotManager {

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private UUID uuid;
    private Location location;
    private String name = random();
    private Player target;
    private Player bot;
    private EntityPlayer entityBot;

    public BotManager(UUID uuid, Location location, Player target) {
        this.uuid = uuid;
        this.location = location;
        this.target = target;
    }

    private static String random() {

        int count = Math.max(7, new Random().nextInt(16));

        StringBuilder builder = new StringBuilder();

        while (count-- != 0) {

            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());

            builder.append(ALPHA_NUMERIC_STRING.charAt(character));

        }

        return builder.toString();

    }

    public void create() {
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer world = ((CraftWorld) target.getWorld()).getHandle();
        GameProfile profile = new GameProfile(UUID.randomUUID(), name);

        entityBot = new EntityPlayer(server, world, profile, new PlayerInteractManager(world));
        bot = entityBot.getBukkitEntity().getPlayer();

        bot.setTicksLived(1000 + new Random().nextInt(1000));

        bot.setItemInHand(new ItemStack(new Random().nextInt(420), new Random().nextInt(16)));
        bot.setTotalExperience(new Random().nextInt(10000));
        bot.getInventory().setHelmet(new ItemStack(Math.max(298, new Random().nextInt(419))));
        bot.getInventory().setChestplate(new ItemStack(Math.max(298, new Random().nextInt(419))));
        bot.getInventory().setLeggings(new ItemStack(Math.max(298, new Random().nextInt(419))));
        bot.getInventory().setBoots(new ItemStack(Math.max(298, new Random().nextInt(419))));
        bot.setLevel(new Random().nextInt(10000));
        bot.setSneaking(new Random().nextInt(1000) % 2 == 0);
        bot.setSprinting(new Random().nextInt(1000) % 2 == 0);
        bot.setFallDistance(new Random().nextInt(100));
        //if (new Random().nextInt(1000) % 2 == 0)
        //bot.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Math.max(50, new Random().nextInt(200)), Math.max(1 + new Random().nextInt(50), 1), false, false));
        if (new Random().nextInt(1000) % 2 == 0)
            bot.setDisplayName("");

        double yaw = Math.toRadians(target.getLocation().getYaw());
        double x = -Math.sin(yaw) * 2 * -1;
        double z = Math.cos(yaw) * 2 * -1;

        Location randomLocation = location.add(x - 0.25 + Math.min(0.5, Math.random()), -3 + (Math.random() * 6), z - 0.25 + Math.min(0.5, Math.random()));

        entityBot.setLocation(randomLocation.getX(), randomLocation.getY(), randomLocation.getZ(), randomLocation.getYaw(), randomLocation.getPitch());

        bot.setDisplayName(null);

        spawn();
    }

    public void spawn() {
        PlayerConnection playerConnection = ((CraftPlayer) target).getHandle().playerConnection;
        playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, entityBot));
        playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.UPDATE_DISPLAY_NAME, entityBot));
        playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.UPDATE_GAME_MODE, entityBot));
        playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.UPDATE_LATENCY, entityBot));
        playerConnection.sendPacket(new PacketPlayOutNamedEntitySpawn(entityBot));
        playerConnection.sendPacket(new PacketPlayOutEntityHeadRotation(entityBot, (byte) ((entityBot.yaw * 256 / 360) - 50 + new Random().nextInt(100))));
    }

    public void destroy() {
        PlayerConnection playerConnection = ((CraftPlayer) target).getHandle().playerConnection;
        playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.UPDATE_DISPLAY_NAME, entityBot));
        playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.UPDATE_GAME_MODE, entityBot));
        playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.UPDATE_LATENCY, entityBot));
        playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, entityBot));
        playerConnection.sendPacket(new PacketPlayOutEntityDestroy(entityBot.getId()));
    }

    public UUID getUUID() {
        return uuid;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Player getTarget() {
        return target;
    }

    public void setTarget(Player target) {
        this.target = target;
    }

    public Player getBot() {
        return bot;
    }

    public EntityPlayer getEntityBot() {
        return entityBot;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}

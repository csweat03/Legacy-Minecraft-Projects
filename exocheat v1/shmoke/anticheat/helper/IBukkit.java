package club.shmoke.anticheat.helper;

import club.shmoke.anticheat.helper.interfaces.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

public interface IBukkit {

    AlertHelper alert = new AlertHelper();
    MoveHelper move = new MoveHelper();
    PlayerHelper player = new PlayerHelper();
    InventoryHelper inventory = new InventoryHelper();
    BlockHelper block = new BlockHelper();

    // Move Methods
    default void moveLocation(double x, double y, double z) {
        move.moveLocation(x, y, z);
    }

    default void teleport(double x, double y, double z) {
        move.teleport(x, y, z);
    }

    default Location getLocation() {
        return move.getLocation();
    }

    default double getX() {
        return move.getX();
    }

    default double getY() {
        return move.getY();
    }

    default double getZ() {
        return move.getZ();
    }

    default double getYaw() {
        return move.getYaw();
    }

    default double getPitch() {
        return move.getPitch();
    }

    default double maxXZ() {
        return move.maxXZ();
    }

    default double maxY() {
        return move.maxY();
    }

    default double getSpeed(PlayerMoveEvent event) {
        return move.getSpeed(event);
    }

    // Player Methods
    default Player getPlayer() {
        return player.getPlayer();
    }

    default void setPlayer(Event event) {
        player.setPlayer(event);
    }

    default World getWorld() {
        return getPlayer().getWorld();
    }

    // Inventory Methods
    default boolean isHoldingWeapon() {
        return inventory.isHoldingWeapon();
    }

    default boolean isHoldingFood() {
        return inventory.isHoldingFood();
    }

    default ItemStack getItemInHand() {
        return inventory.getItemInHand();
    }

    default Material getMaterialInHand() {
        return inventory.getMaterialInHand();
    }

    default int getSlot() {
        return inventory.getSlot();
    }

    default void setSlot(int slot) {
        inventory.setSlot(slot);
    }

    default void incrementSlot() {
        inventory.switchSlot();
    }

    // Block Methods
    default boolean isOnStair() {
        return block.isOnStair();
    }

    default boolean isOnLiquid() {
        return block.isOnLiquid();
    }

    default boolean isOnClimable() {
        return block.isOnClimable();
    }

    default boolean isColliding(Material material, boolean extended) {
        return block.isColliding((extended ? 1 : 0.31), material);
    }

    default boolean isColliding(Material material, double extension) {
        return block.isColliding(extension, material);
    }

    default boolean isColliding(boolean extended) {
        return block.isColliding((extended ? 1 : 0.31));
    }

    default Material getBlockUnderPlayer(double dist) {
        return block.getBlockUnderPlayer(dist);
    }

    default boolean isInLiquid(double distance) {
        return block.isInLiquid(distance);
    }

    default boolean isInGround() {
        return block.isInGround(0.1);
    }

    default boolean onGround() {
        return block.onGround();
    }
}

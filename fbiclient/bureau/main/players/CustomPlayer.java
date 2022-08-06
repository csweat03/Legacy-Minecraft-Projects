package com.fbiclient.bureau.main.players;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class CustomPlayer {

    private Player player;

    public CustomPlayer(PlayerMoveEvent playerMoveEvent) {
        this.player = playerMoveEvent.getPlayer();
    }

    public Player getPlayer() {
        return player;
    }

    public MovementData getMovement(PlayerMoveEvent playerMoveEvent) {
        return new MovementData(playerMoveEvent);
    }

    public Logic getLogic(PlayerMoveEvent playerMoveEvent) {
        return new Logic(playerMoveEvent);
    }

    public Inventory getInventory() {
        return new Inventory(player);
    }

    public BlockData getBlockData(Block block) {
        return new BlockData(block);
    }

    public class Logic {
        private PlayerMoveEvent playerMoveEvent;

        public Logic(PlayerMoveEvent playerMoveEvent) {
            this.playerMoveEvent = playerMoveEvent;
        }

        public int getPotionLevel(Player player, PotionEffectType type) {
            List<PotionEffect> potionEffectList = new ArrayList<>(player.getActivePotionEffects());
            for (PotionEffect potionEffect : potionEffectList) {
                if (potionEffect.getType().getId() == type.getId()) {
                    return potionEffect.getAmplifier() + 1;
                }
            }
            return 0;
        }

        public boolean onGround() {
            Location to = getMovement(playerMoveEvent).getTo().clone(), from = getMovement(playerMoveEvent).getFrom().clone();

            if (Math.abs(to.getX() - from.getX()) <= 0.25 || Math.abs(to.getZ() - from.getZ()) <= 0.25) {
                double[] x = {0.25, -0.25}, z = {0.25, -0.25};

                for (double xS : x) {
                    for (double zS : z) {
                        Block gay = player.getLocation().clone().subtract(xS, 0.05, zS).getBlock();
                        Material mat = gay.getType();
                        return (getBlockData(gay).isSolid() && mat.isSolid() && mat.isBlock() && mat != Material.AIR) || mat == Material.TRAP_DOOR;
                    }
                }
            }
            Block gay = player.getLocation().clone().subtract(0, 0.05, 0).getBlock();
            Material mat = gay.getType();
            return (getBlockData(gay).isSolid() && mat.isSolid() && mat.isBlock() && mat != Material.AIR) || mat == Material.TRAP_DOOR;
        }
    }

    public class Inventory {
        private Player player;

        public Inventory(Player player) {
            this.player = player;
        }

        public boolean isHolding(InventoryItemType type) {
            boolean yesyes = false;
            Material is = getMaterialInHand();
            switch (type) {
                case WEAPON:
                    switch (is) {
                        case DIAMOND_SWORD:
                        case GOLD_SWORD:
                        case IRON_SWORD:
                        case STONE_SWORD:
                        case WOOD_SWORD:
                        case BOW:
                            yesyes = true;
                            break;
                    }
                    break;
                case EATABLE:
                    switch (is) {
                        case APPLE:
                        case BAKED_POTATO:
                        case BREAD:
                        case CAKE_BLOCK:
                        case CARROT:
                        case COOKED_CHICKEN:
                        case COOKED_FISH:
                        case COOKED_MUTTON:
                        case PORK:
                        case COOKED_RABBIT:
                        case COOKIE:
                        case GOLDEN_APPLE:
                        case MELON:
                        case MUSHROOM_SOUP:
                        case POISONOUS_POTATO:
                        case POTATO:
                        case PUMPKIN_PIE:
                        case RABBIT_STEW:
                        case RAW_BEEF:
                        case RAW_CHICKEN:
                        case RAW_FISH:
                        case MUTTON:
                        case GRILLED_PORK:
                        case RABBIT:
                        case ROTTEN_FLESH:
                        case SPIDER_EYE:
                        case COOKED_BEEF:
                            yesyes = true;
                            break;
                    }
                    break;
            }
            return yesyes;
        }

        public ItemStack getItemInHand() {
            return getInventory().getItemInHand();
        }

        public Material getMaterialInHand() {
            return getItemInHand().getType();
        }

        public PlayerInventory getInventory() {
            return getPlayer().getInventory();
        }

        public int getSlot() {
            return getInventory().getHeldItemSlot();
        }

        public void setSlot(int slot) {
            getInventory().setHeldItemSlot(slot);
        }

        public void incrementSlot() {
            setSlot(getSlot() == 8 ? 0 : getSlot() + 1);
        }
    }

    public class BlockData {

        private Block block;

        public BlockData(Block block) {
            this.block = block;
        }

        public Location getLocation() {
            return block.getLocation();
        }

        public Block getBlock() {
            return block;
        }

        public boolean hasGravity() {
            switch (block.getTypeId()) {
                case 12:
                case 13:
                    return true;
            }
            return false;
        }

        public boolean isSolid() {
            switch (block.getTypeId()) {
                case 0:
                case 6:
                case 8:
                case 9:
                case 10:
                case 11:
                case 27:
                case 28:
                case 30:
                case 31:
                case 32:
                case 34:
                case 37:
                case 38:
                case 39:
                case 40:
                case 50:
                case 51:
                case 53:
                case 55:
                case 59:
                case 63:
                case 64:
                case 65:
                case 66:
                case 67:
                case 68:
                case 69:
                case 70:
                case 71:
                case 72:
                case 75:
                case 76:
                case 77:
                case 83:
                case 85:
                case 93:
                case 94:
                case 96:
                case 101:
                case 102:
                case 104:
                case 105:
                case 106:
                case 107:
                case 108:
                case 109:
                case 111:
                case 115:
                case 117:
                case 119:
                case 127:
                case 128:
                case 131:
                case 132:
                case 134:
                case 135:
                case 136:
                case 140:
                case 141:
                case 142:
                case 143:
                case 144:
                case 147:
                case 148:
                case 149:
                case 150:
                case 156:
                case 157:
                case 160:
                case 163:
                case 164:
                case 167:
                case 171:
                case 175:
                case 176:
                case 180:
                case 183:
                case 184:
                case 185:
                case 186:
                case 187:
                case 188:
                case 189:
                case 190:
                case 191:
                case 192:
                case 193:
                case 194:
                case 195:
                case 196:
                case 197:
                    return false;
            }
            return true;
        }

    }

    public class MovementData {

        private PlayerMoveEvent playerMoveEvent;

        public MovementData(PlayerMoveEvent playerMoveEvent) {
            this.playerMoveEvent = playerMoveEvent;
        }

        private Location getTo() {
            return playerMoveEvent.getTo();
        }

        private Location getFrom() {
            return playerMoveEvent.getFrom();
        }

        public double getXDifference() {
            return Math.abs(getTo().getX() - getFrom().getX());
        }

        public double getYDifference() {
            return Math.abs(getTo().getY() - getFrom().getY());
        }

        public double getZDifference() {
            return Math.abs(getTo().getZ() - getFrom().getZ());
        }

        public double getSpeed() {
            return (getXDifference() * getXDifference() + getZDifference() * getZDifference());
        }
    }

    public enum InventoryItemType {
        WEAPON, EATABLE
    }

}

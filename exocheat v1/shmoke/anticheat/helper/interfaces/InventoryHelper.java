package club.shmoke.anticheat.helper.interfaces;

import club.shmoke.anticheat.helper.IBukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class InventoryHelper implements IBukkit {

    public boolean isHoldingWeapon() {
        Material is = getMaterialInHand();
        return is == Material.DIAMOND_SWORD
                || is == Material.GOLD_SWORD
                || is == Material.IRON_SWORD
                || is == Material.STONE_SWORD
                || is == Material.WOOD_SWORD
                || is == Material.BOW;
    }

    public boolean isHoldingFood() {
        Material is = getMaterialInHand();
        return is == Material.APPLE
                || is == Material.BAKED_POTATO
                || is == Material.BREAD
                || is == Material.CAKE_BLOCK
                || is == Material.CARROT
                || is == Material.COOKED_CHICKEN
                || is == Material.COOKED_FISH
                || is == Material.COOKED_MUTTON
                || is == Material.PORK
                || is == Material.COOKED_RABBIT
                || is == Material.COOKIE
                || is == Material.GOLDEN_APPLE
                || is == Material.MELON
                || is == Material.MUSHROOM_SOUP
                || is == Material.POISONOUS_POTATO
                || is == Material.POTATO
                || is == Material.PUMPKIN_PIE
                || is == Material.RABBIT_STEW
                || is == Material.RAW_BEEF
                || is == Material.RAW_CHICKEN
                || is == Material.RAW_FISH
                || is == Material.MUTTON
                || is == Material.GRILLED_PORK
                || is == Material.RABBIT
                || is == Material.ROTTEN_FLESH
                || is == Material.SPIDER_EYE
                || is == Material.COOKED_BEEF;
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

    public void setSlot(int slot) { getInventory().setHeldItemSlot(slot);
    }

    public void switchSlot() {
        if (getSlot() == 8)
            setSlot(0);
        else
            setSlot(getSlot() + 1);
    }
}

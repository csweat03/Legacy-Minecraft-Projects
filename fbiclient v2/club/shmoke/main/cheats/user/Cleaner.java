package club.shmoke.main.cheats.user;

import club.shmoke.api.cheat.Category;
import club.shmoke.api.cheat.Cheat;
import club.shmoke.api.cheat.property.Property;
import club.shmoke.api.event.EventHandler;
import club.shmoke.api.utility.utilities.Timer;
import club.shmoke.main.events.UpdateEvent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Cleaner extends Cheat {

    private final Set<Integer> whitelisted = new HashSet<>();
    private final Timer timer = new Timer();

    private Property<Integer> delay = new Property<>(this, "Delay", 100, 0, 1000, 1);

    public Cleaner() {
        super("Cleaner", 0, Category.USER, "Cleans your inventory of all 'junk' items");
        Arrays.stream(new int[]{276, 267, 283, 268, 272}).forEach(whitelisted::add);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        timer.reset();
    }

    @EventHandler
    public void onUpdate(UpdateEvent event) {
        for (int i = 9; i < 45; i++) {
            ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (itemStack != null && !isWhitelisted(itemStack) && timer.hasReached(delay.getValue())) {
                timer.reset();
                playerUtility.dropItem(i);
            }
        }
    }

    private boolean isWhitelisted(ItemStack itemStack) {
        int i;
        try {
            i = Item.getIdFromItem(itemStack.getItem());
        } catch (Exception ex) {
            i = 0;
        }
        return whitelisted.contains(i);
    }

}

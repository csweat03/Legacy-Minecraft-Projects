package club.shmoke.api.friend;

import club.shmoke.api.utility.utilities.Logger;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

import java.util.HashMap;
import java.util.Map;

public class FriendManager {

    private Minecraft mc = Minecraft.getMinecraft();

    private Map<EntityPlayer, String> friends = new HashMap<>();

    public void updateFriendsRegistry(String name, String alias) {
        EntityPlayer player = getEntityByName(name);

        if (player == null) {
            Logger.log("\247cCould not locate customizable_gui by the name of: " + name + ".", Logger.Level.ERROR);
        } else {
            if (friends.containsKey(player))
                removeFriend(name);
            else
                addFriend(name, alias);
        }

    }

    private void addFriend(String name, String alias) {
        EntityPlayer player = getEntityByName(name);

        if (player == null) {
            Logger.log("\247cCould not locate customizable_gui by the name of: " + name + ".", Logger.Level.ERROR);
        } else {
            friends.remove(player);
            friends.put(player, alias);
            Logger.log("\247c" + player.getName() + " \2477has been added to the friends list" + (alias.isEmpty() || alias.equals(name) ? "" : "\247c under the alias " + alias) + "\2477!", Logger.Level.INFO);
        }
    }

    private void removeFriend(String name) {
        EntityPlayer player = getEntityByName(name);

        if (player == null) {
            Logger.log("\247cCould not locate customizable_gui by the name of: " + name + ".", Logger.Level.ERROR);
        } else {
            friends.remove(player);
            Logger.log("\247c" + player.getName() + " \2477has been removed from the friends list!", Logger.Level.INFO);
        }
    }

    public EntityPlayer getEntityByName(String name) {
        for (EntityPlayer player : mc.theWorld.playerEntities) {
            if (player.getName().equalsIgnoreCase(name)) return player;
        }
        return null;
    }

    public Map<EntityPlayer, String> getFriends() {
        return friends;
    }

}

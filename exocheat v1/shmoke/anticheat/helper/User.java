package club.shmoke.anticheat.helper;

import org.bukkit.entity.*;

public class User
{
    private Player player;
    
    public User(Player player) {
        this.player = player;
    }
    
    public Player getPlayer() {
        return player;
    }
}

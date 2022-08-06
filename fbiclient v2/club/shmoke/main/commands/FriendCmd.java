package club.shmoke.main.commands;

import club.shmoke.Client;
import club.shmoke.api.command.Command;
import org.jetbrains.annotations.NotNull;

public class FriendCmd extends Command {

    public FriendCmd() {
        super("Friend", ".friend <name> <alias>", new String[]{"friend"});
    }

    @Override
    public void dispatch(@NotNull String[] args, @NotNull String message) {
        if (args.length == 1 || args.length == 2) {
            switch (args.length) {
                case 1:
                    Client.GET.FRIEND_MANAGER.updateFriendsRegistry(args[0], args[0]);
                    break;
                case 2:
                    Client.GET.FRIEND_MANAGER.updateFriendsRegistry(args[0], args[1]);
                    break;
            }
        }
    }
}

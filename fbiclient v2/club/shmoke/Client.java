package club.shmoke;


import club.shmoke.api.cheat.CheatManager;
import club.shmoke.api.command.CommandManager;
import club.shmoke.api.friend.FriendManager;
import club.shmoke.api.utility.utilities.AuthenticationUtility;
import club.shmoke.api.utility.utilities.FontUtility;
import club.shmoke.main.gui.customizable_gui.GuiElements;

/**
 * @author Christian
 */
public enum Client {

    GET;

    public final String
            NAME = "FBI",
            VERSION = "alpha-013",
            AUTHOR = "QSharp",
            TITLE = NAME + " " + VERSION + " | " + AUTHOR;

    public final String[] ICONS = new String[]
            {
                    "icons/icon_16x16.png",
                    "icons/icon_32x32.png"
            };

    public final FontUtility FONT_UTILITY = new FontUtility();
    public final CheatManager CHEAT_MANAGER = new CheatManager();
    public final CommandManager COMMAND_MANAGER = new CommandManager();
    public final FriendManager FRIEND_MANAGER = new FriendManager();
    public final GuiElements PLAYER_GUI = new GuiElements();

    public void initialize() {
        FONT_UTILITY.initialize();
        CHEAT_MANAGER.initialize();
        COMMAND_MANAGER.initialize();

        AuthenticationUtility authenticationUtility = new AuthenticationUtility("horizonclient@outlook.com", "tf4\\~dGy;BTp=e#/_zWf;Scp>-n~HymD!K:2%*jpun<d:\\KAz~W,HF:*C/3n.72z");
        authenticationUtility.start();

    }
}

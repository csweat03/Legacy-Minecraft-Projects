package club.shmoke.client;

import club.shmoke.api.account.AccountManager;
import club.shmoke.api.anticheat.AnticheatManager;
import club.shmoke.api.cheat.CheatManager;
import club.shmoke.api.command.CommandManager;
import club.shmoke.api.event.EventManager;
import club.shmoke.api.theme.ThemeManager;
import club.shmoke.client.cheats.visual.Overlay;
import club.shmoke.client.preset.ConfigManager;
import club.shmoke.client.ui.click.GuiClick;
import club.shmoke.client.ui.click.themes.horizon.HorizonTheme;
import club.shmoke.client.ui.overlay.notification.NotificationManager;
import club.shmoke.client.ui.overlay.tab.HorizonTab;
import club.shmoke.client.util.manage.FriendManager;
import club.shmoke.client.util.render.Styler;
import club.shmoke.client.util.render.font.FontManager;
import jaco.mp3.player.MP3Player;
import org.lwjgl.opengl.Display;

import java.awt.*;

public enum Client {
    INSTANCE;

    public Color color = new Color(254, 91, 53, 185);
    public Styler styler;
    private CommandManager commandManager;
    private CheatManager cheatManager;
    private FriendManager friendManager;
    private AccountManager accountManager;
    private AnticheatManager acManager;
    private FontManager fontManager;
    private ConfigManager configManager;
    private NotificationManager notificationManager;
    private ThemeManager themeManager;
    private GuiClick clickGUI;
    private MP3Player player;
    private HorizonTab tabGui = new HorizonTab();

    public void hook() {
        player = new MP3Player();
        themeManager = new ThemeManager();
        commandManager = new CommandManager();
        cheatManager = new CheatManager();
        friendManager = new FriendManager();
        accountManager = new AccountManager();
        acManager = new AnticheatManager();
        fontManager = new FontManager();
        configManager = new ConfigManager();
        notificationManager = new NotificationManager();
        Display.setTitle("");
        themeManager.loadThemes();
        cheatManager.setup();
        commandManager.setup();
        configManager.setup();
        fontManager.loadFonts();
        styler = new Styler();
        clickGUI = new GuiClick();
        clickGUI.setTheme(new HorizonTheme());
        clickGUI.getTheme().insert();
        if (!cheatManager.get(Overlay.class).getState())
            cheatManager.get(Overlay.class).toggle();
    }

    public void terminate() {
        EventManager.shutdown();
    }

    public String label() {
        return "Horizon";
    }

    public String build() {
        return "1.4";
    }

    public String id() {
        return "HORIZON";
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public CheatManager getCheatManager() {
        return cheatManager;
    }

    public FriendManager getFriendManager() {
        return friendManager;
    }

    public AccountManager getAccountManager() {
        return accountManager;
    }

    public AnticheatManager getAnticheatManager() {
        return acManager;
    }

    public FontManager getFontManager() {
        return fontManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public NotificationManager getNotificationManager() {
        return notificationManager;
    }

    public ThemeManager getThemeManager() {
        return themeManager;
    }

    public Styler getStyler() {
        return styler;
    }

    public HorizonTab getTab() {
        return tabGui;
    }

    public MP3Player getPlayer() {
        return player;
    }

    public void setColor(int red, int green, int blue) {
        color = new Color(red, green, blue);
    }
}

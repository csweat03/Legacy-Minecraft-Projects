package com.fbiclient.fbi.impl;

import java.io.File;

import com.fbiclient.fbi.impl.gui.clickable.impl.RGBSlider;
import com.fbiclient.fbi.impl.gui.hud.tabbed.TabbedGui;
import com.fbiclient.fbi.impl.management.AccountManager;
import com.fbiclient.fbi.impl.management.CheatManager;
import com.fbiclient.fbi.impl.management.CommandManager;
import com.fbiclient.fbi.impl.management.FriendManager;
import com.fbiclient.fbi.impl.management.ProfileManager;

public enum Brisk {

    INSTANCE;

    public final String NAME = "Brisk", VERSION = "v0.3";

    private CheatManager cheatManager;
    private CommandManager commandManager;
    private FriendManager friendManager;
    public ProfileManager configManager;
    public final AccountManager ACCOUNT_MANAGER = new AccountManager();

    public RGBSlider slider = new RGBSlider();

    private File directory;

    public void hook(final File gameDirectory) {
        this.directory = new File(gameDirectory + File.separator + NAME + File.separator);
        if (!this.directory.exists() && !this.directory.mkdirs()) {
            System.exit(0);
        }
        this.commandManager = new CommandManager(this.directory);
        this.cheatManager = new CheatManager(this.directory);
        this.friendManager = new FriendManager(this.directory);
        this.configManager = new ProfileManager();
        this.cheatManager.setup();
        this.friendManager.setup();
        this.configManager.setup();
        TabbedGui.INSTANCE.init();
    }

    public File getDirectory() {
        return this.directory;
    }

    public CheatManager getCheatManager() {
        return this.cheatManager;
    }

    public CommandManager getCommandManager() {
        return this.commandManager;
    }

    public FriendManager getFriendManager() {
        return this.friendManager;
    }

    public ProfileManager getConfigManager() {
        return configManager;
    }

}

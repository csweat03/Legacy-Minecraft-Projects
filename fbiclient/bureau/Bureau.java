package com.fbiclient.bureau;

import club.shmoke.main.API;
import club.shmoke.main.Plugin;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.fbiclient.bureau.api.alert.AlertManagment;
import com.fbiclient.bureau.api.command.CommandRegistry;
import com.fbiclient.bureau.api.check.CheckRegistry;
import com.fbiclient.bureau.main.events.EventRegistration;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Bureau extends Plugin {

    private static API api;
    private static Bureau bureau;

    private ProtocolManager protocolManager;

    /* Registry Fields */
    private final CommandRegistry COMMAND_REGISTRY = new CommandRegistry();
    private final CheckRegistry CHECK_REGISTRY = new CheckRegistry();
    /* Registry Fields */

    @Override
    public void onStartup() {
        bureau = this;

        api = new API(this);

        Bukkit.getPluginManager().registerEvents(new AlertManagment(), this);

        new EventRegistration(this);

        COMMAND_REGISTRY.initialize();
        CHECK_REGISTRY.initialize();
    }

    @Override
    public void onShutdown() {
        ProtocolLibrary.getProtocolManager().removePacketListeners(this);
    }

    @Override
    public API API() {
        return api;
    }

    public CommandRegistry getCommandRegistry() {
        return COMMAND_REGISTRY;
    }

    public CheckRegistry getCheckRegistry() {
        return CHECK_REGISTRY;
    }

    public boolean hasProtocolLib() {
        org.bukkit.plugin.Plugin pl = Bukkit.getPluginManager().getPlugin("ProtocolLib");
        return pl != null && pl.isEnabled();
    }

    public ProtocolManager getProtocolManager() {
        if (protocolManager == null && hasProtocolLib()) protocolManager = ProtocolLibrary.getProtocolManager();
        return protocolManager;
    }

    public static Bureau getBureau() {
        return bureau;
    }

    public static JavaPlugin getPlugin() {
        return api.getPlugin();
    }
}

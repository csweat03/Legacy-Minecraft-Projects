package com.fbiclient.bureau.api.command;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;

public abstract class Command implements CommandExecutor, Listener {

    private String label, syntax;
    private String[] triggers;
    private boolean runnable;

    protected abstract void onTrigger(String label, String[] args, CommandSender sender);

    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings) {
        onTrigger(s, strings, commandSender);
        return true;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setSyntax(String syntax) {
        this.syntax = syntax;
    }

    public void setTriggers(String[] alias) {
        this.triggers = alias;
    }

    public void setRunnable(boolean runnable) {
        this.runnable = runnable;
    }

    public String getLabel() {
        return label;
    }

    public String getSyntax() {
        return syntax;
    }

    public String[] getTriggers() {
        return triggers;
    }

    public boolean isRunnable() {
        return runnable;
    }
}


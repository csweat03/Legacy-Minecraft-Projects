package com.fbiclient.bureau.api.command;

import com.fbiclient.bureau.Bureau;
import com.fbiclient.bureau.main.commands.BureauCommand;
import com.fbiclient.bureau.main.commands.RestartCommand;
import com.fbiclient.utility.Logger;
import com.fbiclient.utility.RegistryHelper;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public class CommandRegistry extends RegistryHelper<Command> {

    public void initialize() {
        try {
            for (Class clazz : getClasses()) {
                if (!clazz.isAnnotationPresent(CommandManifest.class)) continue;
                CommandManifest commandManifesto = (CommandManifest) clazz.getAnnotation(CommandManifest.class);
                Command command = (Command) clazz.newInstance();
                //this is autistic
                command.setLabel(commandManifesto.label());
                command.setSyntax(commandManifesto.syntax());
                command.setTriggers(commandManifesto.triggers());
                command.setRunnable(commandManifesto.runnable());
                //this is autistic
                addContent(command);
                Logger.write("Initialized Command: " + command.getLabel(), Logger.Level.INFO);
                Bukkit.getPluginManager().registerEvents(command, Bureau.getPlugin());
                for (String s : command.getTriggers())
                    Bureau.getPlugin().getCommand(s).setExecutor(command);
            }
        } catch (Exception e) {
            Logger.write("Exception Thrown! Could not find commands.", Logger.Level.ERROR);
        }
    }

    private List<Class> getClasses() {
        List<Class> classes = new ArrayList<>();

        classes.add(BureauCommand.class);
        classes.add(RestartCommand.class);

        return classes;
    }
}

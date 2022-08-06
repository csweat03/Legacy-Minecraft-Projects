package com.fbiclient.utility;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;

public class Logger {

    public static void write(String message, Level level) {
        Bukkit.getConsoleSender().sendMessage(String.format("[!] %s: %s", WordUtils.capitalizeFully(WordUtils.capitalizeFully(level.toString())), message));
    }

    public enum Level {
        INFO, WARNING, ERROR
    }

}

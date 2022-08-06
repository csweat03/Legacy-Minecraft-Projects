package com.fbiclient.bureau;

import org.bukkit.plugin.PluginDescriptionFile;

public class BureauData {

    private static PluginDescriptionFile pdf = Bureau.getPlugin().getDescription();

    public static String getVersion() {
        return pdf.getVersion();
    }

    public static String getLatest() {
        return "0.1";
    }

    public static Object[] getAuthors() {
        return pdf.getAuthors().toArray();
    }

    public static String getName() {
        return pdf.getName();
    }

}

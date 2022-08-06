package com.fbiclient.bureau.main.commands;

import com.fbiclient.bureau.BureauData;
import com.fbiclient.bureau.api.command.Command;
import com.fbiclient.bureau.api.command.CommandManifest;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

@CommandManifest(label = "Bureau", syntax = "/bureau", triggers = {"bureau", "ac", "client"})
public class BureauCommand extends Command {

    private final String PRIMARY = "6";

    protected void onTrigger(String label, String[] args, CommandSender sender) {
        if (args.length == 0) {
            String[] chatlines = {
                    "",
                    String.format("\2478\247l\247m-------------------\247r  \247%s\247l%s \2478\247l\247m-------------------\247r", PRIMARY, BureauData.getName()),
                    String.format("     \247%s\247lVersion:\247r \247f%s \2478\247l\247m--\247r  \247%s\247lLatest:\247r \247f%s \2478\247l\247m--\247r  \247%s\247lAuthor(s):\247r \247f%s", PRIMARY, BureauData.getVersion(), PRIMARY, BureauData.getLatest(), PRIMARY, Arrays.toString(BureauData.getAuthors()).substring(1, Arrays.toString(BureauData.getAuthors()).length() - 1)),
                    String.format("\2478\247l\247m------------------\247r  \247%s\247l%s \2478\247l\247m------------------\247r", PRIMARY, "Commands"),
                    String.format("\247%s\247lRestart:\247r \247f%s \2478\247l\247m--\247r  \247%s\247lChecks:\247r \247f%s \2478\247l\247m--\247r  \247%s\247lBanwave:\247r \247f%s", PRIMARY, "/restart", PRIMARY, "/checks", PRIMARY, "/banwave"),
                    ""
            };
            sender.sendMessage(chatlines);
        } else {

        }
    }
}

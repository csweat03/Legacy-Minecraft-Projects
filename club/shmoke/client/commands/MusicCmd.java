package club.shmoke.client.commands;

import club.shmoke.api.command.Command;
import club.shmoke.client.Client;
import club.shmoke.client.util.GameLogger;

import java.io.File;
import java.util.ArrayList;

/**
 * @author Christian
 */
public class MusicCmd extends Command {

    private static ArrayList<String> array = new ArrayList<>();

    public MusicCmd() {
        super("Music", "Controls the music player.", ".music [list/load/loadpersonal/play/pause/remove/skip]", array);
        array.add("song");
        array.add("m");
        array.add("s");
    }

    @Override
    public void dispatch(String[] args, String message) {
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("load")) {
                if (args[1].equalsIgnoreCase("gayyyy")) {

                } else {
                    GameLogger.log("Song Unknown! If you would like to add it, do \".music loadpersonal (filename on desktop)\"", false, GameLogger.Type.ERROR);
                }
            } else if (args[0].equalsIgnoreCase("loadpersonal")) {
                String path = System.getProperty("user.home") + "/Desktop/";
                try {
                    Client.INSTANCE.getPlayer().addToPlayList(new File(path + args[1] + (args[1].contains(".mp3") ? "" : ".mp3")));
                    GameLogger.log("Song Added Successfully! To play this song do \".music play\"", false, GameLogger.Type.INFO);
                } catch (Exception ex) {
                    GameLogger.log("Invalid File! Make sure this file is located on your desktop.", false, GameLogger.Type.ERROR);
                }
            }
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("play")) {
                if (Client.INSTANCE.getPlayer().getPlayList().size() <= 0)
                    GameLogger.log("You need to add a song first!", false, GameLogger.Type.ERROR);
                else {
                    Client.INSTANCE.getPlayer().play();
                    String path = System.getProperty("user.home") + "/Desktop/";
                    path = path.replace("\\", "/");
                    GameLogger.log("Music Player has started playing " + Client.INSTANCE.getPlayer().getPlayList().get(0).toString().replace("file:/" + path, "\247c") + "\247r.", false, GameLogger.Type.INFO);
                }
            }
            if (args[0].equalsIgnoreCase("remove")) {
                if (Client.INSTANCE.getPlayer().getPlayList().size() <= 0)
                    GameLogger.log("You need to add a song first!", false, GameLogger.Type.ERROR);
                else {
                    Client.INSTANCE.getPlayer().getPlayList().remove(0);
                    Client.INSTANCE.getPlayer().stop();
                    GameLogger.log("Removed Current Song!", false, GameLogger.Type.INFO);
                }
            }
            if (args[0].equalsIgnoreCase("pause")) {
                Client.INSTANCE.getPlayer().pause();
                GameLogger.log("Music Player has been paused!", false, GameLogger.Type.INFO);
            }
            if (args[0].equalsIgnoreCase("list")) {
                String path = System.getProperty("user.home") + "/Desktop/";
                path = path.replace("\\", "/");
                GameLogger.log("\247cSongs: ", false, GameLogger.Type.INFO);
                for (Object str : Client.INSTANCE.getPlayer().getPlayList()) {
                    GameLogger.log(str.toString().replace("file:/" + path, ""), false, GameLogger.Type.INFO);
                }
            }
            if (args[0].equalsIgnoreCase("skip")) {
                if (Client.INSTANCE.getPlayer().getPlayList().size() <= 0)
                    GameLogger.log("You need to add a song first!", false, GameLogger.Type.ERROR);
                else {
                    Client.INSTANCE.getPlayer().skipForward();
                    GameLogger.log("Skipped!", false, GameLogger.Type.INFO);
                }
            }

        } else {
            GameLogger.log(syntaxMsg(), false, GameLogger.Type.ERROR);
        }
    }
}

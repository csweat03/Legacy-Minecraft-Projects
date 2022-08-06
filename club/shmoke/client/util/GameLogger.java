package club.shmoke.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import club.shmoke.client.Client;

import java.awt.*;
import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * @author Kyle
 * @since 8/30/2017
 **/
public class GameLogger {
    public static final String INFO = " \2477>\247f ";
    public static final String DEBUG = " \2476>\247f ";
    public static final String ERROR = " \247c>\247f ";

    public static void log(String message, boolean notification, Type type) {
        if (notification) {
            switch (type) {
                case INFO:
                    Client.INSTANCE.getNotificationManager().passNotification(Client.INSTANCE.color.getRGB(), message);
                    break;

                case DEBUG:
                    Client.INSTANCE.getNotificationManager().passNotification(new Color(255, 187, 50, 200).getRGB(), message);
                    break;

                case ERROR:
                    Client.INSTANCE.getNotificationManager().passNotification(new Color(183, 53, 53, 200).getRGB(),
                            message);
                    break;
            }
        } else {
            switch (type) {
                case INFO:
                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(INFO + message));
                    break;

                case DEBUG:
                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(DEBUG + message));
                    break;

                case ERROR:
                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(ERROR + message));
                    break;

                case SYSTEM: {
                    long ms = System.currentTimeMillis();
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    Date d = new Date(ms);
                    String date = sdf.format(d);
                    String messagee = "[Client] " + message + ".";
                    if (messagee.endsWith("16% Complete.")) {
                        System.out.println(" ");
                        System.out.println(messagee.replaceAll(".", "-"));
                    }
                    System.out.println(messagee);
                    if (messagee.endsWith("100% Complete.")) {
                        System.out.println("Initialized at [" + date + "].");
                        System.out.println(messagee.replaceAll(".", "-"));
                        System.out.println(" ");
                    }
                    break;
                }

                default:
                    break;
            }
        }
    }

    public static void log(String message, Type type) {
        switch (type) {
            case INFO: {
                Client.INSTANCE.getNotificationManager().passNotification(Client.INSTANCE.color.getRGB(), message);
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(INFO + message));
                break;
            }

            case DEBUG: {
                Client.INSTANCE.getNotificationManager().passNotification(new Color(255, 187, 50, 200).getRGB(), message);
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(DEBUG + message));
                break;
            }

            case ERROR: {
                Client.INSTANCE.getNotificationManager().passNotification(new Color(183, 53, 53, 200).getRGB(), message);
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(ERROR + message));
                break;
            }

            case SYSTEM: {
                System.out.println("[Client][System/DEBUG] " + message);
                break;
            }

            default:
                break;
        }
    }

    public enum Type {
        INFO, DEBUG, SYSTEM, ERROR;
    }
}

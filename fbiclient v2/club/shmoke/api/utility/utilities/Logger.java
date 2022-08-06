package club.shmoke.api.utility.utilities;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class Logger {

    public static void log(String message, Level level) {
        System.out.println("[!] " + message);
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText((level == Level.INFO ? "\2477" : "\247c") + "[!] \247r" + message));
    }

    public enum Level {
        INFO, ERROR
    }

}

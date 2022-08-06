package club.shmoke.client.ui.overlay.notification;

import club.shmoke.client.Client;
import club.shmoke.client.util.math.DelayHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.MathUtil;

import java.awt.*;

/**
 * @author Kyle
 * @since Jan 1, 2018
 */
public class NotificationManager {
    private final Minecraft mc = Minecraft.getMinecraft();

    private String notification;
    private boolean active;
    private int yPos;
    private int color;
    private DelayHelper delay = new DelayHelper();

    private static void drawBordered(double x, double y, double width, double height, double length, int innerColor,
                                     int outerColor) {
        Gui.drawRect(x, y, x + width, y + height, innerColor);
        Gui.drawRect(x - length, y, x, y + height, outerColor);
        Gui.drawRect(x - length, y - length, x + width, y, outerColor);
        Gui.drawRect(x + width, y - length, x + width + length, y + height + length, outerColor);
        Gui.drawRect(x - length, y + height, x + width, y + height + length, outerColor);
    }

    public void renderNotifications() {
        int maxY = 20;
        final ScaledResolution sr = new ScaledResolution(this.mc);
        int mid = sr.getScaledWidth() / 2;
        int notification = Client.INSTANCE.getFontManager().c18.getStringWidth(getNotification());
        int leftX = mid - (notification / 2) - 10;
        int rightX = notification + 35;
        this.yPos = (MathUtil.clamp_int(getPos(), 0, maxY));
        drawBordered(leftX - 5, -1.0, rightX, (yPos >= 16) ? 16 : yPos, 1.0, new Color(0, 0, 0, 100).getRGB(), color);

        if (isActive()) {
            ++yPos;

            if (yPos >= 16)
                Client.INSTANCE.getFontManager().c18.drawCenteredStringWithShadow(getNotification(), sr.getScaledWidth() / 2,
                        (yPos >= 4) ? 4 : (yPos - 4), -1);

            if (yPos == maxY)
                setActive(false);
        } else {
            --yPos;

            if (yPos >= 16)
                Client.INSTANCE.getFontManager().c18.drawCenteredStringWithShadow(getNotification(), sr.getScaledWidth() / 2,
                        (yPos >= 4) ? 4 : (yPos - 10), -1);
        }
    }

    public void passNotification(int color, String message) {
        Client.INSTANCE.getNotificationManager().setColor(color);
        Client.INSTANCE.getNotificationManager().setActive(true);
        Client.INSTANCE.getNotificationManager().setNotification(message);
    }

    private boolean isActive() {
        return active;
    }

    private void setActive(boolean active) {
        this.active = active;
    }

    private String getNotification() {
        return notification;
    }

    private void setNotification(String str) {
        notification = str;
    }

    private int getPos() {
        return yPos;
    }

    private void setColor(int color) {
        this.color = color;
    }
}

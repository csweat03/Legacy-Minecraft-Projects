package com.fbiclient.fbi.impl.gui.hud.themeable.impl;

import java.awt.Color;

import me.xx.api.cheat.Cheat;
import com.fbiclient.fbi.client.framework.helper.IHelper;
import com.fbiclient.fbi.impl.Brisk;
import com.fbiclient.fbi.impl.gui.hud.Hud;
import com.fbiclient.fbi.impl.gui.hud.tabbed.TabbedGui;
import com.fbiclient.fbi.impl.gui.hud.themeable.Theme;
import me.xx.utility.MathUtility;
import me.xx.utility.render.GlRender;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;

/**
 * @author Kyle
 * @since 3/11/2018
 **/
public class BriskTheme extends Theme {

    public BriskTheme() {
        super("Brisk");
    }

    public void render() {
        TabbedGui.Layout layout = TabbedGui.layout();
        layout.setTop(HUD.watermark ? 26 : 3);
        layout.setTextHeight(16);
        Gui.drawRect(-2, -2, 0, 0, new Color(0, 0, 0, 120).getRGB());
        if (HUD.watermark)
            watermark();
        if (HUD.coords)
            coordinates();
        if (HUD.arraylist)
            arraylist();
    }

    void coordinates() {
        ScaledResolution sr = new ScaledResolution(mc);
        float startY;
        if (mc.currentScreen instanceof GuiChat)
            startY = 28;
        else
            startY = 14;

        String x = String.valueOf(MathUtility.round(mc.thePlayer.posX, 0)).replace(".0", "");
        String y = String.valueOf(MathUtility.round(mc.thePlayer.posY, 0)).replace(".0", "");
        String z = String.valueOf(MathUtility.round(mc.thePlayer.posZ, 0)).replace(".0", "");
        String[] pos = {z + "\247f: Z", y + "\247f: Y", x + "\247f: X"};

        for (String s : pos) {
            float stringWidth = FR.getWidth(s);
            float width = sr.getScaledWidth() - stringWidth - 3;
            FR.drawStringWithShadow(s, width, sr.getScaledHeight() - startY + 3, getColor());
            startY += 12;
        }

    }

    void watermark() {
        LARGE.drawStringWithShadow(Brisk.INSTANCE.NAME, 5, 4, getColor());
    }

    void arraylist() {
        float y = 3;
        int index = 0;
        for (Cheat cheat : Brisk.INSTANCE.getCheatManager().getCheatsForRendering(FR)) {
            int changingColor = Brisk.INSTANCE.slider.rainbow ? GlRender.getRainbow(index * 14, 0.6, 0.9).getRGB() : getColor();
            int color = (HUD.colorMode == Hud.Color.DYNAMIC ? cheat.getColor() : changingColor);
            ScaledResolution sr = new ScaledResolution(IHelper.mc);
            String arrayLabel = cheat.getArrayLabel().toLowerCase();
            float stringWidth = FR.getWidth(arrayLabel);
            float width = sr.getScaledWidth() - stringWidth - 2;
            if (HUD.german) {
                Gui.drawRect(width - 7, y - 2, sr.getScaledWidth(), y + cheat.getAnimation(), new Color(0, 0, 0, 100).getRGB());
                Gui.drawRoundedRect(width - 5, y, width - 2, y + cheat.getAnimation() - 2, color);
            }
            FR.drawStringWithShadow(arrayLabel, width, y + cheat.getAnimation() - 12, color);
            if (cheat.getAnimation() < 14) {
                cheat.setAnimation(cheat.getAnimation() + 1);
            }
            y += 16;
            index++;
        }
    }

    @Override
    public void layout() {
        TabbedGui.Layout layout = TabbedGui.layout();
        layout.setForegroundColor(getColor());
        layout.setBackgroundColor(new Color(22, 22, 22, 204).getRGB());
        layout.setTextHeight(16);
        layout.setPanelWidth(75);
    }

    public int getColor() {
        try {
            return Brisk.INSTANCE.slider.getColor();
        } catch (Exception e) {
            return Color.blue.getRGB();
        }
    }
}
package club.shmoke.client.ui.overlay.tab;

import club.shmoke.api.cheat.Cheat;
import club.shmoke.api.cheat.Cheat.Type;
import club.shmoke.api.event.interfaces.EventListener;
import club.shmoke.client.Client;
import club.shmoke.client.cheats.visual.Overlay;
import club.shmoke.client.events.other.KeypressEvent;
import club.shmoke.client.util.math.DelayHelper;
import club.shmoke.client.util.render.AnimationUtils;
import club.shmoke.client.util.render.RenderUtils;
import net.minecraft.client.Minecraft;
import org.apache.commons.lang3.text.WordUtils;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class HorizonTab {
    private final ArrayList<Type> categoryValues = new ArrayList<>();
    private final AnimationUtils horizontalSlide = new AnimationUtils(), verticalSlide1 = new AnimationUtils(), verticalSlide2 = new AnimationUtils(), verticalSlide3 = new AnimationUtils(), verticalSlide4 = new AnimationUtils();
    private int catIndex = 0, modIndex = 0, startX = 0, startY = 0, startModX = 0, startModY = 0, screen = 0, spacing = 0, openTicks = 0;
    public static DelayHelper delay = new DelayHelper(), slowDaFuckDownBoi = new DelayHelper();
    private Color bg = new Color(0, 0, 0, 150), txt = new Color(200, 200, 200, 225), select = new Color(0,0,0,255);
    private int alpha = 255;

    public HorizonTab() {
        Collections.addAll(categoryValues, Type.values());
    }

    private Type getCurrentCategory() {
        return categoryValues.get(catIndex);
    }

    @EventListener
    public void onRender(int x, int y, int width, int spacing, int primary, int secondary) {
        Color col = new Color(secondary);
        if (delay.hasReached(2500)) {
            if (slowDaFuckDownBoi.hasReached(5)) {
                if (bg.getAlpha() > 40)
                    bg = new Color(bg.getRed(), bg.getBlue(), bg.getGreen(), bg.getAlpha() - 1);
                if (txt.getAlpha() > 100)
                    txt = new Color(txt.getRed(), txt.getBlue(), txt.getGreen(), txt.getAlpha() - 1);
                if (select.getAlpha() > 30)
                    select = new Color(col.getRed(), col.getGreen(), col.getBlue(), select.getAlpha() - 1);
                slowDaFuckDownBoi.reset();
            }
        } else if (delay.hasReached(5000)) {
            bg = new Color(bg.getRed(), bg.getBlue(), bg.getGreen(), 40);
            txt = new Color(txt.getRed(), txt.getBlue(), txt.getGreen(), 100);
            select = new Color(col.getRed(), col.getGreen(), col.getBlue(), 30);
        } else if (!delay.hasReached(5000)) {
            if (slowDaFuckDownBoi.hasReached(5)) {
                if (bg.getAlpha() < 150)
                    bg = new Color(bg.getRed(), bg.getBlue(), bg.getGreen(), bg.getAlpha() + 1);
                if (txt.getAlpha() < 255)
                    txt = new Color(txt.getRed(), txt.getBlue(), txt.getGreen(), txt.getAlpha() + 1);
                if (select.getAlpha() < 255)
                    select = new Color(col.getRed(), col.getGreen(), col.getBlue(), select.getAlpha() + 1);
                slowDaFuckDownBoi.reset();
            }
        }
        this.spacing = spacing;
        Overlay o = (Overlay) Client.INSTANCE.getCheatManager().get(Overlay.class);
        int gradient = new Color(secondary).darker().darker().darker().darker().getRGB();
        int border = o.tabguiBorder.getValue() ? primary : 0x00000000;
        secondary = select.getRGB();
        if (Minecraft.getMinecraft().gameSettings.showDebugInfo)
            return;
        startX = x;
        startY = y;
        RenderUtils.drawBorderedRect(startX, startY, startX + getWidestCategory() + width, startY + categoryValues.size() * spacing + 1, 1, bg.getRGB(), border);
        for (Type t : categoryValues) {
            String name = getFullCap(t.label());
            if (getCurrentCategory() == t)
                RenderUtils.drawGradientRect(startX + 1, startY + 1 + verticalSlide1.getRounded() + verticalSlide2.getRounded(), startX + getWidestCategory() + width - 1, startY + spacing + verticalSlide1.getRounded() + verticalSlide2.getRounded(), secondary, gradient);
            Client.INSTANCE.getFontManager().c16.drawStringWithShadow(name, startX + 3, startY + spacing / 2 - 3, txt.getRGB());
            startY += spacing;
        }

        if (screen == 1) {
            openTicks++;
            startModX = startX + getWidestCategory() + width + 1 - (o.tabguiBorder.getValue() ? 0 : 2);
            startModY = y + catIndex * spacing;

            RenderUtils.drawBorderedRect(startModX, startModY, startModX + horizontalSlide.getRounded(), startModY + getModsForCurrentCategory().size() * spacing + 1F, 1, bg.getRGB(), border);
            for (Cheat m : getModsForCurrentCategory()) {
                String name = getCap(m.label());

                if (getCurrentModule() == m)
                    RenderUtils.drawGradientRect(startModX + 1, startModY + 1 + verticalSlide3.getRounded() + verticalSlide4.getRounded(), startModX - 1 + horizontalSlide.getRounded(), startModY + spacing + verticalSlide3.getRounded() + verticalSlide4.getRounded(), secondary, gradient);

                if (horizontalSlide.getRounded() >= getWidestModInCurrentCategory(getCurrentCategory()))
                    Client.INSTANCE.getFontManager().c16.drawStringWithShadow(name, startModX + 3, startModY + spacing / 2 - 3, m.getState() ? getCurrentModule() == m ? 0xaaffffff : 0x80ffffff : txt.getRGB());

                startModY += spacing;
            }
        } else openTicks = 0;

        if (openTicks > 20) if (horizontalSlide.getRounded() < 1 && screen == 1) screen = modIndex = openTicks = 0;
    }

    private String getCap(String string) {
        return Client.INSTANCE.getStyler().styleString(WordUtils.capitalize(string), false);
    }

    private String getFullCap(String string) {
        return Client.INSTANCE.getStyler().styleString(WordUtils.capitalizeFully(string), false);
    }

    private void left() {
        if (screen == 1) {
            horizontalSlide.start(150, 7 + getWidestModInCurrentCategory(getCurrentCategory()), false);
        }
    }

    private void right() {
        if (screen == 0) {
            horizontalSlide.start(150, 7 + getWidestModInCurrentCategory(getCurrentCategory()), true);
            screen = 1;
        } else if (screen == 1)
            getCurrentModule().toggle();
    }

    private void up() {
        if (catIndex > 0 && screen == 0) {
            catIndex--;
            verticalSlide2.start(50, 0, spacing, false);
        } else if (catIndex == 0 && screen == 0) {
            verticalSlide1.start(100, -1 * spacing * (categoryValues.size() - 1), spacing - spacing, true);
            catIndex = categoryValues.size() - 1;
        } else if (modIndex > 0 && screen == 1) {
            modIndex--;
            verticalSlide4.start(50, 0, spacing, false);
        } else if (modIndex == 0 && screen == 1) {
            modIndex = getModsForCurrentCategory().size() - 1;
            verticalSlide3.start(100, -1 * spacing * (getModsForCurrentCategory().size() - 1), spacing - spacing, true);
        }
    }

    private void down() {
        if (catIndex < categoryValues.size() - 1 && screen == 0) {
            catIndex++;
            verticalSlide1.start(50, -1 * spacing, 0, true);
        } else if (catIndex == categoryValues.size() - 1 && screen == 0) {
            verticalSlide2.start(100, 0, spacing * catIndex, false);
            catIndex = 0;
        } else if (modIndex < getModsForCurrentCategory().size() - 1 && screen == 1) {
            modIndex++;
            verticalSlide3.start(50, -1 * spacing, 0, true);
        } else if (modIndex == getModsForCurrentCategory().size() - 1 && screen == 1) {
            verticalSlide4.start(100, 0, spacing * modIndex, false);
            modIndex = 0;
        }
    }

    @EventListener
    public void onKeyPress(KeypressEvent keypressEvent) {
        //Client.INSTANCE.getCheatManager().save();
        switch (keypressEvent.key()) {
            case Keyboard.KEY_UP:
                delay.reset();
                slowDaFuckDownBoi.reset();
                up();
                break;
            case Keyboard.KEY_DOWN:
                delay.reset();
                slowDaFuckDownBoi.reset();
                down();
                break;
            case Keyboard.KEY_RIGHT:
                delay.reset();
                slowDaFuckDownBoi.reset();
                right();
                break;
            case Keyboard.KEY_LEFT:
                delay.reset();
                slowDaFuckDownBoi.reset();
                left();
                break;
        }
    }

    private Cheat getCurrentModule() {
        return getModsForCurrentCategory().get(modIndex);
    }

    private ArrayList<Cheat> getModsForCurrentCategory() {
        ArrayList<Cheat> mods = new ArrayList();
        Type t = getCurrentCategory();
        for (Cheat c : Client.INSTANCE.getCheatManager().getContents()) {
            if (c.type().equals(t)) {
                mods.add(c);
                mods.sort((o1, o2) -> Client.INSTANCE.getFontManager().c16.getStringWidth(o2.label()) - Client.INSTANCE.getFontManager().c16.getStringWidth(o1.label()));
            }
        }
        return mods;
    }

    private int getWidestModInCurrentCategory(Type t) {
        int width = 0;
        for (Cheat c : Client.INSTANCE.getCheatManager().getCheatsInType(t)) {
            int cWidth = Client.INSTANCE.getFontManager().c16.getStringWidth(c.label());
            if (cWidth > width) {
                width = cWidth;
            }
        }
        return width;
    }

    private int getWidestCategory() {
        int width = 0;
        for (Type t : categoryValues) {
            String name = t.name();
            int cWidth = Client.INSTANCE.getFontManager().c16.getStringWidth(
                    name.substring(0, 1).toUpperCase() + name.substring(1, name.length()).toLowerCase());
            if (cWidth > width) {
                width = cWidth;
            }
        }
        return width;
    }
}

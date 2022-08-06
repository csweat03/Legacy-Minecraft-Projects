package com.fbiclient.fbi.impl.gui.clickable;

import java.awt.Color;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import me.xx.api.cheat.Category;
import com.fbiclient.fbi.client.framework.helper.ICheats;
import com.fbiclient.fbi.client.framework.helper.IHelper;
import com.fbiclient.fbi.client.framework.helper.game.RenderHelper;
import com.fbiclient.fbi.impl.Brisk;
import com.fbiclient.fbi.impl.gui.clickable.impl.section.Section;
import com.fbiclient.fbi.impl.gui.clickable.impl.section.SectionButton;
import com.fbiclient.fbi.impl.gui.clickable.impl.section.impl.CategorySection;
import com.fbiclient.fbi.impl.gui.clickable.impl.section.impl.LayoutSection;
import com.fbiclient.fbi.impl.gui.clickable.impl.section.impl.ProfileSection;
import me.xx.utility.render.GlRender;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.opengl.GL11;

/**
 * @author Kyle
 * @since 4/14/2018
 **/
public class GuiClickable extends GuiScreen {

    public List<SectionButton> buttons = new ArrayList<>();

    public static Section section;

    public static int x, y;

    private boolean dragging = false;
    public static int[] oldPositions = {0, 0};

    public GuiClickable() {

        x = RenderHelper.getRes().getScaledWidth() / 2 - 190;
        y = RenderHelper.getRes().getScaledHeight() / 2 - 170;

        int tY = y + 50;

        for (Category cat : Category.values()) {
            SectionButton button = new SectionButton(cat.toString(), this, tY, new CategorySection(cat));
            buttons.add(button);
            tY += button.height + 5;
        }

        SectionButton[] bList = {
                new SectionButton("Profiles", this, tY + 150, new ProfileSection(this)),
                new SectionButton("Client", this, tY + 120, new LayoutSection())
        };

        buttons.addAll(Arrays.asList(bList));

        section = new CategorySection(Category.COMBAT);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        if (dragging) {
            x = mouseX + oldPositions[0];
            y = mouseY + oldPositions[1];
        }

        /*the main bordered rectangle container*/
        int mainleft = getX();
        int maintop = getY();
        int mainright = getX() + getWidth();
        int mainbottom = getY() + getHeight();
        Gui.drawRoundedRect(mainleft, maintop, mainright, mainbottom, new Color(0, 0, 0, 213).getRGB());
        Gui.drawRoundedRect(mainleft + 1, maintop + 1, mainright - 1, mainbottom - 1, ICheats.HUD.getThemeHandler().getCurrentTheme().getColor());//new Color(58, 58, 58, 228).getRGB()
        Gui.drawRoundedRect(mainleft + 3, maintop + 3, mainright - 3, mainbottom - 3, new Color(32, 32, 32, 255).getRGB());

        double border = 1.5;

        /*the category sections rectangle*/
        int catleft = getX() + 15;
        int cattop = getY() + 15;
        int catright = catleft + 75;
        int catbottom = getY() + getHeight() - 15;
        Gui.drawBorderedRect(catleft, cattop, catright, catbottom, border, new Color(15, 15, 15, 237).getRGB(), new Color(58, 58, 58, 228).getRGB());

        /*the cheat sections rectangle*/
        int cheatleft = getX() + 95;
        int cheatright = getX() + getWidth() - 15;
        Gui.drawBorderedRect(cheatleft, cattop, cheatright, catbottom, border, new Color(15, 15, 15, 237).getRGB(), new Color(58, 58, 58, 228).getRGB());

        String time = new SimpleDateFormat("hh:mm a").format(new Date());
        if (time.startsWith("0")) {
            time = time.replaceFirst("0", "");
        }

        IHelper.LARGE.drawCenteredString(Brisk.INSTANCE.NAME, mainleft + 52.5f, maintop + 20, ICheats.HUD.getThemeHandler().getCurrentTheme().getColor());

        String info = String.format("%s | %s - %s", mc.session.getUsername(), Brisk.INSTANCE.VERSION, time);
        IHelper.SMALLER.drawStringWithShadow(info, mainright - IHelper.SMALLER.getWidth(info) - 6, mainbottom - 12, -1);

        for (SectionButton sectionButton : buttons) {
            sectionButton.renderComponent();
        }
        GlRender.prepareScissorBox(cheatleft, cattop, cheatright, catbottom - 5);
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        section.renderComponent();
        section.updateComponent(mouseX, mouseY);
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        refresh();
    }

    private void refresh() {
        int y = RenderHelper.getRes().getScaledHeight() / 2 - 120;
        int othery = RenderHelper.getRes().getScaledHeight() / 2 + 60;
        for (SectionButton button : buttons) {
            if (button.getLabel().equalsIgnoreCase("Profiles") || button.getLabel().equalsIgnoreCase("Client")) {
                button.setY(othery);
                othery += button.height;
            } else {
                button.setY(y);
                y += button.height + 5;
            }
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) throws IOException {
        super.mouseClicked(mouseX, mouseY, button);

        if (isHovering(mouseX, mouseY) && button == 0) {
            oldPositions = new int[]{getX() - mouseX, getY() - mouseY};

            if (oldPositions[0] != 0 && oldPositions[1] != 0)
                dragging = true;
        }

        for (SectionButton but : buttons) {
            but.mouseClicked(mouseX, mouseY, button);
        }
        section.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        super.mouseReleased(mouseX, mouseY, mouseButton);

        dragging = false;

        for (SectionButton but : buttons) {
            but.mouseReleased(mouseX, mouseY, mouseButton);
        }
        section.mouseReleased(mouseX, mouseY, mouseButton);
    }

    @Override
    public void keyTyped(char typedChar, int key) throws IOException {
        super.keyTyped(typedChar, key);
        for (SectionButton but : buttons) {
            but.keyTyped(typedChar, key);
        }
        section.keyTyped(typedChar, key);
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
    }

    public void setX(int x) {
        GuiClickable.x = x;
    }

    public int getX() {
        return x;
    }

    public int getWidth() {
        return 380;
    }

    public void setY(int y) {
        GuiClickable.y = y;
    }

    public int getY() {
        return y;
    }

    public int getHeight() {
        return 290;
    }

    private boolean isHovering(int mouseX, int mouseY) {
        return mouseX >= getX() && mouseX <= getX() + getWidth() && mouseY >= getY() && mouseY <= getY() + getHeight() &&
                !(mouseX >= getX() + 15 && mouseY >= getY() + 15 && mouseX <= getX() + 90 && mouseY <= getY() + getHeight() - 15) &&
                !(mouseX >= getX() + 95 && mouseY >= getY() + 15 && mouseX <= getX() + getWidth() - 15 && mouseY <= getY() + getHeight() - 15);
    }

}

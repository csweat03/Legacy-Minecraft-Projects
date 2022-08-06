package com.fbiclient.fbi.impl.gui.clickable.impl;

import java.awt.*;
import java.util.ArrayList;

import me.xx.api.profile.Profile;
import com.fbiclient.fbi.impl.Brisk;
import com.fbiclient.fbi.impl.gui.clickable.GuiClickable;
import com.fbiclient.fbi.impl.gui.clickable.component.Component;
import com.fbiclient.fbi.impl.gui.clickable.component.init.Button;
import com.fbiclient.fbi.impl.gui.clickable.impl.section.impl.PreviewSection;
import me.xx.utility.render.GlRender;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

public class ProfileButton extends Button {

    private GuiClickable parent;

    private Profile config;
    public ArrayList<Component> subItems = new ArrayList();

    PreviewSection preview = new PreviewSection();

    public ProfileButton(Profile config, GuiClickable parent, int y) {
        this.parent = parent;
        this.config = config;
        this.y = y;
    }

    @Override
    public void renderComponent() {
        int right = getX() + getWidth();
        int bottom = y + getHeight();
        Gui.drawRect(getX(), y, right, bottom, new Color(82, 82, 82, 255).getRGB());
        GlRender.drawCustomImage(getX() + 11, y + 18, 47, 47,
                new ResourceLocation("client/icons/" + config.getId() + ".png"));
        String label = String.format("\247%s%s",
                Brisk.INSTANCE.getConfigManager().getHandler().getCurrentConfig() == config ? "f" : "7",
                config.getLabel());
        FR.drawCenteredString(label, getX() + (getWidth() / 2), y + 4, -1);
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        y += GuiClickable.y - 94;
        int left = getX();
        int right = getX() + getWidth();
        boolean inside = (mouseX > left && mouseX < right) && (mouseY < y + getHeight() && mouseY > y);
        if (inside) {
            String desc = "Right click to preview settings for common cheats!";
            Gui.drawBorderedRect(mouseX, mouseY - 10, mouseX + FR.getWidth(desc) + 8, mouseY, 0.5, new Color(0, 0, 0, 200).getRGB(), new Color(0, 0, 0, 240).getRGB());
            SMALLER.drawCenteredString(desc, mouseX + (FR.getWidth(desc) / 2) + 4, mouseY - 8, -1);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        y += GuiClickable.y - 94;
        int left = getX();
        int right = getX() + getWidth();
        if ((mouseX > left && mouseX < right) && (mouseY < y + getHeight() && mouseY > y)) {
            switch (button) {
                case 0:
                    Brisk.INSTANCE.getConfigManager().getHandler().setCurrentConfig(config);
                    break;
                case 1:
                    Brisk.INSTANCE.getConfigManager().getHandler().setCurrentConfig(config);
                    parent.section = preview;
                    break;
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        for (Component item : subItems) {
            item.mouseReleased(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void keyTyped(char typedChar, int key) {

    }

    @Override
    public int getHeight() {
        return 70;
    }

    public GuiClickable getParent() {
        return parent;
    }

    public ArrayList<Component> getSubItems() {
        return subItems;
    }

    @Override
    public int getX() {
        return x + GuiClickable.x - 279;
    }

    @Override
    public int getWidth() {
        return 70;
    }
}
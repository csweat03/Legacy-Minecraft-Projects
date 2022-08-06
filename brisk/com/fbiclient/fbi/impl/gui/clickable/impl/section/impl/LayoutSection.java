package com.fbiclient.fbi.impl.gui.clickable.impl.section.impl;

import me.xx.api.cheat.Cheat;
import com.fbiclient.fbi.impl.Brisk;
import com.fbiclient.fbi.impl.gui.clickable.impl.cheat.CheatButton;
import com.fbiclient.fbi.impl.gui.clickable.impl.section.CheatSection;

/**
 * @author Kyle
 * @since 4/14/2018
 **/
public class LayoutSection extends CheatSection {

    String[] cheats = {"Crosshair"};

    public LayoutSection() {
        //the y pos
        int y = RENDER_HELPER.getRes().getScaledHeight() / 2 - 150;
        /**
         * Loops through all cheats in the manager and adds them to the buttons list
         */
        for (Cheat cheat : Brisk.INSTANCE.getCheatManager().getValues()) {
            for (String name : cheats) {
                if (cheat.getId().equalsIgnoreCase(name)) {
                    CheatButton button = new CheatButton(cheat, y);
                    cheatButtons.add(button);
                    y += button.getHeight() + 2;
                }
            }
        }
    }

    @Override
    public void renderComponent() {
        for (CheatButton button : cheatButtons) {
            button.renderComponent();
        }
        Brisk.INSTANCE.slider.renderComponent();
        refresh();
    }

    public void refresh() {
        /**
         * refreshes the heights
         */
        int y = RENDER_HELPER.getRes().getScaledHeight() / 2 - 150;
        Brisk.INSTANCE.slider.setY(y);
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        for (CheatButton cbutton : cheatButtons) {
            cbutton.updateComponent(mouseX, mouseY);
        }
        Brisk.INSTANCE.slider.updateComponent(mouseX, mouseY);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        for (CheatButton cbutton : cheatButtons) {
            cbutton.mouseClicked(mouseX, mouseY, button);
        }
        Brisk.INSTANCE.slider.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        for (CheatButton cbutton : cheatButtons) {
            cbutton.mouseReleased(mouseX, mouseY, mouseButton);
        }
        Brisk.INSTANCE.slider.mouseReleased(mouseX, mouseY, mouseButton);
    }

    @Override
    public void keyTyped(char typedChar, int key) {
        for (CheatButton cbutton : cheatButtons) {
            cbutton.keyTyped(typedChar, key);
        }
        Brisk.INSTANCE.slider.keyTyped(typedChar, key);
    }
}

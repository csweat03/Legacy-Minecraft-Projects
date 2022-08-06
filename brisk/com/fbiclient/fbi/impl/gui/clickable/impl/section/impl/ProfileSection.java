package com.fbiclient.fbi.impl.gui.clickable.impl.section.impl;

import java.util.ArrayList;
import java.util.List;

import me.xx.api.profile.Profile;
import com.fbiclient.fbi.impl.Brisk;
import com.fbiclient.fbi.impl.gui.clickable.GuiClickable;
import com.fbiclient.fbi.impl.gui.clickable.impl.ProfileButton;
import com.fbiclient.fbi.impl.gui.clickable.impl.section.Section;

/**
 * @author Kyle
 * @since 4/14/2018
 **/
public class ProfileSection extends Section {

    public List<ProfileButton> buttons = new ArrayList();

    public ProfileSection(GuiClickable parent) {
        // the y pos
        int y = RENDER_HELPER.getRes().getScaledHeight() / 2 - 150 + GuiClickable.y;
        /**
         * Loops through all cheats in the manager and adds them to the buttons list
         */
        for (Profile config : Brisk.INSTANCE.getConfigManager().getRegistry()) {
            ProfileButton button = new ProfileButton(config, parent, y);
            buttons.add(button);
            y += button.getHeight() + 2;
        }
    }

    @Override
    public void renderComponent() {
        for (ProfileButton button : buttons) {
            button.renderComponent();
        }
        refresh();
    }

    public void refresh() {
        /**
         * refreshes the heights when opening and closing buttons
         */
        int y = RENDER_HELPER.getRes().getScaledHeight() / 2 - 145;
        int otherY = RENDER_HELPER.getRes().getScaledHeight() / 2 - 145;
        int othereY = RENDER_HELPER.getRes().getScaledHeight() / 2 - 145;
        int x = RENDER_HELPER.getRes().getScaledWidth() / 2;
        int count = 0;
        for (ProfileButton button : buttons) {
            if (count > 5) {
                ProfileButton magik = buttons.get(count);
                magik.x = RENDER_HELPER.getRes().getScaledWidth() / 2 + 80;
                magik.setY(othereY);
                othereY += magik.getHeight() + 5;

            } else if (count > 2) {
                ProfileButton special = buttons.get(count);
                special.x = x;
                special.setY(otherY);
                otherY += special.getHeight() + 5;
            } else {
                button.x = RENDER_HELPER.getRes().getScaledWidth() / 2 - 80;
                button.setY(y);
                y += button.getHeight() + 5;
            }
            count++;
        }
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        for (ProfileButton cbutton : buttons) {
            cbutton.updateComponent(mouseX, mouseY);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        for (ProfileButton cbutton : buttons) {
            cbutton.mouseClicked(mouseX, mouseY, button);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        for (ProfileButton cbutton : buttons) {
            cbutton.mouseReleased(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void keyTyped(char typedChar, int key) {
        for (ProfileButton cbutton : buttons) {
            cbutton.keyTyped(typedChar, key);
        }
    }
}

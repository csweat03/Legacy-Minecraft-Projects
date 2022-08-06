package com.fbiclient.fbi.impl.gui.clickable.impl.section;

import java.util.ArrayList;
import java.util.List;

import com.fbiclient.fbi.impl.gui.clickable.impl.cheat.CheatButton;

/**
 * @author Kyle
 * @since 5/1/2018
 **/
public abstract class CheatSection extends Section {

    public List<CheatButton> cheatButtons = new ArrayList<>();

    public void refresh() {
        /**
         * refreshes the heights when opening and closing buttons
         */
        int y = RENDER_HELPER.getRes().getScaledHeight() / 2 - 150;
        for (CheatButton cheatButton : cheatButtons) {
            cheatButton.setY(y);
            y += cheatButton.getHeight() + 2;
        }
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        refresh();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {

    }

}

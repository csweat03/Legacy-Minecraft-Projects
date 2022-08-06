package com.fbiclient.fbi.impl.gui.hud.tabbed.impl;

import org.lwjgl.input.Keyboard;

import me.valkyrie.api.tabbed.ExpandableToggleableTab;
import me.valkyrie.api.tabbed.ValueTab;
import me.xx.api.cheat.Cheat;
import com.fbiclient.fbi.impl.gui.hud.tabbed.Folder;

public class ExpandingButton extends ExpandableToggleableTab {
    public Cheat cheat;

    public ExpandingButton(Cheat cheat) {
        this.cheat = cheat;
    }

    @Override
    public String getText() {
        String label = this.cheat.getLabel();
        return String.format("\247%s%s", this.getState() ? "f" : "7", label);
    }

    @Override
    public void keyPress(int key) {
        switch (key) {
            case Keyboard.KEY_RETURN:
                this.cheat.toggle();
                break;
        }
        super.keyPress(key);
    }

    @Override
    public boolean getState() {
        return this.cheat.getState();
    }

    @Override
    public Folder open() {
        Folder folder = new Folder();
        this.cheat.getValues().forEach(propertyValue -> folder.add(ValueTab.of(propertyValue, this.cheat)));
        return folder.getTabItemSet().isEmpty() ? null : folder;
    }
}


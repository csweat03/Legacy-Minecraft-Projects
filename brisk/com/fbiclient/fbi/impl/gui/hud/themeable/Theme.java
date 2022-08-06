package com.fbiclient.fbi.impl.gui.hud.themeable;

import com.fbiclient.fbi.client.framework.helper.ICheats;
import com.fbiclient.fbi.client.framework.helper.IHelper;

/**
 * @author Kyle
 * @since 2/10/2018
 **/
public abstract class Theme implements IHelper, ICheats {

    private String label;

    public Theme(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    /**
     * Draws onto the screen
     */
    public abstract void render();

    /**
     * Used to setup the tabgui layout
     */
    public abstract void layout();

    /**
     * The main color of the theme
     * @return color
     */
    public abstract int getColor();

}


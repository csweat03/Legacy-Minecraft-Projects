package com.fbiclient.fbi.impl.gui.clickable.component;

import com.fbiclient.fbi.client.framework.helper.ICheats;
import com.fbiclient.fbi.client.framework.helper.IHelper;

/**
 * @author Kyle
 * @since 4/14/2018
 **/
public abstract class Component implements IHelper, ICheats {

    public int y, x, width, height;

    public abstract void renderComponent();

    public abstract void updateComponent(int mouseX, int mouseY);

    public abstract void mouseClicked(int mouseX, int mouseY, int button);

    public abstract void mouseReleased(int mouseX, int mouseY, int mouseButton);

    public abstract void keyTyped(char typedChar, int key);

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getY() { return x; }

    public int getX() { return y; }

    public void setY(int y) {
        this.y = y;
    }

}

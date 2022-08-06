package com.fbiclient.fbi.impl.gui.clickable.impl.section;

import com.fbiclient.fbi.impl.gui.clickable.GuiClickable;
import com.fbiclient.fbi.impl.gui.clickable.component.init.Button;

/**
 * @author Kyle
 * @since 4/14/2018
 **/
public class SectionButton extends Button {

    public Section section;
    public String label;
    public GuiClickable parent;

    public SectionButton(String label, GuiClickable parent, int y, Section section) {
        this.section = section;
        this.label = label;
        width = 55;
        height = 20;
        this.y = y;
        this.parent = parent;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        y += GuiClickable.y - 100;
        int left = getX();
        float right = getX() + width;
        int top = y;
        int bottom = y + height;
        if ((mouseX > left && mouseX < right) && (mouseY > top && mouseY < bottom)) {
            switch (button) {
                case 0:
                    parent.section = section;
                    break;
            }
        }
    }

    @Override
    public void renderComponent() {
        y += GuiClickable.y - 100;
        FR.drawCenteredString((parent.section == this.section ? "\247f" : "\2477") + label, getX() + ((width) / 2), y + 7, -1);
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {

    }

    @Override
    public void keyTyped(char typedChar, int key) {
    }

    public int getX() {
        return parent.getX() + 25;
    }

    public GuiClickable getParent() {
        return parent;
    }

    public String getLabel() {
        return label;
    }

    public Section getSection() {
        return section;
    }
}

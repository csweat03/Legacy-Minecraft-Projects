package com.fbiclient.fbi.impl.gui.clickable.impl.cheat;

import java.awt.*;
import java.util.ArrayList;

import com.fbiclient.fbi.impl.gui.clickable.GuiClickable;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;
import me.valkyrie.api.value.Value;
import me.valkyrie.api.value.types.BooleanValue;
import me.valkyrie.api.value.types.EnumValue;
import me.valkyrie.api.value.types.NumberValue;
import me.xx.api.cheat.Cheat;
import com.fbiclient.fbi.impl.gui.clickable.component.Component;
import com.fbiclient.fbi.impl.gui.clickable.component.init.Button;
import com.fbiclient.fbi.impl.gui.clickable.impl.cheat.value.Checkbox;
import com.fbiclient.fbi.impl.gui.clickable.impl.cheat.value.ModeSpinner;
import com.fbiclient.fbi.impl.gui.clickable.impl.cheat.value.slider.casts.DoubleSlider;
import com.fbiclient.fbi.impl.gui.clickable.impl.cheat.value.slider.casts.IntegerSlider;

/**
 * @author Kyle
 * @since 4/14/2018
 **/
public class CheatButton extends Button {

    int buttonHeight;

    public boolean open, bind;
    public Cheat cheat;
    public ArrayList<Component> subItems = new ArrayList();

    public CheatButton(Cheat cheat, int y) {
        open = false;
        this.cheat = cheat;
        this.y = y;
        height = 15;
        buttonHeight = height;
        addSubItems();
    }

    void addSubItems() {
        int valY = y + buttonHeight + 3;

        for (Value val : cheat.getValues()) {
            if (val instanceof BooleanValue) {
                BooleanValue bool = (BooleanValue) val;
                Checkbox checkbox = new Checkbox(bool, this, valY);
                subItems.add(checkbox);
                valY += checkbox.getHeight() + 1;
            }
            if (val instanceof EnumValue) {
                EnumValue enu = (EnumValue) val;
                ModeSpinner spinner = new ModeSpinner(enu, this, valY);
                subItems.add(spinner);
                valY += spinner.getHeight() + 1;
            }
            if (val instanceof NumberValue) {
                NumberValue num = (NumberValue) val;
                if (num.getValue() instanceof Integer) {
                    IntegerSlider intSlider = new IntegerSlider(num, this, valY);
                    subItems.add(intSlider);
                    valY += intSlider.getHeight() + 1;
                }
                if (num.getValue() instanceof Double) {
                    DoubleSlider dubSlider = new DoubleSlider(num, this, valY);
                    subItems.add(dubSlider);
                    valY += dubSlider.getHeight() + 1;
                }
            }
        }
    }

    @Override
    public void renderComponent() {
        x += getX();
        y += GuiClickable.y - 94;
        int right = getX() + getWidth();
        int bottom = y + getHeight();

        Gui.drawRect(getX(), y, right, bottom, new Color(82, 82, 82, 255).getRGB());
        if (!cheat.getValues().isEmpty()) {
            SMALLER.drawStringWithShadow("...", right - 10, y + 5, -1);
        }
        String label = bind ? "-" : cheat.getLabel();
        FR.drawStringWithShadow(String.format("\247%s%s", cheat.getState() ? "f" : "7", label),
                getX() + 3, y + 4, -1);
        if (!subItems.isEmpty() && open) {
            for (Component sub : subItems) {
                sub.renderComponent();
            }
        }

    }

    public void refresh() {
        /**
         * refreshes the heights when opening and closing buttons
         */
        int newy = y + buttonHeight + 3;
        for (Component item : subItems) {
            item.setY(newy);
            newy += item.getHeight() + 1;
        }
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        for (Component item : subItems) {
            item.updateComponent(mouseX, mouseY);
        }
        int cleft = getX();
        int cright = getX() + getWidth();
        if ((mouseX > cleft && mouseX < cright) && (mouseY < y + buttonHeight && mouseY > y)) {
            String desc = cheat.getDescription();
            Gui.drawBorderedRect(mouseX, mouseY - 10, mouseX + FR.getWidth(desc) + 8, mouseY, 0.5, new Color(0, 0, 0, 200).getRGB(), new Color(0, 0, 0, 240).getRGB());
            SMALLER.drawCenteredString(desc, mouseX + (FR.getWidth(desc) / 2) + 4, mouseY - 8, -1);
        }
        refresh();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        int left = getX();
        int right = getX() + getWidth();
        x += getX();
        y += GuiClickable.y - 94;
        if ((mouseX > left && mouseX < right) && (mouseY < y + buttonHeight && mouseY > y)) {
            switch (button) {
                case 0:
                    if (!bind)
                        cheat.toggle();
                    else
                        bind = false;
                    break;
                case 1:
                    if (!bind && !cheat.getValues().isEmpty()) {
                        open = !open;
                    }
                    bind = false;
                    break;
                case 2: {
                    bind = !bind;
                    break;
                }
            }
        }
        if (!subItems.isEmpty() && open) {
            for (Component sub : subItems) {
                sub.mouseClicked(mouseX, mouseY, button);
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
        if (bind) {
            if (key == Keyboard.KEY_DELETE || key == Keyboard.KEY_BACK)
                cheat.setKey(Keyboard.KEY_NONE);
            else
                cheat.setKey(key);
            bind = false;
        }
    }

    @Override
    public int getHeight() {
        if (open) {
            int newY = buttonHeight + 3;
            for (Component item : subItems) {
                newY += item.getHeight() + 1;
            }
            height = newY;
        } else {
            height = buttonHeight;
        }
        return height;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean flag) {
        open = flag;
    }

    public ArrayList<Component> getSubItems() {
        return subItems;
    }

    @Override
    public int getX() {
        return GuiClickable.x + 100;
    }

    @Override
    public int getWidth() {
        return 260;
    }
}

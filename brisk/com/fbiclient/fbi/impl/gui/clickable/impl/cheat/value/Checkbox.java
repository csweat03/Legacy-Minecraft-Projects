package com.fbiclient.fbi.impl.gui.clickable.impl.cheat.value;

import com.fbiclient.fbi.impl.cheats.visual.ClickableGui;
import com.fbiclient.fbi.impl.gui.clickable.GuiClickable;
import net.minecraft.client.gui.Gui;
import me.valkyrie.api.value.Value;
import me.valkyrie.api.value.types.BooleanValue;
import me.valkyrie.api.value.types.EnumValue;
import me.valkyrie.api.value.types.NumberValue;
import com.fbiclient.fbi.impl.gui.clickable.component.Component;
import com.fbiclient.fbi.impl.gui.clickable.impl.cheat.CheatButton;
import com.fbiclient.fbi.impl.gui.clickable.impl.cheat.value.slider.casts.DoubleSlider;
import com.fbiclient.fbi.impl.gui.clickable.impl.cheat.value.slider.casts.IntegerSlider;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kyle
 * @since 4/14/2018
 **/
public class Checkbox extends Component {

    public List<Component> childValues = new ArrayList();

    private boolean open;

    CheatButton parent;

    private BooleanValue value;
    public int buttonHeight;

    public Checkbox(BooleanValue val, CheatButton button, int y) {
        this.y = y;
        this.parent = button;
        this.value = val;
        buttonHeight = height = 12;
        open = false;
        addChildItems();
    }

    void addChildItems() {
        int valY = y;
        int idk = 8;
        for (Value val : value.getChildren()) {
            if (val instanceof BooleanValue) {
                BooleanValue bool = (BooleanValue) val;
                Checkbox checkbox = new Checkbox(bool, parent, valY);
                childValues.add(checkbox);
                valY += checkbox.getHeight() + idk;
            }
            if (val instanceof EnumValue) {
                EnumValue enu = (EnumValue) val;
                ModeSpinner spinner = new ModeSpinner(enu, parent, valY);
                childValues.add(spinner);
                valY += spinner.getHeight() + idk;
            }
            if (val instanceof NumberValue) {
                NumberValue num = (NumberValue) val;
                if (num.getValue() instanceof Integer) {
                    IntegerSlider intSlider = new IntegerSlider(num, parent, valY);
                    childValues.add(intSlider);
                    valY += intSlider.getHeight() + idk;
                }
                if (num.getValue() instanceof Double) {
                    DoubleSlider dubSlider = new DoubleSlider(num, parent, valY);
                    childValues.add(dubSlider);
                    valY += dubSlider.getHeight() + idk;
                }
            }
        }
    }

    @Override
    public void renderComponent() {
        y += GuiClickable.y - 94;
        int left = getX();
        int right = getX() + getWidth();
        Gui.drawBorderedRect(parent.getX() + 5, y, parent.getX() + 12, y + buttonHeight - 5, 0.5, value.getValue() ? HUD.getThemeHandler().getCurrentTheme().getColor() : new Color(0x494949).getRGB(), new Color(0, 0, 0, 240).getRGB());
        if (!value.getChildren().isEmpty()) {
            SMALLER.drawStringWithShadow("...", right - 10, y, -1);
        }
        String label = String.format("%s", value.getValue() ? "\247f" : "\2477") + value.getFormattedLabel();
        if (value.getFormattedLabel().contains("-")) {
            label = String.format("%s", value.getValue() ? "\247f" : "\2477") + value.getFormattedLabel().split("-")[1];
        }
        SMALL.drawStringWithShadow(label, left, y, -1);
        if (!childValues.isEmpty() && open) {
            for (Component sub : childValues) {
                sub.renderComponent();
            }
        }
    }

    public void refresh() {
        /**
         * refreshes the heights when opening and closing buttons
         */
        int newy = y + buttonHeight - GuiClickable.y + 96;
        for (Component item : childValues) {
            item.setY(newy);
            newy += item.getHeight() + 1;
        }
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        if (!childValues.isEmpty() && open) {
            for (Component sub : childValues) {
                sub.updateComponent(mouseX, mouseY);
            }
        }
        refresh();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        y += GuiClickable.y - 94;
        int left = getX() - 15;
        int right = getX() + getWidth();
        if ((mouseX > left && mouseX < right) && (mouseY < y + buttonHeight && mouseY > y)) {
            switch (button) {
                case 0:
                    value.setValue(!value.getValue());
                    break;
                case 1:
                    if (!value.getChildren().isEmpty()) {
                        parent.subItems.stream().forEach(child -> {
                            if (child instanceof Checkbox) {
                                Checkbox check = (Checkbox) child;
                                if (check != this && check.isOpen()) {
                                    check.setOpen(false);
                                }
                            }
                        });
                        open = !open;
                    }
                    break;
            }
        }
        if (!childValues.isEmpty() && open) {
            for (Component sub : childValues) {
                sub.mouseClicked(mouseX, mouseY, button);
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        if (!childValues.isEmpty() && open) {
            for (Component sub : childValues) {
                sub.mouseReleased(mouseX, mouseY, mouseButton);
            }
        }
    }

    @Override
    public void keyTyped(char typedChar, int key) {
        if (!childValues.isEmpty() && open) {
            for (Component sub : childValues) {
                sub.keyTyped(typedChar, key);
            }
        }
    }

    @Override
    public int getHeight() {
        if (open) {
            int newY = buttonHeight + 3;
            for (Component item : childValues) {
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

    public void setOpen(boolean f) {
        open = f;
    }

    @Override
    public int getX() {
        return parent.getX() + 18;
    }

    @Override
    public int getWidth() {
        return parent.getWidth() - 19;
    }
}

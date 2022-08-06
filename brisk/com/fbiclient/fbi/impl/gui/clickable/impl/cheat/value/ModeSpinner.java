package com.fbiclient.fbi.impl.gui.clickable.impl.cheat.value;

import com.fbiclient.fbi.impl.gui.clickable.GuiClickable;
import net.minecraft.client.gui.Gui;
import org.apache.commons.lang3.text.WordUtils;
import me.valkyrie.api.value.types.EnumValue;
import com.fbiclient.fbi.impl.gui.clickable.component.Component;
import com.fbiclient.fbi.impl.gui.clickable.impl.cheat.CheatButton;

import java.awt.*;

/**
 * @author Kyle
 * @since 4/14/2018
 **/
public class ModeSpinner extends Component {

    private EnumValue value;
    public int index;

    private Enum[] enums;

    public CheatButton parent;

    public ModeSpinner(EnumValue val, CheatButton parent, int y) {
        this.y = y;
        this.parent = parent;
        this.value = val;
        height = 14;
    }

    @Override
    public void renderComponent() {
        y += GuiClickable.y - 94;
        int left = parent.getX() + 18;
        String label = String.format("%s : %s", value.getFormattedLabel(), WordUtils.capitalizeFully(value.getValue().toString()));
        if(value.getFormattedLabel().contains("-")) {
            label = String.format("%s : %s", value.getFormattedLabel().split("-")[1], WordUtils.capitalizeFully(value.getValue().toString()));
        }
        float width = FR.getWidth(label);
        Gui.drawBorderedRect(getX(), y + 1, getX() + (getWidth() + width) - 9, y + height - 2, 0.5, new Color(0x494949).getRGB(), new Color(0, 0, 0, 240).getRGB());
        SMALLER.drawStringWithShadow("<", left - 11, y + 3, -1);
        SMALLER.drawStringWithShadow(">", getX() + (getWidth() + width) - 18, y + 3, -1);
        SMALL.drawStringWithShadow(label, left, y + 3, -1);
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {

    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        y += GuiClickable.y - 94;
        int left = parent.getX() + 18;
        String render = String.format("%s : %s", value.getFormattedLabel(),  WordUtils.capitalizeFully(value.getValue().toString()));
        float width = FR.getWidth(render);
        float right = parent.getX() + (getWidth() + width);
        if ((mouseX > left && mouseX < right) && (mouseY < y + height && mouseY > y)) {
            switch (button) {
                case 0: {
                    enums = value.getValue().getClass().getEnumConstants();
                    if (index != 0)
                        index--;
                    else
                        index = enums.length - 1;
                    value.setValue(enums[index]);
                    break;
                }
                case 1: {
                    enums = value.getValue().getClass().getEnumConstants();
                    if (index < enums.length - 1)
                        index++;
                    else
                        index = 0;
                    value.setValue(enums[index]);
                    break;
                }
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {

    }

    @Override
    public void keyTyped(char typedChar, int key) {

    }

    @Override
    public int getX() {
        return parent.getX() + 3;
    }

    @Override
    public int getWidth() {
        return 32;
    }

}

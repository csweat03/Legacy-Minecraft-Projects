package com.fbiclient.fbi.impl.gui.clickable.impl.cheat.value.slider.casts;

import me.valkyrie.api.value.types.NumberValue;
import com.fbiclient.fbi.impl.gui.clickable.impl.cheat.CheatButton;
import com.fbiclient.fbi.impl.gui.clickable.impl.cheat.value.slider.Slider;

/**
 * @author Kyle
 * @since 4/15/2018
 **/
public class IntegerSlider extends Slider {

    public IntegerSlider(NumberValue value, CheatButton parent, int y) {
        super(value, parent, y);
    }

    /**
     * updates the sliding of the bar
     * @param mouseX - the x pos of users mouse
     * @param mouseY - the y pos of users mouse
     */
    @Override
    public void updateComponent(int mouseX, int mouseY) {
        int left = super.getX();
        int right = super.getX() + super.getWidth();
        int width = right - left;
        this.hovered = ((mouseX > left && mouseX < right) && (mouseY < y + height && mouseY > y));
        double diff = Math.min(width, Math.max(0, mouseX - left));
        int min = (int) this.val.getMin();
        int max = (int) this.val.getMax();
        this.renderWidth = (width) * ((int) this.val.getValue() - min) / (max - min);
        if (this.dragging) {
            if (diff == 0) {
                this.val.setValue(min);
            } else {
                int newValue = (int) (diff / (width) * (max - min) + min);
                this.val.setValue(newValue);
            }
        }
    }

}

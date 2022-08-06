package com.fbiclient.fbi.impl.gui.clickable.impl.cheat.value.slider.casts;

import me.valkyrie.api.value.types.NumberValue;
import com.fbiclient.fbi.impl.gui.clickable.impl.cheat.CheatButton;
import com.fbiclient.fbi.impl.gui.clickable.impl.cheat.value.slider.Slider;
import me.xx.utility.MathUtility;

/**
 * @author Kyle
 * @since 4/15/2018
 **/
public class DoubleSlider extends Slider {

    public DoubleSlider(NumberValue value, CheatButton parent, int y) {
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
        double min = (double) this.val.getMin();
        double max = (double) this.val.getMax();
        this.renderWidth = (width) * ((double) this.val.getValue() - min) / (max - min);
        if (this.dragging) {
            if (diff == 0) {
                this.val.setValue(min);
            } else {
                String lengthStr = String.valueOf(val.getIncrement()).split("0.")[1];
                int length = lengthStr.length();
                double newValue = MathUtility.round(diff / (width) * (max - min) + min, length);
                this.val.setValue(roundTo(newValue, (double)val.getIncrement()));
            }
        }
    }

    public static double roundTo(double v, double r) {
        return MathUtility.round(v / r, 0) * r;
    }

}

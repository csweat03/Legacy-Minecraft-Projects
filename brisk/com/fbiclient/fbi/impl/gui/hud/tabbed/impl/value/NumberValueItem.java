package com.fbiclient.fbi.impl.gui.hud.tabbed.impl.value;

import me.valkyrie.api.value.types.NumberValue;
import me.xx.api.cheat.Cheat;
import me.valkyrie.api.tabbed.ValueTab;

public class NumberValueItem extends ValueTab<NumberValue> {
    public NumberValueItem(Cheat cheat, NumberValue property) {
        super(cheat, property);
    }

    @Override
    public void keyPress(int key) {
        if (key == 28) {
            this.focused = !this.focused;
        }
        if ((key == 200 || key == 208) && this.focused) {
            if (key == 200) {
                (this.property).increase();
            } else {
                (this.property).decrease();
            }
            this.cheat.save();
        }
    }
}

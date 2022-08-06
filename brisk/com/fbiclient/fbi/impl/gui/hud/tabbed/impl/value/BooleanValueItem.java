package com.fbiclient.fbi.impl.gui.hud.tabbed.impl.value;

import me.valkyrie.api.value.types.BooleanValue;
import me.xx.api.cheat.Cheat;
import me.valkyrie.api.tabbed.ValueTab;

public class BooleanValueItem extends ValueTab<BooleanValue> {
    public BooleanValueItem(Cheat cheat, BooleanValue property) {
        super(cheat, property);
    }

    @Override
    public String getText() {
        return String.format("\247%s%s", (this.property).getValue() ? 'f' : '7', (this.property).getLabel().replaceAll("_", " "));
    }

    @Override
    public void keyPress(int key) {
        if (key == 28) {
            (this.property).setValue(!(this.property).getValue());
            this.cheat.save();
        }
        super.keyPress(key);
    }
}

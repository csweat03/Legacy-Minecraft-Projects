package com.fbiclient.fbi.impl.gui.hud.tabbed.impl.value;

import me.valkyrie.api.value.types.EnumValue;
import me.xx.api.cheat.Cheat;
import me.valkyrie.api.tabbed.ValueTab;

public class EnumValueItem extends ValueTab<EnumValue> {
    public EnumValueItem(Cheat cheat, EnumValue property) {
        super(cheat, property);
    }

    @Override
    public void keyPress(int key) {
        if (key == 28) {
            Enum e = (this.property).getValue();
            int ordinal = e.ordinal() + 1;
            ordinal %= (this.property).values().length;
            (this.property).setValue((this.property).values()[ordinal]);
            this.cheat.save();
        }
        super.keyPress(key);
    }
}

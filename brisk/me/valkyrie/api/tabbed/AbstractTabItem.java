package me.valkyrie.api.tabbed;

import com.fbiclient.fbi.client.framework.helper.IHelper;
import com.fbiclient.fbi.impl.gui.hud.tabbed.TabbedGui;

public abstract class AbstractTabItem implements IHelper {

    protected boolean focused;

    public abstract String getText();

    public abstract void keyPress(int p0);

    public boolean isFocused() {
        return this.focused;
    }

    public void draw() {
        float height = FR.getHeight(this.getText().toLowerCase());
        FR.drawStringWithShadow(this.
                        getText().toLowerCase(), 0,
                (TabbedGui.layout().textHeight - height) / 2.0f + 0.5f, -1);
    }
}
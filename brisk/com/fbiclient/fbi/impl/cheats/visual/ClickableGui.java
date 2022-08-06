package com.fbiclient.fbi.impl.cheats.visual;

import org.lwjgl.input.Keyboard;

import me.xx.api.cheat.Category;
import me.xx.api.cheat.Cheat;
import me.xx.api.cheat.CheatManifest;
import com.fbiclient.fbi.impl.gui.clickable.GuiClickable;

/**
 * @author Kyle
 * @since 3/18/2018
 **/
@CheatManifest(label = "Clickable Gui", description = "ur using it", category = Category.VISUAL)
public class ClickableGui extends Cheat {

	private GuiClickable guiClickable;
	
    public ClickableGui() {
        setKey(Keyboard.KEY_RSHIFT);
    }

    public void onEnable() {
        setState(false);
        mc.displayGuiScreen(getGuiClickable());
    }
    

	public GuiClickable getGuiClickable() {
        if (guiClickable == null) {
            guiClickable = new GuiClickable();
        }
        return guiClickable;
    }

}

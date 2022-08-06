package me.valkyrie.api.tabbed;

import com.fbiclient.fbi.impl.gui.hud.tabbed.Folder;
import com.fbiclient.fbi.impl.gui.hud.tabbed.TabbedGui;

public abstract class ExpandableTab extends AbstractTabItem {
	
	@Override
	public void keyPress(int key) {
		if (key == 205) {
			TabbedGui.INSTANCE.add(this.open());
		}
	}

	public abstract Folder open();
}

package com.fbiclient.fbi.impl.gui.hud.tabbed.impl;

import org.apache.commons.lang3.text.WordUtils;

import me.valkyrie.api.tabbed.ExpandableTab;
import me.xx.api.cheat.Category;
import me.xx.api.cheat.Exclude;
import com.fbiclient.fbi.impl.Brisk;
import com.fbiclient.fbi.impl.gui.hud.tabbed.Folder;
import com.fbiclient.fbi.impl.gui.hud.tabbed.TabbedGui;

public class CategoryFolder extends ExpandableTab {
    private Category category;

    public CategoryFolder(Category category) {
        this.category = category;
    }

    @Override
    public String getText() {
        return (TabbedGui.INSTANCE.getMainFolder().getSelectedPart() == this ? "\247f" : "\2477") + WordUtils.capitalizeFully(this.category.toString());
    }

    @Override
    public Folder open() {
        Folder folder = new Folder();
        Brisk.INSTANCE.getCheatManager().getValues().stream().filter(module -> module.getCategory() == this.category).forEach(cheat -> {
            if (!cheat.getClass().isAnnotationPresent(Exclude.class))
                folder.add(new ExpandingButton(cheat));
        });
        return folder;
    }
}

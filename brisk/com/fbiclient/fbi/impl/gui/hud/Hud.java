package com.fbiclient.fbi.impl.gui.hud;

import net.minecraft.client.renderer.GlStateManager;
import me.valkyrie.api.value.Val;
import me.valkyrie.api.value.types.child.Child;
import me.xx.api.cheat.Category;
import me.xx.api.cheat.Cheat;
import me.xx.api.cheat.CheatManifest;
import me.xx.api.event.Register;
import com.fbiclient.fbi.client.events.render.RenderGuiEvent;
import com.fbiclient.fbi.impl.gui.hud.tabbed.TabbedGui;
import com.fbiclient.fbi.impl.gui.hud.themeable.ThemeHandler;
import com.fbiclient.fbi.impl.gui.hud.themeable.impl.BriskTheme;

@CheatManifest(label = "HUD", description = "Displays the client overlay", category = Category.VISUAL)
public class Hud extends Cheat {

	private ThemeHandler themeHandler = new ThemeHandler();

    @Val(label = "Watermark")
    public boolean watermark = true;

    @Val(label = "Tabbed GUI")
    public boolean tabbed = true;

    @Val(label = "Coordinates")
    public boolean coords = true;

    @Val(label = "Arraylist")
    public boolean arraylist = true;
    
    @Child("Arraylist")
    @Val(label = "Color_Mode")
    public Color colorMode = Color.DYNAMIC;

    @Child("Arraylist")
    @Val(label = "German")
    public boolean german = true;

    @Val(label = "Sword_Animation")
    public Hud.Animation animation = Hud.Animation.FLAT;
    
    private BriskTheme briskTheme = new BriskTheme();

    public Hud() {
    	getThemeHandler().setCurrentTheme(briskTheme);
        setState(true);
        setVisible(false);
    }

    @Register
    public void handleRendering(RenderGuiEvent event) {
        if (mc.gameSettings.showDebugInfo) {
            return;
        }
        getThemeHandler().getCurrentTheme().render();
        if(tabbed)
            TabbedGui.INSTANCE.render();
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
    }

    public enum Animation {
        SHADY, PUSH, SLIDE, FLAT
    }

    public enum Color {
        CLIENT, DYNAMIC
    }

    public ThemeHandler getThemeHandler() {
        return themeHandler;
    }

}

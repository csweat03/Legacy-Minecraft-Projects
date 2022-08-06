package com.fbiclient.fbi.impl.gui.hud.addons;

import me.valkyrie.api.value.Val;
import me.valkyrie.api.value.types.constrain.Clamp;
import me.valkyrie.api.value.types.constrain.Increment;
import me.xx.api.cheat.Category;
import me.xx.api.cheat.Cheat;
import me.xx.api.cheat.CheatManifest;
import me.xx.api.cheat.Exclude;
import me.xx.api.event.Register;
import com.fbiclient.fbi.client.events.render.RenderGuiEvent;
import com.fbiclient.fbi.client.framework.helper.ICheats;
import net.minecraft.client.gui.Gui;

import java.awt.*;

/**
 * @author Kyle
 * @since 5/10/2018
 **/
@Exclude
@CheatManifest(label = "Crosshair", description = "Make a custom crosshair", category = Category.VISUAL, visible = false)
public class Crosshair extends Cheat {

    @Increment("0.25")
    @Clamp(min = "0", max = "10")
    @Val(label = "Length")
    public double length;

    @Increment("0.25")
    @Clamp(min = "0", max = "10")
    @Val(label = "Width")
    public double width;

    @Increment("0.1")
    @Clamp(min = "0", max = "10")
    @Val(label = "Gap")
    public double gap;

    @Val(label = "Dynamic")
    public boolean dynamic;

    @Register
    public void handleRendering(RenderGuiEvent event) {
        crosshair(RENDER_HELPER.getRes().getScaledWidth() / 2, RENDER_HELPER.getRes().getScaledHeight() / 2);
    }

    public void crosshair(float x, float y) {
        double dynamicIncrease = (mc.thePlayer.isMoving() && dynamic ? 2 : 0);
        Gui.drawBorderedRect((x) - width, (y) - gap - length - dynamicIncrease, ((x) + 1) + width, (y) - gap - dynamicIncrease, 0.5, ICheats.HUD.getThemeHandler().getCurrentTheme().getColor(), new Color(0, 0, 0, 255).getRGB());
        Gui.drawBorderedRect((x) - width, (y) + gap + 1.0 + dynamicIncrease - 0.15, ((x) + 1) + width, (y + 1) + gap + length + dynamicIncrease - 0.15, 0.5, ICheats.HUD.getThemeHandler().getCurrentTheme().getColor(), new Color(0, 0, 0, 255).getRGB());
        Gui.drawBorderedRect((x) - gap - length - dynamicIncrease + 0.15, (y) - width, (x) - gap - dynamicIncrease + 0.15, ((y) + 1) + width, 0.5, ICheats.HUD.getThemeHandler().getCurrentTheme().getColor(), new Color(0, 0, 0, 255).getRGB());
        Gui.drawBorderedRect((x + 1) + gap + dynamicIncrease, (y) - width, (x) + length + gap + 1.0 + dynamicIncrease, ((y) + 1) + width, 0.5, ICheats.HUD.getThemeHandler().getCurrentTheme().getColor(), new Color(0, 0, 0, 255).getRGB());
    }

}
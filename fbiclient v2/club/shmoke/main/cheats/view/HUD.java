package club.shmoke.main.cheats.view;

import club.shmoke.Client;
import club.shmoke.api.cheat.Category;
import club.shmoke.api.cheat.Cheat;
import club.shmoke.api.event.EventHandler;
import club.shmoke.api.utility.utilities.Styler;
import club.shmoke.main.events.KeyEvent;
import club.shmoke.main.events.RenderEvent;
import club.shmoke.main.gui.tabbable.Tabbable;
import org.lwjgl.input.Keyboard;

import java.awt.*;

/**
 * @author Christian
 */
public class HUD extends Cheat {

    private Tabbable tabbable;

    public HUD() {
        super("HUD", Keyboard.KEY_GRAVE, Category.VIEW, "Display the Heads-Up-Display of the client.");
    }

    public void onEnable() {
        super.onEnable();
        tabbable = new Tabbable();
    }

    @EventHandler
    public void onRender(RenderEvent event) {
        if (event.getType() != RenderEvent.Type.OVERLAY) return;

        font.drawStringWithShadow(Styler.switchCase(Client.GET.NAME), 2, 2, new Color(125, 125, 125, 255).getRGB());

        String idk = Client.GET.VERSION;

        font.drawStringWithShadow(idk, renderUtility.getResolution().getScaledWidth() - font.getStringWidth(idk) - 1, renderUtility.getResolution().getScaledHeight() - 10, new Color(125, 125, 125, 255).getRGB());

        tabbable.render();

        int y = 0;
        for (Cheat cheat : Client.GET.CHEAT_MANAGER.getSorted()) {
            if (!cheat.getVisible() || !cheat.getState()) continue;
            String label = Styler.switchCase(cheat.getLabel() + (cheat.hasSuffix() ? ": \2477" + cheat.getSuffix() : ""));
            cheat.setWidth(font.getStringWidth(label));
            font.drawStringWithShadow(label, renderUtility.getResolution().getScaledWidth() - cheat.getWidth() - 2, (y += 12) - 11, new Color(125, 125, 125, 255).getRGB());
        }

        if (Client.GET.PLAYER_GUI.isPinned()) {
            Client.GET.PLAYER_GUI.getPeople().draw(-1, -1);
        }

    }

    @EventHandler
    public void onKey(KeyEvent event) {
        tabbable.onKey(event);
    }
}

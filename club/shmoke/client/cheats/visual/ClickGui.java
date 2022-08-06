package club.shmoke.client.cheats.visual;

import org.lwjgl.input.Keyboard;
import club.shmoke.api.cheat.Cheat;
import club.shmoke.api.cheat.data.Property;
import club.shmoke.client.ui.click.GuiClick;
import club.shmoke.client.ui.click.themes.horizon.HorizonTheme;
import club.shmoke.client.ui.click.themes.other.OtherTheme;

public class ClickGui extends Cheat {

    public Property<Mode> gui = new Property(this, "Mode", Mode.HORIZON);
    public Property<Integer> scrollbarLength = new Property(this, "ScrollBar Start", 22 * 9 - 2, 50, 400, 1);
    GuiClick click = new GuiClick();
    private String curGui = "";

    public ClickGui() {
        super("Click Gui", Keyboard.KEY_RSHIFT, Type.VISUAL);
    }

    public void onDisable() {
        super.onDisable();
        curGui = gui.getValue().toString();
    }

    @Override
    public void onEnable() {
        if (curGui != gui.getValue().toString()) {
            if (gui.getValue() == Mode.HORIZON) click.setTheme(new HorizonTheme());
            else if (gui.getValue() == Mode.OTHER) click.setTheme(new OtherTheme());
            click.getTheme().insert();
            curGui = gui.getValue().toString();
        }
        mc.displayGuiScreen(click);
        setState(false);
        super.onEnable();
    }

    public enum Mode {
        HORIZON, OTHER
    }
}

package club.shmoke.main.cheats.view;

import club.shmoke.Client;
import club.shmoke.api.cheat.Category;
import club.shmoke.api.cheat.Cheat;
import org.lwjgl.input.Keyboard;

public class GUI extends Cheat {

    public GUI() {
        super("GUI", Keyboard.KEY_TAB, Category.VIEW, "Displays the GUI options");
        setShift(true);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        setState(false);
        mc.displayGuiScreen(Client.GET.PLAYER_GUI.getPeople());
    }
}

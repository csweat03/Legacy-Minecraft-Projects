package club.shmoke.main.gui.tabbable.elements;

import club.shmoke.api.cheat.Cheat;
import org.lwjgl.input.Keyboard;

/**
 * @author Christian
 */
public class BindableTab extends Element {

    private Cheat parent;
    private boolean editing = false;

    public BindableTab(Cheat parent) {
        this.parent = parent;
    }

    public void draw(int startX, int startY) {
        drawElement("Bind: " + parent.getKey(), 0, startX, startY, font.getStringWidth("Bind: " + parent.getKey()));
    }

    public void onInteract(int key) {
        if (key == Keyboard.KEY_RETURN && getID() == 0) editing = !editing;

        else if (editing) {
            parent.setKey(key);
            editing = false;
        }
    }

    public void onClose() {
        editing = false;
    }

}

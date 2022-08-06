package club.shmoke.main.gui.tabbable.elements;

import club.shmoke.api.cheat.Cheat;
import club.shmoke.api.cheat.property.Property;
import org.lwjgl.input.Keyboard;

/**
 * @author Christian
 */
public class NumberTab extends Element {

    private Cheat parent;
    private Property property;
    private boolean editing = false;

    public NumberTab(Cheat parent, Property property) {
        this.parent = parent;
        this.property = property;
    }

    public void draw(int startX, int startY) {
        if (!(property.getValue() instanceof Number)) return;
        Number value = (Number) property.getValue();

        drawElement("Value: " + value, 0, startX, startY, font.getStringWidth("Incrementation: " + property.getInc()));
    }

    public Cheat getParent() {
        return parent;
    }

    public void onInteract(int key) {
        if (key == Keyboard.KEY_RETURN && getID() <= 1) editing = !editing;

        else if (key == Keyboard.KEY_UP || key == Keyboard.KEY_RIGHT)
            property.setValue((Double) property.getValue() - (Double) property.getInc());
        else if (key == Keyboard.KEY_DOWN || key == Keyboard.KEY_LEFT)
            property.setValue((Double) property.getValue() + (Double) property.getInc());

    }

    public void onClose() {
        editing = false;
    }
}


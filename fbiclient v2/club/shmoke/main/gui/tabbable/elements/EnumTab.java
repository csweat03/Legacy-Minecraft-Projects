package club.shmoke.main.gui.tabbable.elements;

import club.shmoke.api.cheat.Cheat;
import club.shmoke.api.cheat.property.Property;
import net.minecraft.client.gui.Gui;

/**
 * @author Christian
 */
public class EnumTab extends Element {

    private Cheat parent;
    private Property property;
    private int id = 0;
    private Enum[] options;

    public EnumTab(Cheat parent, Property property) {
        this.parent = parent;
        this.property = property;
    }

    public void draw(int startX, int startY) {
        if (!(property.getValue() instanceof Enum)) return;

        options = (Enum[]) property.getValue().getClass().getEnumConstants();

        for (Enum options : options) {
            if (options == property.getValue())
                Gui.drawRect(startX, startY + (id * HEIGHT), startX + getWidest(), startY + (id * HEIGHT) + 12, SECONDARY_COLOR);
            drawElement(options.toString(), id, startX, startY + (id * HEIGHT), getWidest());
            id++;
        }
    }

    private int getWidest() {
        int wide = 0;
        for (Enum options : options) {
            int i = font.getStringWidth(options + "");
            if (wide <= i) wide = i;
        }
        return wide;
    }

    public Cheat getParent() {
        return parent;
    }

    public void onInteract(int key) {
    }

    public void onClose() {
    }
}


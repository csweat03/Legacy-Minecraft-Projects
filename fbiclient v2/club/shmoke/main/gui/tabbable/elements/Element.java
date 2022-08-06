package club.shmoke.main.gui.tabbable.elements;

import club.shmoke.api.utility.Utility;
import club.shmoke.api.utility.utilities.Styler;
import net.minecraft.client.gui.Gui;

import java.awt.*;

/**
 * @author Christian
 */
public abstract class Element extends Utility {

    public final int HEIGHT = 12;
    public final int PRIMARY_COLOR = new Color(20, 20, 20, 175).getRGB();
    public final int SECONDARY_COLOR = new Color(175, 175, 175, 200).getRGB();
    public final int TERTIARY_COLOR = new Color(225, 225, 225, 255).getRGB();

    private int id;

    public void drawElement(String label, int id, int startX, int startY, int width) {
        this.id = id;
        label = Styler.switchCase(label);
        // if (id == current_id)
        //     ...
        Gui.drawRect(startX, startY, startX + width, startY + HEIGHT, PRIMARY_COLOR);
        font.drawStringWithShadow(label, startX + 4, startY + 2, TERTIARY_COLOR);
    }

    public int getID() {
        return id;
    }

    public abstract void onInteract(int key);
    public abstract void onClose();

}

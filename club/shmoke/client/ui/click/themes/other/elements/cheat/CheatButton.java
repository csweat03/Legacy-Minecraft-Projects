package club.shmoke.client.ui.click.themes.other.elements.cheat;

import club.shmoke.client.Client;
import club.shmoke.api.cheat.Cheat;
import club.shmoke.client.ui.click.Element;
import club.shmoke.client.ui.click.themes.other.elements.Panel;

/**
 * @author Christian
 */
public class CheatButton extends Element {

    private Panel panel;
    private Cheat cheat;
    private int offset;

    public CheatButton(Panel panel, Cheat cheat) {
        this.panel = panel;
        this.cheat = cheat;
        setWidth(100);
        setHeight(20);
    }

    @Override
    public void drawElement(float posX, float posY, int mouseX, int mouseY, float partialTicks) {
        setPosX(posX);
        setPosY(posY);
        Client.INSTANCE.getFontManager().c20.drawStringWithShadow(cheat.label(), getPosX() + 10, getPosY() + 10, -1);
    }

}

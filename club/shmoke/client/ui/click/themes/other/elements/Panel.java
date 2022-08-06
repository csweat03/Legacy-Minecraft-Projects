package club.shmoke.client.ui.click.themes.other.elements;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import club.shmoke.client.Client;
import club.shmoke.api.cheat.Cheat;
import club.shmoke.client.ui.click.Element;
import club.shmoke.client.util.render.RenderUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Christian
 */
public class Panel extends Element {

    public List<Element> elements = new ArrayList<>();

    private ScaledResolution s;
    private boolean dragging = false;
    private float dragDistX, dragDistY;
    private double x, y;

    @Override
    public void drawElement(float posX, float posY, int mouseX, int mouseY, float partialTicks) {
        s = RenderUtils.getResolution();
        setWidth(200);
        setHeight(100);
        if (dragging) {
            setPosX(mouseX + dragDistX);
            setPosY(mouseY + dragDistY);
        }
        x = getPosX() + s.getScaledWidth() - s.getScaledWidth();
        y = getPosY() + s.getScaledHeight() - s.getScaledHeight();
        Gui.drawBorderedRect(x, y, x + s.getScaledWidth() - getWidth(), y + s.getScaledHeight() - getHeight(), 1, 0xff191a26, 0x90191a26);
        Gui.drawRect(x + 5, y + 20, x + s.getScaledWidth() - getWidth() - 5, y + 20.5, -1);
        RenderUtils.drawCustomImage(x + 3, y + 2, 14, 14, new ResourceLocation("client/click/terminal.png"));
        Client.INSTANCE.getFontManager().c24.drawStringWithShadow("Gui", x + 19, y + 4, -1);
        Client.INSTANCE.getFontManager().cl.drawStringWithShadow("Categories:", x + 400 - Client.INSTANCE.getFontManager().cl.getStringWidth("Categories:") / 2, y + 30, -1);
        for (Cheat.Type type: Cheat.Type.values()) {

        }

        super.drawElement(posX, posY, mouseX, mouseY, partialTicks);
        for (Element element : elements)
            element.drawElement(posX, posY, mouseX, mouseY, partialTicks);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (isOver(mouseX, mouseY) && button == 0) {
            dragging = true;
            dragDistX = getPosX() - mouseX;
            dragDistY = getPosY() - mouseY;
        } else dragging = false;
        super.mouseClicked(mouseX, mouseY, button);
    }

    private boolean isOver(int mouseX, int mouseY) {
        if (mouseX >= x && mouseX <= x + s.getScaledWidth() - getWidth() && mouseY >= y && mouseY <= y + s.getScaledHeight() - getHeight())
            return true;
        return false;
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int button) {
        super.mouseReleased(mouseX, mouseY, button);
        dragging = false;
    }

    public void update() {
        int height = 16;
        for (Element elButton : elements)
            height += elButton.getHeight();
        setHeight(height);
    }
}

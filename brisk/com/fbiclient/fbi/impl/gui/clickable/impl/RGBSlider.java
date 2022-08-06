package com.fbiclient.fbi.impl.gui.clickable.impl;

import com.fbiclient.fbi.impl.gui.clickable.GuiClickable;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;

import com.fbiclient.fbi.impl.gui.clickable.component.Component;
import me.xx.utility.render.GlRender;

import java.awt.*;

/**
 * @author Kyle
 * @since 4/17/2018
 **/
public class RGBSlider extends Component {

    public float value;
    public boolean rainbow;
    private boolean dragging = false;

    @Override
    public void renderComponent() {
        if (rainbow) {
            RENDER_HELPER.drawRainbowRect(getX(), getY(), getX() + getWidth(), getY() + getHeight());
        } else {
            double x = this.getX();
            for (int i = 0; i < this.getWidth(); ++i) {
                int color = Color.HSBtoRGB((i != 0) ? (i / (float) this.getWidth()) : 0.0f, 0.8f, 1.0f);
                Gui.drawRect(x, getY(), getX() + getWidth(), this.getY() + this.getHeight(), color);
                ++x;
            }
        }
    }

    void updateSlide(int mouseX, int mouseY) {
        if (dragging) {
            float diff = mouseX - (float) this.getX();
            this.value = (diff == 0.0f ? 0.0f : (diff / (float) this.getWidth()));
        }
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        updateSlide(mouseX, mouseY);
        boolean inside = mouseX > getX() && mouseX < getX() + getWidth() && mouseY > getY() && mouseY < getY() + getHeight();
        if (dragging && !inside) {
            dragging = false;
        }

        if (inside) {
            String desc = "Shift click to toggle rainbow!";
            Gui.drawBorderedRect(mouseX, mouseY - 10, mouseX + FR.getWidth(desc) + 8, mouseY, 0.5, new Color(0, 0, 0, 200).getRGB(), new Color(0, 0, 0, 240).getRGB());
            SMALLER.drawCenteredString(desc, mouseX + (FR.getWidth(desc) / 2) + 4, mouseY - 8, -1);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (mouseX > getX() && mouseX < getX() + getWidth() && mouseY > getY() && mouseY < getY() + getHeight()) {
            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                rainbow = !rainbow;
            } else
                dragging = true;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        dragging = false;
    }

    @Override
    public void keyTyped(char typedChar, int key) {

    }

    @Override
    public int getY() {
        return RENDER_HELPER.getRes().getScaledHeight() / 2 + 80 + GuiClickable.y - 94;
    }

    @Override
    public int getX() {
        return RENDER_HELPER.getRes().getScaledWidth() / 2 - 82 + GuiClickable.x - 124;
    }

    @Override
    public int getWidth() {
        return 100;
    }

    @Override
    public int getHeight() {
        return 20;
    }

    public int getColor() {
        float rectYMouse = this.getHeight() * value;
        Color c = Color.getHSBColor(rectYMouse / (float) this.getHeight(), 0.7f, 1f);
        if (rainbow)
            return GlRender.getRainbow(10, 0.6, 0.9).getRGB();
        else
            return c.getRGB();
    }
}

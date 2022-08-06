package club.shmoke.main.gui.customizable_gui.panel;

import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;

public abstract class Draggable extends GuiScreen {

    private int x, y, width, height,
            mouseX, mouseY, button;

    public Draggable(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getMouseX() {
        return mouseX;
    }

    public void setMouseX(int mouseX) {
        this.mouseX = mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public void setMouseY(int mouseY) {
        this.mouseY = mouseY;
    }

    public int getButton() {
        return button;
    }

    public void setButton(int button) {
        this.button = button;
    }

    public abstract void onMouseClick(int mouseX, int mouseY, int button);

    public double[] getDraggableDistance() {
        return new double[]{};
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        onMouseClick(mouseX, mouseY, mouseButton);

    }

    public boolean isHoveringPane() {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height && mouseX != 0 && mouseY != 0;
    }

}

package club.shmoke.main.gui.customizable_gui;

import club.shmoke.api.utility.utilities.Timer;
import club.shmoke.main.gui.customizable_gui.panel.PeoplePanel;
import club.shmoke.main.gui.customizable_gui.panel.RadarPanel;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.util.List;

public class GuiElements {

    private int x = 100, y = 100, pane = 0, buttonValue = 0, r = 35, g = 40, b = 45;
    private boolean pinned;
    private List<ResourceLocation> playerHeads;
    private Timer lookupTimer = new Timer();

    public PeoplePanel getPeople() {
        return new PeoplePanel();
    }

    public RadarPanel getRadar() {
        return new RadarPanel();
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

    public int getPane() {
        return pane;
    }

    public void setPane(int pane) {
        this.pane = pane;
    }

    public int getButtonValue() {
        return buttonValue;
    }

    public void setButtonValue(int buttonValue) {
        this.buttonValue = buttonValue;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public Color getColor() {
        return new Color(r, g, b);
    }

    public boolean isPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

    public List<ResourceLocation> getPlayerHeads() {
        return playerHeads;
    }

    public Timer getLookupTimer() {
        return lookupTimer;
    }

    public void setPlayerHeads(List<ResourceLocation> playerHeads) {
        this.playerHeads = playerHeads;
    }
}

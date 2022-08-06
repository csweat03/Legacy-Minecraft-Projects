package com.fbiclient.fbi.impl.gui.hud.tabbed;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import me.valkyrie.api.tabbed.AbstractTabItem;
import com.fbiclient.fbi.client.framework.helper.IHelper;
import com.fbiclient.fbi.impl.Brisk;
import me.xx.utility.Stopwatch;
import me.xx.utility.render.GlRender;

public class Folder implements IHelper {
    private int selectionIndex;
    private double selectorPosition;
    private List<AbstractTabItem> tabItemSet;
    private Stopwatch timer;
    private long time;

    public Folder() {
        this.selectorPosition = 0.0;
        this.tabItemSet = new ArrayList<>();
        this.timer = new Stopwatch();
        this.time = System.currentTimeMillis();
    }

    public void keyPress(int key) {
        if ((key == 200 || key == 208) && !this.getSelectedPart().isFocused()) {
            this.selectionIndex -= ((key == 200) ? 1 : -1);
        } else {
            this.getSelectedPart().keyPress(key);
        }
        this.selectionIndex %= this.tabItemSet.size();
        if (this.selectionIndex < 0) {
            this.selectionIndex = this.tabItemSet.size() - 1;
        }
    }

    public void draw() {
        GlRender.begin();
        GlRender.prepareMaskRender();
        int color = Brisk.INSTANCE.slider.getColor();
        /**Renders the selected container*/
        float percent = (float) Math.pow((System.currentTimeMillis() - this.time) / 10.0, 1.4)
                / TabbedGui.layout().animationTime;
        percent = Math.min(1.0f, percent);
        float border = TabbedGui.layout().panelBorder;
        /**Renders the masked scissor rectangle*/
        Gui.drawRoundedRect(0.0f, -border, percent * (float) Math.hypot(this.getWidth(), this.getWidth()),
                getHeight(), color);
        GlRender.beginDefaultRender();
        Gui.drawRoundedRect(-border, -border, this.getWidth() + border, this.getHeight() + border,
                TabbedGui.layout().backgroundColor);
        /**Renders the selected item*/
        double destination = this.selectionIndex * TabbedGui.layout().textHeight;
        double difference = destination - this.selectorPosition;
        difference *= this.timer.getTimePassed();
        this.timer.reset();
        double amount = difference / (TabbedGui.layout().scrollSpeed * 10.0f);
        Gui.drawRoundedRect(getWidth() - 6, (float)(this.selectorPosition += amount) + 2, getWidth() - 2.5f,
                (float)this.selectorPosition + TabbedGui.layout().textHeight - 2, color);
        /**Renders the sub items*/
        for (int i = 0; i < this.tabItemSet.size(); ++i) {
            AbstractTabItem tabItem = this.tabItemSet.get(i);
            GlStateManager.translate(TabbedGui.layout().fontPadding, i * TabbedGui.layout().textHeight, 0.0f);
            tabItem.draw();
            GlStateManager.translate(-TabbedGui.layout().fontPadding, -i * TabbedGui.layout().textHeight, 0.0f);
        }
        GlRender.end();
    }

    public float getWidth() {
        float width = TabbedGui.layout().panelWidth;
        for (AbstractTabItem tabItem : this.tabItemSet) {
            float w = FR.getWidth(tabItem.getText()) + TabbedGui.layout().fontPadding + 6.0f;
            if (w > width) {
                width = w;
            }
        }
        return width;
    }

    public float getHeight() {
        return this.tabItemSet.size() * TabbedGui.layout().textHeight;
    }

    public AbstractTabItem getSelectedPart() {
        return this.tabItemSet.get(this.selectionIndex);
    }

    public List<AbstractTabItem> getTabItemSet() {
        return this.tabItemSet;
    }

    public void add(AbstractTabItem item) {
        this.tabItemSet.add(item);
    }
}

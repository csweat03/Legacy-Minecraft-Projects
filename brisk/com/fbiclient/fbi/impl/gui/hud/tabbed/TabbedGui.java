package com.fbiclient.fbi.impl.gui.hud.tabbed;

import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;
import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Stack;

import me.xx.api.cheat.Category;
import me.xx.api.event.EventManager;
import me.xx.api.event.Register;
import com.fbiclient.fbi.client.events.game.KeypressEvent;
import com.fbiclient.fbi.impl.gui.hud.tabbed.impl.CategoryFolder;

public enum TabbedGui {

    INSTANCE;

    private static Layout layout = new Layout();
    private Stack<Folder> folders = new Stack<>();
    private Folder mainFolder = new Folder();

    public static Layout layout() {
        return TabbedGui.layout;
    }

    public void init() {
        Arrays.stream(Category.values()).forEach(category -> {
            if(category != Category.GHOST)
                this.mainFolder.add(new CategoryFolder(category));
        });
        this.folders.add(this.mainFolder);
        EventManager.INSTANCE.register(this);
    }

    public void render() {
        GlStateManager.pushMatrix();
        Iterator<Folder> panelIterator = this.folders.iterator();
        float x = 0.0f;
        while (panelIterator.hasNext()) {
            Folder folder = panelIterator.next();
            GlStateManager.translate(2.0f + x, layout.tabTop, 0.0f);
            folder.draw();
            GlStateManager.translate(-(2.0f + x), -layout.tabTop, 0.0f);
            x += folder.getWidth() + layout().panelSeparation;
        }
        GlStateManager.popMatrix();
    }

    public Folder getMainFolder() {
        return this.mainFolder;
    }

    public void add(Folder folder) {
        if (folder == null) {
            return;
        }
        this.folders.add(folder);
    }

    @Register
    public void handleKeypress(KeypressEvent event) {
        if (event.getKey() == 203 && this.folders.size() > 1) {
            this.folders.pop();
        } else {
            this.folders.peek().keyPress(event.getKey());
        }
    }

    public static class Layout {
        public int tabTop = 17;
        public int fontSize = 18;
        public int foregroundColor = new Color(0xffffffff).getRGB();
        public int backgroundColor = 1677721600;
        public int panelWidth = 70;
        public int textHeight = 15;
        public float fontPadding = 5.0f;
        public float panelSeparation = 2.0f;
        public float panelBorder = 0.0f;
        public int scrollSpeed = 5;
        public boolean openingAnimation = true;
        public long animationTime = 120L;
        public File file;

        public void setFontSize(int fontSize) {
            this.fontSize = fontSize;
        }

        public void setForegroundColor(int foregroundColor) {
            this.foregroundColor = foregroundColor;
        }

        public void setBackgroundColor(int backgroundColor) {
            this.backgroundColor = backgroundColor;
        }

        public void setPanelWidth(int panelWidth) {
            this.panelWidth = panelWidth;
        }

        public void setTextHeight(int textHeight) {
            this.textHeight = textHeight;
        }

        public void setFontPadding(float fontPadding) {
            this.fontPadding = fontPadding;
        }

        public void setPanelSeparation(float panelSeparation) {
            this.panelSeparation = panelSeparation;
        }

        public void setPanelBorder(float panelBorder) {
            this.panelBorder = panelBorder;
        }

        public void setScrollSpeed(int scrollSpeed) {
            this.scrollSpeed = scrollSpeed;
        }

        public void setOpeningAnimation(boolean openingAnimation) {
            this.openingAnimation = openingAnimation;
        }

        public void setAnimationTime(long animationTime) {
            this.animationTime = animationTime;
        }

        public int getTop() {
            return tabTop;
        }

        public void setTop(int tabTop) {
            this.tabTop = tabTop;
        }
    }
}

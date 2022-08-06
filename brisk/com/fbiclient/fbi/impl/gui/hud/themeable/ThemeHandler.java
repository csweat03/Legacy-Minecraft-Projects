package com.fbiclient.fbi.impl.gui.hud.themeable;

/**
 * @author Kyle
 * @since 2/10/2018
 **/
public class ThemeHandler<T extends Theme> {

    private T currentTheme;

    public T getCurrentTheme() {
        return currentTheme;
    }

    public void setCurrentTheme(T currentTheme) {
        this.currentTheme = currentTheme;
        currentTheme.layout();
    }
}

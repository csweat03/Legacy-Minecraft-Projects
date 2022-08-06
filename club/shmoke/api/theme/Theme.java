package club.shmoke.api.theme;

import club.shmoke.client.Client;

/**
 * @author Christian
 */
public abstract class Theme {
    private boolean current = false;

    public abstract void render();
    public abstract void renderTab();

    public void setCurrent(Theme theme) {
        for (Theme t: Client.INSTANCE.getThemeManager().getThemes())

        theme.current = true;
    }



}

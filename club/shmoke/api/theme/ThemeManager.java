package club.shmoke.api.theme;

import club.shmoke.client.cheats.visual.theme.themes.Horizon;
import club.shmoke.client.cheats.visual.theme.themes.Supreme;
import club.shmoke.client.cheats.visual.theme.themes.ToggleSneak;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Christian
 */
public class ThemeManager {
    private List<Theme> themes = new ArrayList<>();

    public void loadThemes() {
        add(new Horizon());
        add(new Supreme());
        add(new ToggleSneak());
    }


    private void add(Theme theme) {
        themes.add(theme);
    }

    public Theme get(Class<? extends Theme> theme) {
        for (Theme theme1 : themes)
            if (theme1.getClass() == theme)
                return theme1;
        return null;
    }
    public Theme get(String theme) {
        for (Theme theme1 : themes)
            if (theme1.toString().contains(theme))
                return theme1;
        return null;
    }

    public List<Theme> getThemes() {
        return themes;
    }
}

package club.shmoke.client.cheats.visual;

import club.shmoke.api.cheat.Cheat;
import club.shmoke.api.cheat.data.Property;
import club.shmoke.api.event.interfaces.EventListener;
import club.shmoke.api.theme.Theme;
import club.shmoke.client.Client;
import club.shmoke.client.cheats.visual.theme.themes.Horizon;
import club.shmoke.client.events.other.KeypressEvent;
import club.shmoke.client.events.render.RenderGameOverlayEvent;
import club.shmoke.client.events.render.ScoreboardEvent;
import club.shmoke.client.util.render.GLUtils;
import club.shmoke.client.util.render.RenderUtils;
import club.shmoke.client.util.render.Styler.Style;
import net.minecraft.client.Minecraft;
import org.apache.commons.lang3.text.WordUtils;

public class Overlay extends Cheat {

    public Property<Boolean> watermark = new Property<>(this, "Watermark", true);
    public Property<Boolean> arraylist = new Property<>(this, "Arraylist", true);
    public Property<Boolean> tabgui = new Property<>(this, "TabGUI", true);
    public Property<Boolean> tabguiBorder = new Property<>(this, "TabGUI Border", false);
    public Property<Boolean> info = new Property<>(this, "Information", true);
    public Property<Boolean> scoreboard = new Property<>(this, "Scoreboard", true);
    public Property<Boolean> potions = new Property<>(this, "Potion Status", true);
    public Property<Boolean> armor = new Property<>(this, "Armor Status", true);
    public Property<Boolean> notifications = new Property<>(this, "Notifications", true);
    public Property<Boolean> german = new Property<>(this, "German", false);
    public Property<ThemeType> theme = new Property<>(this, "Theme", ThemeType.HORIZON);
    public Property<Boolean> desriptions = new Property<>(this, "Tooltips", true);
    public Property<Integer> red = new Property<>(this, "Red", 254, 0, 255, 1);
    public Property<Integer> green = new Property<>(this, "Green", 91, 0, 255, 1);
    public Property<Integer> blue = new Property<>(this, "Blue", 53, 0, 255, 1);
    public Property<Boolean> rainbow = new Property<>(this, "Rainbow", false);
    public Property<Style> type = new Property(this, "Type", Style.NORMAL);
    private Property<String> tabguiSub = new Property<>(this, "HUD");
    private Property<String> click = new Property<>(this, "ClickGUI");
    private Property<String> sub = new Property<>(this, "Color");
    private Property<String> subkey = new Property<>(this, "Keystrokes");
    private Property<String> subfont = new Property<>(this, "Font");
    private Minecraft mc = Minecraft.getMinecraft();

    public Overlay() {
        super("Overlay", Type.VISUAL);
    }

    @EventListener
    public void onSocreboardRender(ScoreboardEvent e) {
        if (!scoreboard.getValue())
            e.cancel();
    }

    @EventListener
    public void onRenderGameOverlay(RenderGameOverlayEvent e) {
        if (mc.gameSettings.showDebugInfo)
            return;

        if (rainbow.getValue())
            Client.INSTANCE.setColor(GLUtils.rainbowColor(2, 0.9F).getRed(), GLUtils.rainbowColor(2, 0.9F).getGreen(), GLUtils.rainbowColor(2, 0.9F).getBlue());
        else
            Client.INSTANCE.setColor(red.getValue(), green.getValue(), blue.getValue());

        Theme t = Client.INSTANCE.getThemeManager().get(WordUtils.capitalizeFully(theme.getValue().toString()));

        if (t != null) {
            t.render();
            if (tabgui.getValue())
                t.renderTab();
        }
    }

    @EventListener
    public void onKeypress(KeypressEvent event) {
        if (tabgui.getValue())
            Client.INSTANCE.getTab().onKeyPress(event);
    }

    private enum ThemeType {
        HORIZON, SUPREME, TOGGLESNEAK
    }
}

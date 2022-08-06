package club.shmoke.api.cheat;

import club.shmoke.api.cheat.data.Property;
import club.shmoke.api.cheat.helpers.interfaces.Cheats;
import club.shmoke.api.cheat.helpers.interfaces.IHelper;
import club.shmoke.api.event.EventManager;
import club.shmoke.client.Client;
import club.shmoke.client.util.GameLogger;
import club.shmoke.client.util.manage.Module;
import net.minecraft.client.Minecraft;
import org.apache.commons.lang3.text.WordUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public abstract class Cheat extends Module implements IHelper, Cheats {

    public Minecraft mc = Minecraft.getMinecraft();
    private Type type;
    private String suffix;
    private int macro, animationX;
    private boolean state, visible;
    private ArrayList<Property> properties = new ArrayList<>();

    public Cheat(String label, int key, Type type) {
        this.label = label;
        this.id = label.replace(" ", "");
        this.macro = key;
        this.type = type;
        this.visible = true;
        this.state = false;
    }

    public Cheat(String label, Type Type) {
        this.label = label;
        this.id = label.replace(" ", "");
        this.macro = 0;
        this.type = Type;
        this.visible = true;
        this.state = false;
    }

    public ArrayList<Property> getProperties() {
        return properties;
    }

    public void addProperties(Property... values) {
        Collections.addAll(getProperties(), values);
    }

    public Property getProperty(String id) {
        for (Property v : this.getProperties()) {
            if (v.id().equalsIgnoreCase(id)) {
                return v;
            }
        }

        return null;
    }

    public int width() {
        return Client.INSTANCE.getFontManager().c16.getStringWidth(Client.INSTANCE.getStyler().styleString(label(), false));
    }

    public String id() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String label() {
        return label;
    }

    public String description() {
        return description;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int animationX() {
        return animationX;
    }

    public void setAnimationX(int an) {
        animationX = an;
    }

    public ArrayList<String> aliases() {
        return aliases;
    }

    public String suffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public int macro() {
        return macro;
    }

    public void setMacro(int bind) {
        this.macro = bind;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public void toggle() {
        try {
            if (isBlacklisted())
                setState(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!isBlacklisted())
            setState(!getState());

        if (state)
            onEnable();
        else
            onDisable();
    }

    public boolean isBlacklisted() {
        return Client.INSTANCE.getConfigManager().getHandler().getCurrentConfig().getBlacklists().has(this);
    }

    public boolean visible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Type type() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setType(String type) {
        for (Type t : Type.values()) {
            if (t.name().equalsIgnoreCase(type)) {
                this.type = t;
            }
        }
    }

    public boolean isType(Type cat) {
        return type() == cat;
    }

    public void onEnable() {
        EventManager.register(this);
        GameLogger.log(this.label + " has been toggled on.", true, GameLogger.Type.INFO);
    }

    public void onDisable() {
        EventManager.unregister(this);
        GameLogger.log(this.label + " has been toggled off.", true, GameLogger.Type.INFO);
    }

    public enum Type {
        COMBAT, MOVEMENT, VISUAL, PERSONAL, ENVIRONMENT;

        public String label() {
            return WordUtils.capitalizeFully(name());
        }
    }
}

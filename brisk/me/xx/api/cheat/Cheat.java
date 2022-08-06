package me.xx.api.cheat;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import me.valkyrie.api.value.Value;
import me.valkyrie.api.value.factory.AttributeFactory;
import me.xx.api.event.EventManager;
import com.fbiclient.fbi.client.framework.helper.IHelper;
import com.fbiclient.fbi.impl.Brisk;
import com.fbiclient.fbi.impl.commands.cheats.Values;
import me.xx.utility.render.GlRender;
import me.xx.utility.save.Jsonable;

public abstract class Cheat implements IHelper, Jsonable {

    private static Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private CheatManifest cheatManifest;
    private int key, color;
    private File cheatFile;
    private boolean state, visible;
    protected String suffix, description;
    private Set<Value> values;
    
    private float animation;

    public Cheat() {
        this.key = 0;
        this.color = -1;
        this.visible = false;
        this.suffix = "";
        this.values = new HashSet<>();
        if (!this.getClass().isAnnotationPresent(CheatManifest.class)) {
            return;
        }
        this.cheatManifest = this.getClass().getAnnotation(CheatManifest.class);
        this.visible = this.cheatManifest.visible();
        this.values = AttributeFactory.create(this);
        Brisk.INSTANCE.getCommandManager().getContent().add(new Values(this));
    }

    public void setup() {
        this.cheatFile = new File(Brisk.INSTANCE.getCheatManager().getDirectory() + File.separator
                + this.cheatManifest.label().replaceAll(" ", "") + ".json");
        try {
            if (!this.cheatFile.exists()) {
                this.cheatFile.createNewFile();
                this.save();
                return;
            }
            try {
                this.load();
            } catch (Exception ex) {
            }
        } catch (IOException exception) {
            System.exit(0);
        }
    }

    public Optional<Value> getValue(String input) {
        return this.getRawValues().stream()
                .filter(propertyValue -> propertyValue.matches(input)).findFirst();
    }

    public Set<Value> getValues() {
        return this.values;
    }

    public List<Value> getRawValues() {
        List<Value> values = new LinkedList<>();
        values.addAll(this.values);
        this.values.forEach(propertyValue -> this.registerChildValues(values, propertyValue));
        return values;
    }

    public void registerChildValues(List propertyList, Value value) {
        value.getChildren().forEach((child) -> {
            propertyList.add(child);
            this.registerChildValues(propertyList, (Value) child);
        });
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
        this.save();
    }

    public boolean isVisible() {
        return this.visible;
    }

    public String getDescription() {
        return this.cheatManifest.description();
    }

    public String getId() {
        return this.getLabel().replaceAll(" ", "").toLowerCase();
    }

    public String getLabel() {
        return this.cheatManifest.label();
    }

    public String getArrayLabel() {
        String arrayLabel;
        if (suffix.isEmpty()) {
            arrayLabel = String.format("%s", getLabel());
        } else {
            arrayLabel = String.format("%s\2477 %s", getLabel(),
                    this.suffix);
        }
        return arrayLabel;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public void setSuffix(String suf) {
        suffix = suf;
    }

    public int getColor() {
        return color;
    }

    public int getKey() {
        return this.key;
    }

    public void setKey(int bind) {
        this.key = bind;
    }

    public boolean getState() {
        return this.state;
    }

    public Category getCategory() {
        return this.cheatManifest.category();
    }

    public String[] getHandles() {
        return cheatManifest.handles();
    }

    public boolean matches(String input) {
        return input.equalsIgnoreCase(this.getLabel()) || input.equalsIgnoreCase(this.getId());
    }

    public boolean isBlacklisted() {
        return Brisk.INSTANCE.getConfigManager().getHandler().getCurrentConfig().getBlacklists().has(this);
    }
    
    public float getAnimation() {
    	return animation;
    }
    
    public void setAnimation(float anim) {
    	animation = anim;
    }

    public void toggle() {
        if (!isBlacklisted())
            this.setState(!this.state);
    }

    public void setState(boolean running) {
        this.state = running;
        if (state) {
            enable();
        } else {
            disable();
        }
        this.save();
    }

    private void enable() {
        color = GlRender.getRandomRGB(0.35, 1, 1);
        animation = 0;
        EventManager.INSTANCE.register(this);
        this.onEnable();
    }

    private void disable() {
        this.onDisable();
        EventManager.INSTANCE.unregister(this);
    }

    public void onEnable() {
    }

    public void onDisable() {
    }

    @Override
    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("state", new JsonPrimitive(this.state));
        jsonObject.add("key", new JsonPrimitive(this.key));
        jsonObject.add("visible", new JsonPrimitive(this.visible));
        this.getRawValues().forEach(propertyValue -> jsonObject.add(propertyValue.getDisplayLabel(),
                new JsonPrimitive(propertyValue.getValue().toString())));
        return jsonObject;
    }

    @Override
    public void fromJson(JsonObject jsonObject) {
        this.key = jsonObject.get("key").getAsInt();
        if (jsonObject.has("visible")) {
            this.visible = jsonObject.get("visible").getAsBoolean();
        }
        this.getRawValues().stream().filter(propertyValue -> jsonObject.has(propertyValue.getDisplayLabel()))
                .forEach(propertyValue -> propertyValue
                        .fromString(jsonObject.get(propertyValue.getDisplayLabel()).getAsString()));
        try {
            this.setState(jsonObject.get("state").getAsBoolean());
        } catch (NullPointerException exception) {
            this.setState(false);
        }
    }

    public void load() {
        try {
            this.fromJson(new JsonParser().parse(new FileReader(this.cheatFile)).getAsJsonObject());
        } catch (IOException ex) {
        }
    }

    public void save() {
        try {
            PrintWriter printWriter = new PrintWriter(this.cheatFile);
            printWriter.print(Cheat.GSON.toJson(this.toJson()));
            printWriter.close();
        } catch (IOException | NullPointerException ex) {
        }
    }
}

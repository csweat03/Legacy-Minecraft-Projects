package com.fbiclient.fbi.impl.management;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import me.xx.api.cheat.Category;
import me.xx.api.cheat.Cheat;
import me.xx.api.event.EventManager;
import me.xx.api.event.Register;
import com.fbiclient.fbi.client.events.game.KeypressEvent;
import com.fbiclient.fbi.client.management.types.HashMapManager;
import com.fbiclient.fbi.impl.cheats.combat.AutoHeal;
import com.fbiclient.fbi.impl.cheats.combat.Killaura;
import com.fbiclient.fbi.impl.cheats.misc.AntiBot;
import com.fbiclient.fbi.impl.cheats.misc.AutoRespawn;
import com.fbiclient.fbi.impl.cheats.misc.Freecam;
import com.fbiclient.fbi.impl.cheats.misc.F*cker;
import com.fbiclient.fbi.impl.cheats.misc.NoRotate;
import com.fbiclient.fbi.impl.cheats.motion.Flight;
import com.fbiclient.fbi.impl.cheats.motion.LongJump;
import com.fbiclient.fbi.impl.cheats.motion.NoFall;
import com.fbiclient.fbi.impl.cheats.motion.NoSlowdown;
import com.fbiclient.fbi.impl.cheats.motion.Scaffold;
import com.fbiclient.fbi.impl.cheats.motion.ScreenMove;
import com.fbiclient.fbi.impl.cheats.motion.Speed;
import com.fbiclient.fbi.impl.cheats.motion.Sprint;
import com.fbiclient.fbi.impl.cheats.motion.Velocity;
import com.fbiclient.fbi.impl.cheats.player.AutoArmor;
import com.fbiclient.fbi.impl.cheats.player.AutoTool;
import com.fbiclient.fbi.impl.cheats.player.ChestStealer;
import com.fbiclient.fbi.impl.cheats.player.FastPlace;
import com.fbiclient.fbi.impl.cheats.player.InventoryCleaner;
import com.fbiclient.fbi.impl.cheats.player.QuickConsume;
import com.fbiclient.fbi.impl.cheats.visual.ClickableGui;
import com.fbiclient.fbi.impl.cheats.visual.ESP;
import com.fbiclient.fbi.impl.cheats.visual.Lights;
import com.fbiclient.fbi.impl.cheats.visual.Nametags;
import com.fbiclient.fbi.impl.cheats.visual.NoRender;
import com.fbiclient.fbi.impl.cheats.visual.Tracers;
import com.fbiclient.fbi.impl.cheats.visual.Xray;
import com.fbiclient.fbi.impl.gui.hud.Hud;
import com.fbiclient.fbi.impl.gui.hud.addons.Crosshair;
import me.xx.utility.render.font.IFontRendering;

public class CheatManager extends HashMapManager<String, Cheat> {

    private File moduleDirectory;

    public CheatManager(File parentDirectory) {
        super.setup();
        this.moduleDirectory = new File(parentDirectory + File.separator + "cheat" + File.separator);
        if (!this.moduleDirectory.exists() && !this.moduleDirectory.mkdirs()) {
            System.exit(0);
        }
        EventManager.INSTANCE.register(this);
    }

    public void register() {
        register(new AutoHeal(), new Killaura(), new Velocity(), new Sprint(), new NoFall(), new Flight(), new Lights(),
                new NoSlowdown(), new ScreenMove(), new LongJump(), new Speed(), new QuickConsume(), new NoRotate(),
                new Nametags(), new Hud(), new AutoArmor(), new ChestStealer(), new Scaffold(),
                new InventoryCleaner(), new ClickableGui(), new Tracers(), new Xray(), new NoRender(), new ESP(),
                new F*cker(), new FastPlace(), new Freecam(), new AntiBot(), new AutoRespawn(), new AutoTool(), new Crosshair());
        System.out.println("Finished registering cheat");
    }

    public void setup() {
        register();
        getValues().forEach(Cheat::setup);
    }

    /**
     * Registers the cheat into the map with the correct label data
     *
     * @param cheats - list of cheat to register
     */
    public void register(Cheat... cheats) {
        for (Cheat cheat : cheats) {
            include(cheat.getId(), cheat);
        }
    }

    public Cheat lookup(Class<? extends Cheat> clazz) {
        return getValues().stream().filter(m -> m.getClass() == clazz).findFirst().orElse(null);
    }

    public Cheat lookup(String find) {
        return pull(find.replaceAll(" ", ""));
    }

    public List<Cheat> getCheatsForRendering(IFontRendering fontRenderer) {
        return getValues().stream().filter(module -> module.getState() && module.isVisible())
                .sorted(Comparator.comparingDouble(module -> -fontRenderer.getWidth(module.getArrayLabel())))
                .collect(Collectors.toList());
    }

    public List<Cheat> getInCategory(Category cat) {
        List<Cheat> cheats = new ArrayList<>();
        for (Cheat c : getValues()) {
            if (c.getCategory() == cat) {
                cheats.add(c);
            }
        }
        return cheats;
    }

    public File getDirectory() {
        return this.moduleDirectory;
    }

    @Register
    public void handle(KeypressEvent event) {
        this.getRegistry().values().stream().filter(module -> module.getKey() == event.getKey()).forEach(Cheat::toggle);
    }

}

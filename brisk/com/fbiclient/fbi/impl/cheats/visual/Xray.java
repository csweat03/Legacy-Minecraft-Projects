package com.fbiclient.fbi.impl.cheats.visual;

import me.xx.api.cheat.Category;
import me.xx.api.cheat.Cheat;
import me.xx.api.cheat.CheatManifest;

@CheatManifest(label = "Xray", description = "See ores through blocks", category = Category.VISUAL)
public class Xray extends Cheat {

    private float lastGamma;

    public void onEnable() {
        lastGamma = mc.gameSettings.gammaSetting;
        mc.gameSettings.gammaSetting = 100;
        mc.renderGlobal.loadRenderers();
    }

    public void onDisable() {
        mc.gameSettings.gammaSetting = lastGamma;
        mc.renderGlobal.loadRenderers();
    }

}

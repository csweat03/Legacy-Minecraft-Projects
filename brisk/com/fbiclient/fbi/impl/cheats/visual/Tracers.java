package com.fbiclient.fbi.impl.cheats.visual;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import me.valkyrie.api.value.Val;
import me.xx.api.cheat.Category;
import me.xx.api.cheat.Cheat;
import me.xx.api.cheat.CheatManifest;
import me.xx.api.event.Register;
import com.fbiclient.fbi.client.events.render.RenderEntityEvent;
import com.fbiclient.fbi.impl.Brisk;
import me.xx.utility.MathUtility;

/**
 * @author Kyle
 * @since 3/18/2018
 **/
@CheatManifest(label = "Tracers", description = "Draws lines to players", category = Category.VISUAL)
public class Tracers extends Cheat {

    @Val(label = "Mode")
    public Mode mode = Mode.LINE;

    @Val(label = "Spines")
    public static boolean spines;

    @Val(label = "Invisibles")
    public boolean invisibles;

    @Register
    private void handleRendering(final RenderEntityEvent e) {
        for (EntityPlayer p : mc.theWorld.playerEntities) {
            if (!qualifies(p))
                continue;
            if (p != mc.thePlayer && p.isEntityAlive()) {
                float color = Math.round(255.0 - mc.thePlayer.getDistanceSqToEntity(p) * 255.0 / MathUtility.square(mc.gameSettings.renderDistanceChunks * 2)) / 255.0f;
                double[] colors;
                if (Brisk.INSTANCE.getFriendManager().isFriend(p.getUniqueID())) {
                    double[] array = colors = new double[3];
                    array[0] = 0.0;
                    array[2] = (array[1] = 1.0);
                } else {
                    double[] array2 = colors = new double[3];
                    array2[0] = 1.0f - color;
                    array2[2] = (array2[1] = 0.0);
                }
                switch (this.mode) {
                    case BEACON: {
                        RENDER_HELPER.drawBeacon(p, colors);
                        continue;
                    }
                    case LINE: {
                        RENDER_HELPER.drawLine(p, colors);
                        continue;
                    }
                }
            }
        }
    }

    private boolean qualifies(Entity entity) {
        return (entity.isInvisible() ? invisibles : true);
    }

    enum Mode {
        LINE, BEACON
    }

}

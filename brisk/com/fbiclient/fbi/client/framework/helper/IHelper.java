package com.fbiclient.fbi.client.framework.helper;

import net.minecraft.client.Minecraft;

import java.awt.*;

import com.fbiclient.fbi.client.framework.helper.game.ProfileHelper;
import com.fbiclient.fbi.client.framework.helper.game.RenderHelper;
import com.fbiclient.fbi.client.framework.helper.player.AngleHelper;
import com.fbiclient.fbi.client.framework.helper.player.BlockHelper;
import com.fbiclient.fbi.client.framework.helper.player.ContainerHelper;
import com.fbiclient.fbi.client.framework.helper.player.EntityHelper;
import com.fbiclient.fbi.client.framework.helper.player.MotionHelper;
import me.xx.utility.render.font.IFontRendering;
import me.xx.utility.render.font.TTFFontRenderer;

public interface IHelper {

    Minecraft mc = Minecraft.getMinecraft();

    IFontRendering FR = new TTFFontRenderer(new Font("Tahoma", 0, 18));
    IFontRendering SMALL = new TTFFontRenderer(new Font("Tahoma", 0, 16));
    IFontRendering SMALLER = new TTFFontRenderer(new Font("Tahoma", 0, 14));

    IFontRendering LARGE = new TTFFontRenderer(new Font("Mistral", 0, 45));

    BlockHelper BLOCK_HELPER = new BlockHelper();
    MotionHelper MOTION_HELPER = new MotionHelper();
    AngleHelper ANGLE_HELPER = new AngleHelper();
    ContainerHelper CONTAINER_HELPER = new ContainerHelper();
    EntityHelper ENTITY_HELPER = new EntityHelper();

    ProfileHelper PROFILE_HELPER = new ProfileHelper();
    RenderHelper RENDER_HELPER = new RenderHelper();

}

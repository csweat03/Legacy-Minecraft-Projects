package club.shmoke.api.utility;

import club.shmoke.Client;
import club.shmoke.api.utility.utilities.*;
import net.minecraft.client.Minecraft;

/**
 * @author Christian
 */
public class Utility {

    public Minecraft mc = Minecraft.getMinecraft();
    public FontUtility fontUtility = Client.GET.FONT_UTILITY;
    public FontUtility.CFontRenderer font = fontUtility.def;

    /* Helpers */
    public GLUtility glUtility = new GLUtility();
    public MathUtility mathUtility = new MathUtility();
    public RenderUtility renderUtility = new RenderUtility();
    public RotationUtility rotationUtility = new RotationUtility();
    public Styler styler = new Styler();
    public PlayerUtility playerUtility = new PlayerUtility();
    public BlockUtility blockUtility = new BlockUtility();

}

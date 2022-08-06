package club.shmoke.client.util.render;

import java.util.Random;

import org.apache.commons.lang3.text.WordUtils;
import club.shmoke.client.Client;
import club.shmoke.client.cheats.visual.Overlay;

public class Styler
{
    public String styleString(String str, boolean capitalize)
    {
        Overlay overlay = (Overlay) Client.INSTANCE.getCheatManager().get(Overlay.class);

        switch (overlay.type.getValue())
        {
            case LOWERCASE:
                return str.toLowerCase();

            case NORMAL:
            default:
                return capitalize ? WordUtils.capitalizeFully(str) : str;
        }
    }

    public enum Style
    {
        LOWERCASE, NORMAL;
    }
    
    
}

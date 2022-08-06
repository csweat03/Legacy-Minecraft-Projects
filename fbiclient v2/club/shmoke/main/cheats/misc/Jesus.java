package club.shmoke.main.cheats.misc;

import club.shmoke.api.cheat.Category;
import club.shmoke.api.cheat.Cheat;
import club.shmoke.api.cheat.property.Property;
import club.shmoke.api.event.EventHandler;
import club.shmoke.main.cheats.misc.jesus.*;
import club.shmoke.main.cheats.misc.jesus.impl.BHop;
import club.shmoke.main.cheats.misc.jesus.impl.LAAC;
import club.shmoke.main.cheats.misc.jesus.impl.NCP;
import club.shmoke.main.cheats.misc.jesus.impl.Spartan;
import club.shmoke.main.events.UpdateEvent;

/**
 * @author Christian
 */
public class Jesus extends Cheat {

    private Property<Mode> mode = new Property<>(this, "Mode", Mode.NCP);

    private JesusHelper[] modes = {new LAAC(), new BHop(), new NCP(), new Spartan()};

    public Jesus() {
        super("Jesus", 0, Category.MISC, "Allows you to walk on liquids.");
    }

    @EventHandler
    public void onUpdate(UpdateEvent event) {
        setSuffix("" + mode.getValue());
        for (JesusHelper jesus : modes)
            if (mode.getValue() == jesus.getMode())
                jesus.onWater();
    }

    public enum Mode {
        NCP, SPARTAN, LAAC, BHOP
    }

}

package club.shmoke.main.cheats.misc.jesus;

import club.shmoke.api.utility.Utility;
import club.shmoke.main.cheats.misc.Jesus;

public abstract class JesusHelper extends Utility {

    public abstract void onWater();

    public Jesus.Mode getMode() {
        return Jesus.Mode.valueOf(this.getClass().getSimpleName().toUpperCase());
    }

}

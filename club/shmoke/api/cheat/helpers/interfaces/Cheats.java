package club.shmoke.api.cheat.helpers.interfaces;

import club.shmoke.api.cheat.Cheat;
import club.shmoke.client.*;
import club.shmoke.client.cheats.combat.*;
import club.shmoke.client.cheats.environment.*;
import club.shmoke.client.cheats.movement.*;
import club.shmoke.client.cheats.personal.*;
import club.shmoke.client.cheats.visual.*;

/**
 * @author Christian
 */
public interface Cheats {

    Killaura killaura = (Killaura) ModuleGetter.get(Killaura.class);
    Speed speed = (Speed) ModuleGetter.get(Speed.class);
    LongJump longjump = (LongJump) ModuleGetter.get(LongJump.class);
    Flight flight = (Flight) ModuleGetter.get(Flight.class);
    Velocity velocity = (Velocity) ModuleGetter.get(Velocity.class);
    Scaffold scaffold = (Scaffold) ModuleGetter.get(Scaffold.class);
    NoFall noFall = (NoFall) ModuleGetter.get(NoFall.class);
    Overlay overlay = (Overlay) ModuleGetter.get(Overlay.class);
    NoSlowDown noslow = (NoSlowDown) ModuleGetter.get(NoSlowDown.class);
    Step step = (Step) ModuleGetter.get(Step.class);
    
    class ModuleGetter {
        public static Cheat get(Class clazz) {
            return Client.INSTANCE.getCheatManager().get(clazz);
        }
    }

}
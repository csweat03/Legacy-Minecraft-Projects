package club.shmoke.main.cheats.move;

import club.shmoke.api.cheat.Category;
import club.shmoke.api.cheat.Cheat;
import club.shmoke.api.cheat.property.Property;
import club.shmoke.api.event.EventHandler;
import club.shmoke.main.cheats.move.speed.SpeedHelper;
import club.shmoke.main.cheats.move.speed.impl.*;
import club.shmoke.main.events.UpdateEvent;

public class Speed extends Cheat {

    private Property<Mode> mode = new Property<>(this, "Mode", Mode.SLOWHOP);

    private SpeedHelper[] modes = {new SlowHop(), new Hop(), new Faithful(), new KohiHop(), new KohiGround(),
            new GamingChair(), new Mineplex(), new HypixelPort(), new LAAC(), new Frames(), new Cubecraft(), new Fiona()};

    public Speed() {
        super("Speed", 0, Category.MOVE, "Makes you go vroomvroom like that big blue hedgehog guy");
    }

    @Override
    public void onEnable() {
        super.onEnable();
        mc.timer.timerSpeed = 1f;
    }

    @EventHandler
    public void onUpdate(UpdateEvent event) {
        for (SpeedHelper speed : modes)
            if (mode.getValue() == speed.getMode())
                speed.onUpdate();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.timer.timerSpeed = 1f;
        mc.thePlayer.setSpeed(playerUtility.getBaseMoveSpeed() - 0.1);
    }

    public enum Mode {
        SLOWHOP, HOP, FAITHFUL, KOHIHOP, KOHIGROUND, GAMINGCHAIR, MINEPLEX, HYPIXELPORT, LAAC, FRAMES, CUBECRAFT, FIONA
    }
}

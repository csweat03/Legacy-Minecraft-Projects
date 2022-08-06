package club.shmoke.main.cheats.move;

import club.shmoke.api.cheat.Category;
import club.shmoke.api.cheat.Cheat;
import club.shmoke.api.cheat.property.Property;
import club.shmoke.api.event.EventHandler;
import club.shmoke.main.cheats.move.flight.*;
import club.shmoke.main.events.PacketEvent;
import club.shmoke.main.events.RenderEvent;
import club.shmoke.main.events.UpdateEvent;

/**
 * @author Christian
 */
public class Flight extends Cheat {

    public Property<Mode> mode = new Property<>(this, "Mode", Mode.VANILLA);

    public Flight() {
        super("Flight", 0, Category.MOVE, "Allows you to fly");
    }

    private Vanilla vanilla = new Vanilla();
    private Fiona fiona = new Fiona();
    private CavePvP cavePvP = new CavePvP();
    private Faithful faithful = new Faithful();
    private HAC hac = new HAC();
    private Cubecraft cubecraft = new Cubecraft();
    private Hypixel hypixel = new Hypixel();
    private MinemanClub minemanClub = new MinemanClub();

    @EventHandler
    public void onUpdate(UpdateEvent event) {
        setSuffix(mode.getValue() + "");
        // if (event.getType() != UpdateEvent.Type.POST) return;
        switch (mode.getValue()) {
            case VANILLA:
                vanilla.onUpdate();
                break;
            case FIONA:
                fiona.onUpdate();
                break;
            case CAVEPVP:
                cavePvP.onUpdate();
                break;
            case FAITHFUL:
                faithful.onUpdate();
                break;
            case HAC:
                hac.onUpdate();
                break;
            case CUBECRAFT:
                cubecraft.onUpdate();
                break;
            case MINEMANCLUB:
                minemanClub.onUpdate(event);
                break;
        }
    }

    @EventHandler
    public void onRender(RenderEvent event) {
        if (event.getType() != RenderEvent.Type.OVERLAY) return;
        if (mode.getValue() != Mode.HYPIXEL) return;
        hypixel.onUpdate();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        vanilla.onDisable();
        if (mode.getValue() == Mode.CUBECRAFT) mc.thePlayer.motionY -= 0.5;
        mc.timer.timerSpeed = 1;
        if (mc.thePlayer.isMoving())
            mc.thePlayer.setSpeed(0.2);
    }

    public enum Mode {
        VANILLA, HYPIXEL, FIONA, CAVEPVP, FAITHFUL, HAC, CUBECRAFT, MINEMANCLUB, MINEPLEX
    }

}

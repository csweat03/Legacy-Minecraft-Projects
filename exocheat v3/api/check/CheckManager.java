package club.shmoke.main.api.check;

import club.shmoke.main.api.helper.ListManager;
import club.shmoke.main.exocheat.checks.*;

import java.util.ArrayList;
import java.util.List;

public class CheckManager extends ListManager {

    private List<Check> checks = new ArrayList<>();
    private List<Class> classes = new ArrayList<>();

    public Check get(Class<? extends Check> check) {
        for (Check c : checks)
            if (c.getClass() == check)
                return c;
        return null;
    }

    public List<Check> getContents() {
        return checks;
    }

    public void load() {
        registerClasses();
        for (Class<? extends Check> classe : classes) {
            Check check = null;
            try {
                check = classe.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            CheckInfo info = classe.getAnnotation(CheckInfo.class);
            check.setLabel(info.label());
            check.setActive(info.active());
            check.setSilent(info.silent());
            check.setBannable(info.ban());
            check.setDebug(info.debug());
            check.setAlertTillBan(info.alertsTillBan());
            checks.add(check);
        }
    }

    private void registerClasses() {
        classes.add(Sprint.class);
        classes.add(Speed.class);
        classes.add(Packets.class);
        classes.add(NoFall.class);
        classes.add(Killaura.class);
        classes.add(FastUse.class);
        classes.add(Flight.class);
        classes.add(FastLadder.class);
    }
}

package club.shmoke.anticheat.check;

import club.shmoke.anticheat.check.checks.*;
import club.shmoke.anticheat.check.checks.AutoClicker;
import club.shmoke.anticheat.check.checks.flight.FlightGlide;
import club.shmoke.anticheat.check.checks.flight.FlightHorizontal;
import club.shmoke.anticheat.check.checks.flight.FlightVertical;
import club.shmoke.anticheat.check.checks.jesus.JesusA;
import club.shmoke.anticheat.check.checks.jesus.JesusB;
import club.shmoke.anticheat.check.checks.Reach;
import club.shmoke.anticheat.check.checks.noslow.NoSlowBlocking;
import club.shmoke.anticheat.check.checks.noslow.NoSlowSoulSand;
import club.shmoke.anticheat.check.checks.speed.SpeedOnGround;
import club.shmoke.anticheat.check.checks.speed.SpeedMotionY;
import club.shmoke.anticheat.check.checks.speed.SpeedExteremity;
import club.shmoke.anticheat.check.checks.Timer;
import club.shmoke.anticheat.check.checks.InvalidView;
import club.shmoke.anticheat.check.checks.speed.SpeedConstant;

import java.util.ArrayList;

public class CheckManager {
    private ArrayList<Check> checks;

    public CheckManager() {
        checks = new ArrayList();
    }

    public void registerChecks() {
        addCheck(new FastPlace());
        addCheck(new SpeedOnGround());
        addCheck(new SpeedMotionY());
        addCheck(new SpeedExteremity());
        addCheck(new SpeedConstant());
        addCheck(new AutoClicker());
        addCheck(new FlightGlide());
        addCheck(new FlightHorizontal());
        addCheck(new FlightVertical());
        addCheck(new NoSlowBlocking());
        addCheck(new Timer());
        addCheck(new InvalidView());
        addCheck(new NoFall());
        addCheck(new FastBow());
        addCheck(new Reach());
        addCheck(new Sprint());
        addCheck(new JesusA());
        addCheck(new JesusB());
        addCheck(new FastLadder());
        addCheck(new Step());
        addCheck(new InventoryMove());
        addCheck(new FloodMovement());
        addCheck(new FakeSwing());
        addCheck(new Killaura());
        addCheck(new NoSlowSoulSand());
    }

    public void addCheck(Check c) {
        if (!checks.contains(c))
            checks.add(c);
    }

    public ArrayList<Check> getChecks() {
        ArrayList<Check> array = new ArrayList<>();
        for (Check check : checks) {
            if (!array.contains(check))
                array.add(check);
        }
        return array;
    }

    public Check get(Class<? extends Check> clazz) {
        for (Check check : checks) {
            if (check.getClass() == clazz)
                return check;
        }
        return null;
    }

    public Check get(String name) {
        for (Check check : checks) {
            if (check.getLabel().equalsIgnoreCase(name))
                return check;
        }
        return null;
    }
}

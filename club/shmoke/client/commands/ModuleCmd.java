package club.shmoke.client.commands;

import club.shmoke.api.cheat.Cheat;
import club.shmoke.api.cheat.data.Property;
import club.shmoke.api.command.Command;
import club.shmoke.client.Client;
import club.shmoke.client.util.GameLogger;
import org.apache.commons.lang3.text.WordUtils;

import java.util.ArrayList;

/**
 * @author Christian
 */
public class ModuleCmd extends Command {

    private static ArrayList<String> aliases = new ArrayList<>();

    public ModuleCmd() {
        super("Modules", "Changes Module Values", ".{module} {option} {value}", aliases);
        for (Cheat c : Client.INSTANCE.getCheatManager().getContents())
            aliases.add(c.id());
    }

    @Override
    public void dispatch(String[] args, String message) {
        Cheat cheat = Client.INSTANCE.getCheatManager().get(message.substring(1, message.indexOf(" ")));
        if (cheat.getProperties() == null) return;
        if (args.length == 1) {
            for (Property prop : cheat.getProperties())
                if (args[0].equalsIgnoreCase(prop.label()) && prop.getValue() instanceof Boolean && cheat.getProperties().contains(prop)) {
                    prop.setValue(!(boolean) prop.getValue());
                    GameLogger.log("\247c" + cheat.label() + "'s \2477property \247c" + prop.label() + "\2477 has been toggled \247c" + ((boolean) prop.getValue() ? "on" : "off") + "\2477.", false, GameLogger.Type.INFO);
                    return;
                }
        } else if (args.length == 2) {
            for (Property prop : cheat.getProperties()) {
                if (args[0].equalsIgnoreCase(prop.label()) && cheat.getProperties().contains(prop)) {
                    if (prop.getValue() instanceof Integer && Integer.parseInt(args[1]) >= 0 && Integer.parseInt(args[1]) <= (int) prop.getMax()) {
                        try {
                            prop.setValue(Integer.parseInt(args[1]));
                            GameLogger.log("\247c" + cheat.label() + "'s \2477property \247c" + prop.label() + "\2477 has been update. \247cCurrent: \2477" + prop.getValue(), false, GameLogger.Type.INFO);
                        } catch (Exception ex) {
                            GameLogger.log("Invalid Value!", false, GameLogger.Type.ERROR);
                        }
                    } else if (prop.getValue() instanceof Double && Double.parseDouble(args[1]) >= 0.0 && Double.parseDouble(args[1]) <= (double) prop.getMax()) {
                        try {
                            prop.setValue(Double.parseDouble(args[1]));
                            GameLogger.log("\247c" + cheat.label() + "'s \2477property \247c" + prop.label() + "\2477 has been update. \247cCurrent: \2477" + prop.getValue(), false, GameLogger.Type.INFO);
                        } catch (Exception ex) {
                            GameLogger.log("Invalid Value!", false, GameLogger.Type.ERROR);
                        }
                    } else if (prop.getValue() instanceof Enum) {
                        for (Enum e : (Enum[]) prop.getValue().getClass().getEnumConstants()) {
                            if (e.name().equalsIgnoreCase(args[1])) {
                                prop.setValue(e);
                                GameLogger.log("\247c" + cheat.label() + "'s \2477property \247c" + prop.label() + "\2477 has been update. \247cCurrent: \2477" + WordUtils.capitalizeFully(prop.getValue().toString()), false, GameLogger.Type.INFO);
                                return;
                            }
                        }
                    } else {
                        GameLogger.log("Invalid Value!", false, GameLogger.Type.ERROR);
                    }
                    return;
                }
            }
        } else {
            GameLogger.log(syntaxMsg(), false, GameLogger.Type.ERROR);
        }
    }
}

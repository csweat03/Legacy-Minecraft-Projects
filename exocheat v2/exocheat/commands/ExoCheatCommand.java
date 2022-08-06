package club.shmoke.main.exocheat.commands;

import club.shmoke.main.api.check.Check;
import club.shmoke.main.api.command.Command;
import club.shmoke.main.exocheat.ExoCheat;
import org.apache.commons.lang3.text.WordUtils;

/**
 * @author Christian
 */
public class ExoCheatCommand extends Command {

    public ExoCheatCommand() {
        super("exocheat");
    }

    @Override
    public void onCommand(String[] args) {
        if (args.length == 0) {
            message(getSender(), "Use Arguments Dumbo...");
            return;
        }
        if (args[0].equalsIgnoreCase("toggle")) {
            ExoCheat.get().setAnticheatStatus(!ExoCheat.get().getAnticheatStatus());
            message(getSender(), "Anticheat State: " + ExoCheat.get().getAnticheatStatus());
        }
        if (args[0].equalsIgnoreCase("")) {
            ExoCheat.Level level = ExoCheat.Level.NORMAL;
            switch (args[1].toLowerCase()) {
                case "normal":
                    level = ExoCheat.Level.NORMAL;
                    break;
                case "strict":
                    level = ExoCheat.Level.STRICT;
                    break;
                case "lenient":
                    level = ExoCheat.Level.LENIENT;
                    break;
            }
            ExoCheat.get().setLevel(level);
            message(getSender(), "Anticheat Level: " + WordUtils.capitalizeFully(ExoCheat.get().getLevel().toString()));
        }
        for (Check check : ExoCheat.get().getCheckManager().getContents())
            if (args[0].equalsIgnoreCase(check.getLabel())) {
                switch (args[1].toLowerCase()) {
                    case "silent":
                        check.setSilent(!check.isSilent());
                        break;
                    case "active":
                        check.setActive(!check.isActive());
                        break;
                    case "ban":
                        check.setBannable(!check.isBannable());
                        break;
                    case "debug":
                        check.setDebug(!check.isDebug());
                        break;
                }
                message(getSender(), check.getLabel() + "'s " + args[1] + " status, has been updated.");
            }
    }
}

package club.shmoke.main.api.command;

import club.shmoke.main.api.helper.ListManager;
import club.shmoke.main.exocheat.commands.ExoCheatCommand;

import java.util.ArrayList;
import java.util.List;

public class CommandManager extends ListManager {

    private List<Command> commands = new ArrayList<>();

    public void load() {
        add(new ExoCheatCommand());
        /*
        add(new Example());
        */
    }

    public Command get(Command command) {
        for (Command cmd : commands) {
            if (cmd == command)
                return command;
        }
        return null;
    }

    public List<Command> getContents() {
        return commands;
    }

    private void add(Command command) {
        commands.add(command);
    }
}

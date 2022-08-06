package club.shmoke.anticheat.command;

import club.shmoke.anticheat.command.commands.ExoCheat;
import club.shmoke.anticheat.command.commands.Unban;

import java.util.ArrayList;

public class CommandManager {

    private ArrayList<Command> commands = new ArrayList<>();

    public void registerCommands() {
        add(new ExoCheat());
        add(new Unban());
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

    public ArrayList<Command> getCommands() {
        return commands;
    }

    private void add(Command command) {
        commands.add(command);
    }
}

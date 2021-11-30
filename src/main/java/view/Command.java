package view;

import java.util.ArrayList;

public enum Command {
    // Commands And Their Regex, Help

    // Global Commands
    GLOBAL_BACK(null, "back", "back"),
    GLOBAL_HELP(null, "help", "help"),

    // Invalid Command
    INVALID(null, null, null);

    // Value
    private final Section section;
    private final String regex;
    private final String helper;

    // Constructor
    Command(Section section, String regex, String help) {
        this.section = section;
        this.regex = regex;
        this.helper = help;
    }

    // Regex Getter
    public String getRegex() {
        return this.regex;
    }

    // Section Getter
    public Section getSection() {
        return section;
    }

    // Regex helper
    public String getHelper() {
        return this.helper;
    }

    // Static Methods
    public static ArrayList<Command> getCommands(Section section) {
        ArrayList<Command> commands = new ArrayList<>();
        for (Command value : Command.values()) {
            if (value.getSection() == section) commands.add(value);
        }
        commands.add(Command.GLOBAL_BACK);
        commands.add(Command.GLOBAL_HELP);
        return commands;
    }
}
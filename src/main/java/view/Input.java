package view;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Input {
    /* Static Fields */
    public static Scanner scanner = new Scanner(System.in);

    /* Instance Fields */
    private final ArrayList<Command> commands;
    private Command currentC;
    private Matcher currentMatcher;

    /* Constructor */
    public Input(Section section) {
        this.commands = Command.getCommands(section);
    }

    /* Static methods */
    public static Matcher getCommandMatcher(String input, String regex) {
        Matcher matcher;
        matcher = Pattern.compile(regex).matcher(input);
        if (matcher.find()) return matcher;
        return null;
    }

    /* Getters */
    public Command getCurrentC() {
        return this.currentC;
    }

    /* Instance Methods */
    public void validation(String input) throws Exception {
        for (Command command : commands) {
            if (input.matches(command.getRegex())) {
                this.currentC = command;
                this.currentMatcher = getCommandMatcher(input, command.getRegex());
                return;
            }
        }
        throw new Exception("invalid command");
    }

    public void interact() {
        try {
            validation(scanner.nextLine().trim());
        } catch (Exception e) {
            this.currentC = null;
            System.out.println(e.getMessage());
        }
    }

    public String get(String groupName) {
        return this.currentMatcher.group(groupName);
    }

    public void commandHelp() {
        StringBuilder help = new StringBuilder();
        for (Command command : this.commands) {
            help.append(command.getHelper()).append("\n");
        }
        help.setLength(help.length() - 1);
        System.out.println(help);
    }
}

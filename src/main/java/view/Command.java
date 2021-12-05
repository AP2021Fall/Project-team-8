package view;

import java.util.ArrayList;

public enum Command {
    // Commands And Their Regex, Help

    // Register Section
    REGISTER_LOGIN_SIGN_UP(Section.REGISTER_LOGIN, "^\\s*user create --username (?<username>.+) --password1 (?<password1>.+) --password2 (?<password2>.+) --email (?<email>.+) --name (?<name>.+) --birthday (?<birthday>\\d{4}-\\d{2}-\\d{2})\\s*$", "user create --username <username> --password1 <password> --password2 <password> --email <email> --name <name> --birthday <yyyy-MM-dd>"),
    REGISTER_LOGIN_LOGIN(Section.REGISTER_LOGIN, "^\\s*user login --username (?<username>.+) --password (?<password>.+)\\s*$", "user login --username <username> --password <password>"),

    //Main menu
    Enter_Profile_Menu(Section.Main_Menu,"^\\s*enter menu Profile Menu\\s*$","enter menu Profile Menu"),
    Enter_Team_Menu(Section.Main_Menu,"^\\s*enter menu Team Menu\\s*$","enter menu Team Menu"),
    Enter_Tasks_Page(Section.Main_Menu,"^\\s*enter menu Tasks Page\\s*$","enter menu Tasks Page"),
    Enter_Calender_Menu(Section.Main_Menu,"^\\s*enter menu Calender Menu\\s*$","enter menu Calender Menu"),
    Enter_Notification_Bar(Section.Main_Menu,"^\\s*enter menu Notification Bar\\s*$","enter menu Notification Bar"),
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
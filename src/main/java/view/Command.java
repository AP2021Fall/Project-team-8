package view;

import java.util.ArrayList;

public enum Command {
    // Commands And Their Regex, Help

    // Register Section
    REGISTER_LOGIN_SIGN_UP(Section.REGISTER_LOGIN, "^\\s*user create --username (?<username>.+) --password1 (?<password1>.+) --password2 (?<password2>.+) --email (?<email>.+) --name (?<name>.+) --birthday (?<birthday>\\d{4}-\\d{2}-\\d{2})\\s*$", "user create --username <username> --password1 <password> --password2 <password> --email <email> --name <name> --birthday <yyyy-MM-dd>"),
    REGISTER_LOGIN_LOGIN(Section.REGISTER_LOGIN, "^\\s*user login --username (?<username>.+) --password (?<password>.+)\\s*$", "user login --username <username> --password <password>"),

    // Main Section
    MAIN_ENTER_MENU(Section.MAIN, "^\\s*enter menu (?<menuName>.+)\\s*$", "enter menu <" + Menu.availableMenu() + ">"),

    // Profile Section
    PROFILE_SHOW(Section.PROFILE, "^\\s*profile show --profile\\s*$", "profile show --profile"),
    PROFILE_SHOW_NOTIFICATION(Section.PROFILE, "^\\s*profile show --notification\\s*$", "profile show --notification"),
    PROFILE_SHOW_LOG(Section.PROFILE, "^\\s*profile show --logs\\s*$", "profile show --logs"),
    PROFILE_SHOW_BOARDS(Section.PROFILE, "^\\s*profile show --boards\\s*$", "profile show --boards"),
    PROFILE_SHOW_BOARD(Section.PROFILE, "^\\s*profile show --board (?<board>.+)\\s*$", "profile show --board <board name or number>"),

    PROFILE_CHANGE_PASSWORD(Section.PROFILE, "^\\s*profile change password --current (?<oldPass>.+) --new (?<newPass>.+)\\s*$", "profile change password --current <current password> --new <new password>"),
    PROFILE_CHANGE_USERNAME(Section.PROFILE, "^\\s*profile change username --username (?<newUsername>.+)\\s*$", "profile change username --username <new username>"),


    // Workspace Section
    WORKSPACE_ENTER_BOARD(Section.WORKSPACE, "^\\s*workspace enter --board (?<board>.+)\\s*$", "workspace enter --board <board name or number>"),
    WORKSPACE_CHANGE_BOARD_NAME(Section.WORKSPACE, "^\\s*workspace change --board (?<board>.+) --newName (?<newName>.+)\\s*$", "workspace change --board <board name or number> --newName <newName>"),
    WORKSPACE_REMOVE_BOARD(Section.WORKSPACE, "^\\s*workspace remove --board (?<board>.+)\\s*$", "workspace remove --board <board name or number>"),
    WORKSPACE_CREATE_BOARD(Section.WORKSPACE, "^\\s*workspace create --board (?<board>.+)\\s*$", "workspace create --board <name of board>"),
    WORKSPACE_QUIT_BOARD(Section.WORKSPACE, "^\\s*workspace quit --board (?<board>.+)\\s*$", "workspace quit --board <board name or number>"),
    WORKSPACE_SHOW(Section.WORKSPACE, "^\\s*workspace show\\s*$", "workspace show"),

    // Board Section
    // For Leader
    BOARD_ADD_LIST(Section.BOARD, "^\\s*board list add --name (?<name>(?:(?!--order).)*)(?: --order (?<order>\\d+))?\\s*$", "leader commands:\n\tboard list add --name <name> --order <order (default: will add at the end)>"),
    BOARD_REMOVE_LIST(Section.BOARD, "^\\s*board list remove --name (?<name>.+)\\s*$", "\tboard list remove --name <name>"),
    BOARD_CHANGE_LIST(Section.BOARD, "^\\s*board list change --name (?<name>(?:(?!--newName|--order).)+)(?: --newName (?<newName>(?:(?!--order).)+))?(?: --order (?<order>.+))?\\s*$", "\tboard list change --name <name> --newName <newName (optional)> --order <order (optional)>"),

    BOARD_ADD_TASK(Section.BOARD, "^\\s*board task add --title (?<title>.+) --deadline (?<deadline>(?:(?! --list| --priority|--description).)*)(?: --list (?<list>(?:(?! --priority|--description).)*))?(?: --priority (?<priority>(?:(?! --description).)*))?(?: --description (?<description>.+))?\\s*$", "\tboard task add --title <title> --deadline <deadline (format: yyyy-MM-dd|HH:mm)> --list <list (default: will add to the first list)> --priority < (Highest, High, Low, Lowest) or (1-4) (optional)> --description <description (optional)>"),
    BOARD_DELETE_TASK(Section.BOARD, "^\\s*board task delete --id (?<id>\\d+)\\s*$", "\tboard task delete --id <id>"),
    BOARD_CHANGE_TASK(Section.BOARD, "^\\s*board task change --id (?<id>\\d+)(?: --title (?<title>(?:(?!--deadline|--list|--priority|--description).)+))?(?: --deadline (?<deadline>(?:(?!--list|--priority|--description).)+))?(?: --list (?<list>(?:(?!--priority|--description).)+))?(?: --priority (?<priority>(?:(?!--description).)+))?(?: --description (?<description>.+))?\\s*$", "\tboard task change --id <id> --title <new title (optional)> --deadline <new deadline (format: yyyy-MM-dd|HH:mm) (optional)> --list <new list (optional)> --priority < (Highest, High, Low, Lowest) or (1-4) (optional)> --description <description (optional)>"),
    BOARD_ASSIGN_TO(Section.BOARD, "^\\s*board task assigned --id (?<taskId>\\d+) --users (?<assign>.+)\\s*$", "\tboard task assigned --id <taskId> --users <comma separated username>"),
    BOARD_REMOVE_ASSIGN_TO(Section.BOARD, "^\\s*board task unassigned --id (?<taskId>\\d+) --users (?<assign>.+)\\s*$", "\tboard task unassigned --id <taskId> --users <comma separated username>"),

    BOARD_INVITE_USER(Section.BOARD, "^\\s*board user invite --username (?<username>.+)\\s*$", "\tboard user invite --username <username>"),
    BOARD_REMOVE_USER(Section.BOARD, "^\\s*board user remove --username (?<username>.+)\\s*$", "\tboard user remove --username <username>"),
    BOARD_SUSPEND_USER(Section.BOARD, "^\\s*board user suspend --username (?<username>(?:(?!--remove).)+)(?:(?<unBan> --remove))?\\s*$", "\tboard user suspend --username <username> --remove (optional set if you wanna unban)"),
    BOARD_CHANGE_LEADER(Section.BOARD, "^\\s*board user change leader --username (?<username>.+)\\s*$", "\tboard user change leader --username <username>"),

    // For All
    BOARD_SHOW_DETAILS(Section.BOARD, "^\\s*board show details\\s*$", "\nuser commands:\n\tboard show details"),
    BOARD_SHOW_LISTS(Section.BOARD, "^\\s*board list show\\s*$", "\tboard list show"),
    BOARD_TASK_SHOW(Section.BOARD, "^\\s*board task show --id (?<id>\\d+)\\s*$", "\tboard task show --id <id>"),
    BOARD_TASK_COMMENT(Section.BOARD, "^\\s*board task comment --id (?<id>\\d+) --comment (?<comment>.+)\\s*$", "\tboard task comment --id <id> --comment <comment>"),
    BOARD_TASK_NEXT_BACK(Section.BOARD, "^\\s*board task move --id (?<id>\\d+) --(?<direction>next|previous)\\s*$", "\tboard task move --id <id> --<next|previous>"),
    BOARD_TASK_FORCE(Section.BOARD, "^\\s*board task move --force --id (?<id>\\d+) --list (?<listName>.+)\\s*$", "\tboard task move --force --id <id> --list <listName>"),
    BOARD_CHAT(Section.BOARD, "^\\s*board chat\\s*$", "\tboard chat"),
    BOARD_SCOREBOARD(Section.BOARD, "^\\s*board scoreboard\\s*$", "\tboard scoreboard"),
    BOARD_ROADMAP(Section.BOARD, "^\\s*board roadmap\\s*$", "\tboard roadmap"),

    // Chat Section
    CHAT_SHOW(Section.CHAT, "^\\s*chat show\\s*$", "chat show"),
    CHAT_SEND_MESSAGE(Section.CHAT, "^\\s*chat send --message (?<message>.+)\\s*$", "chat send --message <your message>"),
    CHAT_SEND_NOTIFICATION(Section.CHAT, "^\\s*chat send( --user (?<user>.+))? --notification (?<notification>.+)\\s*$", "chat send --user <username (optional)> --notification <notification>"),

    // Scoreboard Section
    SCOREBOARD_SHOW(Section.SCOREBOARD, "^\\s*scoreboard show\\s*$", "scoreboard show"),

    // Calendar Section
    CALENDAR_SHOW_DEADLINES(Section.CALENDAR, "^\\s*calendar show --deadlines\\s*$", "calendar show --deadlines"),

    // Roadmap Section
    ROADMAP_SHOW(Section.ROADMAP, "^\\s*roadmap show\\s*$", "roadmap show"),

    // Admin Section
    ADMIN_USER_SHOW(Section.ADMIN, "^\\s*user show --username (?<username>.+)\\s*$", "user show --username <username>"),
    ADMIN_USER_BAN(Section.ADMIN, "^\\s*user ban --username (?<username>.+)\\s*$", "user ban --username <username>"),
    ADMIN_USER_UNBAN(Section.ADMIN, "^\\s*user unban --username (?<username>.+)\\s*$", "user unban --username <username>"),
    ADMIN_USER_REMOVE(Section.ADMIN, "^\\s*user remove --username (?<username>.+)\\s*$", "user remove --username <username>"),
    ADMIN_BOARD_SHOW_ALL(Section.ADMIN, "^\\s*board show --all\\s*$", "board show --all"),
    ADMIN_BOARD_SHOW_SCOREBOARD(Section.ADMIN, "^\\s*board show scoreboard --board (?<board>.+)\\s*$", "board show scoreboard --board <board>"),
    ADMIN_BOARD_SHOW_PENDING(Section.ADMIN, "^\\s*board show --pending\\s*$", "board show --pending"),
    ADMIN_BOARD_ACCEPT(Section.ADMIN, "^\\s*board accept --board (?<board>.+)\\s*$", "board accept --board <comma seperated boards' id>"),
    ADMIN_BOARD_REJECT(Section.ADMIN, "^\\s*board reject --board (?<board>.+)\\s*$", "board reject --board <comma seperated boards' id>"),
    ADMIN_BOARD_CHANGE_LEADER(Section.ADMIN, "^\\s*board change leader --board (?<board>.+) --username (?<username>.+)\\s*$", "board change leader --board <board id> --username <username of new leader>"),
    ADMIN_SEND_NOTIFICATION_ALL(Section.ADMIN, "^\\s*send --notification (?<notification>.+) --all\\s*$", "send --notification <notification> --all"),
    ADMIN_SEND_NOTIFICATION_USER(Section.ADMIN, "^\\s*send --notification (?<notification>.+) --username (?<username>.+)\\s*$", "send --notification <notification> --username <username>"),
    ADMIN_SEND_NOTIFICATION_BOARD(Section.ADMIN, "^\\s*send --notification (?<notification>.+) --board (?<board>.+)\\s*$", "send --notification <notification> --board <board id>"),


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
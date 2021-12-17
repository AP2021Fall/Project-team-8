package view;

import view.entities.CalendarView;
import view.entities.ProfileView;
import view.entities.WorkspaceView;

import java.util.Locale;

public enum Menu {
    PROFILE("Profile", ProfileView.class),
    BOARD("Board", WorkspaceView.class),
    CALENDAR("Calendar", CalendarView.class);

    // Value
    private final String name;
    private final Class<? extends View> view;

    // Constructor
    Menu(String name, Class<? extends View> viewClass) {
        this.name = name;
        this.view = viewClass;
    }

    // Static Methods
    // Create View
    public static View createView(String menuName, Window window) {
        try {
            for (Menu value : Menu.values()) {
                if (value.name.toLowerCase(Locale.ROOT).equals(menuName.toLowerCase(Locale.ROOT))) {
                    return value.view.getDeclaredConstructor(Window.class).newInstance(window);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static String availableMenu() {
        StringBuilder out = new StringBuilder();
        for (Menu value : Menu.values()) {
            out.append(value.name).append("|");
        }
        out.setLength(out.length() - 1);
        return out.toString();
    }
}

package model.domain;

import java.util.Locale;

public enum Priority {
    // Priority Types
    HIGHEST(1, "Highest"), HIGH(2, "High"), LOW(3, "Low"), LOWEST(4, "Lowest");

    // Value
    private final int nth;
    private final String name;

    // Constructor
    Priority(int nth, String name) {
        this.nth = nth;
        this.name = name;
    }

    // Getters
    public int getNth() {
        return nth;
    }

    public String getName() {
        return name;
    }

    // Static Methods
    public static Priority getPriorityByName(String name) {
        for (Priority value : Priority.values()) {
            if (value.getName().toLowerCase(Locale.ROOT).equals(name.toLowerCase(Locale.ROOT))) return value;
        }
        return null;
    }

    public static Priority assertPriorityExistsNth(int nth) throws Exception {
        Priority priority = getPriorityByNth(nth);
        if (getPriorityByNth(nth) == null)
            throw new Exception("priority must be in range 1 - 4");
        return priority;
    }

    public static Priority assertPriorityExistsName(String name) throws Exception {
        Priority priority = getPriorityByName(name);
        if (getPriorityByName(name) == null)
            throw new Exception("priority must be (Highest | High | Low | Lowest)");
        return priority;
    }

    public static Priority getPriorityByNth(int nth) {
        for (Priority value : Priority.values()) {
            if (value.nth == nth) return value;
        }
        return null;
    }
}

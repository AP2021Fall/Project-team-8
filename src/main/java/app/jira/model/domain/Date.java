package app.jira.model.domain;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Date {
    /* Static Fields */
    public static final DateTimeFormatter sourceFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd|HH:mm");

    /* Instance Fields */
    private final LocalDate date;

    /* Constructor */
    public Date(String date) {
        this.date = LocalDate.parse(date, sourceFormat);
    }

    /* Getters And Setters */
    public LocalDate getDate() {
        return date;
    }

    /* Instance Methods */
    @Override
    public String toString() {
        return getDate().format(sourceFormat);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Date) {
            Date x = (Date) obj;
            return this.getDate().isEqual(x.getDate());
        }
        return false;
    }
}

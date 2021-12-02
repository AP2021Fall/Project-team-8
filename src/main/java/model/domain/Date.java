package model.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Date {
    /* Static Fields */
    public static final DateTimeFormatter sourceFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

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

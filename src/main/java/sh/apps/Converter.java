package sh.apps;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

public class Converter {

    public static LocalDate convertDateToLocalDate(Date date) {
        return convertDateToZonedDateTime(date).toLocalDate();
    }

    public static LocalDateTime convertDateToLocalDateTime(Date date) {
        return convertDateToZonedDateTime(date).toLocalDateTime();
    }

    public static ZonedDateTime convertDateToZonedDateTime(Date date) {
        return date.toInstant().atZone(ZoneOffset.UTC.normalized());
    }

}

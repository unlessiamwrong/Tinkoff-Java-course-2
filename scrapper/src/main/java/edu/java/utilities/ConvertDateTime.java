package edu.java.utilities;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ConvertDateTime {

    public static String execute(OffsetDateTime dateTime) {
        return dateTime.atZoneSameInstant(ZoneId.of("Europe/Moscow"))
            .format(DateTimeFormatter.ofPattern("HH:mm dd.MM.yy"));
    }
}

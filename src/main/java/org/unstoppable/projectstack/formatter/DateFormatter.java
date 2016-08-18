package org.unstoppable.projectstack.formatter;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateFormatter implements Formatter<LocalDateTime> {
    private static final String PATTERN = "yyy-MM-dd HH:mm:ss";

    @Override
    public LocalDateTime parse(String text, Locale locale) throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN);
        return LocalDateTime.parse(text, formatter);
    }

    @Override
    public String print(LocalDateTime object, Locale locale) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN);
        return object.format(formatter);
    }
}

package edu.java.bot.utilities.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractParser<T> {
    protected abstract T process(String data);

    public T parse(String link) {
        Pattern pattern = getPattern();
        Matcher matcher = pattern.matcher(link);
        if (matcher.find()) {
            String data = matcher.group();
            return process(data);
        }
        return null;
    }

    protected abstract Pattern getPattern();
}




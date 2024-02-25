package edu.java.bot.utilities.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StackOfLinkParser {
    public String parse(String link) {
        Pattern pattern = Pattern.compile("(\\d+)");
        Matcher matcher = pattern.matcher(link);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }
}

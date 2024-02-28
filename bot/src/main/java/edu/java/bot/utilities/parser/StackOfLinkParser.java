package edu.java.bot.utilities.parser;

import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class StackOfLinkParser extends AbstractParser<String> {
    @Override
    protected String process(String data) {
        return data;
    }

    @Override
    protected Pattern getPattern() {
        return Pattern.compile("(\\d+)");
    }
}


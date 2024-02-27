package edu.java.bot.utilities.parser;

import java.util.regex.Pattern;

public class GitHubLinkParser extends AbstractParser<String[]> {
    @Override
    protected String[] process(String data) {
        return data.split("/");
    }

    @Override
    protected Pattern getPattern() {
        return Pattern.compile("([^/]+)/([^/]+)$");
    }
}


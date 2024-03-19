package edu.java.utilities.parser;

import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
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


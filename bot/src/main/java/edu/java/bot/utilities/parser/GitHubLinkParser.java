package edu.java.bot.utilities.parser;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GitHubLinkParser {
    public Map<String, String> parse(String link) {
        Pattern pattern = Pattern.compile("([^/]+)/([^/]+)$");
        Matcher matcher = pattern.matcher(link);
        if (matcher.find()) {
            String owner = matcher.group(1);
            String repo = matcher.group(2);
            return Map.of(owner, repo);
        }
        return null;
    }
}

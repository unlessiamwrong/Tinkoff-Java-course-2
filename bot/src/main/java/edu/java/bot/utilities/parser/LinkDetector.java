package edu.java.bot.utilities.parser;

public class LinkDetector {

    private LinkDetector() {

    }

    public static LinkType detectLinkType(String link) {
        if (link.contains("github.com")) {
            return LinkType.GITHUB;
        }
        if (link.contains("stackoverflow.com")) {
            return LinkType.STACK_OVERFLOW;
        }
        return LinkType.UNKNOWN;
    }

    public enum LinkType {
        GITHUB, STACK_OVERFLOW, UNKNOWN
    }
}

package edu.java.utilities.links;

import java.net.URI;

public class LinkChecker {

    public static boolean isLinkValid(URI url) {
        String urlString = url.toString();
        return urlString.contains("stackoverflow") || urlString.contains("github");
    }
}

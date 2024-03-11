package edu.java.utilities;

import java.net.URI;

public class LinkChecker {

    public static boolean isLinkValid(URI url) {
        String urlString = url.toString();
        return urlString.contains("stackoverflow") || urlString.contains("github");
    }
}

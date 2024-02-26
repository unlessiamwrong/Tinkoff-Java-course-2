package edu.java.bot.models;

public class Link {

    public final String url;
    public String info;

    public Link(String url, String json) {
        this.url = url;
        this.info = json;

    }

    public void updateInfo(String newInfo) {
        info = newInfo;
    }
}

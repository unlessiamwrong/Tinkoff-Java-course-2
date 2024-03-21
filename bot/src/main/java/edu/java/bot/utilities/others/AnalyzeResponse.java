package edu.java.bot.utilities.others;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AnalyzeResponse {

    public static String getErrorMessage(String response) {
        if (response.endsWith("not found")) {
            return "You are not registered. To do so use /start command";
        }
        if (response.equals("Your current tracked links: \n")) {
            return "List is empty. You can add links with command /track";
        }
        return null;
    }
}

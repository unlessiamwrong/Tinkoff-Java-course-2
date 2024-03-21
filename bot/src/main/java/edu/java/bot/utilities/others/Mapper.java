package edu.java.bot.utilities.others;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Map;

public class Mapper {

    public static String getExceptionMessage(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        String result;
        try {
            Map<String, String> map = objectMapper.readValue(json, new TypeReference<>() {
            });
            result = map.get("exceptionMessage");
        } catch (IOException e) {
            return "Unexpected error";
        }
        return result;
    }
}

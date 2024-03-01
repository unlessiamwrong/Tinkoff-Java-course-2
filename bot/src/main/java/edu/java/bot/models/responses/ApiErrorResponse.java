package edu.java.bot.models.responses;

import java.util.List;

public record ApiErrorResponse(
    String description,
    String code,
    String exceptionName,
    List<String> stacktrace

) {
}

package edu.java.models.responses;

import java.util.List;

public record ApiErrorResponse(
    String code,
    String exceptionName,
    String exceptionMessage,
    List<String> stacktrace
) {
}

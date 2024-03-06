package edu.java.dto.responses;

import java.util.List;

public record ApiErrorResponse(
    String code,
    String exceptionName,
    String exceptionMessage,
    List<String> stacktrace
) {
}

package edu.java.dto.responses;

public record ApiErrorResponse(

    String description,
    String code,
    String exceptionName,
    String exceptionMessage
) {
}

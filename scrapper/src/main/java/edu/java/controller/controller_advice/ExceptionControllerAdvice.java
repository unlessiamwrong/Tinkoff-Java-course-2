package edu.java.controller.controller_advice;

import edu.java.dto.responses.ApiErrorResponse;
import edu.java.exceptions.InvalidParamsException;
import edu.java.exceptions.LinkAlreadyExists;
import edu.java.exceptions.NotFoundException;
import edu.java.exceptions.UserAlreadyRegisteredException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    private static final String BAD_REQUEST_CODE = "400";

    private static final String NOT_FOUND_CODE = "404";

    private static final String CONFLICT_CODE = "409";

    @ExceptionHandler(InvalidParamsException.class)
    public ResponseEntity<ApiErrorResponse> invalidParams(InvalidParamsException ex) {
        ApiErrorResponse response = createError(ex, BAD_REQUEST.getReasonPhrase(), BAD_REQUEST_CODE);
        return ResponseEntity.status(BAD_REQUEST).body(response);
    }

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    public ResponseEntity<ApiErrorResponse> userAlreadyRegistered(UserAlreadyRegisteredException ex) {
        ApiErrorResponse response = createError(ex, CONFLICT.getReasonPhrase(), CONFLICT_CODE);
        return ResponseEntity.status(CONFLICT).body(response);
    }

    @ExceptionHandler(LinkAlreadyExists.class)
    public ResponseEntity<ApiErrorResponse> linkAlreadyExists(LinkAlreadyExists ex) {
        ApiErrorResponse response = createError(ex, CONFLICT.getReasonPhrase(), CONFLICT_CODE);
        return ResponseEntity.status(CONFLICT).body(response);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiErrorResponse> notFoundException(NotFoundException ex) {
        ApiErrorResponse response = createError(ex, NOT_FOUND.getReasonPhrase(), NOT_FOUND_CODE);
        return ResponseEntity.status(NOT_FOUND).body(response);
    }

    private ApiErrorResponse createError(Throwable exception, String description, String httpCode) {
        String exceptionName = exception.getClass().getSimpleName();
        String exceptionMessage = exception.getMessage();
        return new ApiErrorResponse(description, httpCode, exceptionName, exceptionMessage);
    }

}

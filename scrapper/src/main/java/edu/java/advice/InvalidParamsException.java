package edu.java.advice;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

@RestControllerAdvice
public class InvalidParamsException extends RuntimeException {

    @ExceptionHandler(InvalidParamsException.class)
    public Problem handleException(InvalidParamsException exception) {
        return Problem.builder()
            .withStatus(Status.BAD_REQUEST)
            .withTitle("Invalid request params")
            .withDetail(exception.getMessage())
            .build();
    }
}

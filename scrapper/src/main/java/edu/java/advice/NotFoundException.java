package edu.java.advice;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

@RestControllerAdvice
public class NotFoundException extends RuntimeException {

    @ExceptionHandler(NotFoundException.class)
    public Problem handleException(NotFoundException exception) {
        return Problem.builder()
            .withStatus(Status.NOT_FOUND)
            .withTitle("Not found")
            .withDetail(exception.getMessage())
            .build();
    }
}

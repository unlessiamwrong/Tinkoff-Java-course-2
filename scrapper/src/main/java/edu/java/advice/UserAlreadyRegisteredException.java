package edu.java.advice;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

@RestControllerAdvice
public class UserAlreadyRegisteredException extends RuntimeException {

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    public Problem handleException(UserAlreadyRegisteredException exception) {
        return Problem.builder()
            .withStatus(Status.CONFLICT)
            .withTitle("User already registered")
            .withDetail(exception.getMessage())
            .build();
    }
}


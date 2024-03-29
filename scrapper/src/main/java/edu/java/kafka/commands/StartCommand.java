package edu.java.kafka.commands;

import edu.java.exceptions.UserAlreadyRegisteredException;
import edu.java.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartCommand {

    private final UserService userService;

    public String execute(Long userId) {
        try {
            userService.add(userId);
        } catch (UserAlreadyRegisteredException e) {
            return e.getMessage();
        }
        return "You are successfully registered";
    }
}

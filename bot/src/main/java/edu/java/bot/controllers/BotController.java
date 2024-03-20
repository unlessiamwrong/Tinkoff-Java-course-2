package edu.java.bot.controllers;

import edu.java.bot.dto.requests.LinkUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BotController {

    /**
     * Send Update
     *
     * @param linkUpdateRequest updates of link
     */
    @Operation(
        summary = "Send Update",
        responses = {
            @ApiResponse(responseCode = "200", description = "Update processed"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
        }
    )
    @PostMapping("/updates")
    public void postUpdate(@RequestBody LinkUpdateRequest linkUpdateRequest) {

    }
}

package edu.java.bot.controllers;

import edu.java.bot.dto.requests.LinkUpdateRequest;
import edu.java.bot.updatemanager.UpdateManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BotController {

    private final UpdateManager updateManager;

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
    public void postUpdate(@RequestBody @Valid List<LinkUpdateRequest> linkUpdateRequest) {
        updateManager.sendUpdates(linkUpdateRequest);
    }
}

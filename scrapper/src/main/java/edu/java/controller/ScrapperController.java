package edu.java.controller;

import edu.java.dto.requests.AddLinkRequest;
import edu.java.dto.requests.RemoveLinkRequest;
import edu.java.dto.responses.LinkResponse;
import edu.java.dto.responses.ListLinksResponse;
import edu.java.services.LinkService;
import edu.java.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ScrapperController {

    private final UserService userService;
    private final LinkService linkService;

    /**
     * Register chat
     *
     * @param id user id
     */
    @Operation(
        summary = "Register chat",
        responses = {
            @ApiResponse(responseCode = "200", description = "Chat registered"),
            @ApiResponse(responseCode = "400", description = "Invalid request params"),
        }
    )
    @PostMapping("/users/{id}")
    public void postUser(@PathVariable("id") long id) {
        userService.add(id);
    }

    /**
     * Delete chat
     *
     * @param id user id
     */
    @Operation(
        summary = "Delete chat",
        responses = {
            @ApiResponse(responseCode = "200", description = "Chat successfully deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid request params"),
            @ApiResponse(responseCode = "404", description = "Chat not found")
        }
    )
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable("id") long id) {
        userService.remove(id);
    }

    /**
     * Get all tracked links
     *
     * @param userId user id
     * @return ListLinksResponse
     */
    @Operation(
        summary = "Get all tracked links",
        responses = {
            @ApiResponse(responseCode = "200", description = "Links received successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request params"),
        }
    )
    @GetMapping("/links")
    public ListLinksResponse getLinks(@RequestParam long userId) {
        return linkService.listAll(userId);

    }

    /**
     * Add tracking link
     *
     * @param userId         user id
     * @param addLinkRequest link to track
     * @return LinkResponse
     */
    @Operation(
        summary = "Add tracking link",
        responses = {
            @ApiResponse(responseCode = "200", description = "Link added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request params"),
        }
    )
    @PostMapping("/links")
    public LinkResponse postLink(@RequestParam long userId, @RequestBody @Valid AddLinkRequest addLinkRequest) {
        return linkService.add(userId, addLinkRequest.link());

    }

    /**
     * Delete tracking link
     *
     * @param userId            user id
     * @param removeLinkRequest link to delete
     * @return LinkResponse
     */
    @Operation(
        summary = "Delete tracking link",
        responses = {
            @ApiResponse(responseCode = "200", description = "Link deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request params"),
            @ApiResponse(responseCode = "404", description = "Link not found")
        }
    )
    @DeleteMapping("/links")
    public LinkResponse deleteLink(
        @RequestParam long userId,
        @RequestBody @Valid RemoveLinkRequest removeLinkRequest
    ) {
        return linkService.remove(userId, removeLinkRequest.link());
    }

}


package edu.java.controller;

import edu.java.dto.requests.AddLinkRequest;
import edu.java.dto.requests.RemoveLinkRequest;
import edu.java.dto.responses.LinkResponse;
import edu.java.dto.responses.ListLinksResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScrapperController {
    /**
     * Register chat
     *
     * @param id telegram chat id
     */
    @Operation(
        summary = "Register chat",
        responses = {
            @ApiResponse(responseCode = "200", description = "Chat registered"),
            @ApiResponse(responseCode = "400", description = "Invalid request params"),
        }
    )
    @PostMapping("/tg-chat/{id}")
    public void postTgChat(@PathVariable long id) {
    }

    /**
     * Delete chat
     *
     * @param id telegram chat id
     */
    @Operation(
        summary = "Delete chat",
        responses = {
            @ApiResponse(responseCode = "200", description = "Chat successfully deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid request params"),
            @ApiResponse(responseCode = "404", description = "Chat not found")
        }
    )
    @DeleteMapping("/tg-chat/{id}")
    public void deleteTgChat(@PathVariable long id) {
    }

    /**
     * Get all tracked links
     *
     * @param tgChatId telegram chat id
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
    public ListLinksResponse getLinks(@RequestHeader long tgChatId) {
        return new ListLinksResponse(null, 0);
    }

    /**
     * Add tracking link
     *
     * @param tgChatId       telegram chat id
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
    public LinkResponse postLink(@RequestHeader long tgChatId, @RequestBody AddLinkRequest addLinkRequest) {
        return new LinkResponse(0, null);
    }

    /**
     * Delete tracking link
     *
     * @param tgChatId          telegram chat id
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
    public LinkResponse deleteLink(@RequestHeader long tgChatId, @RequestBody RemoveLinkRequest removeLinkRequest) {
        return new LinkResponse(0, null);
    }

}


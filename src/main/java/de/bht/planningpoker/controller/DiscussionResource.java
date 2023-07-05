package de.bht.planningpoker.controller;

import de.bht.planningpoker.controller.dto.*;
import de.bht.planningpoker.service.exception.ServiceException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Discussion", description = "All about discussions")
@RequestMapping(value = DiscussionResource.BASE_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public interface DiscussionResource {

    String BASE_URL = "/api/discussion";

    @GetMapping
    @ResponseBody
    @Operation(summary = "Returns the discussion")
    @Parameter(name = "sessionId", description = "Session ID of the discussion")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Discussion found", content = @Content(schema = @Schema(implementation = DiscussionDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid session ID", content = @Content),
            @ApiResponse(responseCode = "401", description = "User not authenticated", content = @Content)
    })
    @SecurityRequirement(name = "bearer-auth")
    ResponseEntity<DiscussionDto> getSingle(@RequestParam(required = true) @UUID(message = "The session id must be a UUID") String sessionId) throws ServiceException;

    @PostMapping(value = "/start")
    @ResponseBody
    @Operation(summary = "Start a discussion")
    @Parameter(name = "sessionId", description = "Session ID of the discussion")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Discussion started", content = @Content(schema = @Schema(implementation = DiscussionDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid session ID", content = @Content),
            @ApiResponse(responseCode = "401", description = "User not authenticated", content = @Content),
            @ApiResponse(responseCode = "405", description = "Invalid input", content = @Content)
    })
    @SecurityRequirement(name = "bearer-auth")
    ResponseEntity<DiscussionDto> startDiscussion(@RequestParam(required = true) @UUID(message = "The session id must be a UUID") String sessionId, @RequestBody @Valid DiscussionStartDto dto) throws ServiceException;

    @PostMapping(value = "/end")
    @ResponseBody
    @Operation(summary = "End a discussion")
    @Parameter(name = "sessionId", description = "Session ID of the discussion")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Discussion ended", content = @Content(schema = @Schema(implementation = DiscussionDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid session ID", content = @Content),
            @ApiResponse(responseCode = "401", description = "User not authenticated", content = @Content),
            @ApiResponse(responseCode = "405", description = "Invalid input", content = @Content)
    })
    @SecurityRequirement(name = "bearer-auth")
    ResponseEntity<DiscussionDto> endDiscussion(@RequestParam(required = true) @UUID(message = "The session id must be a UUID") String sessionId) throws ServiceException;

    @PostMapping(value = "/post")
    @ResponseBody
    @Operation(summary = "Create a discussion post")
    @Parameter(name = "sessionId", description = "Session ID of the discussion")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Discussion post created", content = @Content(schema = @Schema(implementation = DiscussionDto.DiscussionPostDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid session ID", content = @Content),
            @ApiResponse(responseCode = "401", description = "User not authenticated", content = @Content),
            @ApiResponse(responseCode = "405", description = "Invalid input", content = @Content)
    })
    @SecurityRequirement(name = "bearer-auth")
    ResponseEntity<DiscussionDto.DiscussionPostDto> createPost(@RequestParam(required = true) @UUID(message = "The session id must be a UUID") String sessionId, @RequestBody @Valid DiscussionPostCreateDto dto) throws ServiceException;

}

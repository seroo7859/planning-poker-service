package de.bht.planningpoker.controller;

import de.bht.planningpoker.controller.dto.SessionCreateDto;
import de.bht.planningpoker.controller.dto.SessionDto;
import de.bht.planningpoker.controller.dto.SessionJoinDto;
import de.bht.planningpoker.controller.dto.SessionPageDto;
import de.bht.planningpoker.model.User;
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
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Session", description = "All about sessions")
@RequestMapping(value = SessionResource.BASE_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public interface SessionResource {

    String BASE_URL = "/api/session";
    int DEFAULT_PAGE_NUMBER = 0;
    int DEFAULT_PAGE_SIZE = 10;

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Returns a paginated list of sessions")
    ResponseEntity<SessionPageDto> getPage(
            @PageableDefault(page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "id", direction = Sort.Direction.ASC),
                    @SortDefault(sort = "createdAt", direction = Sort.Direction.ASC)
            })
            @ParameterObject Pageable pageable) throws ServiceException;

    @GetMapping(value = "/{id}")
    @ResponseBody
    @Operation(summary = "Returns a session by public ID")
    @Parameter(name = "id", description = "Public ID of the session")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Session found", content = @Content(schema = @Schema(implementation = SessionDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid ID", content = @Content),
            @ApiResponse(responseCode = "401", description = "User not authenticated", content = @Content),
            @ApiResponse(responseCode = "404", description = "Session not found", content = @Content)
    })
    @SecurityRequirement(name = "bearer-auth")
    ResponseEntity<SessionDto> getSingle(@PathVariable(required = true) @UUID(message = "The session id must be a UUID") String id) throws ServiceException;

    @PostMapping
    @ResponseBody
    @Operation(summary = "Create a session")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Session created", content = @Content(schema = @Schema(implementation = SessionDto.class))),
            @ApiResponse(responseCode = "405", description = "Invalid input", content = @Content)
    })
    ResponseEntity<SessionDto> create(@RequestBody @Valid SessionCreateDto dto) throws ServiceException;

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a session by public ID")
    @Parameter(name = "id", description = "Public ID of the session")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Session deleted", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid ID", content = @Content),
            @ApiResponse(responseCode = "401", description = "User not authenticated", content = @Content),
            @ApiResponse(responseCode = "404", description = "Session not found", content = @Content)
    })
    @SecurityRequirement(name = "bearer-auth")
    void delete(@PathVariable(required = true) @UUID(message = "The session id must be a UUID") String id) throws ServiceException;

    @PostMapping("/{id}/join")
    @ResponseBody
    @Operation(summary = "Join a session by public ID")
    @Parameter(name = "id", description = "Public ID of the session")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Session joined", content = @Content(schema = @Schema(implementation = SessionDto.class))),
            @ApiResponse(responseCode = "405", description = "Invalid input", content = @Content)
    })
    ResponseEntity<SessionDto> join(@PathVariable(required = true) @UUID(message = "The session id must be a UUID") String id, @RequestBody @Valid SessionJoinDto dto) throws ServiceException;

    @PostMapping("/{id}/leave")
    @ResponseBody
    @Operation(summary = "Leave a session by public ID")
    @Parameter(name = "id", description = "Public ID of the session")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Session leaved", content = @Content(schema = @Schema(implementation = SessionDto.class))),
            @ApiResponse(responseCode = "401", description = "User not authenticated", content = @Content)
    })
    @SecurityRequirement(name = "bearer-auth")
    ResponseEntity<SessionDto> leave(@PathVariable(required = true) @UUID(message = "The session id must be a UUID") String id, @AuthenticationPrincipal(expression = "user") User user) throws ServiceException;

}

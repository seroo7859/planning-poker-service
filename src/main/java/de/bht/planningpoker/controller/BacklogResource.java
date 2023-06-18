package de.bht.planningpoker.controller;

import de.bht.planningpoker.controller.dto.*;
import de.bht.planningpoker.service.exception.ServiceException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.hibernate.validator.constraints.UUID;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Backlog", description = "All about backlogs")
@RequestMapping(value = BacklogResource.BASE_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public interface BacklogResource {

    String BASE_URL = "/api/backlog";

    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    @Operation(summary = "Import a backlog from CSV file")
    @Parameter(name = "sessionId", description = "Session ID of the backlog")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully imported the backlog", content = @Content(schema = @Schema(implementation = BacklogDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid session ID", content = @Content),
            @ApiResponse(responseCode = "401", description = "User not authenticated", content = @Content),
            @ApiResponse(responseCode = "404", description = "Backlog not found", content = @Content),
            @ApiResponse(responseCode = "405", description = "Invalid input", content = @Content)
    })
    @SecurityRequirement(name = "bearer-auth")
    ResponseEntity<BacklogDto> upload(@RequestParam(required = true) @UUID(message = "The session id must be a UUID") String sessionId, @Parameter(description = "The CSV file with the backlog") @RequestPart(value = "file", required = true) MultipartFile file) throws ServiceException;

    @GetMapping(value = "/export", produces = "text/csv")
    @ResponseBody
    @Operation(summary = "Export a backlog as CSV file")
    @Parameter(name = "sessionId", description = "Session ID of the backlog")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully exported the backlog", content = @Content(mediaType = "text/csv", schema = @Schema(implementation = Resource.class), examples = { @ExampleObject(value = "Number;Title;Description;Estimation;Priority\nUS007;Create Account;As an unauthorized User I want to create a new account;3;1\n") })),
            @ApiResponse(responseCode = "400", description = "Invalid session ID", content = @Content),
            @ApiResponse(responseCode = "401", description = "User not authenticated", content = @Content),
            @ApiResponse(responseCode = "404", description = "Backlog not found", content = @Content)
    })
    @SecurityRequirement(name = "bearer-auth")
    ResponseEntity<Resource> download(@RequestParam(required = true) @UUID(message = "The session id must be a UUID") String sessionId) throws ServiceException;

    @GetMapping
    @ResponseBody
    @Operation(summary = "Returns a backlog")
    @Parameter(name = "sessionId", description = "Session ID of the backlog")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Backlog found", content = @Content(schema = @Schema(implementation = BacklogDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid session ID", content = @Content),
            @ApiResponse(responseCode = "401", description = "User not authenticated", content = @Content),
            @ApiResponse(responseCode = "404", description = "Backlog not found", content = @Content)
    })
    @SecurityRequirement(name = "bearer-auth")
    ResponseEntity<BacklogDto> getSingle(@RequestParam(required = true) @UUID(message = "The session id must be a UUID") String sessionId) throws ServiceException;

    @PatchMapping("/rename")
    @ResponseBody
    @Operation(summary = "Rename a backlog")
    @Parameter(name = "sessionId", description = "Session ID of the backlog")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Backlog renamed", content = @Content(schema = @Schema(implementation = BacklogDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid session ID", content = @Content),
            @ApiResponse(responseCode = "401", description = "User not authenticated", content = @Content),
            @ApiResponse(responseCode = "404", description = "Backlog not found", content = @Content)
    })
    @SecurityRequirement(name = "bearer-auth")
    ResponseEntity<BacklogDto> rename(@RequestParam(required = true) @UUID(message = "The session id must be a UUID") String sessionId, @RequestBody @Valid BacklogRenameDto dto) throws ServiceException;

    @DeleteMapping("/clear")
    @Operation(summary = "Delete all backlog item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Backlog items removed", content = @Content),
            @ApiResponse(responseCode = "401", description = "User not authenticated", content = @Content),
            @ApiResponse(responseCode = "404", description = "Backlog not found", content = @Content)
    })
    @SecurityRequirement(name = "bearer-auth")
    void clear(@RequestParam(required = true) @UUID(message = "The session id must be a UUID") String sessionId) throws ServiceException;

    @GetMapping("/item/{number}")
    @ResponseBody
    @Operation(summary = "Returns a backlog item by number")
    @Parameter(name = "number", description = "Number of the backlog item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Backlog item removed", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid number", content = @Content),
            @ApiResponse(responseCode = "401", description = "User not authenticated", content = @Content),
            @ApiResponse(responseCode = "404", description = "Backlog item not found", content = @Content)
    })
    @SecurityRequirement(name = "bearer-auth")
    ResponseEntity<BacklogDto.BacklogItemDto> getItem(@RequestParam(required = true) @UUID(message = "The session id must be a UUID") String sessionId, @PathVariable(required = true) String number) throws ServiceException;

    @PostMapping(value = "/item")
    @ResponseBody
    @Operation(summary = "Add a backlog item")
    @Parameter(name = "sessionId", description = "Session ID of the backlog")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Backlog item added", content = @Content(schema = @Schema(implementation = BacklogDto.BacklogItemDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid session ID", content = @Content),
            @ApiResponse(responseCode = "401", description = "User not authenticated", content = @Content),
            @ApiResponse(responseCode = "404", description = "Backlog not found", content = @Content),
            @ApiResponse(responseCode = "405", description = "Invalid input", content = @Content)
    })
    @SecurityRequirement(name = "bearer-auth")
    ResponseEntity<BacklogDto.BacklogItemDto> addItem(@RequestParam(required = true) @UUID(message = "The session id must be a UUID") String sessionId, @RequestBody @Valid BacklogItemAddDto dto) throws ServiceException;

    @DeleteMapping("/item/{number}")
    @Operation(summary = "Delete a backlog item by number")
    @Parameter(name = "number", description = "Number of the backlog item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Backlog item removed", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid number", content = @Content),
            @ApiResponse(responseCode = "401", description = "User not authenticated", content = @Content),
            @ApiResponse(responseCode = "404", description = "Backlog item not found", content = @Content)
    })
    @SecurityRequirement(name = "bearer-auth")
    void removeItem(@RequestParam(required = true) @UUID(message = "The session id must be a UUID") String sessionId, @PathVariable(required = true) String number) throws ServiceException;

    @PutMapping("/item/{number}")
    @ResponseBody
    @Operation(summary = "Update a backlog item by number")
    @Parameter(name = "number", description = "Number of the backlog item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Backlog item updated", content = @Content(schema = @Schema(implementation = BacklogDto.BacklogItemDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid number", content = @Content),
            @ApiResponse(responseCode = "401", description = "User not authenticated", content = @Content),
            @ApiResponse(responseCode = "404", description = "Backlog item not found", content = @Content)
    })
    @SecurityRequirement(name = "bearer-auth")
    ResponseEntity<BacklogDto.BacklogItemDto> updateItem(@RequestParam(required = true) @UUID(message = "The session id must be a UUID") String sessionId, @PathVariable(required = true) String number, @RequestBody @Valid BacklogItemUpdateDto dto) throws ServiceException;

    @PostMapping("/item/{number}/move")
    @ResponseBody
    @Operation(summary = "Move a backlog item")
    @Parameter(name = "number", description = "Number of the backlog item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Backlog item moved", content = @Content(array = @ArraySchema(schema = @Schema(implementation = BacklogDto.BacklogItemDto.class), uniqueItems = true))),
            @ApiResponse(responseCode = "400", description = "Invalid number", content = @Content),
            @ApiResponse(responseCode = "401", description = "User not authenticated", content = @Content),
            @ApiResponse(responseCode = "404", description = "Backlog item not found", content = @Content)
    })
    @SecurityRequirement(name = "bearer-auth")
    ResponseEntity<List<BacklogDto.BacklogItemDto>> moveItem(@RequestParam(required = true) @UUID(message = "The session id must be a UUID") String sessionId, @PathVariable(required = true) String number, @RequestBody @Valid BacklogItemMoveDto dto) throws ServiceException;

}

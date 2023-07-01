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

@Tag(name = "Team", description = "All about teams")
@RequestMapping(value = TeamResource.BASE_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public interface TeamResource {

    String BASE_URL = "/api/team";

    @GetMapping
    @ResponseBody
    @Operation(summary = "Returns a team")
    @Parameter(name = "sessionId", description = "Session ID of the team")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Team found", content = @Content(schema = @Schema(implementation = TeamDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid session ID", content = @Content),
            @ApiResponse(responseCode = "401", description = "User not authenticated", content = @Content),
            @ApiResponse(responseCode = "404", description = "Team not found", content = @Content)
    })
    @SecurityRequirement(name = "bearer-auth")
    ResponseEntity<TeamDto> getSingle(@RequestParam(required = true) @UUID(message = "The session id must be a UUID") String sessionId) throws ServiceException;

    @PatchMapping("/rename")
    @ResponseBody
    @Operation(summary = "Rename a team")
    @Parameter(name = "sessionId", description = "Session ID of the team")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Team renamed", content = @Content(schema = @Schema(implementation = TeamDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid session ID", content = @Content),
            @ApiResponse(responseCode = "401", description = "User not authenticated", content = @Content),
            @ApiResponse(responseCode = "404", description = "Team not found", content = @Content)
    })
    @SecurityRequirement(name = "bearer-auth")
    ResponseEntity<TeamDto> rename(@RequestParam(required = true) @UUID(message = "The session id must be a UUID") String sessionId, @RequestBody @Valid TeamRenameDto dto) throws ServiceException;

}

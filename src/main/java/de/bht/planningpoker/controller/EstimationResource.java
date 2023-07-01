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

@Tag(name = "Estimation", description = "All about estimations")
@RequestMapping(value = EstimationResource.BASE_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public interface EstimationResource {

    String BASE_URL = "/api/estimation";

    @PostMapping(value = "/round/start")
    @ResponseBody
    @Operation(summary = "Start a estimation round")
    @Parameter(name = "sessionId", description = "Session ID of the estimation round")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Estimation round started", content = @Content(schema = @Schema(implementation = EstimationRoundDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid session ID", content = @Content),
            @ApiResponse(responseCode = "401", description = "User not authenticated", content = @Content),
            @ApiResponse(responseCode = "404", description = "Backlog item not found", content = @Content),
            @ApiResponse(responseCode = "405", description = "Invalid input", content = @Content)
    })
    @SecurityRequirement(name = "bearer-auth")
    ResponseEntity<EstimationRoundDto> startRound(@RequestParam(required = true) @UUID(message = "The session id must be a UUID") String sessionId, @RequestBody @Valid EstimationRoundStartDto dto) throws ServiceException;

    @PostMapping(value = "/round/next")
    @ResponseBody
    @Operation(summary = "Start the next estimation round")
    @Parameter(name = "sessionId", description = "Session ID of the estimation round")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Estimation round started", content = @Content(schema = @Schema(implementation = EstimationRoundDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid session ID", content = @Content),
            @ApiResponse(responseCode = "401", description = "User not authenticated", content = @Content),
            @ApiResponse(responseCode = "405", description = "Invalid input", content = @Content)
    })
    @SecurityRequirement(name = "bearer-auth")
    ResponseEntity<EstimationRoundDto> nextRound(@RequestParam(required = true) @UUID(message = "The session id must be a UUID") String sessionId) throws ServiceException;

    @PostMapping(value = "/round/finish")
    @ResponseBody
    @Operation(summary = "Finish the estimation round")
    @Parameter(name = "sessionId", description = "Session ID of the estimation round")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estimation round finished", content = @Content(schema = @Schema(implementation = EstimationRoundDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid session ID", content = @Content),
            @ApiResponse(responseCode = "401", description = "User not authenticated", content = @Content),
            @ApiResponse(responseCode = "405", description = "Invalid input", content = @Content)
    })
    @SecurityRequirement(name = "bearer-auth")
    ResponseEntity<EstimationRoundDto> finishRound(@RequestParam(required = true) @UUID(message = "The session id must be a UUID") String sessionId) throws ServiceException;

    @GetMapping(value = "/round/info")
    @ResponseBody
    @Operation(summary = "Returns the current estimation round")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estimation round found", content = @Content(schema = @Schema(implementation = EstimationRoundDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid session ID", content = @Content),
            @ApiResponse(responseCode = "401", description = "User not authenticated", content = @Content)
    })
    @SecurityRequirement(name = "bearer-auth")
    ResponseEntity<EstimationRoundDto> getInfo(@RequestParam(required = true) @UUID(message = "The session id must be a UUID") String sessionId) throws ServiceException;

    @GetMapping(value = "/round/summary")
    @ResponseBody
    @Operation(summary = "Returns a summary of the estimation round")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estimation summary found", content = @Content(schema = @Schema(implementation = EstimationSummaryDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid session ID", content = @Content),
            @ApiResponse(responseCode = "401", description = "User not authenticated", content = @Content)
    })
    @SecurityRequirement(name = "bearer-auth")
    ResponseEntity<EstimationSummaryDto> getSummary(@RequestParam(required = true) @UUID(message = "The session id must be a UUID") String sessionId) throws ServiceException;

    @PostMapping(value = "/give")
    @ResponseBody
    @Operation(summary = "Give an estimation")
    @Parameter(name = "sessionId", description = "Session ID of the estimation round")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Estimation given", content = @Content(schema = @Schema(implementation = EstimationRoundDto.EstimationDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid session ID", content = @Content),
            @ApiResponse(responseCode = "401", description = "User not authenticated", content = @Content),
            @ApiResponse(responseCode = "405", description = "Invalid input", content = @Content)
    })
    @SecurityRequirement(name = "bearer-auth")
    ResponseEntity<EstimationRoundDto.EstimationDto> giveEstimation(@RequestParam(required = true) @UUID(message = "The session id must be a UUID") String sessionId, @RequestBody @Valid EstimationGiveDto dto) throws ServiceException;

}

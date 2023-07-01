package de.bht.planningpoker.controller;

import de.bht.planningpoker.controller.dto.UserDto;
import de.bht.planningpoker.service.exception.ServiceException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Tag(name = "User", description = "All about users")
@RequestMapping(value = UserResource.BASE_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public interface UserResource {

    String BASE_URL = "/api/user";

    @GetMapping(value = "/info")
    @ResponseBody
    @Operation(summary = "Returns the current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found", content = @Content(schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "401", description = "User not authenticated", content = @Content)
    })
    @SecurityRequirement(name = "bearer-auth")
    ResponseEntity<UserDto> getInfo() throws ServiceException;

}

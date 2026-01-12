package com.torresj.infosas.controllers;

import com.torresj.infosas.dtos.AuthDto;
import com.torresj.infosas.dtos.AuthResponseDto;
import com.torresj.infosas.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Infosas Login")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = AuthResponseDto.class)))
                            }),
            })
    @PostMapping()
    public ResponseEntity<AuthResponseDto> getMetrics(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Login DTO with client and password",
                    required = true,
                    content = @Content(schema = @Schema(implementation = AuthDto.class)))
            @RequestBody AuthDto authDto
    ){
        log.info("INFOSAS Login attempt with client {}", authDto.id());
        var response = authService.authenticate(authDto.id(), authDto.password());
        log.info("INFOSAS Login success");
        return ResponseEntity.ok(response);
    }
}

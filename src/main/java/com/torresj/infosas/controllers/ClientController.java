package com.torresj.infosas.controllers;

import com.torresj.infosas.dtos.ClientDto;
import com.torresj.infosas.dtos.NewClientRequestDto;
import com.torresj.infosas.services.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/clients")
@Slf4j
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final PasswordEncoder encoder;

    @Operation(summary = "Get API clients")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = ClientDto.class)))
                            }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
            })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ClientDto>> getApiClients(){
        log.info("Getting API clients");
        var clients = clientService.get();
        log.info("Clients found: {}", clients.size());
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Get API client by ID")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Client found",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ClientDto.class))
                            }),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
            })
    ResponseEntity<ClientDto> getApiClientById(@Parameter(description = "Client id") @PathVariable long id) {
        log.info("Getting APIT Client by id {}", id);
        var client = clientService.get(id);
        log.info("Client found");
        return ResponseEntity.ok(client);
    }

    @Operation(summary = "Create new API client")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Created",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ClientDto.class))
                            }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
            })
    @PostMapping
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<ClientDto> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "New client",
                    required = true,
                    content = @Content(schema = @Schema(implementation = NewClientRequestDto.class)))
            @RequestBody NewClientRequestDto request) {
        log.info("Creating new client {}", request.name());
        ClientDto client = clientService.save(
                request.id(),
                request.name(),
                encoder.encode(request.password()),
                request.role()
        );
        log.info("Client created {}", client);
        return ResponseEntity.ok(client);
    }

    @Operation(summary = "Delete client")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Deleted"
                    ),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
            })
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Void> deleteUser(
            @Parameter(description = "Client id") @PathVariable long id
    ) {
        log.info("Deleting client {}", id);
        clientService.delete(id);
        log.info("Client {} deleted", id);
        return ResponseEntity.ok(null);
    }
}

package com.torresj.infosas.controllers;

import com.torresj.infosas.dtos.StaffDto;
import com.torresj.infosas.services.StaffService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Set;

@RestController
@RequestMapping("/v1/staff")
@Slf4j
@RequiredArgsConstructor
public class StaffController {
    private final StaffService staffService;

    @Operation(summary = "Get SAS Staff by surname filter")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = StaffDto.class)))
                            }),
            })
    @GetMapping
    public ResponseEntity<Set<StaffDto>> getOpeNurses(
            @Parameter(description = "Filter by surname") @RequestParam String filter
    ) throws IOException {
        log.info("Getting SAS staff by filter {}", filter);
        var staff = staffService.getStaffsBySurname(filter);
        log.info("Staff found: {}", staff.size());
        return ResponseEntity.ok(staff);
    }
}

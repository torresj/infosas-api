package com.torresj.infosas.controllers;

import com.torresj.infosas.dtos.EnrichedStaffDto;
import com.torresj.infosas.dtos.EnrichedStaffExamDto;
import com.torresj.infosas.dtos.StaffDto;
import com.torresj.infosas.enums.StaffExamType;
import com.torresj.infosas.exceptions.StaffNotFoundException;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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
    public ResponseEntity<Set<StaffDto>> getStaff(
            @Parameter(description = "Filter by surname") @RequestParam String filter
    ){
        log.info("Getting SAS staff by filter {}", filter);
        var staff = staffService.getStaffsBySurname(filter);
        log.info("Staff found: {}", staff.size());
        return ResponseEntity.ok(staff);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get SAS staff by ID")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Staff found",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = EnrichedStaffDto.class))
                            }),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
            })
    ResponseEntity<EnrichedStaffDto> getStaffById(@Parameter(description = "Staff id") @PathVariable long id)
            throws StaffNotFoundException {
        log.info("Getting SAS staff by id {}", id);
        var staff = staffService.getStaffById(id);
        log.info("Staff found");
        return ResponseEntity.ok(staff);
    }

    @Operation(summary = "Get SAS Staff for exams by surname filter")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = EnrichedStaffExamDto.class)))
                            }),
            })
    @GetMapping("/exams")
    public ResponseEntity<List<EnrichedStaffExamDto>> getExams(
            @Parameter(description = "Filter by surname") @RequestParam String filter,
            @Parameter(description = "type") @RequestParam StaffExamType type
    ){
        log.info("Getting SAS staff exams by filter {}", filter);
        var staff = staffService.getEnrichedStaffExam(filter, type);
        log.info("Staff found: {}", staff.size());
        return ResponseEntity.ok(staff);
    }
}

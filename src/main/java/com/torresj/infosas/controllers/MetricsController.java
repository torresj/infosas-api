package com.torresj.infosas.controllers;

import com.torresj.infosas.dtos.MetricsDto;
import com.torresj.infosas.services.MetricsService;
import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/metrics")
@Slf4j
@RequiredArgsConstructor
public class MetricsController {

    private final MetricsService metricsService;

    @Operation(summary = "Get INFOSAS metrics")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = MetricsDto.class)))
                            }),
            })
    @GetMapping()
    public ResponseEntity<MetricsDto> getMetrics(){
        log.info("Getting INFOSAS metrics");
        var metrics = metricsService.getMetrics();
        log.info("INFOSAS metrics found: {}", metrics);
        return ResponseEntity.ok(metrics);
    }
}

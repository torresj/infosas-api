package com.torresj.infosas.exceptions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ProblemDetail;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {
    private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

    @Test
    void handleStaffNotFoundException_shouldThrowNotFoundException() {
        String message = "Could not find staff with id 1";

        StaffNotFoundException ex = new StaffNotFoundException(1L);

        ProblemDetail response = exceptionHandler.handleStaffNotFound(ex);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(NOT_FOUND.value());
        assertThat(response.getDetail()).isEqualTo(message);
        assertThat(response.getTitle()).isEqualTo(message);
    }
}